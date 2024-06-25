import { NbMenuItem } from '@nebular/theme';

import * as _ from 'lodash';
import { DocumentsOperationEnum } from '../enumerations/documents-operation.enum';

import { ProfileTypeEnum } from '../enumerations/profile-type.enum';
import { SurveyTypeEnum } from '../enumerations/survey-type.enum';


export enum ParameterKeyEnum {

    COLLABORATOR_ID = 'collaborator-id'

}

export interface IActionItem {

    icon: string;
    link: string;
    title?: string;
    linkParameters?: Array<ParameterKeyEnum>;

}

export interface IUserAppLayoutSettings {

    hasSidebar: boolean;
    sidebarMenuItems: NbMenuItem[];
    hasHeaderActions: boolean;
    headerActions: IActionItem[];

}

const DIRECTOR_APP_LAYOUT_SETTINGS: IUserAppLayoutSettings = {

    hasSidebar: false,
    sidebarMenuItems: [],
    hasHeaderActions: false,
    headerActions: []

};

const HR_RESPONSIBLE_APP_LAYOUT_SETTINGS: IUserAppLayoutSettings = {

    hasSidebar: true,
    sidebarMenuItems: [
        {
            icon: 'home-outline',
            title: 'Home',
            link: '/app/home',

        },
        {
            icon: 'person-outline',
            link: '/app/hr-responsible/profile/collaborator-dashboard',
            title: 'Dashboard',
            pathMatch: 'prefix',
        },
        {
            icon: 'file-text-outline',
            link: '/app/hr-responsible/document',
            title: 'Document',
        },
        {
            icon: 'person-outline',
            title: 'Users',
            link: '/app/hr-responsible/users',

        },

        {
            icon: 'person-outline',
            title: 'Candidates',
            link: '/app/hr-responsible/candidates',

        } ,
        {
            icon: 'person-outline',
            title: 'Jobs',
            link: '/app/hr-responsible/jobs',

        },

        {
            icon: 'person-outline',
            title: 'Collaborators',
            link: '/app/hr-responsible/collaborators'
        },
        {
            icon: 'people-outline',
            title: 'Teams',
            link: '/app/hr-responsible/teams'
        },
        {
            icon: 'share-outline',
            title: 'Careers',
            link: '/app/hr-responsible/careers'
        },
     /*    {
            icon: 'file-text-outline',
            title: 'Surveys',
            children: [
                {
                    title: 'Awareness',
                    link: '/app/hr-responsible/surveys/' + SurveyTypeEnum.AWARENESS.toLowerCase()
                },
                {
                    title: 'Exploration',
                    link: '/app/hr-responsible/surveys/' + SurveyTypeEnum.EXPLORATION.toLowerCase()
                },
                {
                    title: 'Evaluation',
                    link: '/app/hr-responsible/surveys/' + SurveyTypeEnum.EVALUATION.toLowerCase()
                }
            ]
        }, */
        {
            icon: 'award-outline',
            title: 'Skills',
            link: '/app/hr-responsible/skills'
        },
        {
            icon: 'attach-2-outline',
            title: 'Documents',
            pathMatch: 'full',
            children: [
                {
                    title: 'Requests',
                    link: '/app/hr-responsible/documents/' + DocumentsOperationEnum.REQUESTS.toLowerCase()
                },
                {
                    title: 'History',
                    link: '/app/hr-responsible/documents/' + DocumentsOperationEnum.HISTORY.toLowerCase()
                },
                {
                    title: 'Templates',
                    link: '/app/hr-responsible/documents/' + DocumentsOperationEnum.TEMPLATES.toLowerCase()
                }
                ,
                {
                    title: 'Payroll',
                    link: '/app/hr-responsible/documents/' + DocumentsOperationEnum.PAYROLL
                }
            ]
        },


    ],
    hasHeaderActions: false,
    headerActions: []

};



const SUPPORT_APP_LAYOUT_SETTINGS: IUserAppLayoutSettings = {

    hasSidebar: true,
    sidebarMenuItems: [
        {
            icon: 'home-outline',
            title: 'Home',
            link: '/app/home',

        },
        {
            icon: 'person-outline',
            link: '/app/hr-responsible/profile/collaborator-dashboard',
            title: 'Dashboard',
            pathMatch: 'prefix',
        },
        {
            icon: 'file-text-outline',
            link: '/app/hr-responsible/document',
            title: 'Document',
        },
        {
            icon: 'person-outline',
            title: 'Users',
            link: '/app/hr-responsible/users',

        },
        {
            icon: 'person-outline',
            title: 'Collaborators',
            link: '/app/hr-responsible/collaborators'
        },
        {
            icon: 'people-outline',
            title: 'Teams',
            link: '/app/hr-responsible/teams'
        },
        {
            icon: 'share-outline',
            title: 'Careers',
            link: '/app/hr-responsible/careers'
        },
     /*    {
            icon: 'file-text-outline',
            title: 'Surveys',
            children: [
                {
                    title: 'Awareness',
                    link: '/app/hr-responsible/surveys/' + SurveyTypeEnum.AWARENESS.toLowerCase()
                },
                {
                    title: 'Exploration',
                    link: '/app/hr-responsible/surveys/' + SurveyTypeEnum.EXPLORATION.toLowerCase()
                },
                {
                    title: 'Evaluation',
                    link: '/app/hr-responsible/surveys/' + SurveyTypeEnum.EVALUATION.toLowerCase()
                }
            ]
        }, */
        {
            icon: 'award-outline',
            title: 'Skills',
            link: '/app/hr-responsible/skills'
        },
        {
            icon: 'attach-2-outline',
            title: 'Documents',
            pathMatch: 'full',
            children: [
                {
                    title: 'Templates',
                    link: '/app/hr-responsible/documents/' + DocumentsOperationEnum.TEMPLATES.toLowerCase()
                }

            ]
        },


    ],
    hasHeaderActions: false,
    headerActions: []

};
const MANAGER_APP_LAYOUT_SETTINGS: IUserAppLayoutSettings = {

    hasSidebar: true,
    sidebarMenuItems: [
        {
            icon: 'home-outline',
            title: 'Home',
            link: '/app/home',

        },
        {
            icon: 'person-outline',
            link: '/app/manager/profile/collaborator-dashboard',
            title: 'Dashboard',
            pathMatch: 'prefix',
        },
        {
            icon: 'file-text-outline',
            link: '/app/manager/document',
            title: 'Document',
        },
        {
            icon: 'share-outline',
            link: '/app/manager/profiles',
            title: 'Career profiles'
        },
        {
            icon: 'people-outline',
            link: '/app/manager/team',
            title: 'Team'
        },


    ],
    hasHeaderActions: false,
    headerActions: [

    ]

};

const COLLABORATOR_APP_LAYOUT_SETTINGS: IUserAppLayoutSettings = {
    hasSidebar: true,
    sidebarMenuItems: [
        {
            icon: 'home-outline',
            link: '/app/home',
            title: 'Home',
        },
        {
            icon: 'person-outline',
            link: '/app/collaborator/collaborator-dashboard',
            title: 'Dashboard',
            pathMatch: 'prefix',
        },
        {
            icon: 'people-outline',
            link: '/app/collaborator/team',
            title: 'Team'
        },

        {
            icon: 'file-text-outline',
            link: '/app/collaborator/document',
            title: 'Document',

        }
    ],
    hasHeaderActions: false,
    headerActions: []

};

export class UserAppLayoutSettings implements IUserAppLayoutSettings {

    private constructor(
        public hasSidebar: boolean,
        public sidebarMenuItems: NbMenuItem[],
        public hasHeaderActions: boolean,
        public headerActions: IActionItem[]
    ) { }

    public static fromProfileType(profileType: ProfileTypeEnum, parameters?: Map<ParameterKeyEnum, string>): IUserAppLayoutSettings {
        let userAppLayoutSettings: IUserAppLayoutSettings;

        switch (profileType) {
            case ProfileTypeEnum.DIRECTOR:
                userAppLayoutSettings = DIRECTOR_APP_LAYOUT_SETTINGS;
                break;
            case ProfileTypeEnum.HR_RESPONSIBLE:
                userAppLayoutSettings = HR_RESPONSIBLE_APP_LAYOUT_SETTINGS;
                break;
            case ProfileTypeEnum.SUPPORT:
                userAppLayoutSettings = SUPPORT_APP_LAYOUT_SETTINGS;
                break;
            case ProfileTypeEnum.MANAGER:
                userAppLayoutSettings = MANAGER_APP_LAYOUT_SETTINGS;
                break;
            case ProfileTypeEnum.COLLABORATOR:
                userAppLayoutSettings = COLLABORATOR_APP_LAYOUT_SETTINGS;
                break;
            default:
                userAppLayoutSettings = new UserAppLayoutSettings(false, [], false, []);
                break;
        }

        const settings: IUserAppLayoutSettings = _.cloneDeep(userAppLayoutSettings);

        if (parameters) {
             settings.sidebarMenuItems?.forEach((action: NbMenuItem) => {
                if (action.title === 'Dashboard') {
                action.link = action.link + '/' + parameters.get(ParameterKeyEnum.COLLABORATOR_ID);
                }
            });
        }

        return settings;
    }

}
