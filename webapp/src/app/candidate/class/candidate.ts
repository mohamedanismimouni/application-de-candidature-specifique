import {Skill} from './skill';
import {IOffers} from './ioffers';
import {CandidateStatus} from './candidate-status';
import {Test} from './test';

export interface Candidate {
    id?: number;
    firstName?: string;
    lastName?: string;
    phoneNumber?: number;
    email?: string;
    id_cv?: string;
    candidateSkills?: Array<Skill>;
    offerEntities?: Array<IOffers>;
    candidateStatusEntity?: CandidateStatus;
    done?: boolean;
    score?: string;
    test?: Test;
    QuizDone?: boolean;
    skills?: Array<string>;
    references?: Array<string>;
    sujets?: Array<string>;
    dateNaissance?: Date;
    emailSecondaire?: string;
     posteActuel?: string;
     societeActuelle?: string;
     universite?: string;
    candidateImg?: string;

}
