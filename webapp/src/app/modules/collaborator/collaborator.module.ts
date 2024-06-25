import { NgModule } from '@angular/core';
import { NbLayoutModule } from '@nebular/theme';

import { CollaboratorRoutingModule } from './collaborator-routing.module';

import { CollaboratorComponent } from './collaborator.component';
import { DocumentModule } from './document/document.module';


@NgModule({
    declarations: [
        CollaboratorComponent,
      
    ],
    imports: [
        CollaboratorRoutingModule,
        DocumentModule,
        NbLayoutModule,
    ]
})
export class CollaboratorModule { }
