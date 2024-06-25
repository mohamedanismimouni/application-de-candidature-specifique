import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import { FormService } from '../../../services/form.service';
import { UserService } from '../../../services/user.service';
import { TeamService } from '../../../services/team.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';

import { ITeam } from '../../../models/team.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ProfileTypeEnum } from '../../../enumerations/profile-type.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './create-team-dialog.component.html',
    styleUrls: [ './create-team-dialog.component.scss' ]
})
export class CreateTeamDialogComponent implements OnInit {

    createTeamFormGroup: FormGroup = this.formBuilder.group({
        name:           [ '', Validators.required, blankValidator() ],
        managedBy:      [ null, Validators.required ]
    });

    unassignedManagers: Array<ICollaborator> = [];

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<CreateTeamDialogComponent>,
        public formService: FormService,
        private userService: UserService,
        private teamService: TeamService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.loadUnassignedManagers();
    }

    loadUnassignedManagers() {
        this.userService.getResourcesByTeamId(ProfileTypeEnum.MANAGER)
            .subscribe(unassignedManagers => this.unassignedManagers = unassignedManagers);
    }

    onCancel() {
        this.dialogRef.close(false);
    }

    onSubmit() {
        this.loadingState = true;
        const team: ITeam = this.createTeamFormGroup.value;
        this.teamService.createTeam(team).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Team created successfully',
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
                        this.createTeamFormGroup.get('name')
                            .setErrors({ notUnique: true });
                        break;
                }
            }
        );
    }

}
