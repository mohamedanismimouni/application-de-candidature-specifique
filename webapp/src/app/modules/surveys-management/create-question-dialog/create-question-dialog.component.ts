import { Component, Input, OnInit, ViewChild, ElementRef, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import * as slider from 'nouislider';

import { FormService } from '../../../services/form.service';
import { QuestionService } from '../../../services/question.service';
import { ToastrService } from '../../../services/toastr.service';

import { blankValidator } from '../../../utils/validators.util';
import { formatQuestionType } from '../../../utils/formaters.util';

import { IQuestion } from '../../../models/question.model';
import { IErrorResponse } from '../../../models/error-response.model';

import { SurveyTypeEnum } from '../../../enumerations/survey-type.enum';
import { QuestionTypeEnum } from '../../../enumerations/question-type.enum';
import { ChoiceTypeEnum } from '../../../enumerations/choice-type.enum';
import { ToastrStatusEnum } from '../../../enumerations/toastr-status.enum';


@Component({
    templateUrl: './create-question-dialog.component.html',
    styleUrls: [ './create-question-dialog.component.scss' ]
})
export class CreateQuestionDialogComponent implements OnInit, OnDestroy {

    @Input()
    data: any;

    createQuestionFormGroup: FormGroup = this.formBuilder.group({
        questionType:       [ '', Validators.required ],
        surveyType:         [ '', Validators.required ],
        content:            [ '', [ Validators.required, Validators.maxLength(250) ], [ blankValidator() ] ],
        required:           [ false ]
    });

    get choices() {
        return this.createQuestionFormGroup.get('choices') as FormArray;
    }

    questionTypes = Object.values(QuestionTypeEnum)
        .filter((questionType) => questionType !== QuestionTypeEnum.QUIZ_CHOICE)
        .map((questionType) => ({
            value: questionType,
            name: formatQuestionType(questionType)
        }));

    quizChoiceQuestionType = QuestionTypeEnum.QUIZ_CHOICE;
    regularChoiceQuestionType = QuestionTypeEnum.REGULAR_CHOICE;
    ratingScaleQuestionType = QuestionTypeEnum.RATING_SCALE;

    @ViewChild('sliderElement', { static: false }) sliderElement: ElementRef;

    loadingState = false;

    constructor(
        private formBuilder: FormBuilder,
        private changeDetectorRef: ChangeDetectorRef,
        private dialogRef: NbDialogRef<CreateQuestionDialogComponent>,
        public formService: FormService,
        private questionService: QuestionService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.createQuestionFormGroup.get('surveyType')
            .setValue(this.data.surveyType);
        if (this.data.surveyType === SurveyTypeEnum.EXPLORATION) {
            this.createQuestionFormGroup.get('questionType')
                .setValue(QuestionTypeEnum.QUIZ_CHOICE);
        }
        this.onQuestionTypeChange();
    }

    onQuestionTypeChange() {

        this.createQuestionFormGroup.removeControl('lowestValue');
        this.createQuestionFormGroup.removeControl('highestValue');
        this.createQuestionFormGroup.removeControl('multipleChoices');
        this.createQuestionFormGroup.removeControl('choices');
        this.createQuestionFormGroup.removeControl('score');

        switch (this.createQuestionFormGroup.get('questionType').value) {
            case QuestionTypeEnum.RATING_SCALE:
                this.createQuestionFormGroup.addControl(
                    'lowestValue',
                    new FormControl(-2, Validators.required)
                );
                this.createQuestionFormGroup.addControl(
                    'highestValue',
                    new FormControl(2, Validators.required)
                );
                this.changeDetectorRef.detectChanges();
                slider.create(this.sliderElement.nativeElement, {
                    range: {
                        'min': -4,
                        'max': 4
                    },
                    start: [ -2, 2 ],
                    margin: 1,
                    limit: 4,
                    step: 1,
                    connect: true,
                    behaviour: 'drag-tap',
                    format: {
                        to: (value: number) => {
                            return value;
                        },
                        from: (value: string) => {
                            return Number(value);
                        }
                    }
                });
                this.sliderElement.nativeElement.noUiSlider.on(
                    'change',
                    (values: Array<number>) => {
                        this.createQuestionFormGroup.get('lowestValue')
                            .setValue(values[0]);
                        this.createQuestionFormGroup.get('highestValue')
                            .setValue(values[1]);
                    }
                );
                break;
            case QuestionTypeEnum.REGULAR_CHOICE:
                this.createQuestionFormGroup.addControl(
                    'multipleChoices',
                    new FormControl(false)
                );
                this.createQuestionFormGroup.addControl(
                    'choices',
                    this.formBuilder.array([])
                );
                this.onAddChoice();
                this.onAddChoice();
                break;
            case QuestionTypeEnum.QUIZ_CHOICE:
                this.createQuestionFormGroup.addControl(
                    'multipleChoices',
                    new FormControl(false)
                );
                this.createQuestionFormGroup.addControl(
                    'choices',
                    this.formBuilder.array([])
                );
                this.onAddChoice();
                this.onAddChoice();
                this.createQuestionFormGroup.addControl(
                    'score',
                    new FormControl('', [ Validators.required, Validators.min(1) ])
                );
                break;
        }

    }

    onToggleChoiceValidity(choiceIndex: number) {
        if (this.createQuestionFormGroup.get('questionType').value === QuestionTypeEnum.QUIZ_CHOICE) {
            this.choices.at(choiceIndex).get('valid')
                .setValue(!this.choices.at(choiceIndex).get('valid').value);
        }
    }

    onChoiceContentInputChange(choiceIndex: number)  {
        for (let i = 0; i < this.choices.length; i++) {
            if (i !== choiceIndex) {
                if (this.choices.at(i).get('content').value === this.choices.at(choiceIndex).get('content').value) {
                    this.choices.at(choiceIndex).get('content').setErrors({ notUnique: true });
                }
            }
        }
    }

    onRemoveChoice(choiceIndex: number) {
        if (this.choices && this.choices.length > 2) {
            this.choices.removeAt(choiceIndex);
        }
    }

    onAddChoice() {
        const choiceFormGroup: FormGroup = this.formBuilder.group({
            choiceType: [ ChoiceTypeEnum.REGULAR, Validators.required ],
            content:    [ '', Validators.required, blankValidator() ]
        });
        if (this.createQuestionFormGroup.get('questionType').value === QuestionTypeEnum.QUIZ_CHOICE) {
            choiceFormGroup.get('choiceType').setValue(ChoiceTypeEnum.QUIZ);
            choiceFormGroup.addControl('valid', new FormControl(false));
            if (this.choices.length <= 0) {
                choiceFormGroup.get('valid').setValue(true);
            }
        }
        this.choices.push(choiceFormGroup);
    }

    onCancel() {
        this.dialogRef.close(false);
    }

    onSubmit() {
        this.loadingState = true;
        const question: IQuestion = this.createQuestionFormGroup.value;
        if (question.choices && question.choices.length > 0) {
            for (const choice of question.choices) {
                if (question.choices.filter((c) => c.content === choice.content).length > 1) {
                    this.toastrService.showStatusToastr(
                        'Question choices content must be unique',
                        ToastrStatusEnum.DANGER);
                    break;
                }
            }
        }
        this.questionService.createQuestion(question).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    'Question created successfully',
                    ToastrStatusEnum.SUCCESS);
                this.dialogRef.close(true);
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                if (error.status === 409) {
                    this.createQuestionFormGroup.get('content')
                        .setErrors({ notUnique: true });
                } else {
                    this.toastrService.showStatusToastr(
                        'Something is not right, please verify '
                        + 'your input and try again later',
                        ToastrStatusEnum.DANGER);
                }
            }
        );
    }

    ngOnDestroy() {
        if (this.sliderElement) {
            this.sliderElement.nativeElement.noUiSlider.off();
        }
    }

}
