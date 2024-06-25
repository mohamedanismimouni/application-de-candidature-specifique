import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import { FormService } from '../../../services/form.service';
import { SkillService } from '../../../services/skill.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';

import { ISkill } from '../../../models/skill.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './create-skill-dialog.component.html',
    styleUrls: [ './create-skill-dialog.component.scss' ]
})
export class CreateSkillDialogComponent {

    @Input()
    data: any;

    createSkillFormGroup: FormGroup = this.formBuilder.group({
        label:          [ '', Validators.required, blankValidator() ]
    });

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<CreateSkillDialogComponent>,
        public formService: FormService,
        private skillService: SkillService,
        private toastrService: ToastrService
    ) { }

    onCancel() {
        this.dialogRef.close(false);
    }

    onSubmit() {
        this.loadingState = true;
        const skill: ISkill = this.createSkillFormGroup.value;
        skill.skillType = this.data.skillType;
        this.skillService.createSkill(skill).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Skill created successfully',
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
                        this.createSkillFormGroup.get('label')
                            .setErrors({ notUnique: true });
                        break;
                }
            }
        );
    }

}
