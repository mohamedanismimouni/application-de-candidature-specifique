import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import * as moment from 'moment';
import { DATE_STORAGE_FORMAT } from 'src/app/constants/date-time-formats.constant';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { IDocument } from 'src/app/models/document.model';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { IEvent } from 'src/app/models/event.model';
import { EDMService } from 'src/app/services/EDM.service';
import { EventService } from 'src/app/services/event.service';
import { FormService } from 'src/app/services/form.service';
import { ToastrService } from 'src/app/services/toastr.service';

@Component({
  selector: 'app-update-event',
  templateUrl: './update-event.component.html',
  styleUrls: ['./update-event.component.scss']
})
export class UpdateEventComponent implements OnInit {

  @Input()
  data: IEvent ;
  currentFileUpload: File;
  uploadedFiles: any[] = [];
  loadingState = false;
  WithOrWithoutMoney:string;
  isPaid = false;
    eventType = '' ;

    today = new Date (moment().subtract(1, 'days').format("YYYY-MM-DD"));
  updateEventFormGroup: FormGroup = this.formBuilder.group({
        title:                  [ '', Validators.required ],
        date:                   [ '', Validators.required ],
        location:               [ '', Validators.required ],
        originLink:             [ '' ],
        idEDMImage:             [ '' ],
        imageExtension:         [ '' ],
        numberMaxParticipation: [ '' ],
        type:                   [ ''],
        eventPrice:             [ '' ]
});
  constructor(private formBuilder: FormBuilder,
    private dialogRef: NbDialogRef<UpdateEventComponent>,
    public formService: FormService,
    private eventService: EventService,
    private toastrService: ToastrService,
    private EDMService: EDMService) { }

ngOnInit(): void {
  

}
onCancel() {
    this.dialogRef.close();
}

onUpload(event) {
  for(let file of event.files) {
      this.uploadedFiles.push(file);
  }
}


onTypeSelect(type: string){

  if (type === "Gratuit") {
      this.isPaid = false;
      this.updateEventFormGroup.controls['type'].setValue('Gratuit');
      this.updateEventFormGroup.get('eventPrice').clearValidators();
  }
  else {
      this.updateEventFormGroup.controls['type'].setValue('Payant');
      //this.updateEventFormGroup.get('eventPrice').setValidators([Validators.required,Validators.pattern(/^[1-9]*$/)]);
      this.updateEventFormGroup.get('eventPrice').setValidators([Validators.required,Validators.pattern(/^([1-9][0-9]*)$/)]);
      this.isPaid = true;
  };
}

updateEvent() {
     this.loadingState = true;
    if(this.updateEventFormGroup.get('type').value==null || this.updateEventFormGroup.get('type').value==""){
          this.updateEventFormGroup.controls['type'].setValue(this.data.type)
    }
    else if(this.updateEventFormGroup.get('type').value=='Gratuit'){
      this.updateEventFormGroup.controls['eventPrice'].setValue(0.0)
    }
    else{ 
      this.updateEventFormGroup.controls['type'].setValue(this.updateEventFormGroup.get('type').value);
    }
    
     this.updateEventFormGroup.controls['date'].setValue( new Date(moment(this.updateEventFormGroup.get('date').value).format(DATE_STORAGE_FORMAT)));
    this.eventService.updateEvent(this.updateEventFormGroup.value, this.data.id).subscribe(() => {
        this.loadingState = false;
        this.toastrService.showStatusToastr(
            'Event created successfully,' + this.data+
            ' points will be added to your score',
            ToastrStatusEnum.SUCCESS);
        this.isPaid = false;
        this.dialogRef.close(true);
        
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
updateEventWithImage() {


  this.currentFileUpload = this.uploadedFiles[this.uploadedFiles.length-1];
  if(this.currentFileUpload!=null){
  this.EDMService.uploadFile(
      this.currentFileUpload,
      JSON.parse(localStorage.getItem("current-user")).id,
      this.randomIntFromInterval().toString(),
      JSON.parse(localStorage.getItem("current-user")).id
  ).subscribe(
      (document: IDocument) => {
          this.updateEventFormGroup.controls['idEDMImage'].setValue(document.id);
          this.updateEventFormGroup.controls['imageExtension'].setValue(this.uploadedFiles[0].name.split('.').pop());
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
          this.updateEvent();
      }
  );
  }
  else{
      this.updateEvent();
  }
}

randomIntFromInterval() {
  return Math.floor((Math.random() * 50000) + 1);
}

}

