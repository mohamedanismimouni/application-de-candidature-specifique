import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { diff } from 'jiff';

import { ITeam } from '../models/team.model';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import {
    FETCHING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS
} from '../constants/http-options.constant';


@Injectable()
export class TeamService {

    constructor(
        private http: HttpClient
    ) { }

    getTeamByManagerId(managerId: number): Observable<ITeam> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('managedBy', managerId.toString());

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.TEAMS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<ITeam>(url, options);

    }

    getTeams(): Observable<Array<ITeam>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.TEAMS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ITeam>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    createTeam(team: ITeam): Observable<ITeam> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.TEAMS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<ITeam>(url, team, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    partialUpdateTeam(teamId: string, changes: {}): Observable<ITeam> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.TEAMS_RESOURCE_PATHS.BASE_PATH
            + '/' + teamId;

        const jsonPatch = diff({}, changes, { invertible: false });

        return this.http.patch<ITeam>(url, jsonPatch, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS);

    }

    updateTeamName(managerTeamId: number, name: string): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.TEAMS_RESOURCE_PATHS.BASE_PATH
            + '/team-name'
            + '/' + managerTeamId;

        return this.http.post<void>(url, name, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
