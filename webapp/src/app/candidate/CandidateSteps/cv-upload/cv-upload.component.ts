// @ts-ignore

import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {CandidacyService} from '../../service/candidacy.service';
import {HttpClient, HttpEventType, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ToastrService} from '../../../services/toastr.service';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import {PDF_TYPE} from '../../../constants/common.constant';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-cv-upload',
  templateUrl: './cv-upload.component.html',
  styleUrls: ['./cv-upload.component.scss']
})
export class CvUploadComponent implements OnInit {
    selectedFileType = 'PayRoll';
    files: any[] = [];
    loadingState: boolean;
    uploadedFile: boolean;
    uploadPayRollDate: Date;
    uploadReminderDate: Date;
    loading = false;
    selectedFiles?: FileList;
    currentFile?: File;
    progress = 0;
    idCv = '';
    message = '';
    fileInfos?: Observable<any>;
    Skills?: string[];
    submitButton?: boolean;
    emails?: string[];
    email = '';
    candidateImg: any;
    phone?: string;

    ngOnInit(): void {

    }

    constructor(private uploadService: CandidacyService, private http: HttpClient, private router: Router, private toastrService: ToastrService, private messageService: MessageService) {
    }

    upload(): void {
        this.progress = 0;
        if (this.selectedFiles) {
            const file: File | null = this.selectedFiles.item(0);
            if (file) {
                this.currentFile = file;
                this.uploadService.upload(this.currentFile).subscribe(
                    (event: any) => {
                        if (event.type === HttpEventType.UploadProgress) {
                            this.progress = Math.round(100 * event.loaded / event.total);
                        } else if (event instanceof HttpResponse) {
                            this.message = event.body.message;
                            this.idCv = event.body.id_cv;
                            this.Skills = event.body.Skills;
                            this.emails = event.body.emails;
                            this.email = this.emails[0];
                            this.candidateImg = event.body.candidateImg;
                            this.phone = event.body.phone;
                            console.log(event.body);
                        }
                    },
                    (err: any) => {
                        console.log(err);
                        this.progress = 0;
                        if (err.error && err.error.message) {
                            this.message = err.error.message;
                        } else {
                            this.message = 'Could not upload the file!';
                        }
                        this.currentFile = undefined;
                    });
            }
            this.selectedFiles = undefined;
        }
    }

    nextPage() {
        if (!this.currentFile) {
            this.messageService.add({severity: 'error', summary: 'Alerte', detail: 'il faut attacher votre CV ! '});
        } else {
            this.router.navigate(['candidacy/GeneralInformation']);
            localStorage.setItem('id_cv', this.idCv);
            localStorage.setItem('skills', JSON.stringify(this.Skills));
            localStorage.setItem('email', this.email);
            localStorage.setItem('img', this.candidateImg);
            localStorage.setItem('phone', this.phone);
        }
    }

    prevPage() {
        this.router.navigate(['candidacy/offerSelection']);
    }

    selectFile(event) {
        this.files.splice(0, this.files.length);
        this.selectedFiles = event.target.files;
        if (this.selectedFiles != null) {
            this.prepareFilesList(event.target.files);
            this.uploadedFile = true;
            this.upload();


            //   this.fichierEncodee = Base64.getEncoder().encodeToString(this.currentFileUpload.getbites());
            //  this.fichierEncodee=this.currentFileUpload.

            //  localStorage.setItem('currentFileUpload', JSON.stringify(this.currentFileUpload));


        }
        if (this.files.length > 0) {
            event.target.value = '';
        }
        this.currentFile = this.files[0];
        if (this.currentFile.type === PDF_TYPE) {
            if (this.currentFile.size <= 0) {
                // empty file
                this.toastrService.showStatusToastr(
                    ' File is Empty !',
                    ToastrStatusEnum.DANGER
                );
                this.files.splice(0, this.files.length);
                this.uploadedFile = false;
                // fichier vide
            }
        } else {
            this.toastrService.showStatusToastr(
                ' You should upload pdf File !',
                ToastrStatusEnum.DANGER
            );
            this.uploadedFile = false;
            this.files.splice(0, this.files.length);
        }
        this.submitButton = this.uploadedFile;
    }


    onFileDropped($event) {
        this.prepareFilesList($event);
    }

    deleteFile(index: number) {
        this.files.splice(index, 1);
        this.submitButton = false;
    }


    cancel() {
        this.files.splice(0, this.files.length);
        this.submitButton = false;
    }

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

    prepareFilesList(files: Array<any>) {
        for (const item of files) {
            item.progress = 0;
            this.files.push(item);
        }
        this.uploadFilesSimulator(0);
    }

    formatBytes(bytes?, decimals?) {
        if (bytes === 0) {
            return '0 Bytes';
        }
        const k = 1024;
        const dm = decimals <= 0 ? 0 : decimals || 2;
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return (
            parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i]
        );
    }
}
