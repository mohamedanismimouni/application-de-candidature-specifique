import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import * as moment from 'moment';

import { FormService } from '../../../../services/form.service';
import { EvaluationService } from '../../../../services/evaluation.service';
import { QuestionService } from '../../../../services/question.service';
import { ResponseService } from '../../../../services/response.service';
import { ToastrService } from '../../../../services/toastr.service';

import { blankValidator } from '../../../../utils/validators.util';

import { IEvaluation } from '../../../../models/evaluation.model';
import { IQuestion } from '../../../../models/question.model';
import { IResponse } from '../../../../models/response.model';
import { IChoice } from '../../../../models/choice.model';
import { IErrorResponse } from '../../../../models/error-response.model';

import { SurveyTypeEnum } from '../../../../enumerations/survey-type.enum';
import { EvaluationStatusEnum } from '../../../../enumerations/evaluation-status.enum';
import { QuestionTypeEnum } from '../../../../enumerations/question-type.enum';
import { ToastrStatusEnum } from '../../../../enumerations/toastr-status.enum';

import { DATE_STORAGE_FORMAT } from '../../../../constants/date-time-formats.constant';


@Component({
    selector: 'app-survey',
    templateUrl: './survey.component.html',
    styleUrls: [ './survey.component.scss' ]
})
export class SurveyComponent implements OnInit {

    @Input()
    collaboratorId: number;

    @Input()
    surveyType: SurveyTypeEnum;

    surveyTypes = SurveyTypeEnum;

    evaluation: IEvaluation;

    isEvaluationReady = false;

    questions: Array<IQuestion>;

    responses: Array<IResponse>;

    questionPosition = 0;

    questionType = QuestionTypeEnum;

    openEndedResponseFormGroup: FormGroup = this.formBuilder.group({
        content: [ '', [ Validators.required, Validators.maxLength(500) ], [ blankValidator() ] ]
    });

    ratingValue: number;

    get rating() {
        return this.ratingValue;
    }

    set rating(rating: number) {
        this.ratingValue = rating;
        this.checkNextEnabledStatus();
    }

    selectedChoicesValue: Array<IChoice>;

    get selectedChoices() {
        return this.selectedChoicesValue;
    }

    set selectedChoices(selectedChoices: Array<IChoice>) {
        this.selectedChoicesValue = selectedChoices;
        this.checkNextEnabledStatus();
    }

    nextEnabled = false;

    constructor(
        private formBuilder: FormBuilder,
        public formService: FormService,
        private evaluationService: EvaluationService,
        private questionService: QuestionService,
        private responseService: ResponseService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        if (this.collaboratorId && this.surveyType) {
            if (this.surveyType === SurveyTypeEnum.EVALUATION) {
                this.loadEvaluation();
            } else {
                this.loadQuestions();
            }
        }
    }

    loadEvaluation() {
        this.evaluationService.getEvaluations(this.collaboratorId, undefined, EvaluationStatusEnum.OPEN).subscribe(
            (evaluations: Array<IEvaluation>) => {
                if (evaluations?.length > 0) {
                    this.evaluation = evaluations[0];
                    this.isEvaluationReady = moment(this.evaluation.evaluationDate, DATE_STORAGE_FORMAT).isSameOrBefore(moment());
                    this.loadQuestions();
                }
            }
        );
    }

    loadQuestions() {
        this.questionService.getQuestions(this.surveyType, true).subscribe(
            (questions: Array<IQuestion>) => {
                this.questions = questions;
                this.loadResponses();
            }
        );
    }

    loadResponses() {
        this.responseService.getResponses(this.collaboratorId, this.surveyType, this.evaluation?.id).subscribe(
            (responses: Array<IResponse>) => {
                this.responses = responses;
                this.responses.forEach((response: IResponse) => {
                    const question: IQuestion = this.questions.find(q => q.id === response.question.id);
                    if (question) {
                        question.response = response;
                    }
                });
            }
        );
    }

    initializeSurvey() {
        this.questionPosition = 1;
        this.initializeResponse();
    }

    onNextQuestion() {
        if (this.nextEnabled && this.questionPosition < this.questions?.length + 1) {
            if (!this.questions[this.questionPosition - 1].response) {
                let hasResponse = false;
                const response: IResponse = {
                    question: this.questions[this.questionPosition - 1]
                };
                if (this.questions[this.questionPosition - 1].surveyType === SurveyTypeEnum.EVALUATION) {
                    response.evaluation = this.evaluation;
                }
                switch (this.questions[this.questionPosition - 1].questionType) {
                    case QuestionTypeEnum.OPEN_ENDED:
                        if (this.openEndedResponseFormGroup.get('content').value) {
                            response.content = this.openEndedResponseFormGroup.get('content').value;
                            hasResponse = true;
                        }
                        break;
                    case QuestionTypeEnum.RATING_SCALE:
                        if (this.rating !== undefined) {
                            response.rating = this.rating;
                            hasResponse = true;
                        }
                        break;
                    case QuestionTypeEnum.REGULAR_CHOICE:
                    case QuestionTypeEnum.QUIZ_CHOICE:
                        if (this.selectedChoices?.length > 0) {
                            response.choices = this.selectedChoices;
                            hasResponse = true;
                        }
                        break;
                }
                if (hasResponse) {
                    this.responseService.createResponse(response).subscribe(
                        (createdResponse: IResponse) => {
                            this.questions[this.questionPosition - 1].response = createdResponse;
                            if (this.questions[this.questionPosition - 1].questionType === QuestionTypeEnum.QUIZ_CHOICE) {
                                this.nextEnabled = false;
                                this.questionService.getQuizChoicesByValidStatus(this.questions[this.questionPosition - 1].id, true).subscribe(
                                    (validQuizChoices: Array<IChoice>) => {
                                        this.setupValidQuizChoices(validQuizChoices);
                                        setTimeout(() => {
                                            this.questionPosition++;
                                            if (this.questionPosition <= this.questions?.length) {
                                                this.initializeResponse();
                                            }
                                        }, 2000);
                                    }
                                );
                            } else {
                                this.questionPosition++;
                                if (this.questionPosition <= this.questions?.length) {
                                    this.initializeResponse();
                                }
                            }
                        },
                        (error: IErrorResponse) => {
                            if (error.status >= 400 && error.status < 500) {
                                this.toastrService.showStatusToastr(
                                    'Something is not right! Your response could '
                                    + 'not be submitted, please try again later',
                                    ToastrStatusEnum.DANGER);
                            }
                        }
                    );
                } else {
                    this.questionPosition++;
                    if (this.questionPosition <= this.questions?.length) {
                        this.initializeResponse();
                    }
                }
            } else {
                this.questionPosition++;
                if (this.questionPosition <= this.questions?.length) {
                    this.initializeResponse();
                }
            }
        }
    }

    onPreviousQuestion() {
        if (this.questionPosition > 1) {
            this.questionPosition--;
            this.initializeResponse();
        }
    }

    initializeResponse() {
        switch (this.questions[this.questionPosition - 1].questionType) {
            case QuestionTypeEnum.OPEN_ENDED:
                this.openEndedResponseFormGroup.reset();
                this.checkNextEnabledStatus();
                break;
            case QuestionTypeEnum.RATING_SCALE:
                this.rating = this.questions[this.questionPosition - 1].response?.rating;
                break;
            case QuestionTypeEnum.REGULAR_CHOICE:
            case QuestionTypeEnum.QUIZ_CHOICE:
                const selectedChoices: Array<IChoice> = [];
                this.questions[this.questionPosition - 1].choices.forEach((choice: IChoice) => {
                    if (this.questions[this.questionPosition - 1].response?.choices?.find(c => c.id === choice.id)) {
                        choice.selected = true;
                        selectedChoices.push(choice);
                    } else {
                        choice.selected = false;
                    }
                });
                if (this.questions[this.questionPosition - 1].questionType === QuestionTypeEnum.QUIZ_CHOICE &&
                        this.questions[this.questionPosition - 1].response) {

                    this.questionService.getQuizChoicesByValidStatus(this.questions[this.questionPosition - 1].id, true).subscribe(
                        (validQuizChoices: Array<IChoice>) => {
                            this.setupValidQuizChoices(validQuizChoices);
                            this.selectedChoices = [ ...selectedChoices ];
                        }
                    );

                } else {
                    this.selectedChoices = [ ...selectedChoices ];
                }
                break;
        }
    }

    checkNextEnabledStatus() {
        if (this.questions[this.questionPosition - 1].response || !this.questions[this.questionPosition - 1].required) {
            this.nextEnabled = true;
        } else {
            switch (this.questions[this.questionPosition - 1].questionType) {
                case QuestionTypeEnum.OPEN_ENDED:
                    this.nextEnabled = this.openEndedResponseFormGroup.valid;
                    break;
                case QuestionTypeEnum.RATING_SCALE:
                    this.nextEnabled = this.rating !== undefined;
                    break;
                case QuestionTypeEnum.REGULAR_CHOICE:
                case QuestionTypeEnum.QUIZ_CHOICE:
                    this.nextEnabled = this.selectedChoices?.length > 0;
                    break;
            }
        }
    }

    private setupValidQuizChoices(validQuizChoices: Array<IChoice>) {
        this.questions[this.questionPosition - 1].choices.forEach((choice: IChoice) => {
            if (validQuizChoices.find(validQuizChoice => validQuizChoice.id === choice.id)) {
                choice.valid = true;
            } else {
                choice.valid = false;
            }
        });
    }

}
