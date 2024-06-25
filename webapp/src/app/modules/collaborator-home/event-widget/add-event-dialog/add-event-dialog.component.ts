import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NbDialogRef } from '@nebular/theme';

import * as moment from 'moment';

import { FormService } from '../../../../services/form.service';
import { UserService } from 'src/app/services/user.service';
import { StorageService } from '../../../../services/storage.service';
import { EventService } from 'src/app/services/event.service';
import { ToastrService } from '../../../../services/toastr.service';

import { ICollaborator } from 'src/app/models/collaborator.model';
import { IErrorResponse } from '../../../../models/error-response.model';

import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { ToastrStatusEnum } from '../../../../enumerations/toastr-status.enum';
import { ScoreService } from 'src/app/services/score.service';
import { LocalStorageService } from 'ngx-webstorage';
import { DATE_STORAGE_FORMAT } from 'src/app/constants/date-time-formats.constant';
import { EDMService } from 'src/app/services/EDM.service';
import { IDocument } from 'src/app/models/document.model';
import { EventStatusEnum } from 'src/app/enumerations/event-status.enum';



@Component({
    templateUrl: './add-event-dialog.component.html',
    styleUrls: [ './add-event-dialog.component.scss' ]
})
export class AddEventDialogComponent implements OnInit {

    @Input()
    data: any;

    currentUser: ICollaborator ;

    loadingState = false;

    isPaid = false;

    eventType = '' ;

    today = new Date (moment().subtract(1, 'days').format("YYYY-MM-DD"));

    addEventFormGroup: FormGroup = this.formBuilder.group({
        title:         [ '', Validators.required ],
        date:          [ '', Validators.required ],
        location:      [ '', Validators.required ],
        originLink:    [ '' ],
        creator:       [ '', Validators.required ],
        idEDMImage: [ '' ],
        imageExtension: [ '' ],
        status:       [ '', Validators.required ],
        numberMaxParticipation: [ '' ],
        type:         [ '', Validators.required ],
        eventPrice:   [ '' ]
    });


    currentFileUpload: File;
    uploadedFiles: any[] = [];

    constructor(
        private formBuilder: FormBuilder,
        private dialogRef: NbDialogRef<AddEventDialogComponent>,
        public formService: FormService,
        private storageService: StorageService,
        private userService: UserService,
        private eventService: EventService,
        private toastrService: ToastrService,
        private scoreService: ScoreService,
        private localStorageService: LocalStorageService,
        private EDMService: EDMService
    ) { }

    ngOnInit() {
        this.userService.getUserById(this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe((currentUser : ICollaborator) => {
            this.currentUser = currentUser;
            this.addEventFormGroup.controls['creator'].setValue(this.currentUser);
        });
        this.addEventFormGroup.controls['status'].setValue(EventStatusEnum.ACTIVE);

    }

    onTypeSelect(type: string){

        if (type === "Gratuit") {
            this.isPaid = false;
            this.addEventFormGroup.controls['type'].setValue('Gratuit');
            this.addEventFormGroup.get('eventPrice').clearValidators();
        }
        else {
            this.addEventFormGroup.controls['type'].setValue('Payant');
            this.addEventFormGroup.get('eventPrice').setValidators(Validators.required);
            this.isPaid = true;
        };
    }

    onCancel() {
        this.dialogRef.close();
    }

    addEvent() {
        this.loadingState = true;;
        this.addEventFormGroup.controls['type'].setValue(this.addEventFormGroup.get('type').value);
        if(this.addEventFormGroup.get('type').value=='Gratuit'){
            this.addEventFormGroup.controls['eventPrice'].setValue(0)
        }
        else{
            this.addEventFormGroup.controls['eventPrice'].setValue(this.addEventFormGroup.get('eventPrice').value);
        }
        this.addEventFormGroup.controls['date'].setValue( new Date(moment(this.addEventFormGroup.get('date').value).format(DATE_STORAGE_FORMAT)));
        this.eventService.addEvent(this.addEventFormGroup.value).subscribe(() => {
            this.loadingState = false;
            this.toastrService.showStatusToastr(
                'Event created successfully,' + this.data+
                ' points will be added to your score',
                ToastrStatusEnum.SUCCESS);
            this.isPaid = false;
            this.dialogRef.close(true);
            this.reloadScore();
        },
        (error: IErrorResponse) => {
            this.loadingState = false;
            if (error.status >= 400 && error.status < 500) {
                this.toastrService.showStatusToastr(
                    'There was a problem creating the event',
                    ToastrStatusEnum.DANGER);
            }
            this.isPaid = false;
            this.dialogRef.close(true);
        })
}

reloadScore(){
    this.scoreService.getScore(this.currentUser.email).subscribe(
        (score: number) => {
            this.localStorageService.store("curentScore",score);
        });
}

onUpload(event) {
    for(let file of event.files) {
        this.uploadedFiles.push(file);
    }
}

addEventWithImage() {

    this.currentFileUpload = this.uploadedFiles[0];
    if(this.currentFileUpload!=null){
    this.EDMService.uploadFile(
        this.currentFileUpload,
        JSON.parse(localStorage.getItem("current-user")).id,
        this.randomIntFromInterval().toString(),
        JSON.parse(localStorage.getItem("current-user")).id
    ).subscribe(
        (document: IDocument) => {
            this.addEventFormGroup.controls['idEDMImage'].setValue(document.id);
            this.addEventFormGroup.controls['imageExtension'].setValue(this.uploadedFiles[0].name.split('.').pop());
        },
        (error: IErrorResponse) => {
            this.loadingState = false;
            this.toastrService.showStatusToastr(
                "Something is not right, please verify " +
                    "your input and try again later",
                ToastrStatusEnum.DANGER
            );
            this.uploadedFiles.splice(0, this.uploadedFiles.length);
        },
        ()=>{
            this.addEvent();
        }
    );
    }
    else{
        this.addEvent();
    }
}

randomIntFromInterval() {
    return Math.floor((Math.random() * 50000) + 1);
  }

}
