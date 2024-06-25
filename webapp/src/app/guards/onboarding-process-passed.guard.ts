import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { StorageService } from '../services/storage.service';
import { OnboardingService } from '../services/onboarding.service';


import { StorageKeyEnum } from '../enumerations/storage-key.enum';
import { ProfileTypeEnum } from '../enumerations/profile-type.enum';
import { ICollaborator } from '../models/collaborator.model';


@Injectable()
export class OnboardingProcessPassedGuard implements CanActivate {

    constructor(
        private location: Location,
        private storageService: StorageService,
        private onboardingService: OnboardingService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> {

        const currentUser: ICollaborator = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);

        if (!currentUser ) {
            this.location.back();
            return false;
        }

        if (currentUser.passedOnboardingProcess) {
            return true;
        } else {
            return this.onboardingService.getOnboardingHint(currentUser.id).pipe(
                map(() => {
                    this.location.back();
                    return false;
                }),
                catchError(() => of(true))
            );
        }

    }

}
