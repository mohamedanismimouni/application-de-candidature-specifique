import { ICollaborator } from "src/app/models/collaborator.model";


export interface ICollaboratorDashboardSettings {

    collaborator: ICollaborator;
    viewer:ICollaborator;
    isSelf: boolean;
    isManager: boolean;
    isSupervisor: boolean;
    isMentor: boolean;

}
