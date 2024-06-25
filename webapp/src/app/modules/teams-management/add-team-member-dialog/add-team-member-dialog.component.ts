import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import { UserService } from '../../../services/user.service';
import { ToastrService } from '../../../services/toastr.service';


import { ProfileTypeEnum } from '../../../enumerations/profile-type.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './add-team-member-dialog.component.html',
    styleUrls: [ './add-team-member-dialog.component.scss' ]
})
export class AddTeamMemberDialogComponent implements OnInit {

    @Input()
    data: any;

    searchFormGroup: FormGroup = this.formBuilder.group({
        keyword: [ '' ]
    });

    unassignedCollaborators: Array<ICollaborator> = [];
    collaborators: Array<ICollaborator> = [];

    loadingState = false;

    collaboratorsId :Array<number> = []; 
    collaboratorMultiCtrl: FormControl = new FormControl();
    list:Array<string>=["element"];

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<AddTeamMemberDialogComponent>,
        private userService: UserService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.loadUnassignedCollaborators();  
    }

    loadUnassignedCollaborators() {
        this.userService.getResourcesByTeamId(ProfileTypeEnum.COLLABORATOR).subscribe(
            (unassignedCollaborators: Array<ICollaborator>) => {
                this.unassignedCollaborators = unassignedCollaborators;
                this.collaborators = unassignedCollaborators;
                this.collaborators.filter(
                    collab => collab.name=collab.firstName+" "+collab.lastName

                );
            }
        );
        
    }

    onCancel() {
        this.dialogRef.close(false);  
    }

    onSubmit() {
            this.collaboratorMultiCtrl.value.filter(
                collab => this.collaboratorsId.push(collab.id)
            );
            this.userService.assignCollaboratorsToTeam(this.data.teamId,this.collaboratorsId).subscribe(
                () => {
                    this.loadingState = false;
                    this.toastrService.showStatusToastr(
                        'Team members added successfully',
                        ToastrStatusEnum.SUCCESS);
                    this.dialogRef.close(true);
                },
             
            );       
    }

}
