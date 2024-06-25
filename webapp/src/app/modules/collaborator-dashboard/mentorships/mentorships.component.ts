import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription } from 'rxjs';

import { CollaboratorDashboardService } from '../collaborator-dashboard.service';
import { MentorshipService } from '../../../services/mentorship.service';

import { formatMentorshipStatus } from '../../../utils/formaters.util';

import { RatingPipe } from '../../shared/pipes/rating.pipe';

import { ICollaboratorDashboardSettings } from '../collaborator-dashboard-settings.model';
import { IMentorship } from '../../../models/mentorship.model';
import { ISingleChartData } from '../../../models/single-chart-data.model';

import { MentorshipStatusEnum } from '../../../enumerations/mentorship-status.enum';

import { CHART_COLOR_SCHEME } from '../../../constants/common.constant';


@Component({
    templateUrl: './mentorships.component.html',
    styleUrls: [ './mentorships.component.scss' ]
})
export class MentorshipsComponent implements OnInit, OnDestroy {

    collaboratorDashboardSettings: ICollaboratorDashboardSettings;
    collaboratorDashboardSettingsSubscription: Subscription;

    mentorships: Array<IMentorship> = [];

    averageRating = 'No available ratings';

    mentorshipsRatingsChartData: Array<ISingleChartData> = [];
    mentorshipsStatusesChartData: Array<ISingleChartData> = [];

    constructor(
        private collaboratorDashboardService: CollaboratorDashboardService,
        private mentorshipService: MentorshipService
    ) { }

    ngOnInit() {
        this.collaboratorDashboardSettingsSubscription = this.collaboratorDashboardService.getCollaboratorDashboardSettings().subscribe(
            (collaboratorDashboardSettings: ICollaboratorDashboardSettings) => {
                if (JSON.stringify(collaboratorDashboardSettings) !== '{}') {
                    this.collaboratorDashboardSettings = collaboratorDashboardSettings;
                    this.mentorshipService.getMentorshipsByMentorId(this.collaboratorDashboardSettings.collaborator.id).subscribe(
                        (mentorships: Array<IMentorship>) => {
                            this.mentorships = mentorships;
                            const ratingPipe: RatingPipe = new RatingPipe();
                            this.mentorshipsRatingsChartData = [-2, -1, 0, 1, 2].map((rating, index) => ({
                                id: rating,
                                name: ratingPipe.transform(rating),
                                value: 0,
                                color: CHART_COLOR_SCHEME.domain[index]
                            }));
                            this.mentorshipsStatusesChartData = Object.values(MentorshipStatusEnum).map((value, index) => ({
                                id: value,
                                name: formatMentorshipStatus(value),
                                value: 0,
                                color: CHART_COLOR_SCHEME.domain[index]
                            }));
                            let ratingsCount = 0;
                            let ratingsSum = 0;
                            this.mentorships.forEach((mentorship: IMentorship) => {
                                if (mentorship.menteeRating !== undefined) {
                                    ratingsCount++;
                                    ratingsSum += mentorship.menteeRating;
                                    for (const chartData of this.mentorshipsRatingsChartData) {
                                        if (mentorship.menteeRating === chartData.id) {
                                            chartData.value += 1;
                                            break;
                                        }
                                    }
                                }
                                for (const chartData of this.mentorshipsStatusesChartData) {
                                    if (mentorship.status === chartData.id) {
                                        chartData.value += 1;
                                        break;
                                    }
                                }
                            });
                            if (ratingsCount > 0) {
                                this.averageRating = (ratingsSum / ratingsCount).toFixed(2) + ' Average rating as a mentor';
                            }
                        }
                    );
                }
            }
        );
    }

    ngOnDestroy() {
        this.collaboratorDashboardSettingsSubscription?.unsubscribe();
    }

}
