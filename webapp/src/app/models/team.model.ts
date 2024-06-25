import { ICollaborator } from "./collaborator.model";


export interface ITeam {

    id: string;
    name: string;
    teamEvaluationDate?: string;
    managedBy: ICollaborator;
    createdAt?: string;
    updatedAt?: string;

}
