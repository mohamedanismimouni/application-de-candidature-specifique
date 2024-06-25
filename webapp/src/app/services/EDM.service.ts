import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {
    API_RESOURCES_BASE_URL,
    API_RESOURCES_PATHS,
} from "../constants/api-urls.constant";
import { IDocument } from "../models/document.model";
@Injectable()
export class EDMService {
    constructor(private http: HttpClient) {}
    /**
     * upload File
     * @param file
     * @param userID
     * @param parentFolder
     * @param identifier
     */
    uploadFile(
        file: File,
        userID: string,
        parentFolder: string,
        identifier: string
    ): Observable<IDocument> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.EDM_FILE_PATHS.UPLOAD_SINGLE_FILE +
            "/" +
            userID +
            "/" +
            parentFolder +
            "/" +
            identifier;
        const data: FormData = new FormData();
        data.append("file", file);
        return this.http.post<IDocument>(url, data);
    }

    /**
     *
     * @param documentID download File from EDM */
     downloadFile(documentID: number):Observable<Blob> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.EDM_FILE_PATHS.DOWNLOAD_SINGLE_FILE +
            "/" +
            documentID;

        return this.http.get<Blob>(url, {
            responseType: 'blob' as 'json'}
        );
    }

    /**
     * delete file
     * @param id
     */

    deleteDocument(id: number): Observable<void> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.EDM_FILE_PATHS.DELDETE_SINGLE_FILE +
            "/" +
            id;

        return this.http.delete<void>(url);
    }
}
