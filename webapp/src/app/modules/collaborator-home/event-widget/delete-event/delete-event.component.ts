import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { IEvent } from 'src/app/models/event.model';
import { EventService } from 'src/app/services/event.service';
import { FormService } from 'src/app/services/form.service';
import { ToastrService } from 'src/app/services/toastr.service';

@Component({
  selector: 'app-delete-event',
  templateUrl: './delete-event.component.html',
  styleUrls: ['./delete-event.component.scss']
})
export class DeleteEventComponent implements OnInit {
  @Input()
  data: IEvent ;
  upcomingEvents: IEvent[] = [];
  loadingState=false;

  documentRequestFormGroup: FormGroup = this.formBuilder.group({
  
    rejectionMotif:  [ '', [Validators.required,Validators.maxLength(255),this.noWhitespaceValidator]]

    });
  constructor(private formBuilder: FormBuilder,
              private dialogRef: NbDialogRef<DeleteEventComponent>,
              public formService: FormService,
              private eventService: EventService,
              private toastrService: ToastrService) { }

  ngOnInit(): void { 
    if(this.data.collaborators.length>0) {
      this.documentRequestFormGroup.get('rejectionMotif').setValidators([Validators.required,Validators.maxLength(255),this.noWhitespaceValidator]);
    } else {
      this.documentRequestFormGroup.get('rejectionMotif').setValidators(Validators.maxLength(255));
    }
    
  }

  onCancel() {
    this.dialogRef.close(false);
  }

    onSubmit(){
      this.loadingState=true;
      this.data.canceledMotif=this.documentRequestFormGroup.controls["rejectionMotif"].value;
      this.eventService.deleteEvent(this.data.id, this.documentRequestFormGroup.controls["rejectionMotif"].value).subscribe(
        () => {
            this.toastrService.showStatusToastr(
                " Event deleted successfully",
                ToastrStatusEnum.SUCCESS
            );
            //this.getUpcomingEvents();
            this.dialogRef.close(false);
            
           // this.reloadScore();
        },
        (error: IErrorResponse) => {
            this.toastrService.showStatusToastr(
                " Event connot be deleted'",
                ToastrStatusEnum.DANGER
            );
        }
    );
    }
  noWhitespaceValidator(control: FormControl) {
    const isWhitespace = (control && control.value && control.value.toString() || '').trim().length === 0;
    const isValid = !isWhitespace;
    return isValid ? null : { 'whitespace': true };
  }
}
