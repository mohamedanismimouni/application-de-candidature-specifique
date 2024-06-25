import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';

import { ITeam } from '../models/team.model';

import { StorageKeyEnum } from '../enumerations/storage-key.enum';
import { ProfileTypeEnum } from '../enumerations/profile-type.enum';
import { ICollaborator } from '../models/collaborator.model';


@Injectable()
export class AssignedCollaboratorGuard implements CanActivate {

    constructor(
        private location: Location,
        private storageService: StorageService,
        private userService: UserService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> {

        const currentUser: ICollaborator = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);

        if (!currentUser) {
            this.location.back();
            return false;
        }

        return this.userService.getCollaboratorTeam(currentUser.id).pipe(
            map((team: ITeam) => {
                if (team) {
                    return true;
                } else {
                    this.location.back();
                    return false;
                }
            })
        );

    }

}
