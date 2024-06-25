import { EventStatusEnum } from "../enumerations/event-status.enum";
import { ICollaborator } from "./collaborator.model";

export interface IEvent {

    id?: number;
    createdAt?:string;
    title?: string;
    date?: string;
    location?: string;
    price?: string;
    originLink?: string;
    creator?: ICollaborator;
    idEDMImage?: number;
    imageExtension?: String;
    status:EventStatusEnum;
    image?:any;
    collaborators?:Array<ICollaborator>;
    numberMaxParticipation?:number;
    participated:boolean;
    canceledMotif?: string;
    type? : string;
    eventPrice? : number;


}
