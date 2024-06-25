import { Component, OnInit } from '@angular/core';

import { StorageService } from '../../services/storage.service';
import { UserService } from '../../services/user.service';

import { ITeam } from '../../models/team.model';

import { StorageKeyEnum } from '../../enumerations/storage-key.enum';


@Component({
    templateUrl: './uninitialized-collaborator.component.html',
    styleUrls: [ './uninitialized-collaborator.component.scss' ]
})
export class UninitializedCollaboratorComponent implements OnInit {

    team: ITeam;

    constructor(
        private storageService: StorageService,
        private userService: UserService
    ) { }

    ngOnInit() {
        this.userService.getCollaboratorTeam(
            this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY)?.id
        ).subscribe(team => this.team = team);
    }

}
