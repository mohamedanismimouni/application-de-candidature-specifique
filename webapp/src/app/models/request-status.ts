import { RequestStatusEnum } from "../enumerations/request-status.enum";

export interface IRequestStatus {

    id: number;
    createdAt?: string;
    updatedAt?: string;
    label: RequestStatusEnum;

}