import { ProfileTypeEnum } from "../enumerations/profile-type.enum";

export interface IUser {

    id: number;
    createdAt?: string;
    updatedAt?: string;
    label: ProfileTypeEnum;
    users:IUser[];
 

}