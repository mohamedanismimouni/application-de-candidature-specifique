import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CareerManagementComponent } from './career-management.component';
import { AwarenessComponent } from './awareness/awareness.component';
import { AwarenessSurveyComponent } from './awareness-survey/awareness-survey.component';
import { SkillsIdentificationComponent } from './skills-identification/skills-identification.component';
import { ExplorationComponent } from './exploration/exploration.component';
import { ExplorationSurveyComponent } from './exploration-survey/exploration-survey.component';
import { CareersExplorationComponent } from './careers-exploration/careers-exploration.component';
import { GoalSettingComponent } from './goal-setting/goal-setting.component';
import { EvaluationComponent } from './evaluation/evaluation.component';


const CAREER_MANAGEMENT_ROUTES: Routes = [
    {
        path: '',
        component: CareerManagementComponent,
        children: [
            {
                path: 'awareness',
                component: AwarenessComponent,
                children: [
                    {
                        path: 'awareness-survey',
                        component: AwarenessSurveyComponent
                    },
                    {
                        path: 'skills-identification',
                        component: SkillsIdentificationComponent
                    },
                    {
                        path: '**',
                        redirectTo: 'awareness-survey',
                        pathMatch: 'full'
                    }
                ]
            },
            {
                path: 'exploration',
                component: ExplorationComponent,
                children: [
                    {
                        path: 'exploration-survey',
                        component: ExplorationSurveyComponent
                    },
                    {
                        path: 'careers-exploration',
                        component: CareersExplorationComponent
                    },
                    {
                        path: '**',
                        redirectTo: 'exploration-survey',
                        pathMatch: 'full'
                    }
                ]
            },
            {
                path: 'goal-setting',
                component: GoalSettingComponent
            },
            {
                path: 'evaluation',
                component: EvaluationComponent
            },
            {
                path: '**',
                redirectTo: 'awareness',
                pathMatch: 'full'
            }
        ]
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(CAREER_MANAGEMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class CareerManagementRoutingModule { }
