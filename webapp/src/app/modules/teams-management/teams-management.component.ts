import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { NbAccordionComponent, NbDialogService } from '@nebular/theme';

import * as moment from 'moment';

import { TeamService } from '../../services/team.service';
import { UserService } from '../../services/user.service';

import { CreateTeamDialogComponent } from './create-team-dialog/create-team-dialog.component';
import { AddTeamMemberDialogComponent } from './add-team-member-dialog/add-team-member-dialog.component';

import { ISingleChartData } from '../../models/single-chart-data.model';
import { ITeamsAssignmentStatistics } from '../../models/teams-assignment-statistics.model';
import { ITeam } from '../../models/team.model';
import { IDatatableConfiguration } from '../shared/components/datatable/models/datatable-configuration.model';

import { ProfileTypeEnum } from '../../enumerations/profile-type.enum';

import { CHART_COLOR_SCHEME } from '../../constants/common.constant';
import { DATE_SHORT_DISPLAY_FORMAT } from '../../constants/date-time-formats.constant';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormService } from 'src/app/services/form.service';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ToastrService } from 'src/app/services/toastr.service';

import { blankValidator } from '../../utils/validators.util';
import { ICollaborator } from 'src/app/models/collaborator.model';
@Component({
    templateUrl: './teams-management.component.html',
    styleUrls: [ './teams-management.component.scss' ]
})
export class TeamsManagementComponent implements OnInit {

    collaboratorsTeamsAssignmentChartData: Array<ISingleChartData> = [];
    managersTeamsAssignmentChartData: Array<ISingleChartData> = [];
    loading=true;
    teams: Array<{teamInformation:ITeam, TeamMembersNumber:number}> = [];
    badgeText:"0";
    keyword :string;
    search = false;
    selectedTeamId: string;
    teamMembersDatatableConfiguration: IDatatableConfiguration = {
        title: 'Team members',
        rowId: 'id',
        columns: [
            {
                id: 'name',
                name: 'Name',
                searchable: true
            },
            {
                id: 'email',
                name: 'Email',
                searchable: true
            },
            {
                id: 'recruitedAt',
                name: 'Recruited at',
                searchable: true
            }
        ],
        rowContextMenuItems: [],
        datatableActions: [
            {
                icon: 'plus-outline',
                title: 'Add team member',
                action: () => this.onAddTeamMember()
            }
        ],
        pageSize: 5
    };

    teamMembersData: Array<any> = [];
    loadingState = false;
    pendingValue: string;
    selectedTeam: ITeam | null;
    updateTeamNameFormGroup: FormGroup ;
    exist:boolean;
    config: any;
    userSelected:any= {};
    @ViewChild('inputBox') inputBox: ElementRef;

    constructor(
        private dialogService: NbDialogService,
        private teamService: TeamService,
        private userService: UserService,
        public formService: FormService,
        private toastrService: ToastrService
    ) {this.pendingValue = "";
    this.selectedTeam = null; 
    this.config = {
        itemsPerPage: 5,
        currentPage: 1,
        totalItems: this.teams.length
      };}


    ngOnInit() {
     
        this.initializeChartsData();
        this.loadTeams();
         this.updateTeamNameFormGroup=new FormGroup({
            pendingValue : new FormControl('' ,[Validators.required,this.noWhitespaceValidator])
           
          });
         
    }
    
    noWhitespaceValidator(control: FormControl) {
        const isWhitespace = (control && control.value && control.value.toString() || '').trim().length === 0;
        const isValid = !isWhitespace;
        return isValid ? null : { 'whitespace': true };
      }

    fireEvent(e) {
        this.inputBox.nativeElement.focus();
        e.stopPropagation();
        e.stopImmediatePropagation();
        return false;
    }

    public cancel() : void {
		this.selectedTeam = null;
    }

	public edit( team: any ) : void {
        
        this.selectedTeam = team.teamInformation;
		this.pendingValue = team.teamInformation.name;
        this.loadingState = false;
    }

    public processChanges() : void {
        let names=[];
        this.teams.map(element=>{names.push(element.teamInformation.name)});
        this.exist = names.includes(this.pendingValue);
      
        if ( this.pendingValue !== this.selectedTeam!.name ) {
            if(this.exist){
                this.toastrService.showStatusToastr(
                    'A team with the same name already exists',
                    ToastrStatusEnum.DANGER);
            }
            else{
                this.selectedTeam!.name = this.pendingValue;
                this.teamService.updateTeamName(this.selectedTeam.managedBy.id,this.pendingValue).subscribe(
                () => {
                    this.loadingState = true;
                    this.toastrService.showStatusToastr(
                     'Team Name changed successfully',
                    ToastrStatusEnum.SUCCESS);
               });
            }
        }
         this.selectedTeam = null;
    }

    initializeChartsData() {
        this.userService.getTeamsAssignmentStatistics().subscribe(
            (teamsAssignmentStatistics: ITeamsAssignmentStatistics) => {
                this.collaboratorsTeamsAssignmentChartData = [];
                this.collaboratorsTeamsAssignmentChartData.push(
                    {
                        name: 'Assigned collaborators',
                        value: teamsAssignmentStatistics.assignedCollaborators,
                        color: CHART_COLOR_SCHEME.domain[0]
                    },
                    {
                        name: 'Unassigned collaborators',
                        value: teamsAssignmentStatistics.unassignedCollaborators,
                        color: CHART_COLOR_SCHEME.domain[1]
                    }
                );
                this.managersTeamsAssignmentChartData = [];
                this.managersTeamsAssignmentChartData.push(
                    {
                        name: 'Assigned managers',
                        value: teamsAssignmentStatistics.assignedManagers,
                        color: CHART_COLOR_SCHEME.domain[0]
                    },
                    {
                        name: 'Unassigned managers',
                        value: teamsAssignmentStatistics.unassignedManagers,
                        color: CHART_COLOR_SCHEME.domain[1]
                    }
                );
            }
        );
    }

    loadTeams() {
        this.teams=[]
        this.teamService.getTeams().subscribe(teams => 
            {    
                teams.forEach(team => {
                    this.userService.getResourcesByTeamId(ProfileTypeEnum.COLLABORATOR, team.id).subscribe(
                        (teamMembers: Array<ICollaborator>) => {
                            this.teams.push({teamInformation:team,TeamMembersNumber:  teamMembers.length});
                        }
                    );

                });
              this.loading=false
    
       }
            
            );
        this.selectedTeamId = undefined;
        this.teamMembersData = [];
    }

    onAccordionItemClick(teamId: string) {
        if (teamId === this.selectedTeamId) {
            this.selectedTeamId = undefined;
        } else {
            this.selectedTeamId = teamId;
        }
        if (this.selectedTeamId) {
            this.loadTeamMembers();
        }
    }

    loadTeamMembers() {
        this.userService.getResourcesByTeamId(ProfileTypeEnum.COLLABORATOR, this.selectedTeamId).subscribe(
            (teamMembers: Array<ICollaborator>) => {
                this.teamMembersData = teamMembers.map((teamMember: ICollaborator) => ({
                    id: teamMember.id,
                    name: teamMember.firstName + ' ' + teamMember.lastName,
                    email: teamMember.email,
                    recruitedAt: moment(teamMember.recruitedAt).format(DATE_SHORT_DISPLAY_FORMAT)
                }));
            }
        );
    }

    onAddTeam() {
        this.dialogService.open(CreateTeamDialogComponent).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.initializeChartsData();
                    this.loadTeams();
                }
            }
        );
    }

    onAddTeamMember() {
        this.dialogService.open(
            AddTeamMemberDialogComponent,
            {
                context: {
                    data: { teamId: this.selectedTeamId }
                }
            }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.initializeChartsData();
                    this.loadTeams();
                }
            }
        );
    }

    pageChanged(event) {
        this.config.currentPage = event;
      }

      toggleSearch() {
        this.search = !this.search;
         if (!this.search) {
            this.keyword = '';
        }
    }


}
