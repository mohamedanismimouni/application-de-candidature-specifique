import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DirectorComponent } from './director.component';


const DIRECTOR_ROUTES: Routes = [
    {
        path: '',
        component: DirectorComponent,
        children: [
            // {
            //     path: '**',
            //     redirectTo: 'default',
            //     pathMatch: 'full'
            // }
        ]
    }
];

@NgModule({
    imports: [ RouterModule.forChild(DIRECTOR_ROUTES) ],
    exports: [ RouterModule ]
})
export class DirectorRoutingModule { }
