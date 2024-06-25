import { Component } from '@angular/core';


@Component({
    templateUrl: './exploration.component.html',
    styleUrls: [ './exploration.component.scss' ]
})
export class ExplorationComponent {

    tabs = [
        {
            title: 'Exploration survey',
            route: 'exploration-survey',
            icon : 'bar-chart-outline'
        },
        {
            title: 'Careers exploration',
            route: 'careers-exploration',
            icon: 'trending-up-outline',
        }
    ];

}
