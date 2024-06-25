import {Candidate} from './candidate';
import {Question} from './question';

export interface Test {
    testid?: number;
    creationDate?: Date;
    testDuration?: number;
    done?: boolean;
    expired?: boolean;
    passageDuration?: number;
    totalScore?: number;
    send?: boolean;
    expirationDate?: Date;
    questions?: Array<Question>;
    candidate?: Candidate;
}
