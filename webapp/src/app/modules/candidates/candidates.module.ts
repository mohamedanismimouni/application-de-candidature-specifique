import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CandidatesRoutingModule } from './candidates-routing.module';
import { CandidatesComponent } from './candidates.component';
import {FormsModule} from '@angular/forms';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import {RippleModule} from "primeng/ripple";
import {ConfirmPopupModule} from "primeng/confirmpopup";
import {ToastModule} from "primeng/toast";
import {CalendarModule} from "primeng/calendar";
import {MatChipsModule} from '@angular/material/chips';
import {CardModule} from 'primeng/card';
import {TimelineModule} from 'primeng/timeline';





@NgModule({
  declarations: [CandidatesComponent],
    imports: [
        DialogModule,
        CommonModule,
        CandidatesRoutingModule,
        FormsModule,
        Ng2SearchPipeModule,
        TableModule,
        ButtonModule,
        DropdownModule,
        RippleModule,
        ConfirmPopupModule,
        ToastModule,
        CalendarModule,
        MatChipsModule,
        CardModule,
        TimelineModule


    ]
})
export class CandidatesModule {




 }
