import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

import { ToastrService } from '../services/toastr.service';

import { IErrorResponse } from '../models/error-response.model';

import { ToastrStatusEnum } from '../enumerations/toastr-status.enum';


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    constructor(
        private router: Router,
        private toastrService: ToastrService
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        return next.handle(request).pipe(
            retry(2),
            catchError(
                (error: HttpErrorResponse) => {

                    const errorResponse: IErrorResponse = error.error;
                    const url = this.router.routerState.snapshot.url;

                    switch (errorResponse.status) {
                        case 400:
                            // TODO: bad request default error handling
                            break;
                        case 401:
                            if (!url.startsWith('/sign-in') && !url.startsWith('/password-initialization')) {
                                this.router.navigate([ '/sign-in' ]);
                                this.toastrService.showStatusToastr('Sorry but your session has expired, you have to refresh your authentication!', ToastrStatusEnum.DANGER);
                            }
                            break;
                        case 403:
                            // TODO: forbidden default error handling
                            break;
                        case 404:
                            // TODO: not found default error handling
                            break;
                        case 409:
                            // TODO: conflict default error handling
                            break;
                        case 500:
                            this.toastrService.showStatusToastr('Oups! Something went wrong, please try again later', ToastrStatusEnum.DANGER);
                            break;
                        case 503:
                            this.toastrService.showStatusToastr('This service is currently unavailable, please try again later!', ToastrStatusEnum.DANGER);
                            break;
                        default:
                            // TODO: default error handling
                            break;
                    }

                    return throwError(errorResponse);

                }
            )
        );

    }

}
