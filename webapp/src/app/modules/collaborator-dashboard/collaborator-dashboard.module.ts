import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { CollaboratorDashboardRoutingModule } from './collaborator-dashboard-routing.module';

import { CollaboratorDashboardService } from './collaborator-dashboard.service';

import { CollaboratorDashboardComponent } from './collaborator-dashboard.component';
import { CareerProgressComponent } from './career-progress/career-progress.component';
import { EvaluationsComponent } from './evaluations/evaluations.component';
import { MentorshipsComponent } from './mentorships/mentorships.component';
import { SkillsComponent } from './skills/skills.component';
import { InitializeTeamMemberDialogComponent } from './initialize-team-member-dialog/initialize-team-member-dialog.component';
import { InitiateOnboardingProcessDialogComponent } from './initiate-onboarding-process-dialog/initiate-onboarding-process-dialog.component';
import { OnboardingProcessDetailsDialogComponent } from './onboarding-process-details-dialog/onboarding-process-details-dialog.component';
import { MentorshipDetailsDialogComponent } from './mentorship-details-dialog/mentorship-details-dialog.component';
import { SetupEvaluationDateDialogComponent } from './setup-evaluation-date-dialog/setup-evaluation-date-dialog.component';
import { EvaluationSurveyResponsesDialogComponent } from './evaluation-survey-responses-dialog/evaluation-survey-responses-dialog.component';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';


@NgModule({
    declarations: [
        CollaboratorDashboardComponent,
        CareerProgressComponent,
        EvaluationsComponent,
        MentorshipsComponent,
        SkillsComponent,
        InitializeTeamMemberDialogComponent,
        InitiateOnboardingProcessDialogComponent,
        OnboardingProcessDetailsDialogComponent,
        MentorshipDetailsDialogComponent,
        SetupEvaluationDateDialogComponent,
        EvaluationSurveyResponsesDialogComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        CollaboratorDashboardRoutingModule,
        DropdownModule,
        FormsModule
    ],
    providers: [
        CollaboratorDashboardService
    ]
})
export class CollaboratorDashboardModule { }
