import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { NbMenuService, NbMenuBag, NbDialogService } from '@nebular/theme';

import { QuestionService } from '../../services/question.service';
import { ToastrService } from '../../services/toastr.service';

import { formatQuestionType } from '../../utils/formaters.util';

import { CreateQuestionDialogComponent } from './create-question-dialog/create-question-dialog.component';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';

import { IQuestion } from '../../models/question.model';
import { IChoice } from '../../models/choice.model';
import { IDialogData } from '../../models/dialog-data.model';
import { IErrorResponse } from '../../models/error-response.model';

import { SurveyTypeEnum } from '../../enumerations/survey-type.enum';
import { QuestionTypeEnum } from '../../enumerations/question-type.enum';
import { ToastrStatusEnum } from '../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './surveys-management.component.html',
    styleUrls: [ './surveys-management.component.scss' ]
})
export class SurveysManagementComponent implements OnInit, OnDestroy {

    surveyType: SurveyTypeEnum;

    enabledQuestions: Array<IQuestion> = new Array<IQuestion>(0);
    disabledQuestions: Array<IQuestion> = new Array<IQuestion>(0);

    questionTypes: Map<QuestionTypeEnum, string> = new Map(Object.values(QuestionTypeEnum)
        .map((questionType) => [ questionType, formatQuestionType(questionType) ]));

    ratingScaleQuestionType = QuestionTypeEnum.RATING_SCALE;
    quizChoiceQuestionType = QuestionTypeEnum.QUIZ_CHOICE;

    questionContextMenuSubscription: Subscription;
    questionContextMenuItems = [
        {
            icon: 'edit-outline',
            title: 'Edit question',
            action: () => this.onEditQuestion()
        },
        {
            icon: 'trash-2-outline',
            title: 'Delete question',
            action: () => this.onDeleteQuestion()
        }
    ];
    triggeredQuestionContextMenuTag: string;

    constructor(
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private menuService: NbMenuService,
        private dialogService: NbDialogService,
        private questionService: QuestionService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {

        this.activatedRoute.paramMap.subscribe(
            (paramMap: ParamMap) => {
                this.surveyType = Object.values(SurveyTypeEnum)
                    .find((surveyType) => surveyType === paramMap.get('survey-type').toUpperCase());
                if (!this.surveyType) {
                    this.router.navigate(['/app/hr-responsible/surveys/' + SurveyTypeEnum.AWARENESS.toLowerCase()]);
                }
                this.loadQuestions();
            }
        );

        this.questionContextMenuSubscription = this.menuService.onItemClick().pipe(
            filter((menuBag: NbMenuBag) => menuBag.tag === this.triggeredQuestionContextMenuTag),
            map((menuBag: NbMenuBag) => menuBag.item)
        ).subscribe((menuItem: any) => menuItem.action());

    }

    loadQuestions() {
        this.questionService.getQuestions(this.surveyType).subscribe(
            (questions: Array<IQuestion>) => {
                this.enabledQuestions = new Array<IQuestion>(0);
                this.disabledQuestions = new Array<IQuestion>(0);
                questions.forEach((question: IQuestion) => {
                    if (question.choices && question.choices.length > 0) {
                        if (question.questionType === QuestionTypeEnum.QUIZ_CHOICE) {
                            this.questionService.getQuizChoicesByValidStatus(question.id, true).subscribe(
                                (validQuizChoices: Array<IChoice>) => {
                                    question.choices.forEach((choice: IChoice) => {
                                        if (validQuizChoices.find(validQuizChoice => validQuizChoice.id === choice.id)) {
                                            choice.valid = true;
                                        }
                                    });
                                }
                            );
                        }
                        for (let i = 0; i < question.choices.length; i++) {
                            if (!question.choices[i].enabled) {
                                const disabledChoice = question.choices.splice(i, 1)[0];
                                question.choices.push(disabledChoice);
                            }
                        }
                    }
                    if (question.enabled) {
                        this.enabledQuestions.push(question);
                    } else {
                        this.disabledQuestions.push(question);
                    }
                });
            }
        );
    }

    onAddQuestion() {
        this.dialogService.open(
            CreateQuestionDialogComponent,
            {
                context: {
                    data: { surveyType: this.surveyType }
                }
            }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.loadQuestions();
                }
            }
        );
    }

    onQuestionReorder(event: CdkDragDrop<Array<IQuestion>>) {
        moveItemInArray(this.enabledQuestions, event.previousIndex, event.currentIndex);
        console.log(this.enabledQuestions);
    }

    onEditQuestion() {
        console.log('Editing question with id: ' + this.triggeredQuestionContextMenuTag);
    }

    onDeleteQuestion() {
        console.log('Deleting question with id: ' + this.triggeredQuestionContextMenuTag);
        const dialogData: IDialogData = {
            title: 'Delete question',
            content: 'You\'re about to delete a question permanently, do you wish to proceed?'
        };
        this.dialogService.open(
            ConfirmationDialogComponent,
            { context: { data: dialogData } }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.questionService.deleteQuestion(this.triggeredQuestionContextMenuTag).subscribe(
                        () => {
                            this.toastrService.showStatusToastr(
                                'Question deleted successfully',
                                ToastrStatusEnum.SUCCESS);
                            this.loadQuestions();
                        },
                        (error: IErrorResponse) => {
                            switch (error.status) {
                                case 400:
                                    this.toastrService.showStatusToastr(
                                        'Question connot be deleted',
                                        ToastrStatusEnum.DANGER);
                                    break;
                                case 404:
                                    this.toastrService.showStatusToastr(
                                        'Oups question is not found, seems like you are seeing '
                                        + 'some outdated data. Questions list is now refreshed!',
                                        ToastrStatusEnum.DANGER);
                                    this.loadQuestions();
                                    break;
                            }
                        }
                    );
                }
            }
        );
    }

    ngOnDestroy() {
        if (this.questionContextMenuSubscription) {
            this.questionContextMenuSubscription.unsubscribe();
        }
    }

}
