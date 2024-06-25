import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { TeamsManagementRoutingModule } from './teams-management-routing.module';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from "ngx-pagination";

import { TeamsManagementComponent } from './teams-management.component';
import { CreateTeamDialogComponent } from './create-team-dialog/create-team-dialog.component';
import { AddTeamMemberDialogComponent } from './add-team-member-dialog/add-team-member-dialog.component';
import { Ng2SearchPipeModule } from 'ng2-search-filter';

import {MultiSelectModule} from 'primeng/multiselect';
import {TableModule} from 'primeng/table';


@NgModule({
    declarations: [
        TeamsManagementComponent,
        CreateTeamDialogComponent,
        AddTeamMemberDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        TeamsManagementRoutingModule,
        NgxPaginationModule,
        Ng2SearchPipeModule,
        FormsModule ,
        MultiSelectModule,
        TableModule
    ]
})
export class TeamsManagementModule { }
