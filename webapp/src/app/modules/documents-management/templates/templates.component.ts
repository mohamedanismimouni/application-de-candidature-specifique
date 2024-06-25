import { Component, OnInit, ViewChild } from "@angular/core";
import { NbDialogService } from "@nebular/theme";
import { ConfirmationDialogComponent } from "src/app/components/confirmation-dialog/confirmation-dialog.component";
import { WORD_EXTENSION, PDF_EXTENSION } from "src/app/constants/common.constant";
import { ToastrStatusEnum } from "src/app/enumerations/toastr-status.enum";
import { IDialogData } from "src/app/models/dialog-data.model";
import { IErrorResponse } from "src/app/models/error-response.model";
import { IRequestType } from "src/app/models/request-type";
import { EDMService } from "src/app/services/EDM.service";
import { RequestTypeService } from "src/app/services/requestType.service";
import { ToastrService } from "src/app/services/toastr.service";
import { downloadFile } from "../../shared/EDM/DownloadFile";
import { Table } from "primeng/table";
import { UploadTemplateComponent } from "../upload-template/upload-template.component";
import { UsefulDocumentService } from "src/app/services/useful-document.service";
import { IUsefulDocument } from "src/app/models/useful-document.model";
import { UploadUsefulDocumentComponent } from "../upload-useful-document/upload-useful-document.component";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { HttpClient } from "@angular/common/http";
@Component({
    selector: "app-templates",
    templateUrl: "./templates.component.html",
    styleUrls: ["./templates.component.scss"],
})
export class TemplatesComponent implements OnInit {

    download = false;
    //pagination
    first = 0;
    rows = 5;
    //var of actions
    loadingState = false;
    loading=true
    @ViewChild('dt', { static: true })
    dt: Table;
    search=false;

    //var of documents list type
    requestTypes: Array<IRequestType> = [];
    selectedRequestType: IRequestType;

    usefulDocuments: Array<IUsefulDocument> = [];
    selectedUsefulDocument: IUsefulDocument;
    constructor( private sanitizer: DomSanitizer,
        private toastrService: ToastrService,
        private requestTypeService: RequestTypeService,
        private EDMService: EDMService,
        private dialogService: NbDialogService,
        private usefulDocumentService: UsefulDocumentService
    ) {}
    ngOnInit(): void {
        this.loadRequestsTypes();
        this.loadUsefulDocuments();


  }


    onRowSelect(event: any, template?: any) {
    }

    /**
     * Load Requests Types
     */
    loadRequestsTypes() {
        this.requestTypeService
            .getRequestsType()
            .subscribe((requestTypes: IRequestType[]) => {
               this.loading=false
              
                this.requestTypes = requestTypes;
            });
    }
    /**
     * upload  /  Update Template Document
     */
    uploadFileCall(requestType: IRequestType) {
        this.selectedRequestType = requestType;
        this.dialogService.open(
            UploadTemplateComponent,
            { context: { data: requestType } }
          ).onClose.subscribe(
              (result: boolean) => {
                  if (result) {
                    this.loadRequestsTypes();
                  }
              }
          );
    }

    link;
    /**
     * downloadFile
     * @param requestType
     */
    downloadTemplate(requestType: IRequestType) {
        this.download = true;
        if(requestType.label=="WORK_CERTIFICATE" || requestType.label=="MISSION_CERTIFICATE" || requestType.label== "SALARY_CERTIFICATE"|| requestType.label== "ETIQUETTE_TYPE") {
        this.EDMService.downloadFile(requestType.idEDM).subscribe(
            (blobFile: Blob) => {
                downloadFile(blobFile, requestType.label + WORD_EXTENSION);
            }
            ,
            (error: IErrorResponse) => {

                this.toastrService.showStatusToastr(
                    " 'Document's Template connot be downloaded'",
                    ToastrStatusEnum.DANGER
                );},
        );
    }
    if(requestType.label=="USER_GUIDE"){
        this.EDMService.downloadFile(requestType.idEDM).subscribe(
            (blobFile: Blob) => {
                downloadFile(blobFile, requestType.label + PDF_EXTENSION);
            }
            ,
            (error: IErrorResponse) => {

                this.toastrService.showStatusToastr(
                    " 'User guide connot be downloaded'",
                    ToastrStatusEnum.DANGER
                );},
        );

    }
    }


    /**
     * Delete document File
     */
    deleteFileFromEDM(requestType: IRequestType) {
        const dialogData: IDialogData = {
            title: "Delete Document's Template",
            content:
                "You're about to delete a Document's Template permanently, do you wish to proceed?",
        };
        this.dialogService
            .open(ConfirmationDialogComponent, {
                context: { data: dialogData },
            })
            .onClose.subscribe((result: boolean) => {
                if (result) {
                    //delete document
                    this.EDMService.deleteDocument(requestType.idEDM).subscribe(
                        () => {
                            requestType.idEDM = null;
                            requestType.uploadDate = null;

                        },
                        (error: IErrorResponse) => {

                                    this.toastrService.showStatusToastr(
                                        " 'Document's Template connot be deleted'",
                                        ToastrStatusEnum.DANGER
                                    );


                        },
                        ()=>{

                            this.requestTypeService
                            .updateRequestType(requestType)
                            .subscribe(
                                (type: IRequestType) => {
                                    this.loadRequestsTypes();
                                    //save id of document to request Type
                                    this.loadRequestsTypes();
                                },
                                (error: IErrorResponse) => {
                                    this.loadingState = false;
                                    this.toastrService.showStatusToastr(
                                        "Something is not right, cannot delete Document 's Template",
                                        ToastrStatusEnum.DANGER
                                    );
                                }
                            );

                        this.toastrService.showStatusToastr(
                            " document deleted successfully",
                            ToastrStatusEnum.SUCCESS
                        );
                        }
                               );
                }
            });
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
        return this.requestTypes
            ? this.first === this.requestTypes.length - this.rows
            : true;
    }

    isFirstPage(): boolean {
        return this.requestTypes ? this.first === 0 : true;
    }

    /**
     *
     * @param event serch document Type
     */
    onFilter(event) {}

    toggleSearch() {
        this.search = !this.search;
         if (!this.search) {
          this.dt.filterGlobal("", 'contains');
        }
      }

      applyFilterGlobal($event, stringVal) {
        this.dt.filterGlobal(($event.target as HTMLInputElement).value, 'contains');
      }

      /**
       * useful document
       */
      loadUsefulDocuments() {
        this.usefulDocumentService
            .getUsefulDocument()
            .subscribe((requestTypes: IUsefulDocument[]) => {
                this.usefulDocuments = requestTypes;
            });
    }

     /**
     * upload  /  Update Template Document
     */
      uploadUsefulDocumentCall(usefulDocument: IUsefulDocument) {
        this.selectedUsefulDocument = usefulDocument;
        this.dialogService.open(
            UploadUsefulDocumentComponent,
            { context: { data: usefulDocument } }
          ).onClose.subscribe(
              (result: boolean) => {
                  if (result) {
                    this.loadRequestsTypes();
                  }
              }
          );
    }

      /**
     * Delete document File
     */
       deleteUsefulDocumentFromEDM(requestType: IUsefulDocument) {
        const dialogData: IDialogData = {
            title: "Delete Useful Document",
            content:
                "You're about to delete a useful document's Template permanently, do you wish to proceed?",
        };
        this.dialogService
            .open(ConfirmationDialogComponent, {
                context: { data: dialogData },
            })
            .onClose.subscribe((result: boolean) => {
                if (result) {
                    //delete document
                    this.EDMService.deleteDocument(requestType.idEDM).subscribe(
                        () => {
                            requestType.idEDM = null;
                            requestType.uploadDate = null;


                        },
                        (error: IErrorResponse) => {
                                    this.toastrService.showStatusToastr(
                                        " 'Document's Template connot be deleted'",
                                        ToastrStatusEnum.DANGER
                                    );
                        },
                        ()=>{

                            this.usefulDocumentService
                            .updateUsefulDocument(requestType)
                            .subscribe(
                                () => {
                                    this.deleteImageOfPDF(requestType);
                                    this.loadRequestsTypes();
                                    //save id of document to request Type
                                    this.loadRequestsTypes();
                                },
                                (error: IErrorResponse) => {
                                    this.loadingState = false;
                                    this.toastrService.showStatusToastr(
                                        "Something is not right, cannot delete Document 's Template",
                                        ToastrStatusEnum.DANGER
                                    );
                                }
                            );

                        this.toastrService.showStatusToastr(
                            " document deleted successfully",
                            ToastrStatusEnum.SUCCESS
                        );
                        });
                }
            });
    }

    deleteImageOfPDF(usefulDocument: IUsefulDocument){
        this.usefulDocumentService.deleteImage(usefulDocument).subscribe( ()=> {
        });
    }

     /**
     * downloadUsefulDocument
     * @param usefulDocument
     */
      downloadUsefulDocument(usefulDocument: IUsefulDocument) {
        this.download = true;
        this.EDMService.downloadFile(usefulDocument.idEDM).subscribe(
            (blobFile: Blob) => {
                downloadFile(blobFile, usefulDocument.label +PDF_EXTENSION);
            }
            ,
            (error: IErrorResponse) => {

                this.toastrService.showStatusToastr(
                    " 'Useful document connot be downloaded'",
                    ToastrStatusEnum.DANGER
                );},
        );
    }
}
