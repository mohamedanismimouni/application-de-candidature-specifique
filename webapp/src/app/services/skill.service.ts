import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { ISkill } from '../models/skill.model';

import { SkillTypeEnum } from '../enumerations/skill-type.enum';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class SkillService {

    constructor(
        private http: HttpClient
    ) { }

    getSkills(skillType: SkillTypeEnum): Observable<Array<ISkill>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('skillType', skillType);

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.SKILLS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ISkill>>(url, options);

    }

    createSkill(skill: ISkill): Observable<ISkill> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.SKILLS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<ISkill>(url, skill, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
