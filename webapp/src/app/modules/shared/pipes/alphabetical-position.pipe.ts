import { PipeTransform, Pipe } from '@angular/core';


@Pipe({
    name: 'alphabeticalPosition'
})
export class AlphabeticalPositionPipe implements PipeTransform {

    transform(position: number): string {
        if (position <= 0) {
            return '-';
        } else {
            return String.fromCharCode(65 + position - 1);
        }
    }

}
