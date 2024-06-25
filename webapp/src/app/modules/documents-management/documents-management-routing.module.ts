import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HistoryComponent } from './history/history.component';
import { RequestsComponent } from './requests/requests.component';
import { TemplatesComponent } from './templates/templates.component';
import { UploadPayrollComponent } from './upload-payroll/upload-payroll.component';


const routes: Routes = [
   {
    path: 'templates',
    component: TemplatesComponent
  },
  {
    path: 'requests',
    component: RequestsComponent
  },
  {
    path: 'history',
    component: HistoryComponent
  },
  {
    path: 'payRoll',
    component: UploadPayrollComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentsManagementRoutingModule { }
