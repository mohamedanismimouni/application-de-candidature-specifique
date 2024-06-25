import { ITeam } from './team.model';
import { ICareerStep } from './career-step.model';


export interface IProfile {

    id: string;
    label: string;
    team: ITeam;
    careerStep: ICareerStep;
    createdAt?: string;
    updatedAt?: string;

}
