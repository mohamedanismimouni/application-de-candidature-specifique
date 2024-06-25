import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Observable } from 'rxjs';

import { StorageService } from '../../services/storage.service';
import { FormService } from '../../services/form.service';
import { UserService } from '../../services/user.service';
import { ToastrService } from '../../services/toastr.service';

import { passwordStrengthValidator } from '../../utils/validators.util';

import { IPasswordSubmission } from '../../models/password-submission.model';
import { IErrorResponse } from '../../models/error-response.model';

import { SessionTypeEnum } from '../../enumerations/session-type.enum';
import { StorageKeyEnum } from '../../enumerations/storage-key.enum';
import { ToastrStatusEnum } from '../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './password-initialization.component.html',
    styleUrls: [ './password-initialization.component.scss' ]
})
export class PasswordInitializationComponent implements OnInit {

    sessionType: SessionTypeEnum;

    sessionTypeEnum = SessionTypeEnum;

    passwordInitializationFormGroup: FormGroup = this.formBuilder.group({
        password:               [ '', Validators.required, passwordStrengthValidator() ],
        passwordConfirmation:   [ '', Validators.required ]
    });

    loadingState = false;

    constructor(
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private formBuilder: FormBuilder,
        private storageService: StorageService,
        public formService: FormService,
        private userService: UserService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.storageService.clearAuthenticationItems();
        this.activatedRoute.paramMap.subscribe(
            (paramMap: ParamMap) => {
                this.sessionType = paramMap.get('session-type') as SessionTypeEnum;
                this.storageService.setItem(StorageKeyEnum.X_AUTH_TOKEN_KEY, { sessionID: paramMap.get('session-id') });
            }
        );
    }

    onSubmit() {
        if (this.passwordInitializationFormGroup.get('password').value !== this.passwordInitializationFormGroup.get('passwordConfirmation').value) {
            this.passwordInitializationFormGroup.get('passwordConfirmation').setErrors({ notConfirmed: true });
            return;
        }
        const passwordSubmission: IPasswordSubmission = this.passwordInitializationFormGroup.value;
        let action: Observable<void>;
        let successMessage: string;
        switch (this.sessionType) {
            case SessionTypeEnum.ACCOUNT_ACTIVATION:
                action = this.userService.activateUserAccount(passwordSubmission);
                successMessage = 'Your password has been successfully '
                    + 'initialized and your account is now activated';
                break;
            case SessionTypeEnum.PASSWORD_RESET:
                action = this.userService.resetPassword(passwordSubmission);
                successMessage = 'Your password has been successfully '
                    + 'reset, you try to can sign in now';
                break;
            default:
                return;
        }
        this.loadingState = true;
        action.subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(successMessage, ToastrStatusEnum.SUCCESS);
                this.storageService.removeItem(StorageKeyEnum.X_AUTH_TOKEN_KEY);
                this.router.navigate([ '/sign-in' ]);
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                switch (error.status) {
                    case 400:
                        this.toastrService.showStatusToastr(
                            'Something is not right, please verify '
                            + 'your input and try again later',
                            ToastrStatusEnum.DANGER);
                        break;
                    case 401:
                    case 403:
                        this.toastrService.showStatusToastr(
                            'Password initialization link has unfortunately expired',
                            ToastrStatusEnum.DANGER);
                        this.storageService.removeItem(StorageKeyEnum.X_AUTH_TOKEN_KEY);
                        this.passwordInitializationFormGroup.reset();
                        break;
                }
            }
        );
    }

}
