import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { IRequiredSkill } from '../models/required-skill.model';

import { SkillTypeEnum } from '../enumerations/skill-type.enum';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class RequiredSkillService {

    constructor(
        private http: HttpClient
    ) { }

    getRequiredSkills(profileId: string, skillType?: SkillTypeEnum): Observable<Array<IRequiredSkill>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('profileId', profileId);

        if (skillType) {
            options.params = options.params.set('skillType', skillType);
        }

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUIRED_SKILLS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IRequiredSkill>>(url, options);

    }

    createRequiredSkill(requiredSkill: IRequiredSkill): Observable<IRequiredSkill> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUIRED_SKILLS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<IRequiredSkill>(url, requiredSkill, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
