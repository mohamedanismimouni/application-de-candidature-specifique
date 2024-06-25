import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';

import { FormService } from 'src/app/services/form.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { blankValidator } from 'src/app/utils/validators.util';

@Component({
  selector: 'app-add-document-request',
  templateUrl: './add-document-request.component.html',
  styleUrls: ['./add-document-request.component.scss']
})
export class AddDocumentRequestComponent implements OnInit {

  @Input()
  data: any;

  documentRequestFormGroup: FormGroup = this.formBuilder.group({
  requestMotif:['', [Validators.maxLength(255)]]
});

  curentUserId: string;
  loadingState = false;

  constructor(private formBuilder: FormBuilder,
    public formService: FormService,
    private dialogRef: NbDialogRef<AddDocumentRequestComponent>,
    private toastrService: ToastrService,
    private documentRequestService: DocumentRequestServiceService
) { }

 ngOnInit(): void {
}

onCancel() {
this.dialogRef.close(false);
}

onSubmit(){
  this.loadingState = true;
    this.documentRequestService.sendDocumentRequest(JSON.parse(localStorage.getItem('current-user')).id, JSON.parse(localStorage.getItem('current-user')).id, this.data.label, this.documentRequestFormGroup.controls["requestMotif"].value, false).subscribe(    () => {
      this.toastrService.showStatusToastr(
          'Your certificate Request has been successfully registered',
          ToastrStatusEnum.SUCCESS);
         this.dialogRef.close(true);

    },
    (error: IErrorResponse) => {
      this.loadingState = false;
      this.toastrService.showStatusToastr(
        'Something is not right, please verify '
        + 'your input and try again later',
        ToastrStatusEnum.DANGER);

    }
);
}

}
