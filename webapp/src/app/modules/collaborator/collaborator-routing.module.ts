import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CollaboratorComponent } from './collaborator.component';


const COLLABORATOR_ROUTES: Routes = [
    {
        path: '',
        component: CollaboratorComponent,
        children: [
            {
                path: 'collaborator-home',
                loadChildren: () => import('../collaborator-home/collaborator-home.module').then((m) => m.CollaboratorHomeModule)
            },
            {
                path: 'team',
                loadChildren: () => import('../team/team.module').then((m) => m.TeamModule)
            },
            {
                path: 'collaborator-dashboard/:collaborator-id',
                loadChildren: () => import('../collaborator-dashboard/collaborator-dashboard.module').then((m) => m.CollaboratorDashboardModule)
            },
            {
                path: 'career-management',
                loadChildren: () => import('../career-management/career-management.module').then((m) => m.CareerManagementModule)
            },
            {
                path: 'document',
                loadChildren: () => import('./document/document.module').then((m) => m.DocumentModule)
            },
            {
                path: '**',
                redirectTo: 'collaborator-home',
                pathMatch: 'full'
            }
        ]
    }
];

@NgModule({
    imports: [ RouterModule.forChild(COLLABORATOR_ROUTES) ],
    exports: [ RouterModule ]
})
export class CollaboratorRoutingModule { }
