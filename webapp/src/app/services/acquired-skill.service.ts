import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { IAcquiredSkill } from '../models/acquired-skill.model';
import { IAcquiredSkillLevel } from '../models/acquired-skill-level.model';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class AcquiredSkillService {

    constructor(
        private http: HttpClient
    ) { }
 
    getAcquiredSkills(collaboratorId: number): Observable<Array<IAcquiredSkill>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('collaboratorId', collaboratorId.toString());

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.ACQUIRED_SKILLS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IAcquiredSkill>>(url, options);

    }

    createAcquiredSkills(acquiredSkills: Array<IAcquiredSkill>): Observable<Array<IAcquiredSkill>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.ACQUIRED_SKILLS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<Array<IAcquiredSkill>>(url, acquiredSkills, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    createAcquiredSkillLevel(acquiredSkillId: string, acquiredSkillLevel: IAcquiredSkillLevel): Observable<IAcquiredSkillLevel> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.ACQUIRED_SKILLS_RESOURCE_PATHS.BASE_PATH
            + '/' + acquiredSkillId
            + API_RESOURCES_PATHS.ACQUIRED_SKILLS_RESOURCE_PATHS.PROGRESS_PATH;

        return this.http.post<IAcquiredSkillLevel>(url, acquiredSkillLevel, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
