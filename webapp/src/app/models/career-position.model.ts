import { IProfile } from './profile.model';

import { CareerPositionStatusEnum } from '../enumerations/career-position-status.enum';
import { ICollaborator } from './collaborator.model';


export interface ICareerPosition {

    id?: number;
    profile: IProfile;
    collaborator: ICollaborator;
    supervisor?: ICollaborator;
    startedAt?: string;
    status: CareerPositionStatusEnum;
    createdAt?: string;
    updatedAt?: string;

}
