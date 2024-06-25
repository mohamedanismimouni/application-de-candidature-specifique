import { Component, OnInit, OnDestroy,HostListener } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';

import * as moment from 'moment';

import { NbDialogService } from '@nebular/theme';

import { StorageService } from '../../services/storage.service';
import { CollaboratorDashboardService } from './collaborator-dashboard.service';
import { UserService } from '../../services/user.service';
import { CareerPositionService } from '../../services/career-position.service';
import { MentorshipService } from '../../services/mentorship.service';

import { InitiateOnboardingProcessDialogComponent } from './initiate-onboarding-process-dialog/initiate-onboarding-process-dialog.component';
import { OnboardingProcessDetailsDialogComponent } from './onboarding-process-details-dialog/onboarding-process-details-dialog.component';

import { ITeam } from '../../models/team.model';
import { ICareerPosition } from '../../models/career-position.model';
import { IMentorship } from '../../models/mentorship.model';
import { ISecretWord } from '../../models/secret-word.model';

import { StorageKeyEnum } from '../../enumerations/storage-key.enum';
import { CareerPositionStatusEnum } from '../../enumerations/career-position-status.enum';
import { MentorshipStatusEnum } from '../../enumerations/mentorship-status.enum';

import { DATE_STORAGE_FORMAT } from '../../constants/date-time-formats.constant';
import { COLLABORATOR_PROBATIONARY_PERIOD_IN_MONTHS } from '../../constants/common.constant';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './collaborator-dashboard.component.html',
    styleUrls: [ './collaborator-dashboard.component.scss' ]
})
export class CollaboratorDashboardComponent implements OnInit, OnDestroy {

    collaboratorDashboardSettings: any = {};

    tabs: any[] =  [];
    yearsOfExperience = 0;

    hasSecretWord = false;
    isFreshRecruit = false;

    secretWord: string;

    constructor(
        private activatedRoute: ActivatedRoute,
        private location: Location,
        private dialogService: NbDialogService,
        private storageService: StorageService,
        private collaboratorDashboardService: CollaboratorDashboardService,
        private userService: UserService,
        private careerPositionService: CareerPositionService,
        private mentorshipService: MentorshipService
    ) { }

    ngOnInit() {
        this.collaboratorDashboardSettings.viewer = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);

    this.activatedRoute.paramMap.subscribe(
            (paramMap: ParamMap) => {
                this.userService.getUserById( Number(paramMap.get('collaborator-id'))).subscribe(
                    (collaborator: ICollaborator) => {
                        this.collaboratorDashboardSettings.collaborator = collaborator;
                        this.collaboratorDashboardSettings.isSelf = this.collaboratorDashboardSettings.collaborator.id === this.collaboratorDashboardSettings.viewer.id;
                        if (this.collaboratorDashboardSettings.isSelf) {
                            this.tabs = [
                                {
                                    title: 'Career progress',
                                    icon: 'trending-up-outline',
                                    responsive: true,
                                    route: 'career-progress'
                                },
                                {
                                    title: 'Evaluations',
                                    icon: 'checkmark-outline',
                                    responsive: true,
                                    route: 'evaluations'
                                },
                                {
                                    title: 'Mentorships',
                                    icon: 'bulb-outline',
                                    responsive: true,
                                    route: 'mentorships'
                                },
                                {
                                    title: 'Skills',
                                    icon: 'award-outline',
                                    responsive: true,
                                    route: 'skills'
                                }
                            ];

                            this.collaboratorDashboardSettings.isManager = false;
                            this.collaboratorDashboardSettings.isSupervisor = false;
                            this.collaboratorDashboardSettings.isMentor = false;
                            this.collaboratorDashboardService.setCollaboratorDashboardSettings(this.collaboratorDashboardSettings);
                        } else {
                            this.userService.getCollaboratorTeam(this.collaboratorDashboardSettings.collaborator.id).subscribe(
                                (team: ITeam) => {
                                    if (team && team.managedBy.id === this.collaboratorDashboardSettings.viewer.id) {
                                        this.collaboratorDashboardSettings.isManager = true;
                                        if (moment.duration(moment().diff(moment(this.collaboratorDashboardSettings.collaborator.recruitedAt, DATE_STORAGE_FORMAT))).asMonths() < COLLABORATOR_PROBATIONARY_PERIOD_IN_MONTHS) {
                                            this.isFreshRecruit = true;
                                        } else {
                                            this.isFreshRecruit = false;
                                        }
                                        this.userService.getSecretWord(this.collaboratorDashboardSettings.collaborator.id).subscribe(
                                            (secretWord: ISecretWord) => {
                                                if (secretWord.value) {
                                                    this.hasSecretWord = true;
                                                    this.secretWord = secretWord.value;
                                                } else {
                                                    this.hasSecretWord = false;
                                                }
                                            }
                                        );
                                    } else {
                                        this.collaboratorDashboardSettings.isManager = false;
                                    }
                                    this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(
                                        this.collaboratorDashboardSettings.collaborator.id,
                                        CareerPositionStatusEnum.CURRENT
                                    ).subscribe(
                                        (careerPositions: Array<ICareerPosition>) => {
                                            if (careerPositions.length > 0 && careerPositions[0].supervisor.id === this.collaboratorDashboardSettings.viewer.id) {
                                                this.collaboratorDashboardSettings.isSupervisor = true;
                                            } else {
                                                this.collaboratorDashboardSettings.isSupervisor = false;
                                            }
                                            this.mentorshipService.getMentorshipsByMentorIdAndMenteeId(
                                                this.collaboratorDashboardSettings.viewer.id,
                                                this.collaboratorDashboardSettings.collaborator.id,
                                                MentorshipStatusEnum.ACTIVE
                                            ).subscribe(
                                                (mentorships: Array<IMentorship>) => {
                                                    this.collaboratorDashboardSettings.isMentor = mentorships.length > 0;
                                                    if (!this.collaboratorDashboardSettings.isManager && !this.collaboratorDashboardSettings.isSupervisor && !this.collaboratorDashboardSettings.isMentor) {
                                                        this.location.back();
                                                    }
                                                    if (!this.collaboratorDashboardSettings.isManager && !this.collaboratorDashboardSettings.isSupervisor && this.collaboratorDashboardSettings.isMentor) {
                                                        this.tabs = [
                                                            {
                                                                title: 'Career progress',
                                                                icon: 'trending-up-outline',
                                                                responsive: true,
                                                                route: 'career-progress'
                                                            },
                                                            {
                                                                title: 'Skills',
                                                                icon: 'award-outline',
                                                                responsive: true,
                                                                route: 'skills'
                                                            }
                                                        ];

                                                    }
                                                    if (this.collaboratorDashboardSettings.isManager || this.collaboratorDashboardSettings.isSupervisor) {
                                                        this.tabs = [
                                                            {
                                                                title: 'Career progress',
                                                                icon: 'trending-up-outline',
                                                                responsive: true,
                                                                route: 'career-progress'
                                                            },
                                                            {
                                                                title: 'Evaluations',
                                                                icon: 'checkmark-outline',
                                                                responsive: true,
                                                                route: 'evaluations'
                                                            },
                                                            {
                                                                title: 'Mentorships',
                                                                icon: 'bulb-outline',
                                                                responsive: true,
                                                                route: 'mentorships'
                                                            },
                                                            {
                                                                title: 'Skills',
                                                                icon: 'award-outline',
                                                                responsive: true,
                                                                route: 'skills'
                                                            }
                                                        ];

                                                    }
                                                    this.collaboratorDashboardService.setCollaboratorDashboardSettings(this.collaboratorDashboardSettings);
                                                }
                                            );
                                        }
                                    );
                                }
                            );
                        }
                        this.yearsOfExperience = moment.duration(moment().diff(moment(this.collaboratorDashboardSettings.collaborator.recruitedAt, DATE_STORAGE_FORMAT))).years();
                    }
                );
            }
        );

    }

    onOnboardingProcessClick() {
        if (this.hasSecretWord) {
            this.dialogService.open(
                OnboardingProcessDetailsDialogComponent,
                {
                    closeOnEsc: true,
                    closeOnBackdropClick: true,
                    context: {
                        data: {
                            collaboratorId: this.collaboratorDashboardSettings.collaborator.id,
                            passedOnboardingProcess: this.collaboratorDashboardSettings.collaborator.passedOnboardingProcess,
                            secretWord: this.secretWord
                        }
                    }
                }
            );
        } else {
            this.dialogService.open(
                InitiateOnboardingProcessDialogComponent,
                {
                    context: {
                        data: { collaboratorId: this.collaboratorDashboardSettings.collaborator.id }
                    }
                }
            );
        }
    }

    ngOnDestroy() {
        this.collaboratorDashboardService.setCollaboratorDashboardSettings({} as any);
    }

}
