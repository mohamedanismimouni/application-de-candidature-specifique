import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HRResponsibleComponent } from './hr-responsible.component';


const HR_RESPONSIBLE_ROUTES: Routes = [
    {
        path: '',
        component: HRResponsibleComponent,
        children: [
            {
                path: 'document',
                loadChildren: () => import('../collaborator/document/document.module').then((m) => m.DocumentModule)
            },
            {
                path: 'profile/collaborator-dashboard/:collaborator-id',
                loadChildren: () => import('../collaborator-dashboard/collaborator-dashboard.module').then((m) => m.CollaboratorDashboardModule)
            },
            {
                path: 'users',
                loadChildren: () => import('../users-management/users-management.module').then((m) => m.UsersManagementModule)
            },

            {
                path: 'candidates',
                loadChildren: () => import('../candidates/candidates.module').then((m) => m.CandidatesModule)
            }, {
                path: 'jobs',
                loadChildren: () => import('../jobs/jobs.module').then((m) => m.JobsModule)
            },
            {
                path: 'collaborators',
                loadChildren: () => import('../collaborator-file/collaborator-file.module').then((m) => m.CollaboratorFileModule)
            },
            {
                path: 'teams',
                loadChildren: () => import('../teams-management/teams-management.module').then((m) => m.TeamsManagementModule)
            },
            {
                path: 'careers',
                loadChildren: () => import('../careers-management/careers-management.module').then((m) => m.CareersManagementModule)
            },
            {
                path: 'surveys',
                loadChildren: () => import('../surveys-management/surveys-management.module').then((m) => m.SurveysManagementModule)
            },
            {
                path: 'skills',
                loadChildren: () => import('../skills-management/skills-management.module').then((m) => m.SkillsManagementModule)
            },
            {
                path: 'documents',
                loadChildren: () => import('../documents-management/documents-management.module').then((m) => m.DocumentsManagementModule)
            },
            {
                path: '**',
                redirectTo: 'collaborator-home',
                pathMatch: 'full'
            },
            {
                path: 'collaborator-home',
                loadChildren: () => import('../collaborator-home/collaborator-home.module').then((m) => m.CollaboratorHomeModule)
            },


        ]
    }
];

@NgModule({
    imports: [ RouterModule.forChild(HR_RESPONSIBLE_ROUTES) ],
    exports: [ RouterModule ]
})
export class HRResponsibleRoutingModule { }
