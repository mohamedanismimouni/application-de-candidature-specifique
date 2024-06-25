import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SkillsManagementComponent } from './skills-management.component';


const SKILLS_MANAGEMENT_ROUTES: Routes = [
    {
        path: '',
        component: SkillsManagementComponent
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(SKILLS_MANAGEMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class SkillsManagementRoutingModule { }
