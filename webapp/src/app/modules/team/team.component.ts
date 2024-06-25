import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { Observable } from 'rxjs';

import { NbDialogService } from '@nebular/theme';

import * as moment from 'moment';

import { StorageService } from '../../services/storage.service';
import { TeamService } from '../../services/team.service';
import { CareerStepService } from '../../services/career-step.service';
import { ProfileService } from '../../services/profile.service';
import { UserService } from '../../services/user.service';

import { TeamMemberDetailsDialogComponent } from './team-member-details-dialog/team-member-details-dialog.component';
import { TeamEvaluationDateDialogComponent } from './team-evaluation-date-dialog/team-evaluation-date-dialog.component';

import { formatTeamStructuralRelation } from '../../utils/formaters.util';

import { ITeam } from '../../models/team.model';
import { ICareerStep } from '../../models/career-step.model';
import { IProfile } from '../../models/profile.model';

import { StorageKeyEnum } from '../../enumerations/storage-key.enum';
import { TeamStructuralRelationEnum } from '../../enumerations/team-structural-relation.enum';
import { CareerPositionStatusEnum } from '../../enumerations/career-position-status.enum';
import { MentorshipStatusEnum } from '../../enumerations/mentorship-status.enum';
import { ProfileTypeEnum } from '../../enumerations/profile-type.enum';

import { MONTH_DAY_STORAGE_FORMAT, MONTH_DAY_DISPLAY_FORMAT } from '../../constants/date-time-formats.constant';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './team.component.html',
    styleUrls: [ './team.component.scss' ]
})
export class TeamComponent implements OnInit {

    currentUser: ICollaborator;
    connectedUser:ICollaborator
    profileType = ProfileTypeEnum;
    loading=true

    config:any;
    configInitialized:any;
    team: any;
    managerOf:ITeam;

    uninitializedTeamMembersSearchFormGroup: FormGroup = this.formBuilder.group({
        keyword:                [ '' ]
    });
    searchUninitializedTeamMembers = false;

    uninitializedTeamMembers: Array<ICollaborator>;
    displayedUninitializedTeamMembers: Array<ICollaborator>=[];

    initializedTeamMembersSearchFormGroup: FormGroup = this.formBuilder.group({
        keyword:                [ '' ],
        careerStep:             [ '' ],
        profile:                [ '' ],
        teamStructuralRelation: [ '' ]
    });
    searchInitializedTeamMembers = false;
    careerSteps: Array<ICareerStep>;
    profiles: Array<IProfile>;
    teamStructuralRelations: Array<any> = Object.values(TeamStructuralRelationEnum).map((teamStructuralRelation) => ({
        name: formatTeamStructuralRelation(teamStructuralRelation),
        value: teamStructuralRelation
    }));

    initializedTeamMembers: Array<ICollaborator>;
    displayedInitializedTeamMembers: Array<ICollaborator>=[];

    constructor(
        private formBuilder: FormBuilder,
        private dialogService: NbDialogService,
        private storageService: StorageService,
        private teamService: TeamService,
        private careerStepService: CareerStepService,
        private profileService: ProfileService,
        private userService: UserService
    ) { 
        this.config = {
            itemsPerPage: 10,
            currentPage: 1,
            totalItems: this.displayedUninitializedTeamMembers.length,
          }; 
          this.configInitialized = {
            itemsPerPage: 10,
            currentPage: 1,
            totalItems: this.displayedInitializedTeamMembers.length,
            id:'d'
          }; 
    }

    ngOnInit() {
        this.loadUser()
        this.loadUserTeam() ;
        this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);


        let teamFetchingObservable: Observable<ITeam>;
        if (this.currentUser.profileType === ProfileTypeEnum.MANAGER) {
            teamFetchingObservable = this.teamService.getTeamByManagerId(this.currentUser.id);
        } else if (this.currentUser.profileType === ProfileTypeEnum.COLLABORATOR) {
            teamFetchingObservable = this.userService.getCollaboratorTeam(this.currentUser.id);
        }
        teamFetchingObservable.subscribe(
            (team: ITeam) => {
                this.team = {
                    ...team,
                    formattedTeamEvaluationDate: team.teamEvaluationDate ? this.formatTeamEvaluationDate(team.teamEvaluationDate) : 'Not set'
                };
                this.careerStepService.getCareerStepsAssociatedToProfilesWithTeamId(this.team.id).subscribe(
                    careerSteps => this.careerSteps = careerSteps
                );
                if (this.currentUser.profileType === ProfileTypeEnum.MANAGER) {
                    this.loadUninitializedTeamMembers();
                   
                }
                this.loadInitializedTeamMembers();
            }
        );

     
    }


  loadUserTeam() {
        this.teamService
            .getTeamByManagerId(
                this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id
            )
            .subscribe((team:ITeam) => {
                this.managerOf = team;
              
            });
    }


    loadUser() {
        this.userService
            .getUserTeamById(
                this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id
            )
            .subscribe((user:ICollaborator) => {
                this.connectedUser = user;

            });
    }

    toggleUninitializedTeamMembersSearch() {
        this.searchUninitializedTeamMembers = !this.searchUninitializedTeamMembers;
        if (!this.searchUninitializedTeamMembers) {
            this.uninitializedTeamMembersSearchFormGroup.get('keyword').reset();
            this.onUninitializedTeamMembersKeywordInputChange();
        }
    }

    onUninitializedTeamMembersKeywordInputChange() {
        let keyword = this.uninitializedTeamMembersSearchFormGroup.get('keyword').value;
        if (!keyword) {
            keyword = '';
        }
        keyword = keyword.trim().toLowerCase();
        this.displayedUninitializedTeamMembers = this.uninitializedTeamMembers.filter(
            teamMember => (teamMember.firstName + ' ' + teamMember.lastName).toLowerCase().includes(keyword)
        );
    }

    loadUninitializedTeamMembers() {
        this.userService.getTeamMembers(false, this.team.id).subscribe(
            (uninitializedTeamMembers: Array<ICollaborator>) => {
                this.uninitializedTeamMembers = uninitializedTeamMembers;
                this.displayedUninitializedTeamMembers = uninitializedTeamMembers;
                this.loading=false

              
          }
        );
    }

    toggleInitializedTeamMembersSearch() {
        this.searchInitializedTeamMembers = !this.searchInitializedTeamMembers;
        if (!this.searchInitializedTeamMembers) {
            this.initializedTeamMembersSearchFormGroup.get('keyword').reset();
            this.onInitializedTeamMembersKeywordInputChange();
        }
    }

    onInitializedTeamMembersKeywordInputChange() {
        let keyword = this.initializedTeamMembersSearchFormGroup.get('keyword').value;
        if (!keyword) {
            keyword = '';
        }
        keyword = keyword.trim().toLowerCase();
        this.displayedInitializedTeamMembers = this.initializedTeamMembers.filter(
            teamMember => (teamMember.firstName + ' ' + teamMember.lastName).toLowerCase().includes(keyword)
  
            );
    }

    onCareerStepSelectChange() {
        const careerStep = this.initializedTeamMembersSearchFormGroup.get('careerStep').value;
        if (!careerStep) {
            this.profiles = [];
            this.initializedTeamMembersSearchFormGroup.get('profile').reset();
            this.loadInitializedTeamMembers();
        } else {
            this.profileService.getProfiles(this.team.id, undefined, careerStep).subscribe(
                (profiles: Array<IProfile>) => {
                    this.profiles = profiles;
                    this.initializedTeamMembersSearchFormGroup.get('profile').reset();
                    this.loadInitializedTeamMembers();
                }
            );
        }
    }

    loadInitializedTeamMembers() {
        let initializedTeamMembersFetchingObservable: Observable<Array<ICollaborator>>;
        switch (this.initializedTeamMembersSearchFormGroup.get('teamStructuralRelation').value) {
            case TeamStructuralRelationEnum.SUBORDINATES:
                initializedTeamMembersFetchingObservable = this.userService.getTeamMembers(
                    true,
                    this.team.id,
                    this.initializedTeamMembersSearchFormGroup.get('profile').value,
                    this.initializedTeamMembersSearchFormGroup.get('careerStep').value,
                    this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY)?.id,
                    CareerPositionStatusEnum.CURRENT);
                break;
            case TeamStructuralRelationEnum.MENTEES:
                initializedTeamMembersFetchingObservable = this.userService.getTeamMembers(
                    true,
                    this.team.id,
                    this.initializedTeamMembersSearchFormGroup.get('profile').value,
                    this.initializedTeamMembersSearchFormGroup.get('careerStep').value,
                    undefined,
                    undefined,
                    this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY)?.id,
                    MentorshipStatusEnum.ACTIVE);
                break;
            default:
                initializedTeamMembersFetchingObservable = this.userService.getTeamMembers(
                    true,
                    this.team.id,
                    this.initializedTeamMembersSearchFormGroup.get('profile').value,
                    this.initializedTeamMembersSearchFormGroup.get('careerStep').value,
                    undefined,
                    CareerPositionStatusEnum.CURRENT);
                break;
        }
        initializedTeamMembersFetchingObservable.subscribe(
            (initializedTeamMembers: Array<ICollaborator>) => {
                this.initializedTeamMembers = initializedTeamMembers.filter(teamMember => teamMember.id !== this.currentUser.id);
                this.onInitializedTeamMembersKeywordInputChange();
                this.loading=false

            }
        );
    }

    onTeamMemberSelection(selectedTeamMember: ICollaborator) {
        this.dialogService.open(
            TeamMemberDetailsDialogComponent,
            {
                closeOnEsc: true,
                closeOnBackdropClick: true,
                context: {
                    data: { teamMember: selectedTeamMember ,team :this.team}
                }
            }
        );
    }

    onTeamEvaluationDateClick() {
        this.dialogService.open(
            TeamEvaluationDateDialogComponent,
            {
                context: {
                    data: {
                        teamId: this.team.id,
                        teamEvaluationDate: this.team.teamEvaluationDate
                    }
                }
            }
        ).onClose.subscribe(
            (result: string) => {
                if (result) {
                    this.team.teamEvaluationDate = result;
                    this.team.formattedTeamEvaluationDate = this.formatTeamEvaluationDate(result);
                }
            }
        );
    }

    private formatTeamEvaluationDate(teamEvaluationDate: string): string {
        return moment(teamEvaluationDate, MONTH_DAY_STORAGE_FORMAT).format(MONTH_DAY_DISPLAY_FORMAT);
    }
    pageChangedUninitialized(event) {
        this.config.currentPage = event;
      }
      pageChangedInitialized(event) {
        this.configInitialized.currentPage = event;
      }
}
