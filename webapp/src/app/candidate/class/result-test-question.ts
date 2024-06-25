import {ResultResponse} from './result-response';

export interface ResultTestQuestion {
    id?: number;
    statement?: string;
    technologyname?: string;
    code?: string;
    result?: boolean;
    resultResponseDtoList?: Array<ResultResponse>;
    scoreTotal?: number;
    candidateName?: string;
    candidateEmail?: string;
    candidatePhone?: string;
}
