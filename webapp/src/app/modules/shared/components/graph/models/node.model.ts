import { INodeMeta } from './node-meta.model';


export interface INode {

    id: string;
    label: string;
    data: any;
    meta: INodeMeta;

}
