import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CandidatesRoutingModule} from '../candidates/candidates-routing.module';
import {FormsModule} from '@angular/forms';
import {Ng2SearchPipeModule} from 'ng2-search-filter';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {JobsComponent} from './jobs.component';
import {JobsRoutingModule} from "./jobs-routing.module";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {DialogModule} from "primeng/dialog";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {DropdownModule} from "primeng/dropdown";

@NgModule({
    declarations: [JobsComponent],
    imports: [
        CommonModule,
        JobsRoutingModule,
        FormsModule,
        Ng2SearchPipeModule,
        TableModule,
        ButtonModule,
        ToastModule,
        ToolbarModule,
        DialogModule,
        ConfirmDialogModule,
        DropdownModule


    ]
})
export class JobsModule {
}
