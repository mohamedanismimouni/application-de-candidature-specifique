import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SurveysManagementComponent } from './surveys-management.component';

import { SurveyTypeEnum } from '../../enumerations/survey-type.enum';


const SURVEYS_MANAGEMENT_ROUTES: Routes = [
    {
        path: ':survey-type',
        component: SurveysManagementComponent
    },
    {
        path: '**',
        redirectTo: SurveyTypeEnum.AWARENESS.toLowerCase(),
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(SURVEYS_MANAGEMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class SurveysManagementRoutingModule { }
