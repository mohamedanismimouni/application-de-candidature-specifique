import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import { FormService } from '../../../services/form.service';
import { CareerStepService } from '../../../services/career-step.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';

import { ICareerStep } from '../../../models/career-step.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './create-career-step-dialog.component.html',
    styleUrls: [ './create-career-step-dialog.component.scss' ]
})
export class CreateCareerStepDialogComponent {

    createCareerStepFormGroup: FormGroup = this.formBuilder.group({
        label: [ '', Validators.required, blankValidator() ]
    });

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<CreateCareerStepDialogComponent>,
        public formService: FormService,
        private careerStepService: CareerStepService,
        private toastrService: ToastrService
    ) { }

    onCancel() {
        this.dialogRef.close(false);
    }

    onSubmit() {
        this.loadingState = true;
        const careerStep: ICareerStep = this.createCareerStepFormGroup.value;
        this.careerStepService.createCareerStep(careerStep).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Career step created successfully',
                    ToastrStatusEnum.SUCCESS);
                this.dialogRef.close(true);
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
                        this.createCareerStepFormGroup.get('label')
                            .setErrors({ notUnique: true });
                        break;
                    default:
                        break;
                }
            }
        );
    }

}
