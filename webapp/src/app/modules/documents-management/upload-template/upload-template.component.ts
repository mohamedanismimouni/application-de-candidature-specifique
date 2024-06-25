import { Component, Input, OnInit } from "@angular/core";
import { NbDialogRef, NbDialogService } from "@nebular/theme";
import { WORD_TYPE, PDF_TYPE } from "src/app/constants/common.constant";
import { ToastrStatusEnum } from "src/app/enumerations/toastr-status.enum";
import { IDocument } from "src/app/models/document.model";
import { IErrorResponse } from "src/app/models/error-response.model";
import { IRequestType } from "src/app/models/request-type";
import { DocumentRequestServiceService } from "src/app/services/document-request-service.service";
import { EDMService } from "src/app/services/EDM.service";
import { RequestTypeService } from "src/app/services/requestType.service";
import { ToastrService } from "src/app/services/toastr.service";

@Component({
    selector: "app-upload-template",
    templateUrl: "./upload-template.component.html",
    styleUrls: ["./upload-template.component.scss"],
})
export class UploadTemplateComponent implements OnInit {
    @Input()
    data: IRequestType;
    file: File;
    requestTypeSaved: IRequestType;
    //var of upload
    selectedFiles: FileList;
    currentFileUpload: File;
    progress: { percentage: number } = { percentage: 0 };
    files: any[] = [];
    //var of actions
    submitButton = false;
    loadingState = false;
    uploadedFile = false;
    constructor(
        private dialogRef: NbDialogRef<UploadTemplateComponent>,
        private toastrService: ToastrService,
        private requestTypeService: RequestTypeService,
        private EDMService: EDMService,
        private documentService: DocumentRequestServiceService
    ) {}

    ngOnInit(): void {}

    onCancel() {
        this.dialogRef.close(false);
    }
    /**
     * save file in the EDM
     */
    uploadFileInGED() {
        this.loadingState = true;
        this.currentFileUpload = this.files[0];
        this.EDMService.uploadFile(
            this.currentFileUpload,
            JSON.parse(localStorage.getItem("current-user")).id,
            this.data.label,
            JSON.parse(localStorage.getItem("current-user")).id
        ).subscribe(
            (document: IDocument) => {
                this.data.idEDM = document.id;
                this.data.uploadDate = document.createdDate;
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    "Something is not right, please verify " +
                        "your input and try again later",
                    ToastrStatusEnum.DANGER
                );
                this.loadingState = false;
                this.files.splice(0, this.files.length);
                this.submitButton = false;
            },
            () => {
                this.requestTypeService.updateRequestType(this.data).subscribe(
                    (type: IRequestType) => {
                        //save id of document to request Type
                        this.toastrService.showStatusToastr(
                            "File uploaded successfully",
                            ToastrStatusEnum.SUCCESS
                        );
                        this.loadingState = false;
                        this.dialogRef.close(false);

                    },
                    (error: IErrorResponse) => {
                        this.loadingState = false;
                        this.toastrService.showStatusToastr(
                            "Something is not right, cannot Saving Document Request Type",
                            ToastrStatusEnum.DANGER
                        );
                        this.files.splice(0, this.files.length);
                        this.submitButton = false;
                        this.loadingState = false;
                    },
                    () => {
                        this.documentService
                            .requestsWithoutTemplate(this.data)
                            .subscribe(
                                (count: number) => {
                                    if (count > 0) {
                                        this.toastrService.showStatusToastr(
                                            count +
                                                " requests without template will be generated automatically",
                                            ToastrStatusEnum.SUCCESS
                                        );
                                        this.files.splice(0, this.files.length);
                                        this.submitButton = false;
                                        this.loadingState = false;
                                    }
                                },
                                (error: IErrorResponse) => {
                                    this.toastrService.showStatusToastr(
                                        "Something is not right, cannot generate requests without template",
                                        ToastrStatusEnum.DANGER
                                    );
                                    this.files.splice(0, this.files.length);
                                    this.submitButton = false;
                                    this.loadingState = false;
                                }
                            );
                    }
                );
                this.submitButton = false;
                this.files.splice(0, this.files.length);
            }
        );
    }
    /**
     *
     * @param event serch document Type
     */
    onFilter(event) {}

    /**
     * select file event
     * @param event
     */
    selectFile(event) {
        this.files.splice(0, this.files.length);
        this.selectedFiles = event.target.files;
        if (this.selectedFiles != null) {
            this.prepareFilesList(event.target.files);
            this.uploadedFile = true;
        }
        if (this.files.length > 0) {
            event.target.value = "";
        }
        this.currentFileUpload = this.files[0];

        if(this.data.label=="WORK_CERTIFICATE" || this.data.label=="MISSION_CERTIFICATE" || this.data.label== "SALARY_CERTIFICATE"){
        if (this.currentFileUpload.type === WORD_TYPE) {
            if (this.currentFileUpload.size <= 0) {
                //empty file
                this.toastrService.showStatusToastr(
                    "file is Empty",
                    ToastrStatusEnum.DANGER
                );
                this.files.splice(0, this.files.length);
                this.uploadedFile = false;
                //fichier vide
            }
        }
        else {
            this.toastrService.showStatusToastr(
                "you should upload document word",
                ToastrStatusEnum.DANGER
            );
            this.uploadedFile = false;
            this.files.splice(0, this.files.length);
        }
    }
        if(this.data.label=="USER_GUIDE"){
            if (this.currentFileUpload.type === PDF_TYPE) {
                if (this.currentFileUpload.size <= 0) {
                    //empty file
                    this.toastrService.showStatusToastr(
                        "file is Empty",
                        ToastrStatusEnum.DANGER
                    );
                    this.files.splice(0, this.files.length);
                    this.uploadedFile = false;
                    //fichier vide
                }
            }
            else {
                this.toastrService.showStatusToastr(
                    "you should upload PDF document",
                    ToastrStatusEnum.DANGER
                );
                this.uploadedFile = false;
                this.files.splice(0, this.files.length);
            }

        }
        this.submitButton = this.uploadedFile;
    }

    /**
     * on file drop handler
     */
    onFileDropped($event) {
        this.prepareFilesList($event);
    }

    /**
     * Delete file from files list
     * @param index (File index)
     */
    deleteFile(index: number) {
        this.files.splice(index, 1);
        this.submitButton = false;
    }
    /**
     * cancel method
     * @param index
     */
    cancel() {
        this.files.splice(0, this.files.length);
        this.submitButton = false;
        this.dialogRef.close(false);
    }

    /**
     * Simulate the upload process
     */
    uploadFilesSimulator(index: number) {
        // this.showSpinner();

        setTimeout(() => {
            if (index === this.files.length) {
                return;
            } else {
                const progressInterval = setInterval(() => {
                    if (this.files[index].progress === 100) {
                        clearInterval(progressInterval);
                        this.uploadFilesSimulator(index + 1);
                    } else {
                        this.files[index].progress += 10;
                    }
                }, 200);
            }
        }, 1000);
    }

    /**
     * Convert Files list to normal array list
     * @param files (Files List)
     */
    prepareFilesList(files: Array<any>) {
        for (const item of files) {
            item.progress = 0;
            this.files.push(item);
        }
        this.uploadFilesSimulator(0);
    }

    /**
     * format bytes
     * @param bytes (File size in bytes)
     * @param decimals (Decimals point)
     */
    formatBytes(bytes?, decimals?) {
        if (bytes === 0) {
            return "0 Bytes";
        }
        const k = 1024;
        const dm = decimals <= 0 ? 0 : decimals || 2;
        const sizes = ["Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return (
            parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + " " + sizes[i]
        );
    }
}
