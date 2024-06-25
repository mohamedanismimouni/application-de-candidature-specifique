import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { StorageService } from '../services/storage.service';
import { CareerPositionService } from '../services/career-position.service';

import { ICareerPosition } from '../models/career-position.model';

import { StorageKeyEnum } from '../enumerations/storage-key.enum';
import { ProfileTypeEnum } from '../enumerations/profile-type.enum';
import { CareerPositionStatusEnum } from '../enumerations/career-position-status.enum';
import { ICollaborator } from '../models/collaborator.model';


@Injectable()
export class UninitializedCollaboratorGuard implements CanActivate {

    constructor(
        private location: Location,
        private storageService: StorageService,
        private careerPositionService: CareerPositionService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> {

        const currentUser: ICollaborator = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);

        if (!currentUser) {
            this.location.back();
            return false;
        }

        return this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(
            currentUser.id,
            CareerPositionStatusEnum.CURRENT
        ).pipe(
            map((careerPositions: Array<ICareerPosition>) => {
                if (careerPositions.length <= 0) {
                    return true;
                } else {
                    this.location.back();
                    return false;
                }
            })
        );

    }

}
