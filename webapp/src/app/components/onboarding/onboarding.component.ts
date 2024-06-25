import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { FormService } from '../../services/form.service';
import { StorageService } from '../../services/storage.service';
import { OnboardingService } from '../../services/onboarding.service';
import { UserService } from '../../services/user.service';
import { ToastrService } from '../../services/toastr.service';

import { blankValidator } from '../../utils/validators.util';
import { getRoutePrefixFromProfileType } from '../../utils/navigation.util';

import { IOnboardingHint } from '../../models/onboarding-hint.model';
import { ISecretWord } from '../../models/secret-word.model';
import { IErrorResponse } from '../../models/error-response.model';

import { StorageKeyEnum } from '../../enumerations/storage-key.enum';
import { ToastrStatusEnum } from '../../enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './onboarding.component.html',
    styleUrls: [ './onboarding.component.scss' ]
})
export class OnboardingComponent implements OnInit {

    currentUser: ICollaborator;
    onboardingHint: IOnboardingHint;

    onboardingFormGroup: FormGroup = this.formBuilder.group({
        secretWord: [ '', [], [ blankValidator() ] ]
    });

    secretWordPlaceholder = '';

    passedOnboardingProcess = false;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        public formService: FormService,
        private storageService: StorageService,
        private onboardingService: OnboardingService,
        private userService: UserService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {

        this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);

        this.onboardingService.getOnboardingHint(this.currentUser.id).subscribe(
            (onboardingHint: IOnboardingHint) => {
                this.onboardingHint = onboardingHint;
                this.secretWordPlaceholder = new Array<string>(this.onboardingHint.secretWordLength).join(' - ');
                this.onboardingFormGroup.get('secretWord').setValidators([
                    Validators.required,
                    Validators.minLength(this.onboardingHint.secretWordLength),
                    Validators.maxLength(this.onboardingHint.secretWordLength)
                ]);
            }
        );

    }

    onSubmit() {
        const secretWord: ISecretWord = {
            value: this.onboardingFormGroup.get('secretWord').value
        };
        this.userService.validateSecretWord(this.currentUser.id, secretWord).subscribe(
            () => {
                this.currentUser.passedOnboardingProcess = true;
                this.storageService.setItem(StorageKeyEnum.CURRENT_USER_KEY, this.currentUser);
                this.passedOnboardingProcess = true;
            },
            (error: IErrorResponse) => {
                if (error.status === 400) {
                    this.toastrService.showStatusToastr(
                        'Secret word did not match! you may want to try again',
                        ToastrStatusEnum.DANGER);
                }
            }
        );
    }

    navigateToCollaboratorHome() {
        this.router.navigate([ getRoutePrefixFromProfileType(this.currentUser.profileType) ]);
    }

}
