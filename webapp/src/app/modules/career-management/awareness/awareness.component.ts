import { Component } from '@angular/core';


@Component({
    templateUrl: './awareness.component.html',
    styleUrls: [ './awareness.component.scss' ]
})
export class AwarenessComponent {

    tabs = [
        {
            title: 'Awareness survey',
            route: 'awareness-survey',
            icon:'layers-outline'
        },
        {
            title: 'Skills identification',
            route: 'skills-identification',
            icon: 'award-outline',

        }
    ];

}
