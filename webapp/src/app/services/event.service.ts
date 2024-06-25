import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import {
    API_RESOURCES_BASE_URL,
    API_RESOURCES_PATHS,
} from "../constants/api-urls.constant";
import {
    FETCHING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_REQUESTS_HTTP_OPTIONS,
} from "../constants/http-options.constant";
import { IEvent } from "../models/event.model";

@Injectable()
export class EventService {

    constructor(
        private http: HttpClient
    ) { }

    getUpcomingEvents(): Observable<Array<IEvent>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.UPCOMING_EVENTS_PATH;

        return this.http.get<Array<IEvent>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }
    getEventScore(): Observable<any> {

        const url = API_RESOURCES_BASE_URL+  API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<any>(url, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
        }
    getPastEvents(): Observable<Array<IEvent>> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.PAST_EVENTS_PATH;

        return this.http.get<Array<IEvent>>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    addEvent(event : IEvent): Observable<IEvent> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.BASE_PATH

        return this.http.post<IEvent>(url, event, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    getEventByID(id: number): Observable<IEvent> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.BASE_PATH +
            "/" +
            id;

        return this.http.get<IEvent>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
    }
    /**
     * participate
     * @param idEvent
     * @param idCollab
     * @returns
     */
    participateToEvent(idEvent: number, idCollab: number): Observable<IEvent> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.PARTICIPATE_EVENTS_PATH +
            "/" +
            idEvent +
            "/" +
            idCollab;
        return this.http.get<IEvent>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
    }
    /**
     * participate
     * @param idEvent
     * @param idCollab
     * @returns
     */
    cancelEvent(idEvent: number, idCollab: number): Observable<IEvent> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS
                .CANCEL_EVENTS_PATH /*  */ +
            "/" +
            idEvent +
            "/" +
            idCollab;
        return this.http.get<IEvent>(url, FETCHING_JSON_REQUESTS_HTTP_OPTIONS);
    }

    deleteEvent(id: number, canceledMotif: string): Observable<void> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.DELETE_Event_PATH +
            "/" +
            id;

        return this.http.post<void>(url,canceledMotif,MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
    }

    updateEvent(event: IEvent, id:number): Observable<void>{
        const url =
        API_RESOURCES_BASE_URL +
        API_RESOURCES_PATHS.EVENTS_RESOURCE_PATHS.UPDATE_EVENT
        +"/"
        +id;

    return this.http.post<void>(url,event,MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
    }

}
