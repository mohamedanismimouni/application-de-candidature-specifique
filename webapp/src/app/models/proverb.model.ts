import { ICollaborator } from "./collaborator.model";

export interface IProverb {

    id: number;
    createdAt?: string;
    updatedAt?: string;
    text: String;
    bySystem:boolean;
    author?:string;
    creator?:ICollaborator;
  
}
