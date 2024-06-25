import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CareersManagementComponent } from './careers-management.component';


const CAREERS_MANAGEMENT_ROUTES: Routes = [
    {
        path: '',
        component: CareersManagementComponent
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(CAREERS_MANAGEMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class CareersManagementRoutingModule { }
