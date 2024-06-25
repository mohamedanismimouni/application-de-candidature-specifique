import { Component, OnInit, Input } from '@angular/core';

import { NbDialogRef } from '@nebular/theme';

import { QuestionService } from '../../../services/question.service';
import { ResponseService } from '../../../services/response.service';

import { IQuestion } from '../../../models/question.model';
import { IResponse } from '../../../models/response.model';
import { IChoice } from '../../../models/choice.model';

import { SurveyTypeEnum } from '../../../enumerations/survey-type.enum';
import { QuestionTypeEnum } from '../../../enumerations/question-type.enum';


@Component({
    templateUrl: './evaluation-survey-responses-dialog.component.html',
    styleUrls: [ './evaluation-survey-responses-dialog.component.scss' ]
})
export class EvaluationSurveyResponsesDialogComponent implements OnInit {

    @Input()
    data: any;

    responses: Array<IResponse>;

    questionType = QuestionTypeEnum;

    constructor(
        private dialogRef: NbDialogRef<EvaluationSurveyResponsesDialogComponent>,
        private questionService: QuestionService,
        private responseService: ResponseService
    ) { }

    ngOnInit() {
        this.questionService.getQuestions(SurveyTypeEnum.EVALUATION).subscribe(
            (questions: Array<IQuestion>) => {
                this.responseService.getResponses(this.data.collaboratorId, SurveyTypeEnum.EVALUATION, this.data.evaluationId).subscribe(
                    (responses: Array<IResponse>) => {
                        this.responses = responses;
                        this.responses.forEach((response: IResponse) => {
                            response.question = questions.find(question => question.id === response.question.id);
                            if (response.question.questionType === QuestionTypeEnum.REGULAR_CHOICE) {
                                response.question.choices.forEach((choice: IChoice) => {
                                    if (response.choices?.find(c => c.id === choice.id)) {
                                        choice.selected = true;
                                    } else {
                                        choice.selected = false;
                                    }
                                });
                            }
                        });
                    }
                );
            }
        );
    }

    onClose() {
        this.dialogRef.close();
    }

}
