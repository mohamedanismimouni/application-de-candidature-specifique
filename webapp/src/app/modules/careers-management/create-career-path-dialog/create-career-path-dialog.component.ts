import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import { FormService } from '../../../services/form.service';
import { CareerPathService } from '../../../services/career-path.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';

import { ICareerPath } from '../../../models/career-path.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './create-career-path-dialog.component.html',
    styleUrls: [ './create-career-path-dialog.component.scss' ]
})
export class CreateCareerPathDialogComponent {

    @Input()
    data: any;

    careerPathOrientation = true;

    newCareerStep = false;

    createCareerPathFormGroup: FormGroup = this.formBuilder.group({
        careerStep:         [ '', Validators.required, blankValidator() ],
        yearsOfExperience:  [ '', [ Validators.required, Validators.min(1) ] ]
    });

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<CreateCareerPathDialogComponent>,
        public formService: FormService,
        private careerPathService: CareerPathService,
        private toastrService: ToastrService
    ) { }

    onReverse() {
        this.careerPathOrientation = !this.careerPathOrientation;
    }

    onChangeCareerStepSelectionMode() {
        this.createCareerPathFormGroup.get('careerStep').reset();
        this.newCareerStep = !this.newCareerStep;
    }

    onCancel() {
        this.dialogRef.close(false);
    }

    onSubmit() {
        this.loadingState = true;
        let careerPathEdge = {};
        if (this.newCareerStep) {
            careerPathEdge = {
                label: this.createCareerPathFormGroup.get('careerStep').value
            };
        } else {
            const selectedCareerPathEdge = this.data.possibleCareerPathEdges.find(
                (value) => value.id === this.createCareerPathFormGroup.get('careerStep').value);
            careerPathEdge = {
                id: selectedCareerPathEdge.id,
                label: selectedCareerPathEdge.label
            };
        }
        let careerPath = {};
        if (this.careerPathOrientation) {
            careerPath = {
                yearsOfExperience: this.createCareerPathFormGroup.get('yearsOfExperience').value,
                fromCareerStep: this.data.selectedCareerStep,
                toCareerStep: careerPathEdge
            };
        } else {
            careerPath = {
                yearsOfExperience: this.createCareerPathFormGroup.get('yearsOfExperience').value,
                fromCareerStep: careerPathEdge,
                toCareerStep: this.data.selectedCareerStep
            };
        }
        this.careerPathService.createCareerPath(careerPath as ICareerPath).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Career path created successfully',
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
                    case 404:
                        this.toastrService.showStatusToastr(
                            'Oups career step is not found, seems like you are seeing '
                            + 'some outdated data. Careers graph is now refreshed!',
                            ToastrStatusEnum.DANGER);
                        this.dialogRef.close(true);
                        break;
                    case 409:
                        if (this.newCareerStep) {
                            this.createCareerPathFormGroup.get('careerStep')
                                .setErrors({ notUnique: true });
                        } else {
                            this.toastrService.showStatusToastr(
                                'A career path between the two selected '
                                + 'career steps already exists',
                                ToastrStatusEnum.DANGER);
                            this.dialogRef.close(true);
                        }
                        break;
                    default:
                        break;
                }
            }
        );
    }

}
