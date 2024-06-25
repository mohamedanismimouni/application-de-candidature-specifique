import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { IResponse } from '../models/response.model';

import { SurveyTypeEnum } from '../enumerations/survey-type.enum';

import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';


@Injectable()
export class ResponseService {

    constructor(
        private http: HttpClient
    ) { }

    getResponses(collaboratorId: number, surveyType: SurveyTypeEnum, evaluationId?: string): Observable<Array<IResponse>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('collaboratorId', collaboratorId.toString())
            .set('surveyType', surveyType);

        if (evaluationId) {
            options.params = options.params.set('evaluationId', evaluationId);
        }

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.RESPONSES_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IResponse>>(url, options);

    }

    createResponse(response: IResponse): Observable<IResponse> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.RESPONSES_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<IResponse>(url, response, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
