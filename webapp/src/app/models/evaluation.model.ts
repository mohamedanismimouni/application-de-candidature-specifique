import { ICareerPosition } from './career-position.model';

import { EvaluationStatusEnum } from '../enumerations/evaluation-status.enum';


export interface IEvaluation {

    id?: string;
    evaluationDate: string;
    careerPosition: ICareerPosition;
    supervisorRating?: number;
    supervisorFeedback?: string;
    status?: EvaluationStatusEnum;
    createdAt?: string;
    updatedAt?: string;

}
