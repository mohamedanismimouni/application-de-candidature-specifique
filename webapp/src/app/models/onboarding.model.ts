import { ICollaborator } from "./collaborator.model";


export interface IOnboarding {

    id: string;
    secretWordPart: string;
    rating: number;
    secretWordPartHolder: ICollaborator;
    freshRecruit: ICollaborator;
    createdAt?: string;
    updatedAt?: string;

}
