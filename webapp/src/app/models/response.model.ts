import { IQuestion } from './question.model';
import { IEvaluation } from './evaluation.model';
import { IChoice } from './choice.model';

import { ResponseTypeEnum } from '../enumerations/response-type.enum';


export interface IResponse {

    id?: string;
    responseType?: ResponseTypeEnum;
    evaluation?: IEvaluation;
    question: IQuestion;
    content?: string;
    rating?: number;
    choices?: Array<IChoice>;

}
