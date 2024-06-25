import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { ICareerStep } from '../models/career-step.model';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class CareerStepService {

    constructor(
        private http: HttpClient
    ) { }

    getCareerSteps(): Observable<Array<ICareerStep>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.CAREER_STEPS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ICareerStep>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    getCareerStepsAssociatedToProfilesWithTeamId(teamId: string): Observable<Array<ICareerStep>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('teamId', teamId);

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.CAREER_STEPS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ICareerStep>>(url, options);

    }

    createCareerStep(careerStep: ICareerStep): Observable<ICareerStep> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.CAREER_STEPS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<ICareerStep>(url, careerStep, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    deleteCareerStep(id: string): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.CAREER_STEPS_RESOURCE_PATHS.BASE_PATH
            + '/' + id;

        return this.http.delete<void>(url);

    }

}
