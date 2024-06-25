import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {
    API_RESOURCES_BASE_URL,
    API_RESOURCES_PATHS,
} from "../constants/api-urls.constant";
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS } from "../constants/http-options.constant";
import { IUsefulLink } from "../models/usefulLink.model";

@Injectable()
export class UsefulLinkService {
    constructor(private http: HttpClient) {}

    getUsefulLinks(): Observable<Array<IUsefulLink>> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.USEFUL_LINK_PATHS.BASE_PATH;

        return this.http.get<Array<IUsefulLink>>(
            url,
            FETCHING_JSON_REQUESTS_HTTP_OPTIONS
        );
    }
}
