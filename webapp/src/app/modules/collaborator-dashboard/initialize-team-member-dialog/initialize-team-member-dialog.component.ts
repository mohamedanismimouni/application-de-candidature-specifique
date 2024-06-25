import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import * as moment from 'moment';

import { FormService } from '../../../services/form.service';
import { StorageService } from '../../../services/storage.service';
import { UserService } from '../../../services/user.service';
import { TeamService } from '../../../services/team.service';
import { CareerStepService } from '../../../services/career-step.service';
import { ProfileService } from '../../../services/profile.service';
import { CareerPositionService } from '../../../services/career-position.service';
import { EvaluationService } from '../../../services/evaluation.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';

import { ITeam } from '../../../models/team.model';
import { ICareerStep } from '../../../models/career-step.model';
import { IProfile } from '../../../models/profile.model';
import { ICareerPosition } from '../../../models/career-position.model';
import { IEvaluation } from '../../../models/evaluation.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { StorageKeyEnum } from '../../../enumerations/storage-key.enum';
import { CareerPositionStatusEnum } from '../../../enumerations/career-position-status.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';

import { DATE_STORAGE_FORMAT, MONTH_DAY_STORAGE_FORMAT } from '../../../constants/date-time-formats.constant';
import { COLLABORATOR_PROBATIONARY_PERIOD_IN_MONTHS } from '../../../constants/common.constant';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './initialize-team-member-dialog.component.html',
    styleUrls: [ './initialize-team-member-dialog.component.scss' ]
})
export class InitializeTeamMemberDialogComponent implements OnInit {

    @Input()
    data: any;

    collaborator: ICollaborator;

    requiresOnboardingProcess = false;

    manager: ICollaborator;

    team: ITeam;

    careerSteps: Array<ICareerStep>;

    profiles: Array<IProfile>;

    supervisors: Array<ICollaborator>;
    displayedSupervisors: Array<ICollaborator>;

    supervisorsSearchFormGroup: FormGroup = this.formBuilder.group({
        keyword: ['']
    });
    searchSupervisors = false;

    selectedSupervisor: ICollaborator;

    initializeTeamMemberFormGroup: FormGroup = this.formBuilder.group({
        careerStep:     [ '', Validators.required ],
        profile:        [ '', Validators.required ],
        startedAt:      [ '', Validators.required ],
        evaluationDate: [ '', Validators.required ]
    });

    minStartedAtDate: Date;
    maxStartedAtDate = new Date();

    minEvaluationDate: Date;

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<InitializeTeamMemberDialogComponent>,
        public formService: FormService,
        private storageService: StorageService,
        private userService: UserService,
        private teamService: TeamService,
        private careerStepService: CareerStepService,
        private profileService: ProfileService,
        private careerPositionService: CareerPositionService,
        private evaluationService: EvaluationService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.userService.getUserById(this.data.collaboratorId).subscribe(
            (collaborator: ICollaborator) => {
                this.collaborator = collaborator;
                this.initializeTeamMemberFormGroup.get('startedAt').setValue(new Date(this.collaborator.recruitedAt));
                this.minStartedAtDate = new Date(moment(this.collaborator.recruitedAt).subtract(1, 'days').format(DATE_STORAGE_FORMAT));
                if (moment.duration(moment().diff(moment(this.collaborator.recruitedAt, DATE_STORAGE_FORMAT))).asMonths() < COLLABORATOR_PROBATIONARY_PERIOD_IN_MONTHS) {
                    this.requiresOnboardingProcess = true;
                    this.initializeTeamMemberFormGroup.addControl(
                        'onboardingProcessSecretWord',
                        new FormControl('', Validators.required, blankValidator() as any)
                    );
                }
                this.manager = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
                this.teamService.getTeamByManagerId(this.manager.id).subscribe(
                    (team: ITeam) => {
                        this.team = team;
                        this.careerStepService.getCareerStepsAssociatedToProfilesWithTeamId(this.team.id).subscribe(
                            (careerSteps: Array<ICareerStep>) => {
                                this.careerSteps = careerSteps;
                            }
                        );
                        this.userService.getTeamMembers(
                            true,
                            this.team.id,
                            undefined,
                            undefined,
                            undefined,
                            CareerPositionStatusEnum.CURRENT,
                            undefined,
                            undefined,
                            this.collaborator.recruitedAt
                        ).subscribe(
                            supervisors => {
                                this.supervisors = [ this.manager, ...supervisors ];
                                this.displayedSupervisors = [ this.manager, ...supervisors ];
                            }
                        );
                        this.minEvaluationDate = new Date(moment().subtract(1, 'days').format(DATE_STORAGE_FORMAT));
                        if (this.team.teamEvaluationDate) {
                            const teamEvaluationMoment = moment(this.team.teamEvaluationDate, MONTH_DAY_STORAGE_FORMAT);
                            if (teamEvaluationMoment.isBefore(moment())) {
                                teamEvaluationMoment.add(1, 'years');
                            }
                            this.initializeTeamMemberFormGroup.get('evaluationDate').setValue(new Date(teamEvaluationMoment.format(DATE_STORAGE_FORMAT)));
                        }
                    }
                );
            }
        );
    }

    onCareerStepSelectionChange() {
        this.initializeTeamMemberFormGroup.get('profile').reset();
        this.profileService.getProfiles(this.team.id, undefined, this.initializeTeamMemberFormGroup.get('careerStep').value.id).subscribe(
            profiles => this.profiles = profiles
        );
    }

    toggleSupervisorsSearch() {
        this.searchSupervisors = !this.searchSupervisors;
        if (!this.searchSupervisors) {
            this.supervisorsSearchFormGroup.get('keyword').reset();
            this.onSupervisorsKeywordInputChange();
        }
    }

    onSupervisorsKeywordInputChange() {
        let keyword = this.supervisorsSearchFormGroup.get('keyword').value;
        if (!keyword) {
            keyword = '';
        }
        keyword = keyword.trim().toLowerCase();
        this.displayedSupervisors = this.supervisors.filter(
            supervisor => (supervisor.firstName + ' ' + supervisor.lastName).toLowerCase().includes(keyword)
        );
    }

    onSupervisorSelection(supervisor: ICollaborator) {
        if (supervisor.id === this.selectedSupervisor?.id) {
            this.selectedSupervisor = undefined;
        } else {
            this.selectedSupervisor = supervisor;
        }
    }

    onCancel() {
        this.dialogRef.close();
    }

    onSubmit() {
        const careerPosition: ICareerPosition = this.initializeTeamMemberFormGroup.value;
        careerPosition.collaborator = this.collaborator;
        careerPosition.supervisor = this.selectedSupervisor;
        careerPosition.startedAt = moment(careerPosition.startedAt).format(DATE_STORAGE_FORMAT);
        careerPosition.status = CareerPositionStatusEnum.CURRENT;
        this.loadingState = true;
        this.careerPositionService.createCareerPosition(careerPosition).subscribe(
            (createdCareerPosition: ICareerPosition) => {
                const evaluation: IEvaluation = {
                    careerPosition: createdCareerPosition,
                    evaluationDate: moment(this.initializeTeamMemberFormGroup.get('evaluationDate').value).format(DATE_STORAGE_FORMAT)
                };
                if (this.requiresOnboardingProcess) {
                    this.userService.initiateOnboardingProcess(
                        this.collaborator.id,
                        this.initializeTeamMemberFormGroup.get('onboardingProcessSecretWord').value
                    ).subscribe(
                        () => {
                            this.evaluationService.createEvaluation(evaluation).subscribe(
                                () => {
                                    this.loadingState = false;
                                    this.toastrService.showStatusToastr(
                                        'Team member initialized, onboarding '
                                        + 'process initiated successfully and '
                                        + 'evaluation is created',
                                        ToastrStatusEnum.SUCCESS);
                                    this.dialogRef.close(true);
                                },
                                (error: IErrorResponse) => {
                                    this.loadingState = false;
                                    if (error.status >= 400 && error.status < 500) {
                                        this.toastrService.showStatusToastr(
                                            'Team member initialized and onboarding process initiated '
                                            + 'successfully, however there was a problem while creating '
                                            + 'the evaluation, please try again later',
                                            ToastrStatusEnum.DANGER);
                                    }
                                    this.dialogRef.close(true);
                                }
                            );
                        },
                        (error: IErrorResponse) => {
                            this.loadingState = false;
                            if (error.status >= 400 && error.status < 500) {
                                this.toastrService.showStatusToastr(
                                    'Team member initialized successfully, but onboarding process '
                                    + 'could not be initiated right now please try again later',
                                    ToastrStatusEnum.DANGER);
                            }
                            this.dialogRef.close(true);
                        }
                    );
                } else {
                    this.evaluationService.createEvaluation(evaluation).subscribe(
                        () => {
                            this.loadingState = false;
                            this.toastrService.showStatusToastr(
                                'Team member initialized successfully '
                                + 'and evaluation is created',
                                ToastrStatusEnum.SUCCESS);
                            this.dialogRef.close(true);
                        },
                        (error: IErrorResponse) => {
                            this.loadingState = false;
                            if (error.status >= 400 && error.status < 500) {
                                this.toastrService.showStatusToastr(
                                    'Team member initialized successfully, however there was a problem '
                                    + 'while creating the evaluation, please try again later',
                                    ToastrStatusEnum.DANGER);
                            }
                            this.dialogRef.close(true);
                        }
                    );
                }
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                if  (error.status >= 400 && error.status < 500) {
                    this.toastrService.showStatusToastr(
                        'Something is not right, please verify '
                        + 'your input and try again later',
                        ToastrStatusEnum.DANGER);
                }
            }
        );
    }

}
