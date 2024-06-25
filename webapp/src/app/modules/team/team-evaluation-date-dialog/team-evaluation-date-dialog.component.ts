import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import * as moment from 'moment';

import { FormService } from '../../../services/form.service';
import { TeamService } from '../../../services/team.service';
import { ToastrService } from '../../../services/toastr.service';

import { ITeam } from '../../../models/team.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';

import { MONTH_DAY_STORAGE_FORMAT } from '../../../constants/date-time-formats.constant';


@Component({
    templateUrl: './team-evaluation-date-dialog.component.html',
    styleUrls: [ './team-evaluation-date-dialog.component.scss' ]
})
export class TeamEvaluationDateDialogComponent implements OnInit {

    @Input()
    data: any;

    months: Array<any> = [ ...new Array(12) ].map((value, index) => ({
        name: this.formatMonth(index + 1),
        value: index + 1
    }));
    days: Array<number> = [];

    teamEvaluationDateFormGroup: FormGroup = this.formBuilder.group({
        month: [ null, Validators.required ],
        day:   [ null, Validators.required ]
    });

    teamEvaluationDate: string;

    isSame = true;

    loadingState = false;

    constructor(
        private dialogRef: NbDialogRef<TeamEvaluationDateDialogComponent>,
        private formBuilder: FormBuilder,
        public formService: FormService,
        private teamService: TeamService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        if (this.data.teamEvaluationDate) {
            const teamEvaluationMoment = moment(this.data.teamEvaluationDate, MONTH_DAY_STORAGE_FORMAT);
            if (teamEvaluationMoment.isBefore(moment())) {
                teamEvaluationMoment.add(1, 'years');
            }
            this.teamEvaluationDateFormGroup.get('month').setValue(teamEvaluationMoment.month() + 1);
            this.teamEvaluationDateFormGroup.get('day').setValue(teamEvaluationMoment.date());
            this.days = [ ...new Array(teamEvaluationMoment.daysInMonth()) ].map((day, index) => (index + 1));
        }
    }

    onMonthSelectChange() {
        const teamEvaluationMoment = moment(this.teamEvaluationDateFormGroup.get('month').value, 'M');
        this.teamEvaluationDateFormGroup.get('day').reset();
        this.days = [ ...new Array(teamEvaluationMoment.daysInMonth()) ].map((day, index) => (index + 1));
    }

    onDaySelectChange() {
        this.teamEvaluationDate = moment(this.teamEvaluationDateFormGroup.get('month').value, 'M')
            .date(this.teamEvaluationDateFormGroup.get('day').value)
            .format(MONTH_DAY_STORAGE_FORMAT);
        this.isSame = this.data.teamEvaluationDate === this.teamEvaluationDate;
    }

    onCancel() {
        this.dialogRef.close();
    }

    onSubmit() {
        this.loadingState = true;
        const changes = {
            teamEvaluationDate: this.teamEvaluationDate
        };
        this.teamService.partialUpdateTeam(this.data.teamId, changes).subscribe(
            (team: ITeam) => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Team evaluation date is set successfully',
                    ToastrStatusEnum.SUCCESS);
                this.dialogRef.close(this.teamEvaluationDate);
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                if (error.status >= 400 && error.status < 500) {
                    this.toastrService.showStatusToastr(
                        'Something is not right, please verify '
                        + 'your input and try again later',
                        ToastrStatusEnum.DANGER);
                }
            }
        );
    }

    private formatMonth(month: number): string {
        return moment(month, 'M').format('MMMM');
    }

}
