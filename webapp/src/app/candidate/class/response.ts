import {Question} from './question';

export interface Response {
    id?: number;
    value?: string;
    correct?: boolean;
    question?: Question;
}
