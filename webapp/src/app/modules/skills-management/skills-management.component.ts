import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { NbDialogService } from '@nebular/theme';

import { SkillService } from '../../services/skill.service';

import { CreateSkillDialogComponent } from './create-skill-dialog/create-skill-dialog.component';

import { formatSkillType } from '../../utils/formaters.util';

import { ISkill } from '../../models/skill.model';

import { SkillTypeEnum } from '../../enumerations/skill-type.enum';


@Component({
    templateUrl: './skills-management.component.html',
    styleUrls: [ './skills-management.component.scss' ]
})
export class SkillsManagementComponent implements OnInit {

    skillTypes = Object.values(SkillTypeEnum).map(skillType => ({
        name: formatSkillType(skillType) + ' skills',
        value: skillType
    }));

    selectedSkillType = SkillTypeEnum.HUMAN;

    skills: Array<ISkill> = [];
    displayedSkills: Array<ISkill> = [];
    loading=true
    searchFormGroup: FormGroup = this.formBuilder.group({
        keyword: [ '' ]
    });

    constructor(
        private formBuilder: FormBuilder,
        private dialogService: NbDialogService,
        private skillService: SkillService
    ) { }

    ngOnInit() {
        this.loadSkills();
    }

    loadSkills() {
        this.skillService.getSkills(this.selectedSkillType).subscribe(
            (skills: Array<ISkill>) => {
                this.skills = skills;
                this.displayedSkills = skills;
                this.searchFormGroup.reset();
                this.loading=false
              
            }
        );
    }

    onAccordionItemClick(skillType: SkillTypeEnum) {
        if (skillType === this.selectedSkillType) {
            this.selectedSkillType = undefined;
        } else {
            this.selectedSkillType = skillType;
        }
        if (this.selectedSkillType) {
            this.loadSkills();
        }
    }

    onSearch() {
        const keyword = this.searchFormGroup.get('keyword').value.trim().toLowerCase();
        this.displayedSkills = this.skills.filter(
            skill => skill.label.toLowerCase().includes(keyword)
        );
    }

    onAddSkill() {
        this.dialogService.open(
            CreateSkillDialogComponent,
            {
                context: {
                    data: { skillType: this.selectedSkillType }
                }
            }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.loadSkills();
                }
            }
        );
    }

}
