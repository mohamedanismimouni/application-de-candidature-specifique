import { ProfileTypeEnum } from '../enumerations/profile-type.enum';
import { AccountStatusEnum } from '../enumerations/account-status.enum';
import { QuestionTypeEnum } from '../enumerations/question-type.enum';
import { SkillTypeEnum } from '../enumerations/skill-type.enum';
import { SkillWeightEnum } from '../enumerations/skill-weight.enum';
import { SkillLevelEnum } from '../enumerations/skill-level.enum';
import { TeamStructuralRelationEnum } from '../enumerations/team-structural-relation.enum';
import { MentorshipStatusEnum } from '../enumerations/mentorship-status.enum';
import { RequestTypeEnum } from '../enumerations/request-type.enum';


export function formatProfileType(profileType: ProfileTypeEnum): string {
    switch (profileType) {
        case ProfileTypeEnum.DIRECTOR:
            return 'Director';
        case ProfileTypeEnum.HR_RESPONSIBLE:
            return 'HR Responsible';
        case ProfileTypeEnum.MANAGER:
            return 'Manager';
        case ProfileTypeEnum.COLLABORATOR:
            return 'Collaborator';
    }
}

export function formatAccountStatus(accountStatus: AccountStatusEnum): string {
    switch (accountStatus) {
        case AccountStatusEnum.INACTIVE:
            return 'Inactive';
        case AccountStatusEnum.ACTIVE:
            return 'Active';
        case AccountStatusEnum.SUSPENDED:
            return 'Suspended';
    }
}

export function formatQuestionType(questionType: QuestionTypeEnum): string {
    switch (questionType) {
        case QuestionTypeEnum.OPEN_ENDED:
            return 'Open ended question';
        case QuestionTypeEnum.RATING_SCALE:
            return 'Rating scale question';
        case QuestionTypeEnum.REGULAR_CHOICE:
            return 'Regular choice question';
        case QuestionTypeEnum.QUIZ_CHOICE:
            return 'Quiz choice question';
    }
}

export function formatSkillType(skillType: SkillTypeEnum): string {
    switch (skillType) {
        case SkillTypeEnum.HUMAN:
            return 'Human';
        case SkillTypeEnum.TECHNICAL:
            return 'Technical';
    }
}

export function formatSkillLevel(skillLevel: SkillLevelEnum): string {
    switch (skillLevel) {
        case SkillLevelEnum.BEGINNER:
            return 'Beginner';
        case SkillLevelEnum.INTERMEDIATE:
            return 'Intermediate';
        case SkillLevelEnum.ADVANCED:
            return 'Advanced';
        case SkillLevelEnum.EXPERT:
            return 'Expert';
    }
}

export function valueOfSkillLevel(skillLevel: SkillLevelEnum): number {
    switch (skillLevel) {
        case SkillLevelEnum.BEGINNER:
            return 0.25;
        case SkillLevelEnum.INTERMEDIATE:
            return 0.5;
        case SkillLevelEnum.ADVANCED:
            return 0.75;
        case SkillLevelEnum.EXPERT:
            return 1;
    }
}

export function skillLevelFromValue(value: number): SkillLevelEnum {
    switch (value) {
        case 0.25:
            return SkillLevelEnum.BEGINNER;
        case 0.5:
            return SkillLevelEnum.INTERMEDIATE;
        case 0.75:
            return SkillLevelEnum.ADVANCED;
        case 1:
            return SkillLevelEnum.EXPERT;
    }
}

export function formatSkillWeight(skillWeight: SkillWeightEnum): string {
    switch (skillWeight) {
        case SkillWeightEnum.LOW:
            return 'Low';
        case SkillWeightEnum.MODERATE:
            return 'Moderate';
        case SkillWeightEnum.HIGH:
            return 'High';
        case SkillWeightEnum.CRITICAL:
            return 'Critical';
    }
}

export function valueOfSkillWeight(skillWeight: SkillWeightEnum): number {
    switch (skillWeight) {
        case SkillWeightEnum.LOW:
            return 0.25;
        case SkillWeightEnum.MODERATE:
            return 0.5;
        case SkillWeightEnum.HIGH:
            return 0.75;
        case SkillWeightEnum.CRITICAL:
            return 1;
    }
}

export function skillWeightFromValue(value: number): SkillWeightEnum {
    switch (value) {
        case 0.25:
            return SkillWeightEnum.LOW;
        case 0.5:
            return SkillWeightEnum.MODERATE;
        case 0.75:
            return SkillWeightEnum.HIGH;
        case 1:
            return SkillWeightEnum.CRITICAL;
    }
}

export function formatTeamStructuralRelation(teamStructuralRelation: TeamStructuralRelationEnum): string {
    switch (teamStructuralRelation) {
        case TeamStructuralRelationEnum.SUBORDINATES:
            return 'Subordinates';
        case TeamStructuralRelationEnum.MENTEES:
            return 'Mentees';
    }
}

export function formatMentorshipStatus(mentorshipStatus: MentorshipStatusEnum): string {
    switch (mentorshipStatus) {
        case MentorshipStatusEnum.ACTIVE:
            return 'Active';
        case MentorshipStatusEnum.TERMINATED:
            return 'Terminated';
    }
}

export function formatRequestType(requestType: RequestTypeEnum): string {
        switch (requestType) {
            case RequestTypeEnum.MISSION_CERTIFICATE:
                return 'mission certificat';
            case RequestTypeEnum.SALARY_CERTIFICATE:
                return 'Salary certificate';
            case RequestTypeEnum.WORK_CERTIFICATE:
                return 'Work certificate';
            case RequestTypeEnum.USER_GUIDE:
                return 'User guide';    
        }
    }
    

