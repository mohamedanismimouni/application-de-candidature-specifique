import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UsersManagementComponent } from './users-management.component';


const USERS_MANAGEMENT_ROUTES: Routes = [
    {
        path: '',
        component: UsersManagementComponent
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(USERS_MANAGEMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class UsersManagementRoutingModule { }
