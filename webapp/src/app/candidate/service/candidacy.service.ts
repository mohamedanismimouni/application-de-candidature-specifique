import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from '@angular/common/http';
import { Candidate } from '../class/candidate';
import {Test} from '../class/test';
import {ResultTestQuestion} from '../class/result-test-question';
import {ICollaborator} from '../../models/collaborator.model';

@Injectable({
  providedIn: 'root'
})
export class CandidacyService {

    constructor(private http: HttpClient) { }

    private baseUrl = 'http://localhost:8081';
    ticketInformation = {
        personalInformation: {
            firstname: '',
            lastname: '',
            age: null
        },
        seatInformation: {
            class: null,
            wagon: null,
            seat: null
        },
        paymentInformation: {
            cardholderName: '',
            cardholderNumber: '',
            date: '',
            cvv: '',
            remember: false
        }
    };

    private paymentComplete = new Subject<any>();

    paymentComplete$ = this.paymentComplete.asObservable();

    getTicketInformation() {
        return this.ticketInformation;
    }

    setTicketInformation(ticketInformation) {
        this.ticketInformation = ticketInformation;
    }

    complete() {
        this.paymentComplete.next(this.ticketInformation.personalInformation);
    }
    upload(file: File): Observable<HttpEvent<any>> {
        const formData: FormData = new FormData();
        formData.append('file', file);
        const req = new HttpRequest('POST', `${this.baseUrl}/candidate/indexCv`, formData, {
            reportProgress: true,
            responseType: 'json'
        });
        return this.http.request(req);
    }


    public getCandidates(): Observable<Candidate[]> {
        return(this.http.get<any>('http://localhost:8081/candidate/all'));
    }
    public getTestByCandidateId(id: number): Observable<Test> {
        return (this.http.get<any>('http://localhost:8081/test/testByCandidate/' + id));
    }
    public getResultDetail(id: number): Observable<Array<ResultTestQuestion>> {
        return this.http.get<any>('http://localhost:8081/test/testResult/' + id);
    }
    public getCvByCandidateId(id: number, firstname: string, lastname: string) {
        const REQUEST_PARAMS = new HttpParams().set('filename', 'CV_' + firstname + '_' + lastname);
        return this.http.get('http://localhost:8081/candidate/ExportCv/' + id, {
            params: REQUEST_PARAMS,
            responseType: 'arraybuffer'
        });
    }

    public getCandidateById(id: number): Observable<Candidate> {
        return(this.http.get<any>('http://localhost:8081/candidate/' + id));
    }


    public updateCandidate( candidat: Candidate ): Observable<Candidate> {

        return(this.http.put<Candidate>(`http://localhost:8081/candidate/update`, candidat));
    }
    public getCandidatesByCandidacyType(type: string): Observable<Array<Candidate>> {
        return this.http.get<any>('http://localhost:8081/candidate/candidatesByCandidacyType/' + type);
    }
    public rejectCandidate(id: number): Observable<Candidate> {
        return this.http.get<any>('http://localhost:8081/candidate/rejectCandidate/' + id);
    }
    public sendTest(id: number): Observable<boolean> {
        return this.http.get<any>('http://localhost:8081/candidate/sendTest/' + id);
    }
    public findCollaboratorsByProfileType(type: string): Observable<Array<ICollaborator>> {
        return this.http.get<any>('http://localhost:8081/api/v2/users/collaboratorsByProfileType/' + type);
    }
}
