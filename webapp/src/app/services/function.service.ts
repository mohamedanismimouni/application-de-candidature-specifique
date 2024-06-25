import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';
import { IFunction } from '../models/function.model';

@Injectable({
  providedIn: 'root'
})
export class FunctionService {

  constructor( private http: HttpClient) { }

  getFunctions(): Observable<Array<IFunction>> {

    const url = API_RESOURCES_BASE_URL
        + API_RESOURCES_PATHS.FUNCTION_PATHS.BASE_PATH;

    return this.http.get<Array<IFunction>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

}
}
