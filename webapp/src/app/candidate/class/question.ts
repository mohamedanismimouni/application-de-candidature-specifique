import {PrmDifficulty} from './prm-difficulty';
import {PrmTechnology} from './prm-technology';
import {Test} from './test';

export interface Question {
    questionid?: number;
    statement?: string;
    code?: string;
    prmDifficulty?: PrmDifficulty;
    prmTechnology?: PrmTechnology;
    responses?: Array<Response> ;
    tests?: Array<Test>;
}
