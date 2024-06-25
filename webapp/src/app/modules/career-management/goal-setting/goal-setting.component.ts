import { Component, OnInit } from '@angular/core';

import { NbDialogService } from '@nebular/theme';

import * as moment from 'moment';

import { StorageService } from '../../../services/storage.service';
import { CareerPositionService } from '../../../services/career-position.service';
import { CareerPathService } from '../../../services/career-path.service';
import { CareerStepService } from '../../../services/career-step.service';
import { UserService } from '../../../services/user.service';
import { ProfileService } from '../../../services/profile.service';
import { RequiredSkillService } from '../../../services/required-skill.service';
import { AcquiredSkillService } from '../../../services/acquired-skill.service';
import { ToastrService } from '../../../services/toastr.service';

import { ConfirmationDialogComponent } from '../../../components/confirmation-dialog/confirmation-dialog.component';

import { formatSkillType, formatSkillWeight, valueOfSkillLevel } from '../../../utils/formaters.util';

import { ITeam } from '../../../models/team.model';
import { ICareerPosition } from '../../../models/career-position.model';
import { INode } from '../../shared/components/graph/models/node.model';
import { ILink } from '../../shared/components/graph/models/link.model';
import { IGraphConfiguration } from '../../shared/components/graph/models/graph-configuration.model';
import { ICareerStep } from '../../../models/career-step.model';
import { ICareerPath } from '../../../models/career-path.model';
import { IProfile } from '../../../models/profile.model';
import { IRequiredSkill } from '../../../models/required-skill.model';
import { IAcquiredSkill } from '../../../models/acquired-skill.model';
import { IDialogData } from '../../../models/dialog-data.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { StorageKeyEnum } from '../../../enumerations/storage-key.enum';
import { SkillTypeEnum } from '../../../enumerations/skill-type.enum';
import { CareerPositionStatusEnum } from '../../../enumerations/career-position-status.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';

import { DATE_EXTENDED_DISPLAY_FORMAT } from '../../../constants/date-time-formats.constant';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './goal-setting.component.html',
    styleUrls: [ './goal-setting.component.scss' ]
})
export class GoalSettingComponent implements OnInit {

    collaborator: ICollaborator;
    team: ITeam;

    collaboratorCareerPositions: Array<ICareerPosition>;

    nodes: Array<INode>;
    links: Array<ILink>;

    selectedNodeValue: INode;

    get selectedNode() {
        return this.selectedNodeValue;
    }

    set selectedNode(selectedNode: INode) {
        this.selectedNodeValue = selectedNode;
        if (!this.selectedNode) {
            this.inboundCareerPaths = [];
            this.selectedCareerStepProfiles = [];
        } else {
            this.initializeSelectedCareerStepInboundCareerPaths();
            this.initializeSelectedCareerStepProfiles();
        }
    }

    graphConfiguration: IGraphConfiguration = {
        withStartingPoint: true,
        selectableStartingPoint: false
    };

    careerSteps: Array<ICareerStep>;
    careerPaths: Array<ICareerPath>;
    inboundCareerPaths: Array<ICareerPath>;

    profiles: Array<IProfile>;
    selectedCareerStepProfiles: Array<IProfile>;
    selectedProfile: IProfile;

    selectedProfileRequiredSkills: Array<IRequiredSkill>;
    selectedProfileRequiredSkillsBySelectedSkillType: Array<IRequiredSkill>;
    collaboratorAcquiredSkills: Array<IAcquiredSkill>;
    skillTypes = Object.values(SkillTypeEnum).map(skillType => ({
        name: 'Required ' + formatSkillType(skillType).toLowerCase() + ' skills',
        value: skillType
    }));
    selectedSkillType = SkillTypeEnum.HUMAN;

    constructor(
        private dialogService: NbDialogService,
        private storageService: StorageService,
        private careerPositionService: CareerPositionService,
        private careerPathService: CareerPathService,
        private careerStepService: CareerStepService,
        private userService: UserService,
        private profileService: ProfileService,
        private requiredSkillService: RequiredSkillService,
        private acquiredSkillService: AcquiredSkillService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.collaborator = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
        this.loadCollaboratorAcquiredSkills();
        this.loadCollaboratorCareerPositions();
    }

    loadCollaboratorAcquiredSkills() {
        this.acquiredSkillService.getAcquiredSkills(this.collaborator.id).subscribe(
            acquiredSkills => this.collaboratorAcquiredSkills = acquiredSkills
        );
    }

    loadCollaboratorCareerPositions() {
        this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(this.collaborator.id).subscribe(
            (careerPositions: Array<ICareerPosition>) => {
                this.collaboratorCareerPositions = careerPositions;
                this.loadCareerPaths();
            }
        );
    }

    loadCareerPaths() {
        this.careerPathService.getCareerPaths().subscribe(
            (careerPaths: Array<ICareerPath>) => {
                this.careerPaths = careerPaths;
                this.loadCareerSteps();
            }
        );
    }

    loadCareerSteps() {
        this.careerStepService.getCareerSteps().subscribe(
            (careerSteps: Array<ICareerStep>) => {
                this.careerSteps = careerSteps;
                this.loadCollaboratorTeam();
            }
        );
    }

    loadCollaboratorTeam() {
        this.userService.getCollaboratorTeam(this.collaborator.id).subscribe(
            (team: ITeam) => {
                this.team = team;
                this.loadTeamProfiles();
            }
        );
    }

    loadTeamProfiles() {
        this.profileService.getProfiles(this.team.id, true).subscribe(
            (profiles: Array<IProfile>) => {
                this.profiles = profiles;
                this.initializeCareersGraph();
            }
        );
    }

    initializeCareersGraph() {
        this.links = this.careerPaths.map((careerPath, index) => ({
            id: 'link-' + index.toString(),
            source: careerPath.fromCareerStep.label.toLowerCase().replace(' ', '-'),
            target: careerPath.toCareerStep.label.toLowerCase().replace(' ', '-'),
            data: careerPath
        }));
        this.nodes = this.careerSteps.map((careerStep) => ({
            id: careerStep.label.toLowerCase().replace(' ', '-'),
            label: careerStep.label,
            data: {
                step: careerStep,
                position: this.findCareerPositionAssociatedWithCareerStep(careerStep)
            },
            meta: {
                selectable: this.isCareerStepSelectable(careerStep),
                covered: this.findCareerPositionAssociatedWithCareerStep(careerStep) !== undefined,
                reachable: this.isCareerStepSelectable(careerStep)
            }
        }));
    }

    isCareerStepSelectable(careerStep: ICareerStep): boolean {
        return this.profiles.filter(profile => profile.careerStep.id === careerStep.id).length > 0;
    }

    findCareerPositionAssociatedWithCareerStep(careerStep: ICareerStep): ICareerPosition {
        return this.collaboratorCareerPositions.find(careerPosition => careerPosition.profile.careerStep.id === careerStep.id);
    }

    initializeSelectedCareerStepInboundCareerPaths() {
        this.inboundCareerPaths = this.links.filter(
            link => link.data.toCareerStep.id === this.selectedNode.data.step.id
        ).map(link => link.data);
    }

    initializeSelectedCareerStepProfiles() {
        this.selectedCareerStepProfiles = this.profiles
            .filter(profile => profile.careerStep.id === this.selectedNode.data.step.id);
    }

    hideCareerStepDrawer() {
        this.selectedNode = undefined;
        this.hideProfileDrawer();
    }

    onProfileSelection(profile: IProfile) {
        if (profile.id === this.selectedProfile?.id) {
            this.selectedProfile = undefined;
        } else {
            this.selectedProfile = profile;
        }
        if (this.selectedProfile) {
            this.loadProfileRequiredSkills();
        }
    }

    loadProfileRequiredSkills() {
        this.requiredSkillService.getRequiredSkills(this.selectedProfile.id).subscribe(
            (requiredSkills: Array<IRequiredSkill>) => {
                this.selectedProfileRequiredSkills = requiredSkills;
                this.initializeProfileRequiredSkillsBySelectedSkillType();
            }
        );
    }

    initializeProfileRequiredSkillsBySelectedSkillType() {
        this.selectedProfileRequiredSkillsBySelectedSkillType = this.selectedProfileRequiredSkills.filter(
            requiredSkill => requiredSkill.skill.skillType === this.selectedSkillType).map(
                requiredSkill => ({
                    ...requiredSkill,
                    level: valueOfSkillLevel(requiredSkill.level) * 100 as any,
                    weight: formatSkillWeight(requiredSkill.weight) as any
                }));
    }

    onAccordionItemClick(skillType: SkillTypeEnum) {
        if (skillType === this.selectedSkillType) {
            this.selectedSkillType = undefined;
        } else {
            this.selectedSkillType = skillType;
        }
        if (this.selectedSkillType) {
            this.initializeProfileRequiredSkillsBySelectedSkillType();
        }
    }

    hideProfileDrawer() {
        this.selectedProfile = undefined;
        this.selectedSkillType = SkillTypeEnum.HUMAN;
    }

    isPotentialGoal() {
        const currentCareerPosition: ICareerPosition = this.collaboratorCareerPositions.find(
            collaboratorCareerPosition => collaboratorCareerPosition.status === CareerPositionStatusEnum.CURRENT);
        const nextCareerPosition: ICareerPosition = this.collaboratorCareerPositions.find(
            collaboratorCareerPosition => collaboratorCareerPosition.status === CareerPositionStatusEnum.NEXT);
        return this.links.find((link: ILink) => {
            return link.data.fromCareerStep.id === currentCareerPosition.profile.careerStep.id &&
                link.data.toCareerStep.id === this.selectedNode.data.step.id;
        }) !== undefined && nextCareerPosition?.profile.id !== this.selectedProfile.id;
    }

    calculateMatchingPercentage(): string {
        const acquiredSkillsLevelsSum = this.collaboratorAcquiredSkills?.filter(
            acquiredSkill => this.selectedProfileRequiredSkills?.find(requiredSkill => requiredSkill.skill.id === acquiredSkill.skill.id)
        ).map(acquiredSkill => valueOfSkillLevel(acquiredSkill.progress[acquiredSkill.progress.length - 1].level)).reduce((acc, cur) => acc + cur, 0);
        const requiredSkillsLevelsSum = this.selectedProfileRequiredSkills?.map(requiredSkill => valueOfSkillLevel(requiredSkill.level)).reduce(
            (acc, cur) => acc + cur, 0);
        const currentCareerPosition = this.collaboratorCareerPositions.find(
            collaboratorCareerPosition => collaboratorCareerPosition.status === CareerPositionStatusEnum.CURRENT);
        const monthsOfExperience = this.links.find(
            link => link.data.fromCareerStep.id === currentCareerPosition.profile.careerStep.id &&
            link.data.toCareerStep.id === this.selectedNode.data.step.id).data.yearsOfExperience * 12;
        const monthsSinceCurrentCareerPositionStarted = moment.duration(moment().diff(moment(currentCareerPosition.startedAt, DATE_EXTENDED_DISPLAY_FORMAT))).months();
        return Number((((acquiredSkillsLevelsSum / requiredSkillsLevelsSum) + (monthsSinceCurrentCareerPositionStarted / monthsOfExperience)) / 2) * 100).toFixed(0);
    }

    onSetGoal() {
        const dialogData: IDialogData = {
            title: 'Set career goal',
            content: 'You\'re about to set ' + this.selectedNode.data.step.label.toLowerCase()
                + ' as your next career step with ' + this.selectedProfile.label.toLowerCase()
                + ' as a target profile, do you wish to proceed?'
        };
        this.dialogService.open(
            ConfirmationDialogComponent,
            { context: { data: dialogData } }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    const nextCareerPosition: ICareerPosition = {
                        profile: this.selectedProfile,
                        collaborator: this.collaborator,
                        status: CareerPositionStatusEnum.NEXT
                    };
                    this.careerPositionService.createCareerPosition(nextCareerPosition).subscribe(
                        (careerPosition: ICareerPosition) => {
                            const oldNextCareerPositionIndex = this.collaboratorCareerPositions.findIndex(
                                collaboratorCareerPosition => collaboratorCareerPosition.status === CareerPositionStatusEnum.NEXT);
                            if (oldNextCareerPositionIndex > -1) {
                                this.collaboratorCareerPositions.splice(oldNextCareerPositionIndex, 1);
                            }
                            this.collaboratorCareerPositions.push(careerPosition);
                            this.selectedNode.data.position = careerPosition;
                            this.selectedNode.meta.covered = true;
                            this.nodes.forEach((node: INode) => {
                                if (node.id === this.selectedNode.id) {
                                    node.data.position = careerPosition;
                                    node.meta.covered = true;
                                }
                            });
                            this.toastrService.showStatusToastr(
                                'Your goal has been set successfully!',
                                ToastrStatusEnum.SUCCESS);
                        },
                        (error: IErrorResponse) => {
                            if (error.status >= 400 && error.status < 500) {
                                this.toastrService.showStatusToastr(
                                    'Something is not right! please try again later',
                                    ToastrStatusEnum.DANGER);
                            }
                        }
                    );
                }
            }
        );
    }

}
