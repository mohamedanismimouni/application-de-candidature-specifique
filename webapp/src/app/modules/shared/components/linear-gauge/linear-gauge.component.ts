import { Component, Input } from '@angular/core';


@Component({
    selector: 'app-linear-gauge',
    templateUrl: './linear-gauge.component.html',
    styleUrls: [ './linear-gauge.component.scss' ]
})
export class LinearGaugeComponent {

    @Input()
    primaryPercentage: number;

    @Input()
    secondaryPercentage: number;

}
