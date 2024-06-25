import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

import { NbDialogRef } from '@nebular/theme';

import { StorageService } from '../../../services/storage.service';
import { MentorshipService } from '../../../services/mentorship.service';
import { CareerPositionService } from '../../../services/career-position.service';
import { OnboardingService } from '../../../services/onboarding.service';
import { ToastrService } from '../../../services/toastr.service';

import { getRoutePrefixFromProfileType } from '../../../utils/navigation.util';

import { IMentorship } from '../../../models/mentorship.model';
import { ICareerPosition } from '../../../models/career-position.model';
import { IOnboarding } from '../../../models/onboarding.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { StorageKeyEnum } from '../../../enumerations/storage-key.enum';
import { ProfileTypeEnum } from '../../../enumerations/profile-type.enum';
import { MentorshipStatusEnum } from '../../../enumerations/mentorship-status.enum';
import { CareerPositionStatusEnum } from '../../../enumerations/career-position-status.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { ProfileService } from 'src/app/services/profile.service';
import { ITeam } from 'src/app/models/team.model';
import { IProfile } from 'src/app/models/profile.model';


@Component({
    templateUrl: './team-member-details-dialog.component.html',
    styleUrls: [ './team-member-details-dialog.component.scss' ]
})
export class TeamMemberDetailsDialogComponent implements OnInit {

    @Input()
    data: any;

    currentUser: ICollaborator;


    isManager = false;
    isSupervisor = false;
    isMentor = false;
    fistLetters:string;
    teamMember: ICollaborator;
    team:ITeam;
    currentCareerPosition: ICareerPosition;
    currentCareerPositionToEdit: ICareerPosition;

    onboarding: IOnboarding;

    profiles: Array<IProfile>=[];
    selectedProfil : IProfile;
    isEditing: boolean = false;
    enableEditIndex = null;
    closeDropDown: boolean = true

    get onboardingRating() {
        return this.onboarding?.rating;
    }

    set onboardingRating(rating: number) {
        if (this.onboarding?.rating !== rating) {
            this.onboardingService.rateOnboarding(this.onboarding.id, rating).subscribe(
                onboarding => this.onboarding = onboarding,
                (error: IErrorResponse) => {
                    if (error.status >= 400 && error.status < 500) {
                        this.toastrService.showStatusToastr(
                            'Something is not right, please try again later!',
                            ToastrStatusEnum.DANGER);
                    }
                }
            );
        }
    }

    constructor(
        private router: Router,
        private dialogRef: NbDialogRef<TeamMemberDetailsDialogComponent>,
        private storageService: StorageService,
        private mentorshipService: MentorshipService,
        private careerPositionService: CareerPositionService,
        private onboardingService: OnboardingService,
        private toastrService: ToastrService,
        private profilService : ProfileService
    ) { }

    ngOnInit() {

        this.team = this.data.team;
        this.teamMember = this.data.teamMember;
        this.fistLetters=this.teamMember.firstName[0]+this.teamMember.lastName[0];
        this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
        this.isManager = this.currentUser.profileType === ProfileTypeEnum.MANAGER;
        if (!this.isManager) {
            this.mentorshipService.getMentorshipsByMentorIdAndMenteeId(
                this.currentUser.id,
                this.teamMember.id,
                MentorshipStatusEnum.ACTIVE
            ).subscribe(
                (mentorships: Array<IMentorship>) => this.isMentor = mentorships.length > 0
            );
        }
        this.loadCareerPosition();
      //  this.loadOnboarding();
       // this.loadProfilBycareerPostionAndTeam();
    }

    loadCareerPosition() {
        this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(
            this.teamMember.id,
            CareerPositionStatusEnum.CURRENT
        ).subscribe({
            next:(careerPositions: Array<ICareerPosition>) => {
                if (careerPositions.length > 0) {
                    this.currentCareerPosition = careerPositions[0];
                    this.isSupervisor = this.currentCareerPosition.supervisor.id === this.currentUser.id;
                }
            },
            complete:()=>{
                    this.loadProfilBycareerPostionAndTeam();
            }
        }
        );
    }

    loadOnboarding() {
        this.onboardingService.getOnboarding(this.currentUser.id, this.teamMember.id).subscribe(
            onboarding => this.onboarding = onboarding
        );
    }

    navigateToCollaboratorDashboard() {
        this.dialogRef.close();
        this.router.navigate([
            getRoutePrefixFromProfileType(this.currentUser.profileType)
            + '/collaborator-dashboard/'
            + this.teamMember.id
        ]);
    }

    loadProfilBycareerPostionAndTeam() {
        this.profilService.getProfiles(this.team.id,undefined,this.currentCareerPosition.profile.careerStep.id).subscribe({
            next :(profil: IProfile[]) => {
               this.profiles= profil
            },
            complete:()=>{
            }});
        }


    switchEditMode() {
        this.closeDropDown=false;
    }

    cancel(){
        this.closeDropDown = true
        this.selectedProfil=null

    }

    updateCareerPostion(){
        if(this.selectedProfil==null || this.currentCareerPosition.profile.label== this.selectedProfil.label)
        {
                            this.closeDropDown = true
                this.toastrService.showStatusToastr(
                    'It is already the current collaborator profile',
                    ToastrStatusEnum.WARNING);

        }
        else{
            this.currentCareerPosition.profile=this.selectedProfil;
            this.careerPositionService.updateCareerPosition(this.currentCareerPosition).subscribe({
               next :(careerPostion: ICareerPosition) => {},
               complete:()=>{
                this.closeDropDown = true
                this.toastrService.showStatusToastr(
                    'Profile has been successfully updated',
                    ToastrStatusEnum.SUCCESS);
               }
            })
         }

    }

}
