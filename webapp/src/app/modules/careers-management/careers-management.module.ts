import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { CareersManagementRoutingModule } from './careers-management-routing.module';

import { CareersManagementComponent } from './careers-management.component';
import { CreateCareerStepDialogComponent } from './create-career-step-dialog/create-career-step-dialog.component';
import { CreateCareerPathDialogComponent } from './create-career-path-dialog/create-career-path-dialog.component';


@NgModule({
    declarations: [
        CareersManagementComponent,
        CreateCareerStepDialogComponent,
        CreateCareerPathDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        CareersManagementRoutingModule
    ]
})
export class CareersManagementModule { }
