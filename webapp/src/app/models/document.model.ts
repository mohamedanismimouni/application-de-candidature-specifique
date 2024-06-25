import { IClient } from "./client.model";
export interface IDocument {
    id: number;
    fileOwner: string;
    /* the file Name from user prespective */
    fileName: string;
    fileTitle: string;
    /* the file Name from file system prespective */
    reference: string;
    /* the file parent folder ex: Certifications, Experiences, CV .. */
    parent: string;
    /* the file Format ex: image/png, image/jpeg, application.pdf */
    fileFormat: string;
    /*
     * the file identifier which indicates the id of the ressource that exists in an
     * another entity ex: id_Certification(Table Certificatio) ==> identifier=1
     */
    identifier: string;
    /* the file size */
    size: number;
    /* the client responsable for the document*/
    client: IClient;
    /* the file Extension ex: png, jpeg, pdf */
    fileExtenion: string;
    createdDate: string;
    lastModifiedBy: string;
    createdBy: string;
    lastModifiedDate: string;}