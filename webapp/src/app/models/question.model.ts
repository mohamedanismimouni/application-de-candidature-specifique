import { IChoice } from '../models/choice.model';
import { IResponse } from './response.model';

import { QuestionTypeEnum } from '../enumerations/question-type.enum';
import { SurveyTypeEnum } from '../enumerations/survey-type.enum';


export interface IQuestion {

    id: string;
    questionType: QuestionTypeEnum;
    surveyType: SurveyTypeEnum;
    position?: number;
    content: string;
    enabled?: boolean;
    required?: boolean;
    lowestValue?: number;
    highestValue?: number;
    multipleChoices?: boolean;
    choices?: Array<IChoice>;
    score?: number;
    response?: IResponse;
    createdAt?: string;
    updatedAt?: string;

}
