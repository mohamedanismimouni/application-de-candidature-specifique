import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpRequest, HttpHandler } from '@angular/common/http';

import { Observable } from 'rxjs';

import { StorageService } from '../services/storage.service';

import { IXAuthToken } from '../models/x-auth-token.model';

import { StorageKeyEnum } from '../enumerations/storage-key.enum';


@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

    constructor(
        private storageService: StorageService
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const xAuthToken: IXAuthToken = this.storageService.getItem(StorageKeyEnum.X_AUTH_TOKEN_KEY);

        if (xAuthToken) {
             
            // request = request.clone({
            //     setHeaders: {
            //         'Authorization': "Bearer "+xAuthToken
            //     }
            // });
        }
 
        return next.handle(request);

    }

}
