import { Component, Input, OnInit } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { WORD_EXTENSION } from 'src/app/constants/common.constant';
import { RequestTypeEnum } from 'src/app/enumerations/request-type.enum';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { IDocumentRequest } from 'src/app/models/document-request';
import { IRequestType } from 'src/app/models/request-type';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';
import { EDMService } from 'src/app/services/EDM.service';
import { RequestTypeService } from 'src/app/services/requestType.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { downloadFile } from '../../shared/EDM/DownloadFile';

@Component({
  selector: 'app-validate-request',
  templateUrl: './validate-request.component.html',
  styleUrls: ['./validate-request.component.scss']
})
export class ValidateRequestComponent implements OnInit {
  @Input()
  data: IDocumentRequest;
  document = '';
  documentRequest: IDocumentRequest;
  loadingState=false;
  etiquetteTemplate=true;
  constructor(  private toastrService: ToastrService,
    private EDMService: EDMService,
    private dialogRef: NbDialogRef<ValidateRequestComponent>,
    private documentRequestService: DocumentRequestServiceService,
    private requestTypeServce: RequestTypeService) { }

  ngOnInit(): void {
    this.checkEtiquetteTamplate() 
  }
  onCancelDialog() {
    this.dialogRef.close(false);

  }

  onValidateRequest(){
    this.loadingState=true;
    this.documentRequestService.ValidateDemandeRequest(JSON.parse(localStorage.getItem('current-user')).id,this.data).subscribe(
      {

      next: value => { this.documentRequest = value },
      error: err =>{ this.toastrService.showStatusToastr(
          'Something is not right, please verify '
          + 'your input and try again later',
          ToastrStatusEnum.DANGER);
          this.loadingState=false;
        }          ,
       complete: () => {
        this.downloadTemplate(this.documentRequest)
        this.toastrService.showStatusToastr(
        'Document Request  of '+this.data.collaborator.firstName+ '  '+ this.data.collaborator.lastName+' has been successfully validate',
        ToastrStatusEnum.SUCCESS);
        this.loadingState=false;
        this.dialogRef.close(true);
        }

  });
}

  downloadTemplate(documentRequest: IDocumentRequest) {
    //download generated document
    this.EDMService.downloadFile(documentRequest.idEDM).subscribe(
            (blobFile: Blob) => {
                this.document = downloadFile(
                    blobFile,
                    documentRequest.collaborator.firstName+"_"+documentRequest.type.label+WORD_EXTENSION
                );
            }
        );

      //download etiquette  
      this.checkEtiquetteTamplate() ;
      //download etiquette  
      if(this.etiquetteTemplate){
        this.EDMService.downloadFile(documentRequest.idEtiquetteEDM).subscribe(
          (blobFile: Blob) => {
              this.document = downloadFile(
                  blobFile,
                  documentRequest.collaborator.firstName+"_Etiquette"+WORD_EXTENSION
              );
          }
      );
      }
     
    }
    
checkEtiquetteTamplate() : void{

    this.requestTypeServce.getTypeByLabel(RequestTypeEnum.ETIQUETTE_TYPE).subscribe(
      (value: IRequestType) => {
        if (!!value.idEDM){
          this.etiquetteTemplate=true
        }
        else{
          this.etiquetteTemplate=false
        }       

    }
    )

    }


}
