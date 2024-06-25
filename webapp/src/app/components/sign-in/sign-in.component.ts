import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormService } from '../../services/form.service';
import { AuthenticationService } from '../../services/authentication.service';
import { StorageService } from '../../services/storage.service';
import { UserService } from '../../services/user.service';
import { CareerPositionService } from '../../services/career-position.service';
import { OnboardingService } from '../../services/onboarding.service';
import { ToastrService } from '../../services/toastr.service';
import { blankValidator, customEmailValidator } from '../../utils/validators.util';
import { getRoutePrefixFromProfileType } from '../../utils/navigation.util';
import { ISignInCredentials } from '../../models/sign-in-credentials.model';
import { IXAuthToken } from '../../models/x-auth-token.model';
import { ITeam } from '../../models/team.model';
import { ICareerPosition } from '../../models/career-position.model';
import { IRedirectUrl } from '../../models/redirect-url.model';
import { IErrorResponse } from '../../models/error-response.model';
import { StorageKeyEnum } from '../../enumerations/storage-key.enum';
import { ToastrStatusEnum } from '../../enumerations/toastr-status.enum';
import { ProfileTypeEnum } from '../../enumerations/profile-type.enum';
import { CareerPositionStatusEnum } from '../../enumerations/career-position-status.enum';
import { DATE_STORAGE_FORMAT } from 'src/app/constants/date-time-formats.constant';
import * as moment from 'moment';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './sign-in.component.html',
    styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

    passwordForgotten = false;
    signUp = false;
    signIn = true;  
    loadingState = false;  

     //signIn Form
    signInFormGroup: FormGroup = this.formBuilder.group({
        email:          ['', Validators.required, customEmailValidator()],
        password:       ['', Validators.required]
    });
     //Sign Up form
    signUpFormGroup: FormGroup = this.formBuilder.group({
        firstName:      [ '', Validators.required, blankValidator() ],
        lastName:       [ '', Validators.required, blankValidator() ],
        email:          [ '', Validators.required, customEmailValidator() ],
        recruitedAt:    [ '', Validators.required]
    });
    //password Forgotten form
    passwordForgottenFormGroup: FormGroup = this.formBuilder.group({
        email: ['', Validators.required, customEmailValidator()]
    });   

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        public formService: FormService,
        private authenticationService: AuthenticationService,
        private storageService: StorageService,
        private userService: UserService,
        private careerPositionService: CareerPositionService,
        private onboardingService: OnboardingService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        if (
            this.storageService.getItem(StorageKeyEnum.X_AUTH_TOKEN_KEY) &&
            this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY)
        ) {
            this.userService.getCurrentUser().subscribe(
                (currentUser: ICollaborator) => {
                    if (currentUser.profileType === ProfileTypeEnum.COLLABORATOR) {
                        this.collaboratorPostSignInNavigation(currentUser);
                    } else {
                        this.router.navigate([getRoutePrefixFromProfileType(currentUser.profileType)]);
                    }
                    this.authenticationService.setAuthenticationStatus(true);
                },
                () => {
                    this.storageService.clearAuthenticationItems();
                    this.authenticationService.setAuthenticationStatus(false);
                }
            );
        }
    }

    onForgotPassword() {
        this.signInFormGroup.reset();
        this.passwordForgotten = true;
        this.signIn=false;
        this.signUp=false;
    } 
    
    onSignUpLink() {
        this.signUpFormGroup.reset();
        this.signUp = true;
        this.signIn=false;
        this.passwordForgotten=false;
        this.signInFormGroup.reset();

    }
    onSignUp()
    {
        this.loadingState = true;
        const user: ICollaborator = this.signUpFormGroup.value;
        user.profileType=ProfileTypeEnum.COLLABORATOR;
        user.recruitedAt = moment(user.recruitedAt).format(DATE_STORAGE_FORMAT);   
        //signUp service
        this.authenticationService.signUp(user).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'User\'s account created successfully! wait for the email activation by the HR Responsible.',
                    ToastrStatusEnum.SUCCESS);
                    this.signUpFormGroup.reset();

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
                    case 409:
                        this.signUpFormGroup.get('email')
                            .setErrors({ notUnique: true });
                        break;
                }
            }
        );  
    }

    onSignIn() {
        this.loadingState = true;
        const signInCredentials: ISignInCredentials = this.signInFormGroup.value;
        this.authenticationService.signIn(signInCredentials).subscribe(
            (xAuthToken: IXAuthToken) => {
                this.storageService.setItem(StorageKeyEnum.X_AUTH_TOKEN_KEY, xAuthToken);
                this.userService.getUserByEmail(this.signInFormGroup.get('email').value).subscribe(
                    (currentUser: ICollaborator) => {
                        this.storageService.setItem(StorageKeyEnum.CURRENT_USER_KEY, currentUser);
                        this.authenticationService.setAuthenticationStatus(true);
                        const redirectUrl: IRedirectUrl = this.storageService.getItem(StorageKeyEnum.REDIRECT_URL_KEY);
                        if (currentUser.profileType === ProfileTypeEnum.COLLABORATOR) {
                            this.collaboratorPostSignInNavigation(currentUser, redirectUrl?.url);
                        } else {
                            this.defaultPostSignInNavigation(currentUser.profileType, redirectUrl?.url);
                        }
                    }
                );
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                switch (error.status) {
                    case 401:
                        this.signInFormGroup.get('password').setErrors({ wrongPassword: true });
                        break;
                    case 403:
                        this.signInFormGroup.get('email').setErrors({ accountNotActive: true });
                        this.signInFormGroup.get('password').reset();
                        break;
                    case 404:
                        this.signInFormGroup.get('email').setErrors({ accountNotFound: true });
                        this.signInFormGroup.get('password').reset();
                        break;
                }
            }
        );
    }

    collaboratorPostSignInNavigation(collaborator: ICollaborator, redirectUrl?: string) {
        this.userService.getCollaboratorTeam(collaborator.id).subscribe(
            (team: ITeam) => {
                if (!team) {
                    this.postSignInNavigation('/unassigned-collaborator');
                } else {
                    this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(
                        collaborator.id,
                        CareerPositionStatusEnum.CURRENT
                    ).subscribe(
                        (careerPositions: Array<ICareerPosition>) => {
                            if (careerPositions.length <= 0) {
                                this.postSignInNavigation('/uninitialized-collaborator');
                            } else {
                                if (collaborator.passedOnboardingProcess) {
                                    this.defaultPostSignInNavigation(ProfileTypeEnum.COLLABORATOR, redirectUrl);
                                } else {
                                    this.onboardingService.getOnboardingHint(collaborator.id).subscribe(
                                        () => this.postSignInNavigation('/onboarding'),
                                        () => this.defaultPostSignInNavigation(ProfileTypeEnum.COLLABORATOR, redirectUrl)
                                    );
                                }
                            }
                        }
                    );
                }
            }
        );
    }

    defaultPostSignInNavigation(profileType: ProfileTypeEnum, redirectUrl?: string) {
        let url: string;
        if (redirectUrl) {
            url = redirectUrl;
            this.storageService.removeItem(StorageKeyEnum.REDIRECT_URL_KEY);
        } else {
            url = getRoutePrefixFromProfileType(profileType);
        }
        this.postSignInNavigation(url);
    }

    postSignInNavigation(url: string) {
        this.router.navigate([url]);
        this.loadingState = false;
        this.toastrService.showSimpleToastr('Hello and welcome to Sirius HR!');
    }

    onBackToSignIn() {
        this.passwordForgottenFormGroup.reset();
        this.signUpFormGroup.reset();
        this.passwordForgotten = false;
        this.signUp=false;
        this.signIn=true;
        this.signInFormGroup.reset();

    }


    onSubmit() {
        this.loadingState = true;
        this.userService.sendPasswordResetMail(this.passwordForgottenFormGroup.get('email').value).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Your password reset request has been successfully '
                    + 'processed, check your inbox for a confirmation mail',
                    ToastrStatusEnum.SUCCESS);
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                switch (error.status) {
                    case 403:
                        this.passwordForgottenFormGroup.get('email').setErrors({ accountNotActive: true });
                        break;
                    case 404:
                        this.passwordForgottenFormGroup.get('email').setErrors({ accountNotFound: true });
                        break;
                }
            }
        );
    }

}
