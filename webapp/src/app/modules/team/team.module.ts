import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { TeamRoutingModule } from './team-routing.module';

import { TeamComponent } from './team.component';
import { TeamMemberDetailsDialogComponent } from './team-member-details-dialog/team-member-details-dialog.component';
import { TeamEvaluationDateDialogComponent } from './team-evaluation-date-dialog/team-evaluation-date-dialog.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';


@NgModule({
    declarations: [
        TeamComponent,
        TeamMemberDetailsDialogComponent,
        TeamEvaluationDateDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        TeamRoutingModule,
        NgxPaginationModule,
        DropdownModule,
        FormsModule


    ]
})
export class TeamModule { }
