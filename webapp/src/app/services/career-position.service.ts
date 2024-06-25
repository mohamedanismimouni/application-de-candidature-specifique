import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { ICareerPosition } from '../models/career-position.model';

import { CareerPositionStatusEnum } from '../enumerations/career-position-status.enum';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class CareerPositionService {

    constructor(
        private http: HttpClient
    ) { }

    getCareerPositionsByCollaboratorIdAndStatus(collaboratorId: number, status?: CareerPositionStatusEnum): Observable<Array<ICareerPosition>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };

        options.params = new HttpParams().set('collaboratorId', collaboratorId.toString());

        if (status) {
            options.params = options.params.set('status', status);
        }

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.CAREER_POSITIONS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<ICareerPosition>>(url, options);

    }

    createCareerPosition(careerPosition: ICareerPosition): Observable<ICareerPosition> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.CAREER_POSITIONS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<ICareerPosition>(url, careerPosition, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    updateCareerPosition(careerPosition: ICareerPosition): Observable<ICareerPosition> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.CAREER_POSITIONS_RESOURCE_PATHS.UPDATE_PATH;

        return this.http.post<ICareerPosition>(url, careerPosition, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
