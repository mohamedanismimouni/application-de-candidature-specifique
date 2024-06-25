import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import {
    FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS,
} from '../constants/http-options.constant';
import { ICollaborator } from '../models/collaborator.model';


@Injectable()
export class CollaboratorService {

    constructor(
        private http: HttpClient
    ) { }

    getCollabs(): Observable<Array<ICollaborator>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.COLLAB_PATHS.BASE_PATH;

        return this.http.get<Array<ICollaborator>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    synchroCollabs(): Observable<Array<ICollaborator>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.COLLAB_PATHS.SYNCHRO_PATH;

        return this.http.get<Array<ICollaborator>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    getCollabsWithUser(): Observable<Array<ICollaborator>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.COLLAB_PATHS.PATH_COLLAB_WITH_USER;

        return this.http.get<Array<ICollaborator>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }
    updateCollabFunction(collabId: number, libelle:string): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.USERS_RESOURCE_PATHS.UPDATE_FUNCTION
            + '/'
            + collabId
            + '/'
            + libelle;

        return this.http.post<void>(url, {}, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

}
