import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription } from 'rxjs';

import { NbDialogService } from '@nebular/theme';

import * as moment from 'moment';

import { CollaboratorDashboardService } from '../collaborator-dashboard.service';
import { CareerPositionService } from '../../../services/career-position.service';
import { CareerPathService } from '../../../services/career-path.service';
import { AcquiredSkillService } from '../../../services/acquired-skill.service';
import { RequiredSkillService } from '../../../services/required-skill.service';
import { MentorshipService } from '../../../services/mentorship.service';

import { InitializeTeamMemberDialogComponent } from '../initialize-team-member-dialog/initialize-team-member-dialog.component';
import { MentorshipDetailsDialogComponent } from '../mentorship-details-dialog/mentorship-details-dialog.component';

import { formatSkillType, formatSkillWeight, valueOfSkillLevel, formatSkillLevel, formatMentorshipStatus } from '../../../utils/formaters.util';

import { ICollaboratorDashboardSettings } from '../collaborator-dashboard-settings.model';
import { ICareerPosition } from '../../../models/career-position.model';
import { ICareerPath } from '../../../models/career-path.model';
import { INode } from '../../shared/components/graph/models/node.model';
import { ILink } from '../../shared/components/graph/models/link.model';
import { IGraphConfiguration } from '../../shared/components/graph/models/graph-configuration.model';
import { IAcquiredSkill } from '../../../models/acquired-skill.model';
import { IRequiredSkill } from '../../../models/required-skill.model';
import { IMentorship } from '../../../models/mentorship.model';

import { CareerPositionStatusEnum } from '../../../enumerations/career-position-status.enum';
import { SkillTypeEnum } from '../../../enumerations/skill-type.enum';
import { MentorshipStatusEnum } from '../../../enumerations/mentorship-status.enum';

import { DATE_EXTENDED_DISPLAY_FORMAT, DATE_STORAGE_FORMAT } from '../../../constants/date-time-formats.constant';
import { ProfileService } from 'src/app/services/profile.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { IProfile } from 'src/app/models/profile.model';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { StorageService } from 'src/app/services/storage.service';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { ProfileTypeEnum } from 'src/app/enumerations/profile-type.enum';


@Component({
    templateUrl: './career-progress.component.html',
    styleUrls: [ './career-progress.component.scss' ]
})
export class CareerProgressComponent implements OnInit, OnDestroy {

    collaboratorDashboardSettings: ICollaboratorDashboardSettings;
    collaboratorDashboardSettingsSubscription: Subscription;


    currentUser: ICollaborator;
    loading=true
    isManager = false;

    mentorships: Array<IMentorship>;
    mentorshipStatus = MentorshipStatusEnum;

    careerPositions: Array<ICareerPosition>;

    selectedCareerPosition: ICareerPosition;
    selectedCareerPositionToEdit: ICareerPosition;


    progressToNextCareerPosition = '0';

    careerPositionStatusEnum = CareerPositionStatusEnum;

    nodes: Array<INode>;
    links: Array<ILink>;

    profiles: Array<IProfile>=[];
    selectedProfil : IProfile;
    isEditing: boolean = false;
    enableEditIndex = null;
    closeDropDown: boolean = true

    selectedNodeValue: INode;

    get selectedNode() {
        return this.selectedNodeValue;
    }

    set selectedNode(selectedNode: INode) {
        this.selectedNodeValue = selectedNode;
        this.selectedSkillType = SkillTypeEnum.HUMAN;
        this.selectedSkill = undefined;
        if (!this.selectedNode) {
            this.selectedCareerPosition = undefined;
            this.skills = [];
        } else {
            this.selectedCareerPosition = this.careerPositions.find(
                careerPosition => careerPosition.profile.careerStep.id === this.selectedNode.data.id);
            this.loadRequiredSkills();
        }
    }

    graphConfiguration: IGraphConfiguration = {
        withStartingPoint: false,
        selectableStartingPoint: false
    };

    acquiredSkills: Array<IAcquiredSkill>;
    requiredSkills: Array<IRequiredSkill>;
    skills: Array<any>;
    selectedSkill: any;
    skillTypes = Object.values(SkillTypeEnum).map(skillType => ({
        name: formatSkillType(skillType) + ' skills',
        value: skillType
    }));
    selectedSkillType = SkillTypeEnum.HUMAN;

    constructor(
        private dialogService: NbDialogService,
        private collaboratorDashboardService: CollaboratorDashboardService,
        private careerPositionService: CareerPositionService,
        private careerPathService: CareerPathService,
        private acquiredSkillService: AcquiredSkillService,
        private requiredSkillService: RequiredSkillService,
        private mentorshipService: MentorshipService,
        private toastrService: ToastrService,
        private profilService : ProfileService,
        private storageService: StorageService
    ) { }

    ngOnInit() {
        this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
        this.isManager = this.currentUser.profileType === ProfileTypeEnum.MANAGER;
       this.getCollabDash();
    }


    getCollabDash()
    {
        this.collaboratorDashboardSettingsSubscription = this.collaboratorDashboardService.getCollaboratorDashboardSettings().subscribe(
            (collaboratorDashboardSettings: ICollaboratorDashboardSettings) => {
                if (JSON.stringify(collaboratorDashboardSettings) !== '{}') {
                    this.collaboratorDashboardSettings = collaboratorDashboardSettings;
                    if (this.collaboratorDashboardSettings.isMentor) {
                        this.loadMentorships();
                    } else {
                        this.loadCareerPositions();
                    }
                }
            }
        );
    }
    loadMentorships() {
        this.mentorshipService.getMentorshipsByMentorIdAndMenteeId(
            this.collaboratorDashboardSettings.viewer.id,
            this.collaboratorDashboardSettings.collaborator.id
        ).subscribe(
            (mentorships: Array<IMentorship>) => {
                this.mentorships = mentorships;
                this.loadCareerPositions();
            }
        );
    }

    loadCareerPositions() {
        this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(this.collaboratorDashboardSettings.collaborator.id).subscribe({
            next: (careerPositions: Array<ICareerPosition>) => {
                this.careerPositions = careerPositions.map(careerPosition => ({
                    ...careerPosition,
                    startedAt: moment(careerPosition.startedAt, DATE_STORAGE_FORMAT).format(DATE_EXTENDED_DISPLAY_FORMAT)
                }));
                if (this.careerPositions.length > 0) {
                    this.selectedCareerPosition = this.careerPositions[this.careerPositions.length - 1];
                    this.selectedCareerPositionToEdit= careerPositions[this.careerPositions.length - 1];

                    this.loadAcquiredSkills();
                
                }
                if (this.careerPositions.length > 1) {
                    this.initializeCareerPathGraph();

                }
            },
            complete:()=>{
                this.loading=false
                this.loadProfilBycareerPostionAndTeam();
            }
        });
    }

    loadAcquiredSkills() {
        this.acquiredSkillService.getAcquiredSkills(this.collaboratorDashboardSettings.collaborator.id).subscribe(
            (acquiredSkills: Array<IAcquiredSkill>) => {
                this.acquiredSkills = acquiredSkills;
                this.loadRequiredSkills();
            }
        );
    }

    loadRequiredSkills() {
        this.requiredSkillService.getRequiredSkills(this.selectedCareerPosition.profile.id).subscribe(
            (requiredSkills: Array<IRequiredSkill>) => {
                this.requiredSkills = requiredSkills;
                this.initializeSkills();
                if (this.selectedCareerPosition.status === CareerPositionStatusEnum.NEXT) {
                    this.calculateProgressToNextCareerPosition();
                }
            }
        );
    }

    initializeSkills() {
        this.skills = [];
        this.requiredSkills?.forEach((requiredSkill: IRequiredSkill) => {
            if (requiredSkill.skill.skillType === this.selectedSkillType) {
                const acquiredSkill = this.acquiredSkills?.find(
                    as => as.skill.id === requiredSkill.skill.id);
                const mentorship = this.mentorships?.find(
                    m => m.skill.id === requiredSkill.skill.id && m.careerPosition.id === this.selectedCareerPosition.id);
                this.skills.push({
                    id: requiredSkill.skill.id,
                    label: requiredSkill.skill.label,
                    type: formatSkillType(requiredSkill.skill.skillType),
                    weight: formatSkillWeight(requiredSkill.weight),
                    requiredLevelValue: valueOfSkillLevel(requiredSkill.level) * 100,
                    requiredLevelFormated: formatSkillLevel(requiredSkill.level),
                    acquiredLevelValue: acquiredSkill ? valueOfSkillLevel(acquiredSkill.progress[acquiredSkill.progress.length - 1].level) * 100 : 0,
                    acquiredLevelFormated: acquiredSkill ? formatSkillLevel(acquiredSkill.progress[acquiredSkill.progress.length - 1].level) : undefined,
                    mentorshipStatus: mentorship?.status
                });
            }
        });
    }

    initializeCareerPathGraph() {
        this.nodes = this.careerPositions.map((careerPosition) => ({
            id: careerPosition.profile.careerStep.label.toLowerCase().replace(' ', '-'),
            label: careerPosition.profile.careerStep.label,
            data: careerPosition.profile.careerStep,
            meta: this.getCareerPathGraphNodeMeta(careerPosition)
        }));
        this.careerPathService.getCareerPaths().subscribe(
            (careerPaths: Array<ICareerPath>) => {
                this.links = careerPaths.filter(
                    (careerPath: ICareerPath) => {
                        return this.nodes.find(node => node.data.id === careerPath.fromCareerStep.id) &&
                            this.nodes.find(node => node.data.id === careerPath.toCareerStep.id);
                }).map((careerPath, index) => ({
                    id: 'link-' + index.toString(),
                    source: careerPath.fromCareerStep.label.toLowerCase().replace(' ', '-'),
                    target: careerPath.toCareerStep.label.toLowerCase().replace(' ', '-'),
                    data: careerPath
                }));
            }
        );
        this.selectedNode = this.nodes.find(node => node.data.id === this.selectedCareerPosition.profile.careerStep.id);
    }

    getCareerPathGraphNodeMeta(careerPosition: ICareerPosition) {
        if (this.collaboratorDashboardSettings.isSelf || this.collaboratorDashboardSettings.isManager) {
            return {
                selectable: true,
                covered: true,
                reachable: true
            };
        }
        if (this.collaboratorDashboardSettings.isSupervisor && careerPosition.supervisor?.id === this.collaboratorDashboardSettings.viewer.id) {
            return {
                selectable: true,
                covered: true,
                reachable: true
            };
        }
        if (this.collaboratorDashboardSettings.isMentor && this.mentorships?.find(mentorship => mentorship.careerPosition.id === careerPosition.id)) {
            return {
                selectable: true,
                covered: true,
                reachable: true
            };
        }
        return {
            selectable: false,
            covered: false,
            reachable: false
        };
    }

    calculateProgressToNextCareerPosition() {
        const acquiredSkillsLevelsSum = this.acquiredSkills?.filter(
            acquiredSkill => this.requiredSkills?.find(requiredSkill => requiredSkill.skill.id === acquiredSkill.skill.id)
        ).map(acquiredSkill => valueOfSkillLevel(acquiredSkill.progress[acquiredSkill.progress.length - 1].level)).reduce((acc, cur) => acc + cur, 0);
        const requiredSkillsLevelsSum = this.requiredSkills?.map(requiredSkill => valueOfSkillLevel(requiredSkill.level)).reduce(
            (acc, cur) => acc + cur, 0);
        const monthsOfExperience = this.links?.find(link => link.data.toCareerStep.id === this.selectedCareerPosition.profile.careerStep.id).data.yearsOfExperience * 12;
        const currentCareerPosition = this.careerPositions?.find(careerPosition => careerPosition.status === CareerPositionStatusEnum.CURRENT);
        const monthsSinceCurrentCareerPositionStarted = moment.duration(moment().diff(moment(currentCareerPosition.startedAt, DATE_EXTENDED_DISPLAY_FORMAT))).months();
        this.progressToNextCareerPosition = Number((((acquiredSkillsLevelsSum / requiredSkillsLevelsSum) + (monthsSinceCurrentCareerPositionStarted / monthsOfExperience)) / 2) * 100).toFixed(0);
    }

    onAccordionItemClick(skillType: SkillTypeEnum) {
        if (skillType === this.selectedSkillType) {
            this.selectedSkillType = undefined;
        } else {
            this.selectedSkillType = skillType;
        }
        if (this.selectedSkillType) {
            this.initializeSkills();
        }
        this.hideSelectedSkillDrawer();
    }

    onSkillSelection(skill: any) {
        if (!this.collaboratorDashboardSettings.isSelf &&
            !this.collaboratorDashboardSettings.isManager &&
            !this.collaboratorDashboardSettings.isSupervisor &&
            (this.collaboratorDashboardSettings.isMentor && !skill.mentorshipStatus)) {
            return;
        }
        if (skill.id === this.selectedSkill?.id) {
            this.selectedSkill = undefined;
        } else {
            this.selectedSkill = skill;
        }
        if (this.selectedSkill) {
            if (this.collaboratorDashboardSettings.isMentor) {
                const mentorship = this.mentorships.find(
                    m => m.skill.id === this.selectedSkill.id && m.careerPosition.id === this.selectedCareerPosition.id);
                this.selectedSkill.mentorship = {
                    ...mentorship,
                    statusFormated: formatMentorshipStatus(mentorship.status)
                };
            } else {
                this.mentorshipService.getMentorshipBySkillIdAndCareerPositionId(this.selectedSkill.id, this.selectedCareerPosition.id).subscribe(
                    (mentorship: IMentorship) => {
                        if (mentorship) {
                            this.selectedSkill.mentorship = {
                                ...mentorship,
                                statusFormated: formatMentorshipStatus(mentorship.status)
                            };
                        }
                    }
                );
            }
        }
    }

    hideSelectedSkillDrawer() {
        this.selectedSkill = undefined;
    }

    onInitializeTeamMember() {
        this.dialogService.open(
            InitializeTeamMemberDialogComponent,
            {
                context: {
                    data: {
                        collaboratorId: this.collaboratorDashboardSettings.collaborator.id
                    }
                },
                hasScroll : true
            }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.loadCareerPositions();
                }
            }
        );
    }

    onMentorshipClick() {
        this.dialogService.open(
            MentorshipDetailsDialogComponent,
            {
                closeOnEsc: true,
                closeOnBackdropClick: true,
                context: {
                    data: {
                        mentorship: this.selectedSkill.mentorship,
                        acquiredSkill: this.acquiredSkills.find(
                            acquiredSkill => acquiredSkill.skill.id === this.selectedSkill.id),
                        requiredSkill: this.requiredSkills.find(
                            requiredSkill => requiredSkill.skill.id === this.selectedSkill.id),
                        settings: this.collaboratorDashboardSettings
                    }
                }
            }
        ).onClose.subscribe(
            (result: any) => {
                if (result?.mentorship) {
                    if (this.collaboratorDashboardSettings.isMentor) {
                        const mentorshipIndex = this.mentorships.findIndex(m => m.id === result.mentorship.id);
                        this.mentorships[mentorshipIndex] = result.mentorship;
                        this.selectedSkill.mentorshipStatus = result.mentorship.status;
                    }
                    this.selectedSkill.mentorship = {
                        ...result.mentorship,
                        statusFormated: formatMentorshipStatus(result.mentorship.status)
                    };
                    const selectedSkillIndex = this.skills.findIndex(s => s.id === this.selectedSkill.id);
                    this.skills[selectedSkillIndex] = this.selectedSkill;
                }
                if (result?.acquiredSkill) {
                    const acquiredSkillIndex = this.acquiredSkills.findIndex(as => as.id === result.acquiredSkill.id);
                    if (acquiredSkillIndex !== -1) {
                        this.acquiredSkills[acquiredSkillIndex] = result.acquiredSkill;
                    } else {
                        this.acquiredSkills.push(result.acquiredSkill);
                    }
                    this.selectedSkill.acquiredLevelValue = valueOfSkillLevel(result.acquiredSkill.progress[result.acquiredSkill.progress.length - 1].level) * 100;
                    this.selectedSkill.acquiredLevelFormated = formatSkillLevel(result.acquiredSkill.progress[result.acquiredSkill.progress.length - 1].level);
                    const selectedSkillIndex = this.skills.findIndex(s => s.id === this.selectedSkill.id);
                    this.skills[selectedSkillIndex] = this.selectedSkill;
                    if (this.selectedCareerPosition.status === CareerPositionStatusEnum.NEXT) {
                        this.calculateProgressToNextCareerPosition();
                    }
                }
            }
        );
    }

    ngOnDestroy() {
        this.collaboratorDashboardSettingsSubscription?.unsubscribe();
    }


    loadProfilBycareerPostionAndTeam() {
        this.profilService.getProfiles(this.selectedCareerPosition.profile.team.id,undefined,this.selectedCareerPosition.profile.careerStep.id).subscribe({
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
        if(this.selectedProfil==null || this.selectedCareerPositionToEdit.profile.label== this.selectedProfil.label)
        {
                            this.closeDropDown = true
                this.toastrService.showStatusToastr(
                    'It is already the current collaborator profile',
                    ToastrStatusEnum.WARNING);

        }
        else{

            this.selectedCareerPositionToEdit.profile=this.selectedProfil;
            this.careerPositionService.updateCareerPosition(this.selectedCareerPositionToEdit).subscribe({
               next :(careerPostion: ICareerPosition) => {},
               complete:()=>{
                this.getCollabDash();
                this.closeDropDown = true
                this.toastrService.showStatusToastr(
                    'Profile has been successfully updated',
                    ToastrStatusEnum.SUCCESS);
               }
            })
         }

    }


}
