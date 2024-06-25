import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { diff } from 'jiff';

import { IPasswordSubmission } from '../models/password-submission.model';
import { ITeamsAssignmentStatistics } from '../models/teams-assignment-statistics.model';
import { ITeam } from '../models/team.model';
import { ISecretWord } from '../models/secret-word.model';

import { ProfileTypeEnum } from '../enumerations/profile-type.enum';
import { CareerPositionStatusEnum } from '../enumerations/career-position-status.enum';
import { MentorshipStatusEnum } from '../enumerations/mentorship-status.enum';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import {
    FETCHING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS
} from '../constants/http-options.constant';
import { ICollaborator } from '../models/collaborator.model';


@Injectable()
export class UserService {

    constructor(
        private http: HttpClient
    ) { }

    getUserById(userId: number): Observable<ICollaborator> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + userId;

        return this.http.get<ICollaborator>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }


    getUserTeamById(userId: number): Observable<ICollaborator> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.USER_TEAM_IFORMATION_PATH
            + '/' + userId;

        return this.http.get<ICollaborator>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }


    getUserByEmail(email: string): Observable<ICollaborator> {
        
        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('email', email);

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH;
   

        return this.http.get<ICollaborator>(url, options);

    }

    getCurrentUser(): Observable<ICollaborator> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.CURRENT_USER_PATH;

        return this.http.get<ICollaborator>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    getResourcesByTeamId(profileType: ProfileTypeEnum, teamId?: string): Observable<Array<ICollaborator>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('profileType', profileType)
            .set('teamId', (teamId === undefined) ? '' : teamId);

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ICollaborator>>(url, options);

    }

    getUsers(): Observable<Array<ICollaborator>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ICollaborator>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    getTeamMembers(
            initialized: boolean,
            teamId: string,
            profileId?: string,
            careerStepId?: string,
            supervisorId?: number,
            careerPositionStatus?: CareerPositionStatusEnum,
            mentorId?: number,
            mentorshipStatus?: MentorshipStatusEnum,
            recruitedBefore?: string) {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('initialized', '' + initialized)
            .set('teamId', teamId);
        if (profileId) {
            options.params = options.params.set('profileId', profileId);
        }
        if (careerStepId) {
            options.params = options.params.set('careerStepId', careerStepId);
        }
        if (supervisorId) {
            options.params = options.params.set('supervisorId', supervisorId.toString());
        }
        if (careerPositionStatus) {
            options.params = options.params.set('careerPositionStatus', careerPositionStatus);
        }
        if (mentorId) {
            options.params = options.params.set('mentorId', mentorId.toString());
        }
        if (mentorshipStatus) {
            options.params = options.params.set('mentorshipStatus', mentorshipStatus);
        }
        if (recruitedBefore) {
            options.params = options.params.set('recruitedBefore', recruitedBefore);
        }

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ICollaborator>>(url, options);

    }

    getTeamsAssignmentStatistics(): Observable<ITeamsAssignmentStatistics> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.TEAMS_ASSIGNMENT_STATISTICS_PATH;

        return this.http.get<ITeamsAssignmentStatistics>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    createUser(user: ICollaborator): Observable<ICollaborator> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<ICollaborator>(url, user, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    activateUserAccount(passwordSubmission: IPasswordSubmission): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.ACCOUNT_ACTIVATION_PATH;

        return this.http.post<void>(url, passwordSubmission, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    sendAccountActivationMail(id: number): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.ACCOUNT_ACTIVATION_MAIL_PATH
            + '/' + id;

        return this.http.post<void>(url, {}, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    sendPasswordResetMail(email: string): Observable<void> {

        const options = { ...MUTATING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('email', email);

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.PASSWORD_RESET_MAIL_PATH;

        return this.http.post<void>(url, {}, options);

    }

    resetPassword(passwordSubmission: IPasswordSubmission): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.PASSWORD_RESET_PATH;

        return this.http.post<void>(url, passwordSubmission, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    updateUserAccountStatus(id: number): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.ACCOUNT_STATUS_UPDATE_PATH
            + '/' + id;

        return this.http.post<void>(url, {}, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    assignCollaboratorToTeam(userId: number, teamId: string): Observable<ICollaborator> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + userId
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.MEMBER_OF_PATH;

        const jsonPatch = diff({}, { memberOf: { id: teamId } }, { invertible: false });

        return this.http.patch<ICollaborator>(url, jsonPatch, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS);

    }

    getCollaboratorTeam(userId: number): Observable<ITeam> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + userId
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.MEMBER_OF_PATH;

        return this.http.get<ITeam>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    initiateOnboardingProcess(userId: number, secretWordValue: string): Observable<ICollaborator> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + userId
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.SECRET_WORD_PATH;

        const jsonPatch = diff({}, { secretWord: secretWordValue }, { invertible: false });

        return this.http.patch<ICollaborator>(url, jsonPatch, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS);

    }

    validateSecretWord(userId: number, secretWord: ISecretWord): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + userId
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.SECRET_WORD_PATH;

        return this.http.post<void>(url, secretWord, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    getSecretWord(userId: number): Observable<ISecretWord> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + userId
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.SECRET_WORD_PATH;

        return this.http.get<ISecretWord>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    deleteUser(id: number): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + id;

        return this.http.delete<void>(url);

    }

    updateUser(user: ICollaborator): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/update-user'
            ;

        return this.http.post<void>(url, user, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    assignCollaboratorsToTeam(teamId: string, collaboratorsId: Array<number>): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.BASE_PATH
            + '/' + teamId
            + '/assignCollaborators';
        return this.http.post<void>(url, collaboratorsId, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
    }


}
