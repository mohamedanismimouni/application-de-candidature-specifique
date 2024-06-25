import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import * as moment from 'moment';

import { FormService } from '../../../services/form.service';
import { UserService } from '../../../services/user.service';
import { TeamService } from '../../../services/team.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator, customEmailValidator } from '../../../utils/validators.util';
import { formatProfileType } from '../../../utils/formaters.util';

import { ITeam } from '../../../models/team.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ProfileTypeEnum } from '../../../enumerations/profile-type.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';

import { DATE_STORAGE_FORMAT } from '../../../constants/date-time-formats.constant';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './create-user-dialog.component.html',
    styleUrls: [ './create-user-dialog.component.scss' ]
})
export class CreateUserDialogComponent {

    createUserFormGroup: FormGroup = this.formBuilder.group({
        firstName:      [ '', Validators.required, blankValidator() ],
        lastName:       [ '', Validators.required, blankValidator() ],
        email:          [ '', Validators.required, customEmailValidator() ],
        profileType:    [ '', Validators.required ]
    });

    profileTypes = Object.values(ProfileTypeEnum).map((profileType) => ({
        value: profileType,
        name: formatProfileType(profileType)
    }));

    profileType = ProfileTypeEnum;

    teams: Array<ITeam> = [];

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<CreateUserDialogComponent>,
        public formService: FormService,
        private userService: UserService,
        private teamService: TeamService,
        private toastrService: ToastrService
    ) { }

    onProfileTypeChange() {

        this.createUserFormGroup.removeControl('recruitedAt');
        this.createUserFormGroup.removeControl('memberOf');

        if (this.createUserFormGroup.get('profileType').value === ProfileTypeEnum.COLLABORATOR) {
            this.createUserFormGroup.addControl(
                'recruitedAt',
                new FormControl('', Validators.required)
            );
            this.createUserFormGroup.addControl(
                'memberOf',
                new FormControl(null)
            );
            this.loadTeams();
        }

    }

    loadTeams() {
        this.teamService.getTeams().subscribe(teams => this.teams = teams);
    }

    onCancel() {
        this.dialogRef.close(false);
    }

    onSubmit() {
        this.loadingState = true;
        const user: ICollaborator = this.createUserFormGroup.value;
        if (user.profileType === ProfileTypeEnum.COLLABORATOR) {
            user.recruitedAt = moment(user.recruitedAt).format(DATE_STORAGE_FORMAT);
        }
        this.userService.createUser(user).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'User\'s account created successfully',
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
                        this.createUserFormGroup.get('email')
                            .setErrors({ notUnique: true });
                        break;
                }
            }
        );
    }

}
