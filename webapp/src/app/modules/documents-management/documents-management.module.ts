import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentsManagementRoutingModule } from './documents-management-routing.module';
import { RequestsComponent } from './requests/requests.component';
import { HistoryComponent } from './history/history.component';
import { TemplatesComponent } from './templates/templates.component';
import { SharedModule } from '../shared/shared.module';
import { AppLibrariesModule } from 'src/app/app-libraries.module';
import { ProgressComponent } from './progress/progress.component';
import {DropdownModule} from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import {  ButtonModule } from 'primeng/button';
import { DocumentRequestRejectionComponent } from './document-request-rejection/document-request-rejection.component';
import { NgxDocViewerModule } from 'ngx-doc-viewer';
import { UploadPayrollComponent } from './upload-payroll/upload-payroll.component';
import { ValidateRequestComponent } from './validate-request/validate-request.component';
import { NbAutocompleteModule } from '@nebular/theme';
import {AutoCompleteModule} from 'primeng/autocomplete';
import { UploadTemplateComponent } from './upload-template/upload-template.component';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import {RadioButtonModule} from 'primeng/radiobutton';
import { TabViewModule } from 'primeng/tabview';

import { DocumentRequestValidationDialogComponent } from './document-request-validation-dialog/document-request-validation-dialog.component';
import { UploadUsefulDocumentComponent } from './upload-useful-document/upload-useful-document.component';
@NgModule({
  declarations: [UploadUsefulDocumentComponent, RequestsComponent, HistoryComponent, TemplatesComponent, ProgressComponent, DocumentRequestRejectionComponent, UploadPayrollComponent, ValidateRequestComponent, UploadTemplateComponent, DocumentRequestValidationDialogComponent],
  imports: [
    CommonModule,
    DocumentsManagementRoutingModule,
    CommonModule,
    AppLibrariesModule,
    SharedModule,
    DropdownModule,
    FormsModule,
    TableModule,
    InputTextModule,
    ButtonModule,
    NgxDocViewerModule,
    NbAutocompleteModule,
    AutoCompleteModule,
    RadioButtonModule,
    TabViewModule,
    BsDatepickerModule.forRoot()

  ]
})
export class DocumentsManagementModule { }
