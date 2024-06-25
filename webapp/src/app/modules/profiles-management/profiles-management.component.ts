import { Component, OnInit } from '@angular/core';

import { NbDialogService } from '@nebular/theme';

import { StorageService } from '../../services/storage.service';
import { CareerPathService } from '../../services/career-path.service';
import { CareerStepService } from '../../services/career-step.service';
import { TeamService } from '../../services/team.service';
import { ProfileService } from '../../services/profile.service';
import { RequiredSkillService } from '../../services/required-skill.service';

import { CreateProfileDialogComponent } from './create-profile-dialog/create-profile-dialog.component';
import { CreateRequiredSkillDialogComponent } from './create-required-skill-dialog/create-required-skill-dialog.component';

import { formatSkillType, formatSkillWeight, valueOfSkillLevel } from '../../utils/formaters.util';

import { INode } from '../shared/components/graph/models/node.model';
import { ILink } from '../shared/components/graph/models/link.model';
import { IGraphConfiguration } from '../shared/components/graph/models/graph-configuration.model';
import { ICareerStep } from '../../models/career-step.model';
import { ICareerPath } from '../../models/career-path.model';
import { ITeam } from '../../models/team.model';
import { IProfile } from '../../models/profile.model';
import { IRequiredSkill } from '../../models/required-skill.model';

import { StorageKeyEnum } from '../../enumerations/storage-key.enum';
import { SkillTypeEnum } from '../../enumerations/skill-type.enum';


@Component({
    templateUrl: './profiles-management.component.html',
    styleUrls: [ './profiles-management.component.scss' ]
})
export class ProfilesManagementComponent implements OnInit {

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

    team: ITeam;

    profiles: Array<IProfile>;
    selectedCareerStepProfiles: Array<IProfile>;
    selectedProfile: IProfile;
    selectedProfileRequiredSkills: Array<IRequiredSkill>;

    skillTypes = Object.values(SkillTypeEnum).map(skillType => ({
        name: 'Required ' + formatSkillType(skillType).toLowerCase() + ' skills',
        value: skillType
    }));

    selectedSkillType = SkillTypeEnum.HUMAN;

    constructor(
        private dialogService: NbDialogService,
        private storageService: StorageService,
        private careerPathService: CareerPathService,
        private careerStepService: CareerStepService,
        private teamService: TeamService,
        private profileService: ProfileService,
        private requiredSkillService: RequiredSkillService
    ) { }

    ngOnInit() {
        this.initializeCareersGraph();
    }

    initializeCareersGraph() {
        this.careerPathService.getCareerPaths().subscribe(
            (careerPaths: Array<ICareerPath>) => {
                this.careerPaths = careerPaths;
                this.careerStepService.getCareerSteps().subscribe(
                    (careerSteps: Array<ICareerStep>) => {
                        this.careerSteps = careerSteps;
                        this.teamService.getTeamByManagerId(
                            this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY)?.id
                        ).subscribe(
                            (team: ITeam) => {
                                this.team = team;
                                this.loadTeamProfiles();
                            }
                        );
                    }
                );
            }
        );
    }

    loadTeamProfiles() {
        this.profileService.getProfiles(this.team.id, false).subscribe(
            (profiles: Array<IProfile>) => {
                this.profiles = profiles;
                this.links = this.careerPaths.map((careerPath, index) => ({
                    id: 'link-' + index.toString(),
                    source: careerPath.fromCareerStep.label.toLowerCase().replace(' ', '-'),
                    target: careerPath.toCareerStep.label.toLowerCase().replace(' ', '-'),
                    data: careerPath
                }));
                this.nodes = this.careerSteps.map((careerStep) => ({
                    id: careerStep.label.toLowerCase().replace(' ', '-'),
                    label: careerStep.label,
                    data: careerStep,
                    meta: {
                        selectable: true,
                        covered: this.isCareerStepCovered(careerStep),
                        reachable: this.isCareerStepReachable(careerStep)
                    }
                }));
                if (this.selectedNode) {
                    this.initializeSelectedCareerStepProfiles();
                }
            }
        );
    }

    isCareerStepCovered(careerStep: ICareerStep): boolean {
        return this.profiles.filter(profile => profile.careerStep.id === careerStep.id).length > 0;
    }

    isCareerStepReachable(careerStep: ICareerStep): boolean {
        const predecessorCareerSteps: Array<ICareerStep> = this.links.filter(
            link => link.data.toCareerStep.id === careerStep.id
        ).map(link => link.data.fromCareerStep);
        if (predecessorCareerSteps.length === 0) {
            return true;
        }
        for (const predecessorCareerStep of predecessorCareerSteps) {
            if (this.isCareerStepCovered(predecessorCareerStep)) {
                return true;
            }
        }
        return false;
    }

    initializeSelectedCareerStepInboundCareerPaths() {
        this.inboundCareerPaths = this.links.filter(
            link => link.data.toCareerStep.id === this.selectedNode.data.id
        ).map(link => link.data);
    }

    initializeSelectedCareerStepProfiles() {
        this.selectedCareerStepProfiles = this.profiles
            .filter(profile => profile.careerStep.id === this.selectedNode.data.id);
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

    onAddProfile() {
        this.dialogService.open(
            CreateProfileDialogComponent,
            {
                context: {
                    data: {
                        team: this.team,
                        careerStep: this.selectedNode.data
                    }
                }
            }
        ).onClose.subscribe(
            (createdProfile: IProfile) => {
                if (createdProfile) {
                    this.selectedProfile = createdProfile;
                    this.loadTeamProfiles();
                    this.selectedSkillType = SkillTypeEnum.HUMAN;
                    this.loadProfileRequiredSkills();
                }
            }
        );
    }

    hideProfileDrawer() {
        this.selectedProfile = undefined;
        this.selectedSkillType = SkillTypeEnum.HUMAN;
    }

    onAccordionItemClick(skillType: SkillTypeEnum) {
        if (skillType === this.selectedSkillType) {
            this.selectedSkillType = undefined;
        } else {
            this.selectedSkillType = skillType;
        }
        if (this.selectedSkillType) {
            this.loadProfileRequiredSkills();
        }
    }

    loadProfileRequiredSkills() {
        this.requiredSkillService.getRequiredSkills(this.selectedProfile.id, this.selectedSkillType).subscribe(
            (requiredSkills: Array<IRequiredSkill>) => {
                this.selectedProfileRequiredSkills = requiredSkills.map(requiredSkill => ({
                    ...requiredSkill,
                    level: valueOfSkillLevel(requiredSkill.level) * 100 as any,
                    weight: formatSkillWeight(requiredSkill.weight) as any
                }));
            }
        );
    }

    onAddRequiredSkill() {
        this.dialogService.open(
            CreateRequiredSkillDialogComponent,
            {
                context: {
                    data: {
                        skillType: this.selectedSkillType,
                        unavailableSkills: this.selectedProfileRequiredSkills
                            .map(requiredSkill => requiredSkill.skill),
                        profile: this.selectedProfile
                    }
                }
            }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.loadProfileRequiredSkills();
                }
            }
        );
    }

}
