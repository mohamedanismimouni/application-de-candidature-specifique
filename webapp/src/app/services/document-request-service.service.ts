import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { MUTATING_JSON_REQUESTS_HTTP_OPTIONS} from '../constants/http-options.constant';
import { RequestTypeEnum } from '../enumerations/request-type.enum';
import { IDocumentRequest } from '../models/document-request';
import {
  FETCHING_JSON_REQUESTS_HTTP_OPTIONS,

} from '../constants/http-options.constant';
import { IRequestType } from '../models/request-type';
import { IDocumentResuestsStatics } from '../models/DocumentResuestsStatics.model';
import { RequestStatusEnum } from '../enumerations/request-status.enum';
@Injectable({
  providedIn: 'root'
})
export class DocumentRequestServiceService {

  constructor(private http: HttpClient) { }


  sendDocumentRequest(createdById: number, collaboratorId: number, requestType: RequestTypeEnum, requestMotif:string , createdByRh : boolean): Observable<IDocumentRequest> {

    const url = API_RESOURCES_BASE_URL
        + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.BASE_PATH
        + '/'
        + createdById
        +'/'
        +collaboratorId
        + '/'
        +requestType
        +'/'
        +createdByRh

    return this.http.post<IDocumentRequest>(url, requestMotif,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
}

getDocumentType(): Observable<Array<IRequestType>> {
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.REQUESTS_TYPE_RESOURCE_PATHS.BASE_PATH
return this.http.get<Array<IRequestType>>(url,FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
}


getDocumentRequest(collaboratorId : string, type : number): Observable<Array<IDocumentRequest>> {

const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
options.params = new HttpParams().set('collaboratorId', collaboratorId);
options.params = options.params.set('type', type.toString());

const url = API_RESOURCES_BASE_URL
    + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.BASE_PATH;

return this.http.get<Array<IDocumentRequest>>(url, options);

}
/**
 * get In waiting document Request
 */
getInWaitingRequests(): Observable<Array<IDocumentRequest>> {

  const url = API_RESOURCES_BASE_URL
      + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.IN_WAITING_REQUEST_PATH;
  return this.http.get<Array<IDocumentRequest>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

}
/**
 * get Processed Requests
 */
getProcessedRequests(): Observable<Array<IDocumentRequest>> {

  const url = API_RESOURCES_BASE_URL
      + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.PROCESSED_REQUEST_PATH;
  return this.http.get<Array<IDocumentRequest>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
}
/**
 * Reject a docment Request
 * @param document
 */
documentRequestsRejection(hrResponsibleId: number, document: IDocumentRequest): Observable<IDocumentRequest> {
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.REJECTION_REQUEST_PATH
  + '/'
  +hrResponsibleId
  return this.http.post<IDocumentRequest>(url,document, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
}

CancelDemandeRequest(DemandeRequest : IDocumentRequest): Observable<IDocumentRequest>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.CANCEL_REQUEST_PATH;
return this.http.post<IDocumentRequest>(url, DemandeRequest,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

}

ValidateDemandeRequest(hrResponsibleId: number,DemandeRequest : IDocumentRequest): Observable<IDocumentRequest>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.VALIDATE_REQUEST_PATH  + '/'
  +hrResponsibleId
return this.http.post<IDocumentRequest>(url, DemandeRequest,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

}
ValidateAllDemandeRequest(hrResponsibleId: number,DemandeRequest : IDocumentRequest[]): Observable<IDocumentRequest[]>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.VALIDATE_ALL_REQUEST_PATH  + '/'
  +hrResponsibleId
return this.http.post<IDocumentRequest[]>(url, DemandeRequest,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

}


downloadEtiquetteMergedFiles(  requestsToValidate: IDocumentRequest[]): Observable<Blob> {
  const headers = new HttpHeaders({
   'Content-Type': 'application/json',
        'Accept': 'application/json'
  });
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.DOWNLOAD_ETIQUETTE_MERGED_PATH
  return this.http.post<Blob>(url,
    
      requestsToValidate
   , {responseType: 'blob' as 'json' });
}


requestsWithoutTemplate(requestType : IRequestType): Observable<number>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.REQUESTS_WITHOUT_TEMPLAT

return this.http.post<number>(url, requestType,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

}


getCollabRequests( idCollab:number): Observable<IDocumentRequest[]>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.BASE_PATH
  +'/'
  +idCollab
  
return this.http.post<IDocumentRequest[]>(url,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

}
getCollabsRequestsStatics( idCollab:number): Observable<IDocumentResuestsStatics[]>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.BASE_PATH
  +'/statics/'
  +idCollab
  
return this.http.post<IDocumentResuestsStatics[]>(url,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
}

ReceivedDemandeRequest(DemandeRequest : IDocumentRequest): Observable<IDocumentRequest>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.DOCUMENT_REQUEST_PATHS.UPDATE_REQUEST_PATH;
return this.http.post<IDocumentRequest>(url, DemandeRequest,  MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

}

countDocumentBySatus(status: RequestStatusEnum, collabId: number):Observable<number>{
  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.KPI_COLLAB_PATH.BASE_PATH
  +'/'
  + status
  +'/'
  + collabId

return this.http.get<number>(url,FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
}

}
