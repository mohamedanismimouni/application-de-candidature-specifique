import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import { FormService } from '../../../services/form.service';
import { UserService } from '../../../services/user.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';

import { IErrorResponse } from '../../../models/error-response.model';

import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './initiate-onboarding-process-dialog.component.html',
    styleUrls: [ './initiate-onboarding-process-dialog.component.scss' ]
})
export class InitiateOnboardingProcessDialogComponent {

    @Input()
    data: any;

    onboardingProcessInitiationFormGroup: FormGroup = this.formBuilder.group({
        secretWord:     [ '', Validators.required, blankValidator() ]
    });

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<InitiateOnboardingProcessDialogComponent>,
        public formService: FormService,
        private userService: UserService,
        private toastrService: ToastrService
    ) { }

    onCancel() {
        this.dialogRef.close();
    }

    onSubmit() {
        this.loadingState = true;
        this.userService.initiateOnboardingProcess(
            this.data.collaboratorId,
            this.onboardingProcessInitiationFormGroup.get('secretWord').value
        ).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Onboarding process initiated successfully',
                    ToastrStatusEnum.SUCCESS);
                this.dialogRef.close();
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                if (error.status >= 400 && error.status < 500) {
                    this.toastrService.showStatusToastr(
                        'Onboarding process could not be initiated '
                        + 'right now please try again later',
                        ToastrStatusEnum.DANGER);
                }
                this.dialogRef.close();
            }
        );
    }

}
