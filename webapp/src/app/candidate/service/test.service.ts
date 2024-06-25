import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Test} from '../class/test';
import {TestQuestion} from '../class/test-question';
import {Candidate} from '../class/candidate';
import {PrmTechnology} from "../class/prm-technology";

@Injectable({
  providedIn: 'root'
})
export class TestService {
  private serverapiUrl = 'http://localhost:8081';
  constructor( private http: HttpClient) { }


    public getAllTestQuestions(id: number): Observable<Array<TestQuestion>> {
          return this.http.get<any>(`${this.serverapiUrl}/testQuestion/find/` + id);
    }

    public getTestById(id: number): Observable<Test> {
      return this.http.get<any>(`${this.serverapiUrl}/test/findtest/` + id);
    }

    public getCandidateByTestId(id: number): Observable<Candidate> {
        return this.http.get<any>(`${this.serverapiUrl}/test/getCandidate/` + id);
    }

    public getTechnolgiesByTest(id: number): Observable<Array<PrmTechnology>> {
      return this.http.get<any>(`${this.serverapiUrl}/testQuestion/getAllTechnologies/` + id);
    }
}
