import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IOffers} from '../class/ioffers';

@Injectable({
  providedIn: 'root'
})
export class JobServiceService {

    baseURL = 'http://localhost:8081';
    constructor(private http: HttpClient) { }

    public getAllJobs(): Observable<Array<IOffers>> {
      return this.http.get<any>(`${this.baseURL}/offer/allOffers`);
    }

    public updateJob( offer: IOffers ): Observable<IOffers> {

      return(this.http.put<IOffers>(`http://localhost:8081/offer/update`, offer));
  }

  deleteJob(id:number):Observable<Object>{

    return this.http.delete(`http://localhost:8081/offer/delete/${id}`);

  }


  public saveJob( offer: IOffers ): Observable<IOffers> {

    return(this.http.post<IOffers>(`http://localhost:8081/offer`, offer));
}




}
