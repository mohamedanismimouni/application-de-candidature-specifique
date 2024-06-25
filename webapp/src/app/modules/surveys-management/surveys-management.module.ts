import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { SurveysManagementRoutingModule } from './surveys-management-routing.module';

import { SurveysManagementComponent } from './surveys-management.component';
import { CreateQuestionDialogComponent } from './create-question-dialog/create-question-dialog.component';


@NgModule({
    declarations: [
        SurveysManagementComponent,
        CreateQuestionDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        SurveysManagementRoutingModule
    ]
})
export class SurveysManagementModule { }
