import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class QuizService {

    constructor(
        private http: HttpClient
    ) { }

    getQuiz(): Observable<any> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUIZ_PATHS.BASE_PATH;

        return this.http.get<Array<any>>(url, options);

    }

    submitAnswer(response: string,id :string): Observable<any> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('response', response).set('id',id);

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUIZ_PATHS.BASE_PATH;

        return this.http.get<Array<any>>(url, options);

    }

}
