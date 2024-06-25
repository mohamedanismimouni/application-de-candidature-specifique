import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { diff } from 'jiff';

import { IOnboarding } from '../models/onboarding.model';
import { IOnboardingHint } from '../models/onboarding-hint.model';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class OnboardingService {

    constructor(
        private http: HttpClient
    ) { }

    getOnboardings(freshRecruitId: number): Observable<Array<IOnboarding>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('freshRecruitId', freshRecruitId.toString());

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.ONBOARDINGS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IOnboarding>>(url, options);

    }

    getOnboarding(secretWordPartHolderId: number, freshRecruitId: number): Observable<IOnboarding> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('secretWordPartHolderId', secretWordPartHolderId.toString())
            .set('freshRecruitId', freshRecruitId.toString());

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.ONBOARDINGS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<IOnboarding>(url, options);

    }

    getOnboardingHint(freshRecruitId: number): Observable<IOnboardingHint> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('freshRecruitId', freshRecruitId.toString());

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.ONBOARDINGS_RESOURCE_PATHS.ONBOARDING_HINT_PATH;
        return this.http.get<IOnboardingHint>(url, options);

    }

    rateOnboarding(onboardingId: string, ratingValue: number): Observable<IOnboarding> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.ONBOARDINGS_RESOURCE_PATHS.BASE_PATH
            + '/' + onboardingId
            + API_RESOURCES_PATHS.ONBOARDINGS_RESOURCE_PATHS.RATING_PATH;

        const jsonPatch = diff({}, { rating: ratingValue }, { invertible: false });

        return this.http.patch<IOnboarding>(url, jsonPatch, MUTATING_JSON_PATCH_REQUESTS_HTTP_OPTIONS);

    }

}
