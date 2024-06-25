package com.talan.polaris.dto.edmmodelsdto;
import com.talan.polaris.dto.CollaboratorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class DocumentDTO {
    private Long id;
    private String fileOwner;
    /* the file Name from user prespective */
    private String fileName;
    private String fileTitle;
    /* the file Name from file system prespective */
    private String reference;
    /* the file parent folder ex: Certifications, Experiences, CV .. */
    private String parent;
    /* the file Format ex: image/png, image/jpeg, application.pdf */
    private String fileFormat;    /*
     * the file identifier which indicates the id of the ressource that exists in an
     * another entity ex: id_Certification(Table Certificatio) ==> identifier=1
     */
    private String identifier;
    /* the file size */
    private long size;
    /* the client responsable for the document*/
    private ClientDTO client;
    /* the client responsable for the document*/
    private CollaboratorDTO user;
    /* the file Extension ex: png, jpeg, pdf */
    private String fileExtension;
    private Instant createdDate;
    private String lastModifiedBy;
    private String createdBy;
    private Instant lastModifiedDate;
}