import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import { FormService } from '../../../services/form.service';
import { ProfileService } from '../../../services/profile.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';

import { IProfile } from '../../../models/profile.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './create-profile-dialog.component.html',
    styleUrls: [ './create-profile-dialog.component.scss' ]
})
export class CreateProfileDialogComponent {

    @Input()
    data: any;

    createProfileFormGroup: FormGroup = this.formBuilder.group({
        label:      [ '', Validators.required, blankValidator() ]
    });

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<CreateProfileDialogComponent>,
        public formService: FormService,
        private profileService: ProfileService,
        private toastrService: ToastrService
    ) { }

    onCancel() {
        this.dialogRef.close();
    }

    onSubmit() {
        this.loadingState = true;
        const profile: IProfile = this.createProfileFormGroup.value;
        profile.team = this.data.team;
        profile.careerStep = this.data.careerStep;
        this.profileService.createProfile(profile).subscribe(
            (createdProfile: IProfile) => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Profile created successfully',
                    ToastrStatusEnum.SUCCESS);
                this.dialogRef.close(createdProfile);
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
                        this.createProfileFormGroup.get('label')
                            .setErrors({ notUnique: true });
                        break;
                }
            }
        );
    }

}
