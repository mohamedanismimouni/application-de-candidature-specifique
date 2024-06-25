import {Candidate} from './candidate';
import {OfferRequirment} from "./offer-requirment";

export interface IOffers {
    id?: number;
    createdAt?: string;
    updatedAt?: string;
    reference?: string;
    subject?: string;
    contexte?: string;
    description?: string;
    department?: string;
    offerRequirementEntities?: Array<OfferRequirment>;

}
