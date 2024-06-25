import { Component, OnInit, ViewChild } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import { Table } from 'primeng/table';
import { WORD_EXTENSION } from 'src/app/constants/common.constant';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { IDocumentRequest } from 'src/app/models/document-request';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { IRequestType } from 'src/app/models/request-type';
import { CollaboratorService } from 'src/app/services/collaborator.service';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';
import { EDMService } from 'src/app/services/EDM.service';
import { RequestTypeService } from 'src/app/services/requestType.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { downloadFile } from '../../shared/EDM/DownloadFile';
import { DocumentRequestRejectionComponent } from '../document-request-rejection/document-request-rejection.component';
import { DocumentRequestValidationDialogComponent } from '../document-request-validation-dialog/document-request-validation-dialog.component';
import { ValidateRequestComponent } from '../validate-request/validate-request.component';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.scss']
})
export class RequestsComponent implements OnInit {
  first = 0;
  rows = 5;
  search=false;
  documentRequestsList: Array<IDocumentRequest> = [];
  document = '';
  collabs: Array<{collabInformation:ICollaborator, collabName:string}> = [];
  selectedCollab : any;
  selectedType : IRequestType;
  selectedRequests: IDocumentRequest[];
  @ViewChild('dt', { static: true })
  dt: Table;
  filteredCollab: ICollaborator[];
  loadingState=false;
  loading=true;


  motif="";

    //var of documents list type
    requestTypes: Array<IRequestType> = [];
  constructor(private documentRequestService: DocumentRequestServiceService,
    private dialogService: NbDialogService ,
    private EDMService: EDMService ,
    private collabService: CollaboratorService,
    private requestTypeService: RequestTypeService,
    private toastrService: ToastrService,) {
  }
  ngOnInit(): void {
    this.loadRequestsTypes();
    this.loadCollabs()
    this.loadInWaitingDocuemntsRequest();

  }

  loadInWaitingDocuemntsRequest() {
    this.documentRequestService.getInWaitingRequests().subscribe(
      (requestTypes: IDocumentRequest[]) => {
        this.documentRequestsList = requestTypes;
     this.loading=false
   
      }
    );
  }

/**
 * open rekjection Request Dialog
 * @param documentRequest
 */
  onRejectDocumentRequest(documentRequest: IDocumentRequest) {
    this.dialogService.open(
      DocumentRequestRejectionComponent,
      { context: { data: documentRequest } }
    ).onClose.subscribe(
        (result: boolean) => {
            if (result) {
              this.loadInWaitingDocuemntsRequest();
            }
        }
    );
}
/**
 * validate list of document requests
 * @param documentRequest
 */
onValidateDocumentRequest() {
//delete without template from list
this.deleteRequestsWithoutTemplate();

  this.dialogService.open(
    DocumentRequestValidationDialogComponent,
    { context: { requestsToValidate: this.selectedRequests } }
  ).onClose.subscribe(
      (result: boolean) => {
          if (result) {
            this.loadInWaitingDocuemntsRequest();
           /*  setTimeout(() => {

              this.loadInWaitingDocuemntsRequest();
            }, 1000); */

          }
      }
  );
}
deleteRequestsWithoutTemplate() {
  for (var i = this.selectedRequests.length - 1; i >= 0; i--) {
    if (this.selectedRequests[i].withoutTemplate === true) {
      this.selectedRequests.splice(i, 1);
    }
}
}
/**
 * Upload Updated File
 * @param documentRequest
 */
ValidateRequest(documentRequest: IDocumentRequest) {
  this.dialogService.open(
    ValidateRequestComponent,
    { context: { data: documentRequest } }
  ).onClose.subscribe(
      (result: boolean) => {
          if (result) {
            this.loadInWaitingDocuemntsRequest();
          }
      }
  );
}

  next() {
    this.first = this.first + this.rows;
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
  }

  isLastPage(): boolean {
    return this.documentRequestsList ? this.first === (this.documentRequestsList.length - this.rows) : true;
  }

  isFirstPage(): boolean {
    return this.documentRequestsList ? this.first === 0 : true;
  }

 /**
 * downloadFile
 * @param requestType
 */
  downloadTemplate(documentRequest: IDocumentRequest) {


    this.EDMService.downloadFile(documentRequest.idEDM).subscribe(
            (blobFile: Blob) => {
                this.document = downloadFile(
                    blobFile,
                    documentRequest.collaborator.firstName+"_"+documentRequest.type.label+WORD_EXTENSION
                );
            }
        );
    }

    loadCollabs() {
      this.collabService.getCollabsWithUser().subscribe(
       (collabs: ICollaborator[]) => {      
         
        collabs.forEach(collab => {
                  this.collabs.push({collabInformation:collab,collabName:  collab.firstName+' '+collab.lastName});


      });      });
 }
     /**
     * Load Requests Types
     * getRequestsTemplate()
     */
      loadRequestsTypes() {
        this.requestTypeService
            .getRequestsTemplate()
            .subscribe((requestTypes: IRequestType[]) => {
                this.requestTypes = requestTypes;
            });
    }

    onSubmit(){
      this.loadingState=true;
     this.documentRequestService.sendDocumentRequest(JSON.parse(localStorage.getItem('current-user')).id,this.selectedCollab.collabInformation.id,this.selectedType.label, this.motif , true).subscribe(
        () => {
          this.selectedCollab=null;
          this.selectedType=null
          this.motif=null
          this.toastrService.showStatusToastr(
              'The Request has been successfully registered',
              ToastrStatusEnum.SUCCESS);
              this.loadInWaitingDocuemntsRequest();
              this.loadingState=false;


        },
        (error: IErrorResponse) => {
          this.toastrService.showStatusToastr(
            'Something is not right, please verify '
            + 'your input and try again later',
            ToastrStatusEnum.DANGER);
            this.loadingState=false;
            this.selectedCollab=null;
            this.selectedType=null
            this.motif=null

        }
    );
    }

    onCancel()
    {
      this.selectedCollab=null;
      this.selectedType=null
      this.motif=null
    }

    toggleSearch() {
      this.search = !this.search;
       if (!this.search) {
        this.dt.filterGlobal("", 'contains');
      }
    }

    applyFilterGlobal($event, stringVal) {
      this.dt.filterGlobal(($event.target as HTMLInputElement).value, 'contains');
    }


    filterCollabs(event) {
      //in a real application, make a request to a remote url with the query and return filtered results, for demo we filter at client side
      let filtered: any[] = [];
      let query = event.query;
      for (let i = 0; i < this.collabs.length; i++) {
        let collab = this.collabs[i];
        if (collab.collabInformation.firstName.toLowerCase().indexOf(query.toLowerCase()) == 0 ||
        collab.collabInformation.lastName.toLowerCase().indexOf(query.toLowerCase()) ==0  ||
        collab.collabName.toLowerCase().indexOf(query.toLowerCase()) ==0   ) {
          filtered.push(collab);
        }
      }

      this.filteredCollab = filtered;
    }


}
