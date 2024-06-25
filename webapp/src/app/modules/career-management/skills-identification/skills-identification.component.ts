import { Component, OnInit, ViewChildren, ElementRef, ChangeDetectorRef, QueryList } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import * as slider from 'nouislider';

import { SkillService } from '../../../services/skill.service';
import { StorageService } from '../../../services/storage.service';
import { AcquiredSkillService } from '../../../services/acquired-skill.service';
import { ToastrService } from '../../../services/toastr.service';

import { formatSkillType, valueOfSkillLevel, skillLevelFromValue } from '../../../utils/formaters.util';

import { ISkill } from '../../../models/skill.model';
import { IAcquiredSkill } from '../../../models/acquired-skill.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { SkillTypeEnum } from '../../../enumerations/skill-type.enum';
import { StorageKeyEnum } from '../../../enumerations/storage-key.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './skills-identification.component.html',
    styleUrls: [ './skills-identification.component.scss' ]
})
export class SkillsIdentificationComponent implements OnInit {

    collaborator: ICollaborator;

    searchSkills = false;

    skillTypes = Object.values(SkillTypeEnum).map(skillType => ({
        name: formatSkillType(skillType) + ' skills',
        value: skillType
    }));

    searchSkillsFormGroup: FormGroup = this.formBuilder.group({
        keyword:       [ '' ],
        skillType:     [ SkillTypeEnum.HUMAN ]
    });

    skills: Array<ISkill>;
    displayedSkills: Array<ISkill>;

    acquiredSkills: Array<IAcquiredSkill>;
    acquiredSkillsBySelectedSkillType: Array<IAcquiredSkill>;

    newAcquiredSkills: Array<ISkill> = [];

    @ViewChildren('newAcquiredSkillLevelSlider')
    newAcquiredSkillsLevelsSliders: QueryList<ElementRef>;

    sliderConfiguration = {
        range: {
            'min': 0,
            'max': 1
        },
        start: 0.25,
        step: 0.25,
        padding: [ 0.25, 0 ],
        connect: 'lower',
        behaviour: 'drag-tap',
        format: {
            to: (value: number) => {
                return value;
            },
            from: (value: string) => {
                return Number(value);
            }
        }
    };

    constructor(
        private formBuilder: FormBuilder,
        private changeDetectorRef: ChangeDetectorRef,
        private storageService: StorageService,
        private skillService: SkillService,
        private acquiredSkillService: AcquiredSkillService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.collaborator = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
        this.loadAcquiredSkills();
    }

    loadAcquiredSkills() {
        this.acquiredSkillService.getAcquiredSkills(this.collaborator.id).subscribe(
            (acquiredSkills: Array<IAcquiredSkill>) => {
                this.acquiredSkills = acquiredSkills.map(acquiredSkill => ({
                    ...acquiredSkill,
                    progress: acquiredSkill.progress.map(skillLevel => ({
                        ...skillLevel,
                        levelValue: valueOfSkillLevel(skillLevel.level) * 100
                    }))
                }));
                this.acquiredSkillsBySelectedSkillType = this.acquiredSkills.filter(
                    acquiredSkill => acquiredSkill.skill.skillType === this.searchSkillsFormGroup.get('skillType').value);
                this.loadSkills();
            }
        );
    }

    loadSkills() {
        this.skillService.getSkills(this.searchSkillsFormGroup.get('skillType').value).subscribe(
            (skills: Array<ISkill>) => {
                this.skills = skills.filter(
                    skill => !this.acquiredSkills.find(acquiredSkill => acquiredSkill.skill.id === skill.id) &&
                        !this.newAcquiredSkills.find(newAcquiredSkill => newAcquiredSkill.id === skill.id));
                this.displayedSkills = [ ...this.skills ];
                this.searchSkillsFormGroup.get('keyword').reset();
            }
        );
    }

    toggleSkillsKeywordSearch() {
        this.searchSkills = !this.searchSkills;
        if (!this.searchSkills) {
            this.searchSkillsFormGroup.get('keyword').reset();
            this.onSearchSkillsKeywordInputChange();
        }
    }

    onSearchSkillsKeywordInputChange() {
        let keyword = this.searchSkillsFormGroup.get('keyword').value;
        if (!keyword) {
            keyword = '';
        }
        keyword = keyword.trim().toLowerCase();
        this.displayedSkills = this.skills.filter(
            skill => skill.label.toLowerCase().includes(keyword));
    }

    onSearchSkillsSkillTypeSelectChange() {
        this.acquiredSkillsBySelectedSkillType = this.acquiredSkills.filter(
            acquiredSkill => acquiredSkill.skill.skillType === this.searchSkillsFormGroup.get('skillType').value);
        this.loadSkills();
    }

    onAddNewAcquiredSkill(skill: ISkill) {
        const indexInSkills: number = this.skills.findIndex(s => s.id === skill.id);
        this.skills.splice(indexInSkills, 1);
        const indexInDisplayedSkills: number = this.displayedSkills.findIndex(s => s.id === skill.id);
        this.displayedSkills.splice(indexInDisplayedSkills, 1);
        this.newAcquiredSkills.push(skill);
        this.changeDetectorRef.detectChanges();
        slider.create(this.newAcquiredSkillsLevelsSliders.last.nativeElement, this.sliderConfiguration);
    }

    onRemoveNewAcquiredSkill(skill: ISkill) {
        const indexInNewAcquiredSkills: number = this.newAcquiredSkills.findIndex(s => s.id === skill.id);
        this.newAcquiredSkills.splice(indexInNewAcquiredSkills, 1);
        this.skills.push(skill);
        this.displayedSkills.push(skill);
        this.changeDetectorRef.detectChanges();
    }

    onSubmit() {
        const newAcquiredSkills: Array<IAcquiredSkill> = this.newAcquiredSkillsLevelsSliders.map((elementRef: ElementRef, index: number) => ({
            collaborator: this.collaborator,
            skill: this.newAcquiredSkills[index],
            progress: [
                {
                    level: skillLevelFromValue(elementRef.nativeElement.noUiSlider.get())
                }
            ]
        }));
        this.acquiredSkillService.createAcquiredSkills(newAcquiredSkills).subscribe(
            (acquiredSkills: Array<IAcquiredSkill>) => {
                this.newAcquiredSkills = [];
                this.changeDetectorRef.detectChanges();
                this.acquiredSkills.push(...acquiredSkills.map(acquiredSkill => ({
                    ...acquiredSkill,
                    progress: acquiredSkill.progress.map(skillLevel => ({
                        ...skillLevel,
                        levelValue: valueOfSkillLevel(skillLevel.level) * 100
                    }))
                })));
                this.acquiredSkillsBySelectedSkillType = this.acquiredSkills.filter(
                    acquiredSkill => acquiredSkill.skill.skillType === this.searchSkillsFormGroup.get('skillType').value);
                this.toastrService.showStatusToastr(
                    'Your skills have been added successfully!',
                    ToastrStatusEnum.SUCCESS);
            },
            (error: IErrorResponse) => {
                if (error.status >= 400 && error.status < 500) {
                    this.toastrService.showStatusToastr(
                        'Something is not right! please try again later',
                        ToastrStatusEnum.DANGER);
                }
            }
        );
    }

}
