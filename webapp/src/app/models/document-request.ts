import { ICollaborator } from "./collaborator.model";
import { IRequestType } from "./request-type";

export interface IDocumentRequest {

    id: number;
    createdAt?: string;
    uploadDate?: string;
    updatedAt?: string;
    requestMotif?: string;
    rejectionMotif?: string;
    type: IRequestType;
    status: IRequestType;
    collaborator?: ICollaborator;
    upload?:boolean;
    idEDM:number;
    reference: string;
    createdByRH: boolean;
    idEtiquetteEDM:number;
    validateddBySystem: boolean;
    withoutTemplate: boolean;
    createdBy: ICollaborator;


}
