import {ICollaborator} from '../../models/collaborator.model';
import {Candidate} from './candidate';

export interface Entretien {
    id?: number;
    entretienType?: string;
    candidate?: Candidate;
    collaborator?: ICollaborator;
    comment?: string;
    result?: string ;
    send?: boolean ;
    done?: boolean;
    date?: Date;
}
