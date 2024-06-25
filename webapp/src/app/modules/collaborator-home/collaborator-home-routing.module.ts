import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CollaboratorHomeComponent } from './collaborator-home.component';



const routes: Routes = [
  {
    path: '',
    component: CollaboratorHomeComponent
},
{
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CollaboratorHomeRoutingModule { }
