import { Component, OnInit } from "@angular/core";
import {
    BsDatepickerConfig,
    BsDatepickerViewMode,
    BsLocaleService,
} from "ngx-bootstrap/datepicker";
import { EXCEL_TYPE } from "src/app/constants/common.constant";
import { ToastrStatusEnum } from "src/app/enumerations/toastr-status.enum";
import { IErrorResponse } from "src/app/models/error-response.model";
import { PayRollService } from "src/app/services/payRoll.service";
import { RequestTypeService } from "src/app/services/requestType.service";
import { ToastrService } from "src/app/services/toastr.service";
import { frLocale } from "ngx-bootstrap/locale";
import { defineLocale } from "ngx-bootstrap/chronos";
import { formatDate } from "@angular/common";
import { RequestTypeEnum } from "src/app/enumerations/request-type.enum";
defineLocale("fr", frLocale);
@Component({
    selector: "app-upload-payroll",
    templateUrl: "./upload-payroll.component.html",
    styleUrls: ["./upload-payroll.component.scss"],
})
export class UploadPayrollComponent implements OnInit {
    //radio button
    selectedFileType: string = "PayRoll";
    //var of upload
    selectedFiles: FileList;
    currentFileUpload: File;
    progress: { percentage: number } = { percentage: 0 };
    files: any[] = [];
    IsEmptydate = true;
    payrollDate;
    dateRangePickerValue: Date[];
    range1: Date = new Date(2020, 5);
    range2: Date = new Date(2020, 8);
    minMode: BsDatepickerViewMode = "month";
    bsConfig: Partial<BsDatepickerConfig>;
    maxDate: Date;
    submitButton = false;
    loadingState = false;
    uploadedFile = false;
    uploadPayRollDate: Date;
    uploadReminderDate: Date;
    loading=true
    constructor(
        private toastrService: ToastrService,
        private payRollService: PayRollService,
        private requestTypeService: RequestTypeService,
        private localeService: BsLocaleService
    ) {
        this.localeService.use("fr");
    }
    ngOnInit(): void {
        this.maxDate = new Date();
        this.getLastUpload(RequestTypeEnum.PAYROLL);
        this.getLastUpload(RequestTypeEnum.REMINDER);
        this.dateRangePickerValue = [this.range1, this.range2];

        this.bsConfig = Object.assign(
            {},
            {
                minMode: this.minMode,
                containerClass: "theme-dark-blue",
                isAnimated: true,
            }
        );
    this.loading=false
     
    }

    /**
     * get last upload Date of PayRoll + reminder
     */
    getLastUpload(type: RequestTypeEnum) {
        if (type === RequestTypeEnum.PAYROLL) {
            this.requestTypeService.lastUploadOfPayRoll().subscribe((data) => {
                this.uploadPayRollDate = data;
            });
        } else {
            this.requestTypeService
                .lastUploadOfPReminder()
                .subscribe((data) => {
                    this.uploadReminderDate = data;
                });
        }
    }

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
        if (this.currentFileUpload.type === EXCEL_TYPE) {
            if (this.currentFileUpload.size <= 0) {
                //empty file
                this.toastrService.showStatusToastr(
                    " File is Empty !",
                    ToastrStatusEnum.DANGER
                );
                this.files.splice(0, this.files.length);
                this.uploadedFile = false;
                //fichier vide
            }
        } else {
            this.toastrService.showStatusToastr(
                " You should upload Excel File !",
                ToastrStatusEnum.DANGER
            );
            this.uploadedFile = false;
            this.files.splice(0, this.files.length);
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
        this.payrollDate = "";
    }

    /**
     * Simulate the upload process
     */
    uploadFilesSimulator(index: number) {
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

    uploadPayRollFile() {
        const format = "MM/dd/yyyy";
        const payRollDate = formatDate(this.payrollDate, format, "en-US");
        this.loadingState = true;
        this.currentFileUpload = this.files[0];
        if (this.selectedFileType === "PayRoll") {
            //call service traietemtn des journal de Paie
            this.payRollService
                .uploadPayRoll(this.currentFileUpload, payRollDate)
                .subscribe(
                    () => {
                        this.toastrService.showStatusToastr(
                            " File uploaded successfully",
                            ToastrStatusEnum.SUCCESS
                        );
                        this.getLastUpload(RequestTypeEnum.PAYROLL);
                        this.loadingState = false;
                        this.files.splice(0, this.files.length);
                        this.submitButton = false;
                        this.payrollDate = "";
                    },
                    (error: IErrorResponse) => {
                        this.getLastUpload(RequestTypeEnum.PAYROLL);
                        this.loadingState = false;
                        this.toastrService.showStatusToastr(
                            " Something is not right, cannot upload PayRoll !",
                            ToastrStatusEnum.DANGER
                        );
                        this.files.splice(0, this.files.length);
                        this.submitButton = false;
                    }
                );
        } else {
            //call service traietemtn des journal de Paie
            this.payRollService
                .uploadReminder(this.currentFileUpload, payRollDate)
                .subscribe(
                    () => {
                        this.toastrService.showStatusToastr(
                            " File uploaded successfully",
                            ToastrStatusEnum.SUCCESS
                        );
                        this.getLastUpload(RequestTypeEnum.REMINDER);
                        this.loadingState = false;
                        this.files.splice(0, this.files.length);
                        this.submitButton = false;
                        this.payrollDate = "";
                    },
                    (error: IErrorResponse) => {
                        this.getLastUpload(RequestTypeEnum.REMINDER);
                        this.loadingState = false;
                        this.toastrService.showStatusToastr(
                            " Something is not right, cannot upload Reminder !",
                            ToastrStatusEnum.DANGER
                        );
                        this.files.splice(0, this.files.length);
                        this.submitButton = false;
                    }
                );
        }
        //initialise radio button
        this.selectedFileType = "PayRoll";
    }

    onValueChange(value: Date): void {
        if (value != null) {
            this.IsEmptydate = false;
        } else {
            this.IsEmptydate = true;
        }
    }
}
