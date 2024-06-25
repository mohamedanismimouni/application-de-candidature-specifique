import { AccountStatusEnum } from "../enumerations/account-status.enum";
import { ICivility } from "./civility.model";
import { IEvent } from "./event.model";
import { IFunction } from "./function.model";
import { IQualification } from "./qualification.model";
import { ITeam } from "./team.model";

export interface ICollaborator {
    id: number;
    createdAt?: string;
    updatedAt?: string;
    firstName: string;
    lastName: string;
    matricule: string ;
    qualification: IQualification;
    function: IFunction;
    entryDate: Date;
    civility: ICivility;
    endContractDate?:Date;
    events:IEvent;
    //user
    profileType: any;
    email: string;
    accountStatus: AccountStatusEnum;
    recruitedAt?: string;
    memberOf?: ITeam;
    managerOf?: ITeam;
    secretWord?: string;
    passedOnboardingProcess?: boolean;
    name?: string;
    score?:number;
    
}








   