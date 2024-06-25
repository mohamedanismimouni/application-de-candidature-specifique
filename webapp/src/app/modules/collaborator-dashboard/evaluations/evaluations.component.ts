import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Subscription, Observable } from 'rxjs';

import { NbDialogService } from '@nebular/theme';

import * as moment from 'moment';

import { FormService } from '../../../services/form.service';
import { CollaboratorDashboardService } from '../collaborator-dashboard.service';
import { EvaluationService } from '../../../services/evaluation.service';
import { CareerPositionService } from '../../../services/career-position.service';
import { ToastrService } from '../../../services/toastr.service';

import { SetupEvaluationDateDialogComponent } from '../setup-evaluation-date-dialog/setup-evaluation-date-dialog.component';
import { ConfirmationDialogComponent } from '../../../components/confirmation-dialog/confirmation-dialog.component';

import { blankValidator } from '../../../utils/validators.util';

import { ICollaboratorDashboardSettings } from '../collaborator-dashboard-settings.model';
import { IEvaluation } from '../../../models/evaluation.model';
import { ICareerPosition } from '../../../models/career-position.model';
import { IDialogData } from '../../../models/dialog-data.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { EvaluationStatusEnum } from '../../../enumerations/evaluation-status.enum';
import { CareerPositionStatusEnum } from '../../../enumerations/career-position-status.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';

import { DATE_STORAGE_FORMAT, MONTH_DAY_STORAGE_FORMAT } from '../../../constants/date-time-formats.constant';
import { EvaluationSurveyResponsesDialogComponent } from '../evaluation-survey-responses-dialog/evaluation-survey-responses-dialog.component';


@Component({
    templateUrl: './evaluations.component.html',
    styleUrls: [ './evaluations.component.scss' ]
})
export class EvaluationsComponent implements OnInit, OnDestroy {

    collaboratorDashboardSettings: ICollaboratorDashboardSettings;
    collaboratorDashboardSettingsSubscription: Subscription;

    evaluations: Array<IEvaluation>;

    openEvaluation: IEvaluation;

    evaluationStatus = EvaluationStatusEnum;

    isOpenEvaluationReady = false;

    supervisorRatingValue: number;

    get supervisorRating() {
        return this.supervisorRatingValue;
    }

    set supervisorRating(supervisorRating: number) {
        this.supervisorRatingValue = supervisorRating;
        if (this.openEvaluation?.supervisorRating !== this.supervisorRatingValue) {
            this.changes.supervisorRating = this.supervisorRatingValue;
        } else {
            delete this.changes.supervisorRating;
        }
        this.canSubmit = JSON.stringify(this.changes) !== '{}' && this.evaluationFormGroup.valid && this.supervisorRating !== undefined;
    }

    evaluationFormGroup: FormGroup = this.formBuilder.group({
        supervisorFeedback: [ '', [ Validators.required, Validators.maxLength(250) ], [ blankValidator() ] ]
    });

    evaluationToggleStatus = true;

    changes: any = {};

    canSubmit = false;
    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogService: NbDialogService,
        public formService: FormService,
        private collaboratorDashboardService: CollaboratorDashboardService,
        private evaluationService: EvaluationService,
        private careerPositionService: CareerPositionService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.collaboratorDashboardSettingsSubscription = this.collaboratorDashboardService.getCollaboratorDashboardSettings().subscribe(
            (collaboratorDashboardSettings: ICollaboratorDashboardSettings) => {
                if (JSON.stringify(collaboratorDashboardSettings) !== '{}') {
                    this.collaboratorDashboardSettings = collaboratorDashboardSettings;
                    this.loadEvaluations();
                }
            }
        );
    }

    loadEvaluations() {
        let evaluationsObservable: Observable<Array<IEvaluation>>;
        if (this.collaboratorDashboardSettings.isManager || this.collaboratorDashboardSettings.isSelf) {
            evaluationsObservable = this.evaluationService.getEvaluations(this.collaboratorDashboardSettings.collaborator.id);
        }
        if (this.collaboratorDashboardSettings.isSupervisor && !this.collaboratorDashboardSettings.isManager) {
            evaluationsObservable = this.evaluationService.getEvaluations(
                this.collaboratorDashboardSettings.collaborator.id,
                this.collaboratorDashboardSettings.viewer.id);
        }
        evaluationsObservable.subscribe(
            (evaluations: Array<IEvaluation>) => {
                this.evaluations = evaluations;
                this.openEvaluation = this.evaluations.find(evaluation => evaluation.status === EvaluationStatusEnum.OPEN);
                if (this.openEvaluation) {
                    this.isOpenEvaluationReady = moment(this.openEvaluation.evaluationDate, DATE_STORAGE_FORMAT).isSameOrBefore(moment());
                    this.supervisorRating = this.openEvaluation.supervisorRating;
                    this.evaluationFormGroup.get('supervisorFeedback').setValue(this.openEvaluation.supervisorFeedback);
                    this.evaluationToggleStatus = true;
                }
            }
        );
    }

    openSetupEvaluationDateDialog() {
        this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(
            this.collaboratorDashboardSettings.collaborator.id,
            CareerPositionStatusEnum.CURRENT
        ).subscribe(
            (careerPositions: Array<ICareerPosition>) => {
                this.dialogService.open(
                    SetupEvaluationDateDialogComponent,
                    {
                        context: {
                            data: {
                                openEvaluation: this.openEvaluation,
                                currentCareerPosition: careerPositions[0]
                            }
                        }
                    }
                ).onClose.subscribe(
                    (result: IEvaluation) => {
                        if (result) {
                            if (!this.openEvaluation) {
                                this.evaluations = [
                                    result,
                                    ...this.evaluations
                                ];
                            } else {
                                const openEvaluationIndex: number = this.evaluations.findIndex(evaluation => evaluation.status === EvaluationStatusEnum.OPEN);
                                this.evaluations[openEvaluationIndex] = result;
                            }
                            this.openEvaluation = result;
                            this.isOpenEvaluationReady = moment(this.openEvaluation.evaluationDate, DATE_STORAGE_FORMAT).isSameOrBefore(moment());
                        }
                    }
                );
            }
        );
    }

    onSupervisorFeedbackChange() {
        if (this.evaluationFormGroup.get('supervisorFeedback').value !== this.openEvaluation?.supervisorFeedback) {
            this.changes.supervisorFeedback = this.evaluationFormGroup.get('supervisorFeedback').value;
        } else {
            delete this.changes.supervisorFeedback;
        }
        this.canSubmit = JSON.stringify(this.changes) !== '{}' && this.evaluationFormGroup.valid && this.supervisorRating !== undefined;
    }

    onEvaluationStatusChange(event: any) {
        this.evaluationToggleStatus = event;
        if (!this.evaluationToggleStatus) {
            const dialogData: IDialogData = {
                title: 'Close evaluation',
                content: 'You\'re about to close this evaluation, '
                + 'once closed it cannot be updated after you submit, '
                + 'do you wish to proceed?'
            };
            this.dialogService.open(
                ConfirmationDialogComponent,
                { context: { data: dialogData } }
            ).onClose.subscribe(
                (result: boolean) => {
                    if (result) {
                        this.changes.status = this.evaluationStatus.CLOSED;
                        this.canSubmit = JSON.stringify(this.changes) !== '{}' && this.evaluationFormGroup.valid && this.supervisorRating !== undefined;
                    } else {
                        this.evaluationToggleStatus = true;
                    }
                }
            );
        } else {
            delete this.changes.status;
        }
        this.canSubmit = JSON.stringify(this.changes) !== '{}' && this.evaluationFormGroup.valid && this.supervisorRating !== undefined;
    }

    openEvaluationSurveyResponsesDialog(evaluation: IEvaluation) {
        this.dialogService.open(
            EvaluationSurveyResponsesDialogComponent,
            {
                closeOnEsc: true,
                closeOnBackdropClick: true,
                context: {
                    data: {
                        collaboratorId: this.collaboratorDashboardSettings.collaborator.id,
                        evaluationId: evaluation.id
                    }
                }
            }
        );
    }

    onSubmit() {
        this.loadingState = true;
        this.evaluationService.partialUpdateEvaluation(this.openEvaluation.id, this.changes).subscribe(
            (evaluation: IEvaluation) => {
                if (evaluation.status === EvaluationStatusEnum.CLOSED) {
                    let nextEvaluationDate: string;
                    if (evaluation.careerPosition.profile.team.teamEvaluationDate) {
                        const teamEvaluationMoment = moment(evaluation.careerPosition.profile.team.teamEvaluationDate, MONTH_DAY_STORAGE_FORMAT);
                        if (teamEvaluationMoment.isBefore(moment())) {
                            teamEvaluationMoment.add(1, 'years');
                        }
                        nextEvaluationDate = teamEvaluationMoment.format(DATE_STORAGE_FORMAT);
                    } else {
                        nextEvaluationDate = moment(evaluation.evaluationDate, DATE_STORAGE_FORMAT).add(1, 'years').format(DATE_STORAGE_FORMAT);
                    }
                    const nextEvaluation: IEvaluation = {
                        evaluationDate: nextEvaluationDate,
                        careerPosition: evaluation.careerPosition
                    };
                    this.evaluationService.createEvaluation(nextEvaluation).subscribe(
                        (openEvaluation: IEvaluation) => {
                            const openEvaluationIndex: number = this.evaluations.findIndex(e => e.status === EvaluationStatusEnum.OPEN);
                            this.evaluations[openEvaluationIndex] = evaluation;
                            this.evaluations = [
                                openEvaluation,
                                ...this.evaluations
                            ];
                            this.openEvaluation = openEvaluation;
                            this.isOpenEvaluationReady = moment(this.openEvaluation.evaluationDate, DATE_STORAGE_FORMAT).isSameOrBefore(moment());
                            this.supervisorRating = undefined;
                            this.evaluationFormGroup.get('supervisorFeedback').reset();
                            this.evaluationToggleStatus = true;
                            this.changes = {};
                            this.canSubmit = false;
                            this.loadingState = false;
                            this.toastrService.showStatusToastr(
                                'Your evaluation has been submitted successfully, '
                                + 'and the next evaluation has been created',
                                ToastrStatusEnum.SUCCESS);
                        },
                        (error: IErrorResponse) => {
                            const openEvaluationIndex: number = this.evaluations.findIndex(e => e.status === EvaluationStatusEnum.OPEN);
                            this.evaluations[openEvaluationIndex] = evaluation;
                            this.openEvaluation = undefined;
                            this.isOpenEvaluationReady = false;
                            this.supervisorRating = undefined;
                            this.evaluationFormGroup.get('supervisorFeedback').reset();
                            this.evaluationToggleStatus = true;
                            this.changes = {};
                            this.canSubmit = false;
                            this.loadingState = false;
                            if (error.status >= 400 && error.status < 500) {
                                this.toastrService.showStatusToastr(
                                    'Your evaluation has been submitted successfully, however, the '
                                    + 'next evaluation could not be created. Please try again later!',
                                    ToastrStatusEnum.DANGER);
                            }
                        }
                    );
                } else {
                    const openEvaluationIndex: number = this.evaluations.findIndex(e => e.status === EvaluationStatusEnum.OPEN);
                    this.evaluations[openEvaluationIndex] = evaluation;
                    this.openEvaluation = evaluation;
                    this.changes = {};
                    this.canSubmit = false;
                    this.loadingState = false;
                    this.toastrService.showStatusToastr(
                        'Your evaluation has been submitted successfully',
                        ToastrStatusEnum.SUCCESS);
                }
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                if (error.status >= 400 && error.status < 500) {
                    this.toastrService.showStatusToastr(
                        'Something is not right, your evaluation could '
                        + 'not be submitted. Please try again later!',
                        ToastrStatusEnum.DANGER);
                }
            }
        );
    }

    onCancel() {
        this.supervisorRating = this.openEvaluation.supervisorRating;
        this.evaluationFormGroup.get('supervisorFeedback').setValue(this.openEvaluation.supervisorFeedback);
        this.evaluationToggleStatus = true;
        this.changes = {};
        this.canSubmit = false;
    }

    ngOnDestroy() {
        this.collaboratorDashboardSettingsSubscription?.unsubscribe();
    }

}
