import { HttpClient,HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { MUTATING_JSON_REQUESTS_HTTP_OPTIONS} from '../constants/http-options.constant';
import {FETCHING_JSON_REQUESTS_HTTP_OPTIONS,} from '../constants/http-options.constant';
import { IMoodCollab } from '../models/moodCollab.mode';

@Injectable({
  providedIn: 'root'
})
export class MoodCollabService {
   
    constructor(private http: HttpClient) { }

    
    createMood(mood: IMoodCollab): Observable<IMoodCollab> {

        const url = API_RESOURCES_BASE_URL + API_RESOURCES_PATHS.MOOD_COLLAB.BASE_PATH;

        return this.http.post<IMoodCollab>(url,mood, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
        }

        getMoodScore(): Observable<any> {

          const url = API_RESOURCES_BASE_URL+ API_RESOURCES_PATHS.MOOD_COLLAB.BASE_PATH;
  
          return this.http.get<any>(url, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
          }

    IsMoodAdded(userId: string):  Observable<Boolean> {

      const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
      options.params = new HttpParams().set('id', userId);

      const url = API_RESOURCES_BASE_URL + API_RESOURCES_PATHS.MOOD_COLLAB.ACTUAL_MOOD;

      return this.http.get<Boolean>(url, options);

  }

  updateMood(mood:IMoodCollab,userId: string): Observable<IMoodCollab> {
    const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
    options.params = new HttpParams().set('id', userId);
    const url = API_RESOURCES_BASE_URL + API_RESOURCES_PATHS.MOOD_COLLAB.UPDATE_MOOD;

    return this.http.put<IMoodCollab>(url,mood,options);

  }

  getActualMood(collaboratorId: string): Observable<IMoodCollab> {
    const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
    options.params = new HttpParams().set('collaboratorId', collaboratorId);
        const url = API_RESOURCES_BASE_URL + API_RESOURCES_PATHS.MOOD_COLLAB.CURRENT_MOOD;

        return this.http.get<IMoodCollab>(url, options);

  }


}