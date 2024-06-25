import { Component, OnInit } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { ToastrService } from 'src/app/services/toastr.service';

@Component({
  selector: 'app-cancel-document-request',
  templateUrl: './cancel-document-request.component.html',
  styleUrls: ['./cancel-document-request.component.scss']
})
export class CancelDocumentRequestComponent implements OnInit {

  constructor(   private toastrService: ToastrService,  
                 private dialogRef: NbDialogRef<CancelDocumentRequestComponent>) { }

  ngOnInit(): void {
    
  }

    onCancelDialog() {
      this.dialogRef.close(false);

    }

    onCancelRequest() {
    this.dialogRef.close(true);
      }
  

}
