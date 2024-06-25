import { IRequestType } from "./request-type";

export interface ISignatory {

    id: number;
    createdAt?: string;
    updatedAt?: string;
    firstName: string;
    lastName: string;   
    requests?: Array<IRequestType>;


}


