import { Component, Input, OnInit } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { WORD_EXTENSION } from 'src/app/constants/common.constant';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { IDocumentRequest } from 'src/app/models/document-request';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';
import { EDMService } from 'src/app/services/EDM.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { downloadFile } from '../../shared/EDM/DownloadFile';
import { saveAs } from 'file-saver';
@Component({
  selector: 'app-document-request-validation-dialog',
  templateUrl: './document-request-validation-dialog.component.html',
  styleUrls: ['./document-request-validation-dialog.component.scss']
})
export class DocumentRequestValidationDialogComponent implements OnInit {
  @Input()
  requestsToValidate: IDocumentRequest[];
  loadingState=false;
  document = '';

  constructor( private dialogRef: NbDialogRef<DocumentRequestValidationDialogComponent>,
    private documentRequestService: DocumentRequestServiceService,
    private toastrService: ToastrService,
    private EDMService: EDMService) { }

  ngOnInit(): void {
  }
  onCancel() {
    this.dialogRef.close(false);
  }

        onSubmit(){
        this.loadingState=true
        this.documentRequestService.ValidateAllDemandeRequest(JSON.parse(localStorage.getItem('current-user')).id,this.requestsToValidate).subscribe(
         {
          next :(data) => { if(data){
            this.downloadEtiquetteMerged(data);
            Object.values(data).map(documentrequest =>{
            this.downloadTemplate(documentrequest)
  
            this.toastrService.showStatusToastr(
            'Selected Document Requests  has been successfully validate',
            ToastrStatusEnum.SUCCESS);
            this.dialogRef.close(true);
            this.requestsToValidate.splice(0, Object.values(data).length);
            });
        
            this.loadingState=false
          }
            },
            error : (IErrorResponse)=>{
              this.toastrService.showStatusToastr(
                'Something is not right, please verify '
                + 'your input and try again later',
                ToastrStatusEnum.DANGER);
                this.loadingState=false;
            },
            complete:()=>{
           
            }
         }
            );
          }

      downloadEtiquetteMerged(  requestsToValidate: IDocumentRequest[]): void {
        this.documentRequestService.downloadEtiquetteMergedFiles(requestsToValidate).subscribe({
          next :(blob) => {
            saveAs(blob, "Etiquette.docx")
            },
          complete:()=>{}}
     

        );
  }




 downloadTemplate(documentRequest: IDocumentRequest) {
    this.EDMService.downloadFile(documentRequest.idEDM).subscribe({

      next :  (blobFile: Blob) => {
        this.document = downloadFile(
            blobFile,
            documentRequest.collaborator.firstName+"_"+documentRequest.type.label+WORD_EXTENSION
        );
    },
    complete: ()=>{
  }
         });
}


}

