import { ChoiceTypeEnum } from '../enumerations/choice-type.enum';


export interface IChoice {

    id: string;
    choiceType: ChoiceTypeEnum;
    position?: number;
    content: string;
    enabled?: boolean;
    valid?: boolean;
    selected?: boolean;
    createdAt?: string;
    updatedAt?: string;

}
