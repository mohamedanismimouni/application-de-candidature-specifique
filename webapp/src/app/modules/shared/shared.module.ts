import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';

import { PieChartComponent } from './components/pie-chart/pie-chart.component';
import { DatatableComponent } from './components/datatable/datatable.component';
import { GraphComponent } from './components/graph/graph.component';
import { LinearGaugeComponent } from './components/linear-gauge/linear-gauge.component';
import { RatingScaleComponent } from './components/rating-scale/rating-scale.component';
import { ChoicesPickerComponent } from './components/choices-picker/choices-picker.component';
import { SurveyComponent } from './components/survey/survey.component';

import { AlphabeticalPositionPipe } from './pipes/alphabetical-position.pipe';
import { RatingPipe } from './pipes/rating.pipe';
import { NbBadgeModule } from '@nebular/theme';
import { NbTooltipModule } from '@nebular/theme';
import { SpinnerComponent } from './components/spinner/spinner.component';



@NgModule({
    declarations: [
        PieChartComponent,
        DatatableComponent,
        GraphComponent,
        LinearGaugeComponent,
        RatingScaleComponent,
        ChoicesPickerComponent,
        SurveyComponent,
        AlphabeticalPositionPipe,
        RatingPipe,
        SpinnerComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        NbBadgeModule,
        NbTooltipModule,

    ],
    exports: [
        PieChartComponent,
        DatatableComponent,
        GraphComponent,
        LinearGaugeComponent,
        RatingScaleComponent,
        ChoicesPickerComponent,
        SurveyComponent,
        AlphabeticalPositionPipe,
        RatingPipe,
        SpinnerComponent
    ]
})
export class SharedModule { }
