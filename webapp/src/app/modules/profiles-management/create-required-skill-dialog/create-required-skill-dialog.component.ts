import { Component, OnInit, OnDestroy, Input, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import * as slider from 'nouislider';

import { FormService } from '../../../services/form.service';
import { SkillService } from '../../../services/skill.service';
import { RequiredSkillService } from '../../../services/required-skill.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';
import { formatSkillLevel, formatSkillWeight, skillLevelFromValue, skillWeightFromValue } from '../../../utils/formaters.util';

import { ISkill } from '../../../models/skill.model';
import { IRequiredSkill } from '../../../models/required-skill.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { SkillLevelEnum } from '../../../enumerations/skill-level.enum';
import { SkillWeightEnum } from 'src/app/enumerations/skill-weight.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './create-required-skill-dialog.component.html',
    styleUrls: [ './create-required-skill-dialog.component.scss' ]
})
export class CreateRequiredSkillDialogComponent implements OnInit, OnDestroy {

    @Input()
    data: any;

    newSkill = false;

    searchFormGroup: FormGroup = this.formBuilder.group({
        keyword:        [ '' ]
    });

    createSkillFormGroup: FormGroup = this.formBuilder.group({
        label:          [ '', Validators.required, blankValidator() ]
    });

    skills: Array<ISkill> = [];
    displayedSkills: Array<ISkill> = [];

    selectedSkill: ISkill;

    @ViewChild('skillLevelSliderElement', { static: false }) skillLevelSliderElement: ElementRef;
    @ViewChild('skillWeightSliderElement', { static: false }) skillWeightSliderElement: ElementRef;

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

    skillLevel = {
        name: formatSkillLevel(SkillLevelEnum.BEGINNER),
        value: SkillLevelEnum.BEGINNER
    };

    skillWeight = {
        name: formatSkillWeight(SkillWeightEnum.LOW),
        value: SkillWeightEnum.LOW
    };

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private changeDetectorRef: ChangeDetectorRef,
        private dialogRef: NbDialogRef<CreateRequiredSkillDialogComponent>,
        public formService: FormService,
        private skillService: SkillService,
        private requiredSkillService: RequiredSkillService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.loadSkills();
        this.initializeSliders();
    }

    loadSkills() {
        this.skillService.getSkills(this.data.skillType).subscribe(
            (skills: Array<ISkill>) => {
                this.skills = skills.filter(
                    skill => !this.data.unavailableSkills.find(
                        (unavailableSkill: ISkill) => skill.id === unavailableSkill.id));
                this.displayedSkills = [ ...this.skills ];
                this.searchFormGroup.reset();
            }
        );
    }

    onSearch() {
        const keyword = this.searchFormGroup.get('keyword').value.trim().toLowerCase();
        this.displayedSkills = this.skills.filter(
            skill => skill.label.toLowerCase().includes(keyword)
        );
    }

    onSkillSelection(skill: ISkill) {
        if (skill.id === this.selectedSkill?.id) {
            this.selectedSkill = undefined;
        } else {
            this.selectedSkill = skill;
        }
    }

    onChangeSkillSelectionMode() {
        this.newSkill = !this.newSkill;
        this.createSkillFormGroup.reset();
        this.searchFormGroup.reset();
        this.displayedSkills = [ ...this.skills ];
        this.selectedSkill = undefined;
    }

    initializeSliders() {
        this.changeDetectorRef.detectChanges();
        slider.create(this.skillLevelSliderElement.nativeElement, this.sliderConfiguration);
        this.skillLevelSliderElement.nativeElement.noUiSlider.on(
            'change',
            (values: Array<number>) => {
                this.skillLevel.value = skillLevelFromValue(values[0]);
                this.skillLevel.name = formatSkillLevel(this.skillLevel.value);
            }
        );
        slider.create(this.skillWeightSliderElement.nativeElement, this.sliderConfiguration);
        this.skillWeightSliderElement.nativeElement.noUiSlider.on(
            'change',
            (values: Array<number>) => {
                this.skillWeight.value = skillWeightFromValue(values[0]);
                this.skillWeight.name = formatSkillWeight(this.skillWeight.value);
            }
        );
    }

    onCancel() {
        this.dialogRef.close(false);
    }

    onSubmit() {
        if (this.newSkill) {
            this.loadingState = true;
            const skill: ISkill = this.createSkillFormGroup.value;
            skill.skillType = this.data.skillType;
            this.skillService.createSkill(skill).subscribe(
                (createdSkill: ISkill) => {
                    this.createRequiredSkill(createdSkill);
                },
                (error: IErrorResponse) => {
                    this.loadingState = false;
                    switch (error.status) {
                        case 400:
                            this.toastrService.showStatusToastr(
                                'Something is not right, please verify '
                                + 'your input and try again later',
                                ToastrStatusEnum.DANGER);
                            break;
                        case 409:
                            this.createSkillFormGroup.get('label')
                                .setErrors({ notUnique: true });
                            break;
                    }
                }
            );
        } else {
            this.createRequiredSkill(this.selectedSkill);
        }
    }

    createRequiredSkill(s: ISkill) {
        this.loadingState = true;
        const requiredSkill: IRequiredSkill = {
            id: '',
            level: this.skillLevel.value,
            weight: this.skillWeight.value,
            profile: this.data.profile,
            skill: s
        };
        this.requiredSkillService.createRequiredSkill(requiredSkill).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Required skill added successfully',
                    ToastrStatusEnum.SUCCESS);
                this.dialogRef.close(true);
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                switch (error.status) {
                    case 400:
                        this.toastrService.showStatusToastr(
                            'Something is not right, please verify '
                            + 'your input and try again later',
                            ToastrStatusEnum.DANGER);
                        break;
                    case 409:
                        this.toastrService.showStatusToastr(
                            'This profile requires already '
                            + 'the very same skill',
                            ToastrStatusEnum.DANGER);
                        this.dialogRef.close(true);
                        break;
                }
            }
        );
    }

    ngOnDestroy() {
        if (this.skillLevelSliderElement) {
            this.skillLevelSliderElement.nativeElement.noUiSlider.off();
        }
        if (this.skillWeightSliderElement) {
            this.skillWeightSliderElement.nativeElement.noUiSlider.off();
        }
    }

}
