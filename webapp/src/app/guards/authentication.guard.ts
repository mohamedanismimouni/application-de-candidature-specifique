import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

import { StorageService } from '../services/storage.service';

import { IRedirectUrl } from '../models/redirect-url.model';

import { StorageKeyEnum } from '../enumerations/storage-key.enum';


@Injectable()
export class AuthenticationGuard implements CanActivate {

    constructor(
        private router: Router,
        private storageService: StorageService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {

        if (
            !this.storageService.getItem(StorageKeyEnum.X_AUTH_TOKEN_KEY) ||
            !this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY)
        ) {
            const redirectUrl: IRedirectUrl = { url: state.url };
            this.storageService.setItem(StorageKeyEnum.REDIRECT_URL_KEY, redirectUrl);
            this.storageService.clearAuthenticationItems();
            this.router.navigate(['/sign-in']);
            return false;
        } else {
            return true;
        }

    }

}
