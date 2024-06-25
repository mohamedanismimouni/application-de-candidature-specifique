import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { ICareerPath } from '../models/career-path.model';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class CareerPathService {

    constructor(
        private http: HttpClient
    ) { }

    getCareerPaths(): Observable<ICareerPath[]> {

        const url = API_RESOURCES_BASE_URL + API_RESOURCES_PATHS.CAREER_PATHS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<ICareerPath[]>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    createCareerPath(careerPath: ICareerPath): Observable<ICareerPath> {

        const url = API_RESOURCES_BASE_URL + API_RESOURCES_PATHS.CAREER_PATHS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<ICareerPath>(url, careerPath, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    deleteCareerPath(id: string): Observable<void> {

        const url = API_RESOURCES_BASE_URL + API_RESOURCES_PATHS.CAREER_PATHS_RESOURCE_PATHS.BASE_PATH + '/' + id;

        return this.http.delete<void>(url);

    }

}
