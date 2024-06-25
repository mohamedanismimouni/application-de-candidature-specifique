import {Test} from "./test";
import {Question} from "./question";
import {Response} from "./response";

export interface TestResponses {
    test?: Test;
    question?: Question;
    responses?: Array<Response>;
    index?: number;
    testDuration?: number;
    timeOver?: boolean;
}
