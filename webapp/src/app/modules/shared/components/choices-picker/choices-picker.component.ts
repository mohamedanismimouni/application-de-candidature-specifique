import { Component, OnChanges, Input, Output, EventEmitter } from '@angular/core';

import { IChoice } from '../../../../models/choice.model';


@Component({
    selector: 'app-choices-picker',
    templateUrl: './choices-picker.component.html',
    styleUrls: [ './choices-picker.component.scss' ]
})
export class ChoicesPickerComponent implements OnChanges {

    @Input()
    enabled: boolean;

    @Input()
    multipleChoices: boolean;

    @Input()
    choices: Array<IChoice>;

    selectedChoicesValue: Array<IChoice>;

    @Input()
    get selectedChoices() {
        return this.selectedChoicesValue;
    }

    set selectedChoices(selectedChoices: Array<IChoice>) {
        this.selectedChoicesValue = selectedChoices;
        this.selectedChoicesChange.emit(this.selectedChoicesValue);
    }

    @Output()
    selectedChoicesChange = new EventEmitter<Array<IChoice>>();

    ngOnChanges() {
        if (this.enabled === undefined) {
            this.enabled = true;
        }
        if (this.multipleChoices === undefined) {
            this.multipleChoices = true;
        }
        if (this.choices === undefined) {
            this.choices = [];
        }
        if (this.selectedChoicesValue === undefined) {
            this.selectedChoicesValue = [];
        }
    }

    onChoiceSelection(choice: IChoice) {
        if (!this.enabled) {
            return;
        }
        if (!choice.selected) {
            if (!this.multipleChoices) {
                this.choices.forEach(c => c.selected = false);
            }
            choice.selected = true;
            this.selectedChoices = this.multipleChoices ? [ ...this.selectedChoices, choice ] : [ choice ];
        } else {
            choice.selected = false;
            this.selectedChoices = this.multipleChoices ? this.selectedChoices.filter(c => c.id !== choice.id) : [];
        }
    }

}
