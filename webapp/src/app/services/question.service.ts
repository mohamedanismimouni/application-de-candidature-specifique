import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Observable } from 'rxjs';

import { IQuestion } from '../models/question.model';
import { IChoice } from '../models/choice.model';

import { SurveyTypeEnum } from '../enumerations/survey-type.enum';

import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { FETCHING_JSON_REQUESTS_HTTP_OPTIONS, MUTATING_JSON_REQUESTS_HTTP_OPTIONS } from '../constants/http-options.constant';


@Injectable()
export class QuestionService {

    constructor(
        private http: HttpClient
    ) { }

    getQuestions(surveyType: SurveyTypeEnum, enabled?: boolean): Observable<Array<IQuestion>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('surveyType', surveyType)
            .set('enabled', (enabled === undefined) ? '' : String(enabled));

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.BASE_PATH;

        return this.http.get<Array<IQuestion>>(url, options);

    }

    getQuizChoicesByValidStatus(questionId: string, valid: boolean): Observable<Array<IChoice>> {

        const options = { ...FETCHING_JSON_REQUESTS_HTTP_OPTIONS };
        options.params = new HttpParams().set('valid', String(valid));

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.BASE_PATH
            + '/' + questionId
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.CHOICES_PATH;

        return this.http.get<Array<IChoice>>(url, options);

    }

    createQuestion(question: IQuestion): Observable<IQuestion> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.BASE_PATH;

        return this.http.post<IQuestion>(url, question, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    createChoice(questionId: string, choice: IChoice): Observable<IChoice> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.BASE_PATH
            + '/' + questionId
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.CHOICES_PATH;

        return this.http.post<IChoice>(url, choice, MUTATING_JSON_REQUESTS_HTTP_OPTIONS);

    }

    deleteQuestion(questionId: string): Observable<void> {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.BASE_PATH
            + '/' + questionId;

        return this.http.delete<void>(url);

    }

    deleteChoice(questionId: string, choiceId: string) {

        const url = API_RESOURCES_BASE_URL
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.BASE_PATH
            + '/' + questionId
            + API_RESOURCES_PATHS.QUESTIONS_RESOURCE_PATHS.CHOICES_PATH
            + '/' + choiceId;

        return this.http.delete<void>(url);

    }

}
