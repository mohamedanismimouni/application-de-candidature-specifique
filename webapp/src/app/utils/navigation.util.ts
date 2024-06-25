import { ProfileTypeEnum } from '../enumerations/profile-type.enum';


export function getRoutePrefixFromProfileType(profileType: ProfileTypeEnum): string {    
    return '/app/' + profileType.toLowerCase().replace('_', '-');
}
