import { Component, OnChanges, Input, Output, EventEmitter } from '@angular/core';


@Component({
    selector: 'app-rating-scale',
    templateUrl: './rating-scale.component.html',
    styleUrls: [ './rating-scale.component.scss' ]
})
export class RatingScaleComponent implements OnChanges {

    @Input()
    enabled: boolean;

    @Input()
    clearable: boolean;

    @Input()
    lowestValue: number;

    @Input()
    highestValue: number;

    value: number;

    @Input()
    get selectedValue() {
        return this.value;
    }

    set selectedValue(value: number) {
        this.value = value;
        this.selectedValueChange.emit(this.value);
    }

    @Output()
    selectedValueChange = new EventEmitter<number>();

    ngOnChanges() {
        if (this.enabled === undefined) {
            this.enabled = true;
        }
        if (this.clearable === undefined) {
            this.clearable = true;
        }
    }

    displayRatingEmoticon(value: number): boolean {
        return value >= this.lowestValue && value <= this.highestValue;
    }

    onRating(value: number) {
        if (!this.enabled) {
            return;
        }
        if (this.selectedValue === value && this.clearable) {
            this.selectedValue = undefined;
        } else if (this.selectedValue !== value) {
            this.selectedValue = value;
        }
    }

}
