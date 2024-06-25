import { Component, OnInit, OnChanges, OnDestroy, Input } from '@angular/core';

import { Subscription } from 'rxjs';

import { NbThemeService } from '@nebular/theme';

import { ISingleChartData } from '../../../../models/single-chart-data.model';

import { CHART_COLOR_SCHEME, EMPTY_CHART_LIGHT_COLOR_SCHEME, EMPTY_CHART_DARK_COLOR_SCHEME, THEME_DEFAULT, THEME_LIGHT } from '../../../../constants/common.constant';


@Component({
    selector: 'app-pie-chart',
    templateUrl: './pie-chart.component.html',
    styleUrls: [ './pie-chart.component.scss' ]
})
export class PieChartComponent implements OnInit, OnChanges, OnDestroy {

    themeSubscription: Subscription;

    @Input()
    title: string;

    @Input()
    data: Array<ISingleChartData>;

    chartColorScheme = CHART_COLOR_SCHEME;

    isEmpty = true;
    emptyChartData = [
        {
            name: '',
            value: 1
        }
    ];
    emptyChartColorScheme: any;

    constructor(
        private themeService: NbThemeService
    ) { }

    ngOnInit() {
        this.themeSubscription = this.themeService.getJsTheme().subscribe(
            (themeOptions) => {
                if (themeOptions.name === THEME_LIGHT) {
                    this.emptyChartColorScheme = EMPTY_CHART_LIGHT_COLOR_SCHEME;
                } else {
                    this.emptyChartColorScheme = EMPTY_CHART_DARK_COLOR_SCHEME;
                }
            },
            () => {
                if (THEME_DEFAULT === THEME_LIGHT) {
                    this.emptyChartColorScheme = EMPTY_CHART_LIGHT_COLOR_SCHEME;
                } else {
                    this.emptyChartColorScheme = EMPTY_CHART_DARK_COLOR_SCHEME;
                }
            }
        );
    }

    ngOnChanges() {
        this.isEmpty = this.data?.map(item => item.value).reduce((acc, cur) => acc + cur, 0) === 0;
    }

    ngOnDestroy() {
        this.themeSubscription?.unsubscribe();
    }

}
