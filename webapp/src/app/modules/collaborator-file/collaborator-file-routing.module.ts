import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersManagementComponent } from '../users-management/users-management.component';
import { CollaboratorFileComponent } from './collaborator-file/collaborator-file.component';


const routes: Routes = [{
  path: '',
  component: CollaboratorFileComponent
},
{
  path: '**',
  redirectTo: '',
  pathMatch: 'full'
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CollaboratorFileRoutingModule { }
