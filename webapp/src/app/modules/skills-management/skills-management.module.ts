import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { SkillsManagementRoutingModule } from './skills-management-routing.module';

import { SkillsManagementComponent } from './skills-management.component';
import { CreateSkillDialogComponent } from './create-skill-dialog/create-skill-dialog.component';


@NgModule({
    declarations: [
        SkillsManagementComponent,
        CreateSkillDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        SkillsManagementRoutingModule
    ]
})
export class SkillsManagementModule { }
