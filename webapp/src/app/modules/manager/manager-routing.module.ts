import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ManagerComponent } from './manager.component';


const MANAGER_ROUTES: Routes = [
    {
        path: '',
        component: ManagerComponent,
        children: [
            {
                path: 'team',
                loadChildren: () => import('../team/team.module').then((m) => m.TeamModule)
            },
            {
                path: 'collaborator-dashboard/:collaborator-id',
                loadChildren: () => import('../collaborator-dashboard/collaborator-dashboard.module').then((m) => m.CollaboratorDashboardModule)
            },
            {
                path: 'profiles',
                loadChildren: () => import('../profiles-management/profiles-management.module').then((m) => m.ProfilesManagementModule)
            },
            {
                path: 'collaborator-home',
                loadChildren: () => import('../collaborator-home/collaborator-home.module').then((m) => m.CollaboratorHomeModule)
            },
            {
                path: 'document',
                loadChildren: () => import('../collaborator/document/document.module').then((m) => m.DocumentModule)
            },
            {
                path: 'profile/collaborator-dashboard/:collaborator-id',
                loadChildren: () => import('../collaborator-dashboard/collaborator-dashboard.module').then((m) => m.CollaboratorDashboardModule)
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
    imports: [ RouterModule.forChild(MANAGER_ROUTES) ],
    exports: [ RouterModule ]
})
export class ManagerRoutingModule { }
