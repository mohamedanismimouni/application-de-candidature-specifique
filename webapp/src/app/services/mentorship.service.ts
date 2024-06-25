import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { diff } from 'jiff';

import { IMentorship } from '../models/mentorship.model';

import { MentorshipStatusEnum } from '../enumerations/mentorship-status.enum';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class MentorshipService {

    constructor(
        private http: HttpClient
    ) { }

    getMentorshipBySkillIdAndCareerPositionId(skillId: string, careerPositionId: number): Observable<IMentorship> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('skillId', skillId)
            .set('careerPositionId', careerPositionId.toString());

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.MENTORSHIPS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<IMentorship>(url, options);

    }

    getMentorshipsByMentorIdAndMenteeId(mentorId: number, menteeId: number, mentorshipStatus?: MentorshipStatusEnum): Observable<Array<IMentorship>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('mentorId', mentorId.toString())
            .set('menteeId', menteeId.toString());

        if (mentorshipStatus) {
            options.params = options.params.set('mentorshipStatus', mentorshipStatus);
        }

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.MENTORSHIPS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IMentorship>>(url, options);

    }

    getMentorshipsByMentorId(mentorId: number): Observable<Array<IMentorship>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('mentorId', mentorId.toString());

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.MENTORSHIPS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IMentorship>>(url, options);

    }

    evaluateMentorshipForMentor(mentorshipId: string, changes: {}): Observable<IMentorship> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.MENTORSHIPS_RESOURCE_PATHS.BASE_PATH
            + '/' + mentorshipId
            + API_RESOURCES_PATHS.MENTORSHIPS_RESOURCE_PATHS.MENTOR_EVALUATION_PATH;

        const jsonPatch = diff({}, changes, { invertible: false });

        return this.http.patch<IMentorship>(url, jsonPatch, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS);

    }

    evaluateMentorshipForMentee(mentorshipId: string, changes: {}): Observable<IMentorship> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.MENTORSHIPS_RESOURCE_PATHS.BASE_PATH
            + '/' + mentorshipId
            + API_RESOURCES_PATHS.MENTORSHIPS_RESOURCE_PATHS.MENTEE_EVALUATION_PATH;

        const jsonPatch = diff({}, changes, { invertible: false });

        return this.http.patch<IMentorship>(url, jsonPatch, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS);

    }

}
