import { Component, OnInit, Input  } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { IDocumentRequest } from 'src/app/models/document-request';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';
import { FormService } from 'src/app/services/form.service';
import { ToastrService } from 'src/app/services/toastr.service';
@Component({
  selector: 'app-document-request-rejection',
  templateUrl: './document-request-rejection.component.html',
  styleUrls: ['./document-request-rejection.component.scss']
})
export class DocumentRequestRejectionComponent implements OnInit {
  @Input()
  data: IDocumentRequest;
  loadingState=false;
  documentRequestFormGroup: FormGroup = this.formBuilder.group({
  rejectionMotif:  [ '', [Validators.required,Validators.maxLength(255),this.noWhitespaceValidator]],
  });

    constructor(private formBuilder: FormBuilder,
      public formService: FormService,
      private dialogRef: NbDialogRef<DocumentRequestRejectionComponent>,
      private toastrService: ToastrService,
      private documentRequestService: DocumentRequestServiceService
  ) { }


  ngOnInit(): void {
  }
 

  onCancel() {
    this.dialogRef.close(false);
  }

    onSubmit(){
      this.loadingState=true
      this.data.rejectionMotif=this.documentRequestFormGroup.controls["rejectionMotif"].value
       this.documentRequestService.documentRequestsRejection(JSON.parse(localStorage.getItem('current-user')).id,this.data).subscribe(
        () => {
         this.toastrService.showStatusToastr(
              'Document Request of '+this.data.collaborator.firstName + '  '+ this.data.collaborator.lastName + '  '+ 'is Rejected',
              ToastrStatusEnum.SUCCESS);
             this.dialogRef.close(true);
             this.loadingState=false
        },
        (error: IErrorResponse) => {
          this.toastrService.showStatusToastr(
            'Something is not right, please verify '
            + 'your input and try again later',
            ToastrStatusEnum.DANGER);
            this.loadingState=false

        }
    );
    }

    noWhitespaceValidator(control: FormControl) {
      const isWhitespace = (control && control.value && control.value.toString() || '').trim().length === 0;
      const isValid = !isWhitespace;
      return isValid ? null : { 'whitespace': true };
    }

}
