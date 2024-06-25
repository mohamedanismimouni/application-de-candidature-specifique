import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { diff } from 'jiff';

import { IEvaluation } from '../models/evaluation.model';

import { EvaluationStatusEnum } from '../enumerations/evaluation-status.enum';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import {
    FETCHING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS
} from '../constants/http-options.constant';


@Injectable()
export class EvaluationService {

    constructor(
        private http: HttpClient
    ) { }             

    getEvaluations(collaboratorId: number, supervisorId?: number, evaluationStatus?: EvaluationStatusEnum): Observable<Array<IEvaluation>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('collaboratorId', collaboratorId.toString());

        if (supervisorId) {
            options.params = options.params.set('supervisorId', supervisorId.toString());
        }

        if (evaluationStatus) {
            options.params = options.params.set('evaluationStatus', evaluationStatus);
        }

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.EVALUATIONS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IEvaluation>>(url, options);

    }

    createEvaluation(evaluation: IEvaluation): Observable<IEvaluation> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.EVALUATIONS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<IEvaluation>(url, evaluation, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    partialUpdateEvaluation(evaluationId: string, changes: {}): Observable<IEvaluation> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.EVALUATIONS_RESOURCE_PATHS.BASE_PATH
            + '/' + evaluationId;

        const jsonPatch = diff({}, changes, { invertible: false });

        return this.http.patch<IEvaluation>(url, jsonPatch, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS);

    }

}
