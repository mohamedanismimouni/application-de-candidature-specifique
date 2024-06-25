import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TeamComponent } from './team.component';


const TEAM_ROUTES: Routes = [
    {
        path: '',
        component: TeamComponent
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(TEAM_ROUTES) ],
    exports: [ RouterModule ]
})
export class TeamRoutingModule { }
