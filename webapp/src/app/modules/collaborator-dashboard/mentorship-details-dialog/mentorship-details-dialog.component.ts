import { Component, OnInit, OnDestroy, Input, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { NbDialogRef } from '@nebular/theme';

import * as slider from 'nouislider';

import { FormService } from '../../../services/form.service';
import { MentorshipService } from '../../../services/mentorship.service';
import { AcquiredSkillService } from '../../../services/acquired-skill.service';
import { ToastrService } from '../../../services/toastr.service';

import { formatSkillLevel, skillLevelFromValue, valueOfSkillLevel } from '../../../utils/formaters.util';
import { blankValidator } from '../../../utils/validators.util';

import { IMentorship } from '../../../models/mentorship.model';
import { IAcquiredSkillLevel } from '../../../models/acquired-skill-level.model';
import { IAcquiredSkill } from '../../../models/acquired-skill.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { MentorshipStatusEnum } from '../../../enumerations/mentorship-status.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './mentorship-details-dialog.component.html',
    styleUrls: [ './mentorship-details-dialog.component.scss' ]
})
export class MentorshipDetailsDialogComponent implements OnInit, OnDestroy {

    @Input()
    data: any;

    @ViewChild('acquiredSkillLevelSliderElement', { static: false })
    acquiredSkillLevelSliderElement: ElementRef;

    mentorshipStatus = MentorshipStatusEnum;

    sliderConfiguration = {
        range: {
            'min': 0,
            'max': 1
        },
        start: 0,
        step: 0.25,
        padding: [ 0, 0 ],
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

    acquiredSkillLevel: any = {};

    mentorshipRatingValue: number;

    get mentorshipRating() {
        return this.mentorshipRatingValue;
    }

    set mentorshipRating(mentorshipRating: number) {
        if (this.mentorshipRatingValue !== mentorshipRating) {
            this.mentorshipRatingValue = mentorshipRating;
            if (this.data.settings.isMentor) {
                if (this.data.mentorship.mentorRating !== this.mentorshipRatingValue) {
                    this.changes.mentorRating = this.mentorshipRatingValue;
                } else {
                    delete this.changes.mentorRating;
                }
            }
            if (this.data.settings.isSelf) {
                if (this.data.mentorship.menteeRating !== this.mentorshipRatingValue) {
                    this.changes.menteeRating = this.mentorshipRatingValue;
                } else {
                    delete this.changes.menteeRating;
                }
            }
        }
        if (JSON.stringify(this.changes) !== '{}') {
            this.canSubmit = this.mentorshipFormGroup.valid;
        } else {
            this.canSubmit = (this.data.acquiredSkill && this.data.acquiredSkill.progress[this.data.acquiredSkill.progress.length - 1].level !== this.acquiredSkillLevel.value) || (!this.data.acquiredSkill && this.acquiredSkillLevel.value);
        }
    }

    mentorshipFormGroup: FormGroup = this.formBuilder.group({
        mentorshipFeedback: [ '', [ Validators.required, Validators.maxLength(250) ], [ blankValidator() ] ]
    });

    changes: any = {};

    canSubmit = false;
    loadingState = false;

    constructor(
        private changeDetectorRef: ChangeDetectorRef,
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<MentorshipDetailsDialogComponent>,
        public formService: FormService,
        private mentorshipService: MentorshipService,
        private acquiredSkillService: AcquiredSkillService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.data.requiredSkill.requiredLevelFormated = formatSkillLevel(this.data.requiredSkill.level);
        if (this.data.acquiredSkill) {
            this.acquiredSkillLevel = {
                name: formatSkillLevel(this.data.acquiredSkill.progress[this.data.acquiredSkill.progress.length - 1].level),
                value: this.data.acquiredSkill.progress[this.data.acquiredSkill.progress.length - 1].level
            };
        } else {
            this.acquiredSkillLevel = {
                name: 'Not acquired',
                value: undefined
            };
        }
        if (this.data.mentorship.status !== this.mentorshipStatus.TERMINATED) {
            if (this.data.settings.isMentor) {
                this.initializeSlider();
                this.mentorshipRating = this.data.mentorship.mentorRating;
                this.mentorshipFormGroup.get('mentorshipFeedback').setValue(this.data.mentorship.mentorFeedback);
            }
            if (this.data.settings.isSelf) {
                this.mentorshipRating = this.data.mentorship.menteeRating;
                this.mentorshipFormGroup.get('mentorshipFeedback').setValue(this.data.mentorship.menteeFeedback);
            }
        }
    }

    initializeSlider() {
        if (this.acquiredSkillLevel.value) {
            this.sliderConfiguration.start = valueOfSkillLevel(this.acquiredSkillLevel.value);
            this.sliderConfiguration.padding = [ 0.25, 0 ];
        }
        this.changeDetectorRef.detectChanges();
        slider.create(this.acquiredSkillLevelSliderElement.nativeElement, this.sliderConfiguration);
        this.acquiredSkillLevelSliderElement.nativeElement.noUiSlider.on(
            'change',
            (values: Array<number>) => {
                this.acquiredSkillLevel.value = (values[0] === 0) ? undefined : skillLevelFromValue(values[0]);
                this.acquiredSkillLevel.name = this.acquiredSkillLevel.value ? formatSkillLevel(this.acquiredSkillLevel.value) : 'Not acquired';
                if ((this.data.acquiredSkill && this.data.acquiredSkill.progress[this.data.acquiredSkill.progress.length - 1].level !== this.acquiredSkillLevel.value) || (!this.data.acquiredSkill && this.acquiredSkillLevel.value)) {
                    if (JSON.stringify(this.changes) !== '{}') {
                        this.canSubmit = this.mentorshipFormGroup.valid && this.mentorshipRating !== undefined;
                    } else  {
                        this.canSubmit = true;
                    }
                } else {
                    if (JSON.stringify(this.changes) !== '{}') {
                        this.canSubmit = this.mentorshipFormGroup.valid && this.mentorshipRating !== undefined;
                    } else {
                        this.canSubmit = false;
                    }
                }
            }
        );
    }

    onMentorshipFeedbackChange() {
        if (this.data.settings.isMentor) {
            if (this.mentorshipFormGroup.get('mentorshipFeedback').value !== this.data.mentorship.mentorFeedback) {
                this.changes.mentorFeedback = this.mentorshipFormGroup.get('mentorshipFeedback').value;
            } else {
                delete this.changes.mentorFeedback;
            }
        }
        if (this.data.settings.isSelf) {
            if (this.mentorshipFormGroup.get('mentorshipFeedback').value !== this.data.mentorship.menteeFeedback) {
                this.changes.menteeFeedback = this.mentorshipFormGroup.get('mentorshipFeedback').value;
            } else {
                delete this.changes.menteeFeedback;
            }
        }
        if (JSON.stringify(this.changes) !== '{}') {
            this.canSubmit = this.mentorshipFormGroup.valid && this.mentorshipRating !== undefined;
        } else {
            this.canSubmit = (this.data.acquiredSkill && this.data.acquiredSkill.progress[this.data.acquiredSkill.progress.length - 1].level !== this.acquiredSkillLevel.value) || (!this.data.acquiredSkill && this.acquiredSkillLevel.value);
        }
    }

    onMentorshipStatusChange(event: any) {
        if (!event) {
            this.changes.status = this.mentorshipStatus.TERMINATED;
        } else {
            delete this.changes.status;
        }
        if (JSON.stringify(this.changes) !== '{}') {
            this.canSubmit = this.mentorshipFormGroup.valid && this.mentorshipRating !== undefined;
        } else {
            this.canSubmit = (this.data.acquiredSkill && this.data.acquiredSkill.progress[this.data.acquiredSkill.progress.length - 1].level !== this.acquiredSkillLevel.value) || (!this.data.acquiredSkill && this.acquiredSkillLevel.value);
        }
    }

    onSubmit() {
        let mentorshipEvaluationObservable: Observable<IMentorship>;
        let acquiredSkillEvaluationObservable: Observable<IAcquiredSkill>;
        if (JSON.stringify(this.changes) !== '{}') {
            this.loadingState = true;
            if (this.data.settings.isMentor) {
                mentorshipEvaluationObservable = this.mentorshipService.evaluateMentorshipForMentor(this.data.mentorship.id, this.changes);
            }
            if (this.data.settings.isSelf) {
                mentorshipEvaluationObservable = this.mentorshipService.evaluateMentorshipForMentee(this.data.mentorship.id, this.changes);
            }
        }
        if (this.data.acquiredSkill && this.data.acquiredSkill.progress[this.data.acquiredSkill.progress.length - 1].level !== this.acquiredSkillLevel.value) {
            this.loadingState = true;
            const level: IAcquiredSkillLevel = {
                level: this.acquiredSkillLevel.value
            };
            acquiredSkillEvaluationObservable = this.acquiredSkillService.createAcquiredSkillLevel(
                this.data.acquiredSkill.id,
                level
            ).pipe(
                map((acquiredSkillLevel: IAcquiredSkillLevel) => {
                    this.data.acquiredSkill.progress.push(acquiredSkillLevel);
                    return this.data.acquiredSkill as IAcquiredSkill;
                })
            );
        }
        if (!this.data.acquiredSkill && this.acquiredSkillLevel.value) {
            this.loadingState = true;
            const acquiredSkill: IAcquiredSkill = {
                collaborator: this.data.settings.collaborator,
                skill: this.data.mentorship.skill,
                progress: [
                    {
                        level: this.acquiredSkillLevel.value
                    }
                ]
            };
            acquiredSkillEvaluationObservable = this.acquiredSkillService.createAcquiredSkills([ acquiredSkill ]).pipe(
                map(acquiredSkills => acquiredSkills[0])
            );
        }
        if (mentorshipEvaluationObservable) {
            mentorshipEvaluationObservable.subscribe(
                (mentorship: IMentorship) => {
                    if (acquiredSkillEvaluationObservable) {
                        acquiredSkillEvaluationObservable.subscribe(
                            (acquiredSkill: IAcquiredSkill) => {
                                this.loadingState = false;
                                this.toastrService.showStatusToastr(
                                    'Evaluation has been submitted successfully',
                                    ToastrStatusEnum.SUCCESS);
                                this.dialogRef.close({
                                    mentorship: { ...mentorship },
                                    acquiredSkill: { ...acquiredSkill }
                                });
                            },
                            (error: IErrorResponse) => {
                                this.loadingState = false;
                                if (error.status >= 400 && error.status < 500) {
                                    this.toastrService.showStatusToastr(
                                        'Mentorship evaluation has been submitted successfully, however there '
                                        + 'is a problem with the acquired skill evaluation. Please try again later!',
                                        ToastrStatusEnum.DANGER);
                                }
                                this.dialogRef.close({
                                    mentorship: { ...mentorship },
                                    acquiredSkill: undefined
                                });
                            }
                        );
                    } else {
                        this.loadingState = false;
                        this.toastrService.showStatusToastr(
                            'Mentorship evaluation has been submitted successfully',
                            ToastrStatusEnum.SUCCESS);
                        this.dialogRef.close({
                            mentorship: { ...mentorship },
                            acquiredSkill: undefined
                        });
                    }
                },
                (error: IErrorResponse) => this.onSubmitError(error)
            );
        } else if (acquiredSkillEvaluationObservable) {
            acquiredSkillEvaluationObservable.subscribe(
                (acquiredSkill: IAcquiredSkill) => {
                    this.loadingState = false;
                    this.toastrService.showStatusToastr(
                        'Acquired skill evaluation has been submitted successfully',
                        ToastrStatusEnum.SUCCESS);
                    this.dialogRef.close({
                        mentorship: undefined,
                        acquiredSkill: { ...acquiredSkill }
                    });
                },
                (error: IErrorResponse) => this.onSubmitError(error)
            );
        }
    }

    onSubmitError(error: IErrorResponse) {
        this.loadingState = false;
        if (error.status >= 400 && error.status < 500) {
            this.toastrService.showStatusToastr(
                'Something is not right, your evaluation could '
                + 'not be submitted. Please try again later!',
                ToastrStatusEnum.DANGER);
        }
        this.dialogRef.close();
    }

    ngOnDestroy() {
        this.acquiredSkillLevelSliderElement?.nativeElement.noUiSlider.off();
    }

}
