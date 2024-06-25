import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Observable } from 'rxjs';

import { NbDialogRef } from '@nebular/theme';

import * as moment from 'moment';

import { FormService } from '../../../services/form.service';
import { EvaluationService } from '../../../services/evaluation.service';
import { ToastrService } from '../../../services/toastr.service';

import { IEvaluation } from '../../../models/evaluation.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';

import { DATE_STORAGE_FORMAT, MONTH_DAY_STORAGE_FORMAT } from '../../../constants/date-time-formats.constant';


@Component({
    templateUrl: './setup-evaluation-date-dialog.component.html',
    styleUrls: [ './setup-evaluation-date-dialog.component.scss' ]
})
export class SetupEvaluationDateDialogComponent implements OnInit {

    @Input()
    data: any;

    setupEvaluationDateFormGroup: FormGroup = this.formBuilder.group({
        evaluationDate: [ '', Validators.required ]
    });

    minEvaluationDate: Date;

    isSame = true;

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<SetupEvaluationDateDialogComponent>,
        public formService: FormService,
        private evaluationService: EvaluationService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        if (this.data.openEvaluation) {
            this.setupEvaluationDateFormGroup.get('evaluationDate').setValue(new Date(this.data.openEvaluation.evaluationDate));
        } else if (this.data.currentCareerPosition.profile.team.teamEvaluationDate) {
            const teamEvaluationMoment = moment(this.data.currentCareerPosition.profile.team.teamEvaluationDate, MONTH_DAY_STORAGE_FORMAT);
            if (teamEvaluationMoment.isBefore(moment())) {
                teamEvaluationMoment.add(1, 'years');
            }
            this.setupEvaluationDateFormGroup.get('evaluationDate').setValue(new Date(teamEvaluationMoment.format(DATE_STORAGE_FORMAT)));
        }
        this.minEvaluationDate = new Date(moment().subtract(1, 'days').format(DATE_STORAGE_FORMAT));
    }

    onEvaluationDateChange(date: Date) {
        this.isSame = this.data.openEvaluation?.evaluationDate === moment(date).format(DATE_STORAGE_FORMAT);
    }

    onCancel() {
        this.dialogRef.close();
    }

    onSubmit() {
        let setupEvaluationDateObservable: Observable<IEvaluation>;
        if (this.data.openEvaluation) {
            setupEvaluationDateObservable = this.evaluationService.partialUpdateEvaluation(
                this.data.openEvaluation.id,
                {
                    evaluationDate: moment(this.setupEvaluationDateFormGroup.get('evaluationDate').value).format(DATE_STORAGE_FORMAT)
                }
            );
        } else {
            const evaluation: IEvaluation = {
                evaluationDate: moment(this.setupEvaluationDateFormGroup.get('evaluationDate').value).format(DATE_STORAGE_FORMAT),
                careerPosition: this.data.currentCareerPosition
            };
            setupEvaluationDateObservable = this.evaluationService.createEvaluation(evaluation);
        }
        this.loadingState = true;
        setupEvaluationDateObservable.subscribe(
            (evaluation: IEvaluation) => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Evaluation date has been set successfully!',
                    ToastrStatusEnum.SUCCESS);
                this.dialogRef.close(evaluation);
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                if (error.status >= 400 && error.status < 500) {
                    this.toastrService.showStatusToastr(
                        'Something is not right, evaluation date '
                        + 'could not be set, please try again later',
                        ToastrStatusEnum.DANGER);
                }
                this.dialogRef.close();
            }
        );
    }

}
