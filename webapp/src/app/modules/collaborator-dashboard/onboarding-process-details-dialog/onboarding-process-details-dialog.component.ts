import { Component, OnInit, Input } from '@angular/core';

import { NbDialogRef } from '@nebular/theme';

import { OnboardingService } from '../../../services/onboarding.service';

import { IOnboarding } from '../../../models/onboarding.model';


@Component({
    templateUrl: './onboarding-process-details-dialog.component.html',
    styleUrls: [ './onboarding-process-details-dialog.component.scss' ]
})
export class OnboardingProcessDetailsDialogComponent implements OnInit {

    @Input()
    data: any;

    onboardings: Array<IOnboarding>;

    averageRating: string;

    constructor(
        private dialogRef: NbDialogRef<OnboardingProcessDetailsDialogComponent>,
        private onboardingService: OnboardingService
    ) { }

    ngOnInit() {
        this.onboardingService.getOnboardings(this.data.collaboratorId).subscribe(
            (onboardings: Array<IOnboarding>) => {
                this.onboardings = onboardings;
                const ratings: Array<number> = this.onboardings.filter(onboarding => onboarding.rating).map(onboarding => onboarding.rating);
                this.averageRating = (ratings.length > 0) ? (ratings.reduce((acc, cur) => acc + cur, 0) / ratings.length).toFixed(2) : undefined;
            }
        );
    }

}
