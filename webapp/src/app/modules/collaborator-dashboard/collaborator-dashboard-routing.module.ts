import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CollaboratorDashboardComponent } from './collaborator-dashboard.component';
import { CareerProgressComponent } from './career-progress/career-progress.component';
import { EvaluationsComponent } from './evaluations/evaluations.component';
import { MentorshipsComponent } from './mentorships/mentorships.component';
import { SkillsComponent } from './skills/skills.component';


const COLLABORATOR_DASHBOARD_ROUTES: Routes = [
    {
        path: '',
        component: CollaboratorDashboardComponent,
        children: [
            {
                path: 'career-progress',
                component: CareerProgressComponent
            },
            {
                path: 'evaluations',
                component: EvaluationsComponent
            },
            {
                path: 'mentorships',
                component: MentorshipsComponent
            },
            {
                path: 'skills',
                component: SkillsComponent
            },
            {
                path: '**',
                redirectTo: 'career-progress',
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
    imports: [ RouterModule.forChild(COLLABORATOR_DASHBOARD_ROUTES) ],
    exports: [ RouterModule ]
})
export class CollaboratorDashboardRoutingModule { }
