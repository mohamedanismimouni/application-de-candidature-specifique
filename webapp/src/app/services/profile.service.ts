import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { IProfile } from '../models/profile.model';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class ProfileService {

    constructor(
        private http: HttpClient
    ) { }

    getProfiles(teamId: string, initializedOnly?: boolean, careerStepId?: string): Observable<Array<IProfile>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('teamId', teamId);
        if (initializedOnly !== undefined) {
            options.params = options.params.set('initializedOnly', String(initializedOnly));
        } else if (careerStepId) {
            options.params = options.params.set('careerStepId', careerStepId);
        }

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.PROFILES_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IProfile>>(url, options);

    }

    createProfile(profile: IProfile): Observable<IProfile> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.PROFILES_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<IProfile>(url, profile, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
