import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';


@Injectable()
export class FormService {

    constructor() { }

    checkInputInvalidity(control: AbstractControl): boolean {
        return control && control.invalid && (control.dirty || control.touched);
    }

    checkInputError(control: AbstractControl, error: string): boolean {
        return control && control.hasError(error) && (control.dirty || control.touched);
    }

}
