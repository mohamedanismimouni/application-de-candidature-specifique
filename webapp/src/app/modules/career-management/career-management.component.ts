import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
    templateUrl: './career-management.component.html',
    styleUrls: [ './career-management.component.scss' ]
})
export class CareerManagementComponent {

    careerManagementNavigationItems = [
        {
            icon: 'bulb-outline',
            title: 'Awareness',
            route: '/app/collaborator/career-management/awareness',
            enabled: true,
            fulfilled: false
        },
        {
            icon: 'search-outline',
            title: 'Exploration',
            route: '/app/collaborator/career-management/exploration',
            enabled: true,
            fulfilled: false
        },
        {
            icon: 'pin-outline',
            title: 'Goal setting',
            route: '/app/collaborator/career-management/goal-setting',
            enabled: true,
            fulfilled: false
        },
        {
            icon: 'award-outline',
            title: 'Evaluation',
            route: '/app/collaborator/career-management/evaluation',
            enabled: true,
            fulfilled: false
        }
    ];

    constructor(
        private router: Router
    ) { }

    onCareerManagementNavigationItemClick(careerManagementNavigationItem: any) {

        if (careerManagementNavigationItem.enabled &&
                !this.isCareerManagementNavigationItemActive(careerManagementNavigationItem.route)) {

            this.router.navigate([ careerManagementNavigationItem.route ]);

        }

    }

    isCareerManagementNavigationItemActive(route: string): boolean {
        return this.router.url.startsWith(route);
    }

}
