import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { SharedModule } from '../shared/shared.module';
import { CareerManagementRoutingModule } from './career-management-routing.module';

import { CareerManagementComponent } from './career-management.component';
import { AwarenessComponent } from './awareness/awareness.component';
import { AwarenessSurveyComponent } from './awareness-survey/awareness-survey.component';
import { SkillsIdentificationComponent } from './skills-identification/skills-identification.component';
import { ExplorationComponent } from './exploration/exploration.component';
import { ExplorationSurveyComponent } from './exploration-survey/exploration-survey.component';
import { CareersExplorationComponent } from './careers-exploration/careers-exploration.component';
import { GoalSettingComponent } from './goal-setting/goal-setting.component';
import { EvaluationComponent } from './evaluation/evaluation.component';


@NgModule({
    declarations: [
        CareerManagementComponent,
        AwarenessComponent,
        AwarenessSurveyComponent,
        SkillsIdentificationComponent,
        ExplorationComponent,
        ExplorationSurveyComponent,
        CareersExplorationComponent,
        GoalSettingComponent,
        EvaluationComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        SharedModule,
        CareerManagementRoutingModule
    ]
})
export class CareerManagementModule { }
