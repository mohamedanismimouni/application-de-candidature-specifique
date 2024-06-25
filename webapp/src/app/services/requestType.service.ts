import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import {
    FETCHING_JSON_REQUESTS_HTTP_OPTIONS,

} from '../constants/http-options.constant';
import { MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';
import { IRequestType } from '../models/request-type';
import { RequestTypeEnum } from '../enumerations/request-type.enum';



@Injectable()
export class RequestTypeService {

    constructor(
        private http: HttpClient
    ) { }

    getRequestsType(): Observable<Array<IRequestType>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUESTS_TYPE_RESOURCE_PATHS.BASE_PATH;
        return this.http.get<Array<IRequestType>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
    }

    getRequestsTemplate(): Observable<Array<IRequestType>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUESTS_TYPE_RESOURCE_PATHS.GET_TEMPLATE_REQUESTS;
        return this.http.get<Array<IRequestType>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
    }


    updateRequestType(requestType: IRequestType): Observable<IRequestType> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUESTS_TYPE_RESOURCE_PATHS.UPDATE_REQUEST_TYPE

        return this.http.post<IRequestType>(url, requestType, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
    }


    lastUploadOfPayRoll(): Observable<Date> {
        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUESTS_TYPE_RESOURCE_PATHS.LAST_UPLOAD_PAYROLL;
        return this.http.get<Date>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
    }

    lastUploadOfPReminder(): Observable<Date> {
        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUESTS_TYPE_RESOURCE_PATHS.LAST_UPLOAD_REMINDER;
        return this.http.get<Date>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
    }

    getTypeByLabel(requestTypeEnum: RequestTypeEnum):Observable<IRequestType> {
        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.REQUESTS_TYPE_RESOURCE_PATHS.GET_TYPE_BY_LABEL
            +'/'
            +requestTypeEnum

        return this.http.get<IRequestType>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }
}
