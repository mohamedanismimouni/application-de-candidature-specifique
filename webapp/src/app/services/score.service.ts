import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';

@Injectable({
  providedIn: 'root'
})
export class ScoreService {

  constructor(private http: HttpClient) { }

  getScore(email: string): Observable<number> {

    const url = API_RESOURCES_BASE_URL
        + API_RESOURCES_PATHS.SCORE.BASE_PATH
        +'/'
        + email;

    return this.http.get<number>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

}
}
