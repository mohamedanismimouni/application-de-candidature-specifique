import { SkillLevelEnum } from '../enumerations/skill-level.enum';


export interface IAcquiredSkillLevel {

    id?: string;
    level: SkillLevelEnum;
    createdAt?: string;
    updatedAt?: string;

}
