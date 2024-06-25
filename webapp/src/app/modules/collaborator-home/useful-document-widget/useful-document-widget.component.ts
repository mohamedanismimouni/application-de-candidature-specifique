import { Component, OnInit } from '@angular/core';
import { PDF_EXTENSION } from 'src/app/constants/common.constant';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { IImagePDF } from 'src/app/models/image-PDF.model';
import { IUsefulDocument } from 'src/app/models/useful-document.model';
import { EDMService } from 'src/app/services/EDM.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { UsefulDocumentService } from 'src/app/services/useful-document.service';
import { downloadFile } from '../../shared/EDM/DownloadFile';

@Component({
  selector: 'app-useful-document-widget',
  templateUrl: './useful-document-widget.component.html',
  styleUrls: ['./useful-document-widget.component.scss']
})
export class UsefulDocumentWidgetComponent implements OnInit {
  usefulDocumentList: IUsefulDocument[]= [];
  responsiveOptions;
  imageOfPDFDocList: IImagePDF[] = [];
  loading=true
  constructor(private usefulDocumentService: UsefulDocumentService,
    private toastrService: ToastrService,
    private EDMService: EDMService) { }

  ngOnInit(): void {
    this.loadUsefulDocuments();
    this.loadImages(); 
  }

    
  loadUsefulDocuments() {
    this.usefulDocumentService
        .getUsefulDocument()
        .subscribe((usefulDocument: IUsefulDocument[]) => {
            this.usefulDocumentList = usefulDocument;
            this.loading=false
           
        });
}
loadImages(){
  this.usefulDocumentService.getImg().subscribe((usefulDocument: IImagePDF[]) => {
    this.imageOfPDFDocList= usefulDocument;
    } );   
  } 
downloadUsefulDocument(imageOfPDFDoc: IImagePDF) {
  this.EDMService.downloadFile(imageOfPDFDoc.idEDM).subscribe(
      (blobFile: Blob) => {
          downloadFile(blobFile, imageOfPDFDoc.label +PDF_EXTENSION);
      },
      (error: IErrorResponse) => {
          this.toastrService.showStatusToastr(
              " 'this document connot be downloaded'",
              ToastrStatusEnum.DANGER
          );},
  );
}
}
