import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollaboratorFileRoutingModule } from './collaborator-file-routing.module';
import { CollaboratorFileComponent } from './collaborator-file/collaborator-file.component';
import { AppLibrariesModule } from 'src/app/app-libraries.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import {DropdownModule} from 'primeng/dropdown';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [CollaboratorFileComponent],
  imports: [
    CommonModule,
    AppLibrariesModule,
    CollaboratorFileRoutingModule,
    NgxPaginationModule,
    FormsModule,
    Ng2SearchPipeModule,
    DropdownModule,  
    SharedModule
  ]
})
export class CollaboratorFileModule { }
