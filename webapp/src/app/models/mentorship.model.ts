import { ISkill } from './skill.model';
import { ICareerPosition } from './career-position.model';

import { MentorshipStatusEnum } from '../enumerations/mentorship-status.enum';
import { ICollaborator } from './collaborator.model';


export interface IMentorship {

    id: number;
    mentor: ICollaborator;
    skill: ISkill;
    careerPosition: ICareerPosition;
    status: MentorshipStatusEnum;
    mentorRating?: number;
    mentorFeedback?: string;
    menteeRating?: number;
    menteeFeedback?: string;
    createdAt?: string;
    updatedAt?: string;

}
