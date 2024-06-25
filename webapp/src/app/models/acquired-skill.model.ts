import { ISkill } from './skill.model';

import { IAcquiredSkillLevel } from './acquired-skill-level.model';
import { ICollaborator } from './collaborator.model';


export interface IAcquiredSkill {

    id?: string;
    collaborator: ICollaborator;
    skill: ISkill;
    progress: Array<IAcquiredSkillLevel>;
    createdAt?: string;
    updatedAt?: string;

}
