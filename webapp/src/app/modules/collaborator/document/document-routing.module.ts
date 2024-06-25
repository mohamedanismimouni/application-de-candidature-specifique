import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DocumentComponent } from './document.component';


const DOCUMENT_ROUTES: Routes = [
    {
        path: '',
        component: DocumentComponent
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [ RouterModule.forChild(DOCUMENT_ROUTES) ],
    exports: [ RouterModule ]
})
export class DocumentRoutingModule { }
