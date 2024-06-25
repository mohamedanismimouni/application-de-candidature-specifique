import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, BehaviorSubject } from 'rxjs';

import { StorageService } from './storage.service';

import { ISignInCredentials } from '../models/sign-in-credentials.model';
import { IXAuthToken } from '../models/x-auth-token.model';

import { API_AUTHENTICATION_BASE_URL, API_AUTHENTICATION_PATHS } from '../constants/api-urls.constant';
import { MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';
import { StorageKeyEnum } from '../enumerations/storage-key.enum';
import { ICollaborator } from '../models/collaborator.model';


@Injectable()
export class AuthenticationService {

    private authenticationStatus: BehaviorSubject<boolean>;

    constructor(
        private http: HttpClient,
        private storageService: StorageService
    ) {
        if (this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY) &&
            this.storageService.getItem(StorageKeyEnum.X_AUTH_TOKEN_KEY)) {
            this.authenticationStatus = new BehaviorSubject<boolean>(true);
        } else {
            this.authenticationStatus = new BehaviorSubject<boolean>(false);
        }
    }

    signIn(signInCredentials: ISignInCredentials): Observable<IXAuthToken> {

        const url = API_AUTHENTICATION_BASE_URL + API_AUTHENTICATION_PATHS.SIGN_IN_PATH;

        return this.http.post<IXAuthToken>(url, signInCredentials, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    signOut(): Observable<void> {

        const url = API_AUTHENTICATION_BASE_URL + API_AUTHENTICATION_PATHS.SIGN_OUT_PATH;

        return this.http.post<void>(url, {});

    }

    signUp(user: ICollaborator): Observable<ICollaborator> {

        const url = API_AUTHENTICATION_BASE_URL + API_AUTHENTICATION_PATHS.SIGN_UP_PATH;

        return this.http.post<ICollaborator>(url, user, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }



    setAuthenticationStatus(authenticationStatus: boolean) {
        this.authenticationStatus.next(authenticationStatus);
    }

    getAuthenticationStatus(): Observable<boolean> {
        return this.authenticationStatus.asObservable();
    }

}
