import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DragDropModule } from '@angular/cdk/drag-drop';

import {
    NbThemeModule,
    NbCardModule,
    NbLayoutModule,
    NbSidebarModule,
    NbInputModule,
    NbSelectModule,
    NbDatepickerModule,
    NbButtonModule,
    NbToggleModule,
    NbIconModule,
    NbToastrModule,
    NbUserModule,
    NbListModule,
    NbMenuModule,
    NbContextMenuModule,
    NbDialogModule,
    NbSpinnerModule,
    NbAccordionModule,
    NbActionsModule,
    NbTabsetModule,
    NbRouteTabsetModule,
    NbTooltipModule
} from '@nebular/theme';

import { NbEvaIconsModule } from '@nebular/eva-icons';

import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgxGraphModule } from '@swimlane/ngx-graph';

import { THEME_DEFAULT, TOASTR_CONFIG, DIALOG_CONFIG } from './constants/common.constant';
import { MultiSelectModule } from 'primeng/multiselect';


@NgModule({
    imports: [
        ReactiveFormsModule,
        HttpClientModule,
        DragDropModule,
        NbThemeModule.forRoot({ name: THEME_DEFAULT }),
        NbLayoutModule,
        NbSidebarModule.forRoot(),
        NbCardModule,
        NbInputModule,
        NbSelectModule,
        NbDatepickerModule.forRoot(),
        NbButtonModule,
        NbToggleModule,
        NbEvaIconsModule,
        NbIconModule,
        NbToastrModule.forRoot(TOASTR_CONFIG),
        NbUserModule,
        NbListModule,
        NbMenuModule.forRoot(),
        NbContextMenuModule,
        NbDialogModule.forRoot(DIALOG_CONFIG),
        NbSpinnerModule,
        NbAccordionModule,
        NbActionsModule,
        NbTabsetModule,
        NbRouteTabsetModule,
        NbTooltipModule,
        NgxChartsModule,
        NgxGraphModule,
        MultiSelectModule

    ],
    exports: [
        ReactiveFormsModule,
        HttpClientModule,
        DragDropModule,
        NbThemeModule,
        NbLayoutModule,
        NbSidebarModule,
        NbCardModule,
        NbInputModule,
        NbSelectModule,
        NbDatepickerModule,
        NbButtonModule,
        NbToggleModule,
        NbEvaIconsModule,
        NbIconModule,
        NbToastrModule,
        NbUserModule,
        NbListModule,
        NbMenuModule,
        NbContextMenuModule,
        NbDialogModule,
        NbSpinnerModule,
        NbAccordionModule,
        NbActionsModule,
        NbTabsetModule,
        NbRouteTabsetModule,
        NbTooltipModule,
        NgxChartsModule,
        NgxGraphModule
    ]
})
export class AppLibrariesModule { }
