import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import * as moment from 'moment';
import { WORD_EXTENSION } from 'src/app/constants/common.constant';
import { DATE_EXTENDED_DISPLAY_FORMAT, DATE_STORAGE_FORMAT } from 'src/app/constants/date-time-formats.constant';
import { RequestStatusEnum } from 'src/app/enumerations/request-status.enum';
import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { IDocumentRequest } from 'src/app/models/document-request';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { IRequestType } from 'src/app/models/request-type';
import { ITeam } from 'src/app/models/team.model';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';
import { EDMService } from 'src/app/services/EDM.service';
import { RequestTypeService } from 'src/app/services/requestType.service';
import { StorageService } from 'src/app/services/storage.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { UserService } from 'src/app/services/user.service';
import { downloadFile } from '../../shared/EDM/DownloadFile';
import { AddDocumentRequestComponent } from './add-document-request/add-document-request.component';
import { CancelDocumentRequestComponent } from './cancel-document-request/cancel-document-request.component';
import {QrCodeScannerComponent} from './qr-code-scanner/qr-code-scanner.component';


@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.scss']
})
export class DocumentComponent implements OnInit {
  qrCodeResult :string;
  etiquetteCode : Boolean;
  codeValue: String;
  response =""  
  colorArray = ['#1D4D91', '#1ABC9C', '#1B4F72', '#1ABC9C', '#1ABC9C',
  '#E6B333', '#3366E6', '#999966', '#99FF99', '#B34D4D',
  '#80B300', '#809900', '#E6B3B3', '#6680B3', '#66991A', ];
  selectedTypeId: string;
  documentTypes: Array<{documentType:IRequestType, RequestsNumber:number}> = [];
  requestDocument: Array<IDocumentRequest> = [];
  config: any;
  configHistorique: any;
  enableEditIndex = null;
  enableAccordionIndex = null;
  
  loading=true;

  requests: IDocumentRequest[];

    documentDelete =
    {
        icon: 'close-outline',
        title: 'Cancel Request',
    };

    documentdownload =
    {
        icon: 'download-outline',
        title: 'Download document',
    };

    documentAdd=
    {
      icon: 'file-add-outline',
        title: 'Add request',
    };
    documentRejetionInfo=
    {
      icon: 'info-outline',
        title: 'Add request',

    };
    documentReceived=
    {
      icon: 'camera-outline',
        title: 'Scan the QR Code',
    };


    doc = '';

  collaboratorSettings: any = {};
  collaboratorID: string;
  fistLetters:string;

  team:any
  currentUser: ICollaborator

  inwaitingRequestsNumber: number=0;
  validatedRequestsNumber: number=0;
  receivedRequestsNumber: number=0;
  @ViewChild('addButton') addButton: ElementRef;

  constructor( private storageService: StorageService , private dialogService: NbDialogService,
     private documentService: DocumentRequestServiceService , private requestTypeService: RequestTypeService,
     private toastrService: ToastrService,  private EDMService: EDMService, private userService: UserService


    ) {
    this.config = {
      itemsPerPage: 4,
      currentPage: 1,
      totalItems: this.documentTypes.length,
      id:'d'
    };
    this.configHistorique = {
      itemsPerPage: 5,
      currentPage: 1,
      totalItems: this.requestDocument.length,
    };
   }

  ngOnInit(): void {
    this.getStatics();
    this.getDocumentTypes();
    this.collaboratorSettings = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY)
    this.collaboratorSettings.recruitedAt=moment(this.collaboratorSettings.recruitedAt, DATE_STORAGE_FORMAT).format(DATE_EXTENDED_DISPLAY_FORMAT);
    this.collaboratorID =   this.collaboratorSettings.id;
 this.userService.getCollaboratorTeam(this.collaboratorSettings.id).subscribe((team: ITeam)=>
 this.team=team);
    this.fistLetters=this.collaboratorSettings.firstName[0]+this.collaboratorSettings.lastName[0];
  }

  pageChanged(event) {
    this.config.currentPage = event;
  }

  pageChangedHistorique(event) {
    this.configHistorique.currentPage = event;
  }

 getRandomColor () {
  return  this.colorArray[Math.floor(Math.random()*this.colorArray.length)];
};


onAddDocumentRequest(type:IRequestType) {
    this.dialogService.open(
      AddDocumentRequestComponent,
              {
                context: { data: type }
               }
    ).onClose.subscribe(
        (result: boolean) => {
            if (result) {
              this.loadRequests(type);
            }

});
}


onCancelDocumentRequest(DemandeRequest : IDocumentRequest,type:IRequestType ) {
  this.dialogService.open(
    CancelDocumentRequestComponent,
      {
          context: {

          }
      }
  ).onClose.subscribe(
      (result: boolean) => {
          if (result) {
            this.documentService.CancelDemandeRequest(DemandeRequest).subscribe(
              () => {
                this.toastrService.showStatusToastr(
                    'Request has been successfully cancled',
                    ToastrStatusEnum.SUCCESS);
                    this.loadRequests(type);

              },
              (error: IErrorResponse) => {
                this.toastrService.showStatusToastr(
                  'Something is not right',
                  ToastrStatusEnum.DANGER);

              }
          );
          }
      }
  );
}


getDocumentTypes()
{
  this.documentTypes=[];
  this.requestTypeService.getRequestsTemplate().subscribe(
    (documentTypes: Array<IRequestType>) => {

      documentTypes.forEach(documentType => {
        this.documentService.getDocumentRequest(this.collaboratorID,documentType.id).subscribe(
            (requestDocument: Array<IDocumentRequest>) => {
                this.documentTypes.push({documentType:documentType,RequestsNumber:  requestDocument.length});
             
                 this.loading=false
               
            }
        );
    });
    }
    );
    this.selectedTypeId = undefined;
  }


getDocumentRequest(typeID: number)
{
  this.documentService.getDocumentRequest(this.collaboratorID,typeID).subscribe(
    (requestDocument: Array<IDocumentRequest>) => {
      this.requestDocument = requestDocument;
    }
    );
}


onAccordionItemClick(typeID: number) {
  this.CloseEditMode();
   this.getDocumentRequest(typeID);

}

onAccordionClick(typeID: number) {
 return typeID;
}

loadRequests(type:IRequestType){
  this.documentService.getDocumentRequest(this.collaboratorID,this.onAccordionClick(type.id)).subscribe(
    (requestDocument: Array<IDocumentRequest>) => {
      this.requestDocument= requestDocument;
      this.documentTypes.map(documentType =>{ if(documentType.documentType===type){
                                                         documentType.RequestsNumber= this.requestDocument.length}}
                                                         );
  })
  this.getStatics();
}

fireEvent(e) {
  this.addButton.nativeElement.focus();
  e.stopPropagation();
  e.stopImmediatePropagation();
  return false;
}


    /**
     * downloadFile
     * @param requestType
     */
    downloadTemplate(DemandeRequest : IDocumentRequest ) {
      var link;
      this.EDMService.downloadFile(DemandeRequest.idEDM).subscribe(
          (blobFile: Blob) => {
              this.doc = downloadFile(
                  blobFile,
                  DemandeRequest.type.label+WORD_EXTENSION
              );
          }
      );
  }



  getStatics(){
   this.documentService.countDocumentBySatus(RequestStatusEnum.IN_WAITING, this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe(
    (data:number) => {
      this.inwaitingRequestsNumber = data;
    });

    this.documentService.countDocumentBySatus(RequestStatusEnum.VALIDATED, this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe(
      (data:number) => {
        this.validatedRequestsNumber = data;
      });

    this.documentService.countDocumentBySatus(RequestStatusEnum.RECEIVED, this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe(
        (data:number) => {
          this.receivedRequestsNumber = data;
    });
  }

  ReceivedDemandeRequest(documentRequest : IDocumentRequest ,type:IRequestType, isWeb:Boolean )
  {

      if(!isWeb){
        this.dialogService.open(
          QrCodeScannerComponent
        ).onClose.subscribe(
            (result: string) => {
              if(result!="false")
              console.log("reslt from QR code back",result);
              console.log("Document request id",documentRequest.id.toString());
              { 
                this.UpadteDocumentRequest(documentRequest,type,result);
              }
            }
        );
  
      }
      else{
        this.toastrService.showStatusToastr(
          'Please connect with your mobile phone, to scan the Qr Code.',
          ToastrStatusEnum.DANGER);
      }
  

  
  }

UpadteDocumentRequest(documentRequest :IDocumentRequest ,type:IRequestType , result : String){

  if(result === documentRequest.id.toString())
  {
    console.log("documentRequest",documentRequest);
    this.documentService.ReceivedDemandeRequest(documentRequest).subscribe(
      () => {
          this.toastrService.showStatusToastr(
            'Request has been successfully Received',
            ToastrStatusEnum.SUCCESS);
            this.loadRequests(type);
  
      },
      (error: IErrorResponse) => {
        this.toastrService.showStatusToastr(
          'Oups! Something went wrong, please try again later',
          ToastrStatusEnum.DANGER);
      },
  
  );
  }else{
    this.toastrService.showStatusToastr(
      'This Code is not for this document request',
      ToastrStatusEnum.DANGER);
  }


}

switchEditMode(i:number , j:number) {
  console.log("display i :",i)
  this.etiquetteCode=false
  this.enableEditIndex = j;
  this.enableAccordionIndex=i;
}

CloseEditMode() {
  this.response=""
  this.etiquetteCode=true
  this.enableEditIndex = null;
  this.enableAccordionIndex=null;
}

SubmitEtiquetteCode(documentRequest :IDocumentRequest ,type:IRequestType) {
  let result =this.response
  this.UpadteDocumentRequest(documentRequest,type,result.toString())
  this.response=""
  this.etiquetteCode=true
  this.enableEditIndex = null;
  this.enableAccordionIndex=null;
}

}
