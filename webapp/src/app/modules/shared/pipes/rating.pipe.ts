import { PipeTransform, Pipe } from '@angular/core';


@Pipe({
    name: 'rating'
})
export class RatingPipe implements PipeTransform {

    transform(rating: number): string {
        switch (rating) {
            case -4:
                return 'Extremely dissatisfied';
            case -3:
                return 'Highly dissatisfied';
            case -2:
                return 'Dissatisfied';
            case -1:
                return 'Slightly dissatisfied';
            case 0:
                return 'Neutral';
            case 1:
                return 'Slightly satisfied';
            case 2:
                return 'Satisfied';
            case 3:
                return 'Highly satisfied';
            case 4:
                return 'Extremely satisfied';
        }
    }

}
