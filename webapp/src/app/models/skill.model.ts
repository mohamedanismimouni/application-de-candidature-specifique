import { SkillTypeEnum } from '../enumerations/skill-type.enum';


export interface ISkill {

    id: string;
    skillType: SkillTypeEnum;
    label: string;
    createdAt?: string;
    updatedAt?: string;

}
