import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { UsersManagementRoutingModule } from './users-management-routing.module';

import { UsersManagementComponent } from './users-management.component';
import { CreateUserDialogComponent } from './create-user-dialog/create-user-dialog.component';


@NgModule({
    declarations: [
        UsersManagementComponent,
        CreateUserDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        UsersManagementRoutingModule
    ]
})
export class UsersManagementModule { }
