import { ICareerStep } from './career-step.model';


export interface ICareerPath {

    id: string;
    yearsOfExperience: number;
    fromCareerStep: ICareerStep;
    toCareerStep: ICareerStep;
    createdAt?: string;
    updatedAt?: string;

}
