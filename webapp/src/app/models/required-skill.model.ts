import { IProfile } from './profile.model';
import { ISkill } from './skill.model';

import { SkillLevelEnum } from '../enumerations/skill-level.enum';
import { SkillWeightEnum } from '../enumerations/skill-weight.enum';


export interface IRequiredSkill {

    id: string;
    level: SkillLevelEnum;
    weight: SkillWeightEnum;
    profile: IProfile;
    skill: ISkill;
    createdAt?: string;
    updatedAt?: string;

}
