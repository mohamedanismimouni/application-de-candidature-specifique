import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TeamsManagementComponent } from './teams-management.component';


const TEAMS_MANAGEMENT_ROUTES: Routes = [
    {
        path: '',
        component: TeamsManagementComponent
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(TEAMS_MANAGEMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class TeamsManagementRoutingModule { }
