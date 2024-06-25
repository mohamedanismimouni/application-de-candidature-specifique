import { RequestTypeEnum } from "../enumerations/request-type.enum";
import { ISignatory } from "./signatory.model";

export interface IRequestType {

    id: number;
    createdAt?: string;
    updatedAt?: string;
    label: RequestTypeEnum;
    idEDM: number;
    uploadDate: string;
    visibility:boolean;
    signatory:ISignatory;
    isTemplateOfRequest:boolean;
}
