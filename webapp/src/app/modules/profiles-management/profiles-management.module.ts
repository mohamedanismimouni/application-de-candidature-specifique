import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { ProfilesManagementRoutingModule } from './profiles-management-routing.module';

import { ProfilesManagementComponent } from './profiles-management.component';
import { CreateProfileDialogComponent } from './create-profile-dialog/create-profile-dialog.component';
import { CreateRequiredSkillDialogComponent } from './create-required-skill-dialog/create-required-skill-dialog.component';


@NgModule({
    declarations: [
        ProfilesManagementComponent,
        CreateProfileDialogComponent,
        CreateRequiredSkillDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        ProfilesManagementRoutingModule
    ]
})
export class ProfilesManagementModule { }
