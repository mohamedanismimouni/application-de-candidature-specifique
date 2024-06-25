import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import {
    API_RESOURCES_BASE_URL,
    API_RESOURCES_PATHS,
} from "../constants/api-urls.constant";
import {
    FETCHING_JSON_REQUESTS_HTTP_OPTIONS,
    MUTATING_JSON_REQUESTS_HTTP_OPTIONS,
} from "../constants/http-options.constant";
import { IProverb } from "../models/proverb.model";

@Injectable({
    providedIn: "root",
})
export class ProverbService {
    constructor(private http: HttpClient) {}
    /**
     *
     * @returns get all proverbs
     */
    getProverbs(): Observable<Array<IProverb>> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.PROVERB_PATHS.BASE_PATH;
        return this.http.get<Array<IProverb>>(
            url,
            FETCHING_JSON_REQUESTS_HTTP_OPTIONS
        );
    }

        /**
     *
     * @returns get Proverb Of The Day proverbs
     */
         getProverbOfTheDay(): Observable<IProverb> {
            const url =
                API_RESOURCES_BASE_URL +
                API_RESOURCES_PATHS.PROVERB_PATHS.PUBLISHED_PROVERB_PATH;
            return this.http.get<IProverb>(
                url,
                FETCHING_JSON_REQUESTS_HTTP_OPTIONS
            );
        }
    
      /**
     *
     * @returns Check if exist prover of the day
     */
       existProverOfDay(): Observable<Boolean> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.PROVERB_PATHS.EXIST_PROVERB_DAY;
        return this.http.get<Boolean>(
            url,
            FETCHING_JSON_REQUESTS_HTTP_OPTIONS
        );
    }
    /**
     * add proverb
     * @param proverb
     * @returns
     */
    addEvent(proverb: IProverb): Observable<IProverb> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.PROVERB_PATHS.ADD_PROVERB_PATH;

        return this.http.post<IProverb>(
            url,
            proverb,
            MUTATING_JSON_REQUESTS_HTTP_OPTIONS
        );
    }

    /**
     *
     * @returns proverv Score
     */
    getProverbScore(): Observable<any> {
        const url =
            API_RESOURCES_BASE_URL +
            API_RESOURCES_PATHS.PROVERB_PATHS.GET_PROVERB_SCORE_PATH;

        return this.http.get<any>(url, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);
    }

 
/**
 * 
 * @param id 
 * @returns 
 */
   deleteProverb(id: number): Observable<void> {
    const url =
        API_RESOURCES_BASE_URL +
        API_RESOURCES_PATHS.PROVERB_PATHS.DELETE_PROVERB_PATH +
        "/" +
        id;

    return this.http.delete<void>(url);
}
}