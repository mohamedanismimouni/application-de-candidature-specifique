import {TestQuestionKeys} from './test-question-keys';
import {Test} from './test';
import {Question} from './question';

export interface TestQuestion {
    id?: TestQuestionKeys;
    result?: number;
    test?: Test;
    question?: Question;
}
