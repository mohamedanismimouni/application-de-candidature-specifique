import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';
import { IImagePDF } from '../models/image-PDF.model';
import { IUsefulDocument } from '../models/useful-document.model';

@Injectable({
  providedIn: 'root'
})
export class UsefulDocumentService {

  constructor( private http: HttpClient) { }
  getUsefulDocument(): Observable<Array<IUsefulDocument>> {

    const url = API_RESOURCES_BASE_URL
        + API_RESOURCES_PATHS.USEFUL_DOCUMENT_PATHS.BASE_PATH;
    return this.http.get<Array<IUsefulDocument>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
}

updateUsefulDocument(requestUsefulDocument: IUsefulDocument): Observable<IUsefulDocument> {

  const url = API_RESOURCES_BASE_URL
      + API_RESOURCES_PATHS.USEFUL_DOCUMENT_PATHS.UPDATE_USEFUL_DOCUMENT

  return this.http.post<IUsefulDocument>(url, requestUsefulDocument, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
}

generateFirstUsefulDocumentPage(requestUsefulDocument: IUsefulDocument): Observable<void> {

  const url = API_RESOURCES_BASE_URL
      + API_RESOURCES_PATHS.USEFUL_DOCUMENT_PATHS.GENERATE_IMAGE;

  return this.http.post<void>(url, requestUsefulDocument);
}

deleteImage(requestUsefulDocument: IUsefulDocument): Observable<void> {

  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.USEFUL_DOCUMENT_PATHS.DELETE_IMAGE;

  return this.http.post<void>(url, requestUsefulDocument);

}
getImg(): Observable<IImagePDF[]> {

  const url = API_RESOURCES_BASE_URL
  + API_RESOURCES_PATHS.USEFUL_DOCUMENT_PATHS.GET_IMAGES;

  return this.http.get<IImagePDF[]>(url,FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

}
}
