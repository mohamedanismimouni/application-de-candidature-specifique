import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProfilesManagementComponent } from './profiles-management.component';


const PROFILES_MANAGEMENT_ROUTES: Routes = [
    {
        path: '',
        component: ProfilesManagementComponent
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(PROFILES_MANAGEMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class ProfilesManagementRoutingModule { }
