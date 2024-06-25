import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { StorageService } from '../services/storage.service';

import { getRoutePrefixFromProfileType } from '../utils/navigation.util';


import { StorageKeyEnum } from '../enumerations/storage-key.enum';
import { ICollaborator } from '../models/collaborator.model';


@Injectable()
export class AuthorizationGuard implements CanActivate {

    constructor(
        private storageService: StorageService,
        private location: Location
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

        const currentUser: ICollaborator = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);

        if (currentUser) {
            
            const canActivate = state.url.startsWith(getRoutePrefixFromProfileType(currentUser.profileType));
            if (!canActivate) {
                this.location.back();
            }
            return canActivate;
        }

    }

}
