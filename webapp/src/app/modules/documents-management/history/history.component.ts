import { Component, OnInit, ViewChild } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import { FilterMetadata } from 'primeng/api';
import { Table } from 'primeng/table';
import { IDocumentRequest } from 'src/app/models/document-request';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';
import { EDMService } from 'src/app/services/EDM.service';
import { downloadFile } from '../../shared/EDM/DownloadFile';
import { WORD_EXTENSION } from 'src/app/constants/common.constant';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {
  @ViewChild('dt', { static: true })
  dt: Table;
  filters: { [s: string]: FilterMetadata | FilterMetadata[]; };
  first = 0;
  rows = 5;
  search = false;
  loading=true
  documentRequestsList: Array<IDocumentRequest> = [];
  document = '';
  constructor(private documentRequestService: DocumentRequestServiceService,private dialogService: NbDialogService,
    private EDMService: EDMService) {
  }
  ngOnInit(): void {

    this.loadProcessedDocuemntsRequest()
  }

  loadProcessedDocuemntsRequest() {
    this.documentRequestService.getProcessedRequests().subscribe(
      (documentrequests: IDocumentRequest[]) => {
        this.documentRequestsList = documentrequests;
      this.loading=false
   
      }
    );
  }
  next() {
    this.first = this.first + this.rows;
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
  }

  isLastPage(): boolean {
    return this.documentRequestsList ? this.first === (this.documentRequestsList.length - this.rows) : true;
  }

  isFirstPage(): boolean {
    return this.documentRequestsList ? this.first === 0 : true;
  }





  toggleSearch() {
    this.search = !this.search;
     if (!this.search) {
      this.dt.filterGlobal("", 'contains');
    }
  }

  applyFilterGlobal($event, stringVal) {
    this.dt.filterGlobal(($event.target as HTMLInputElement).value, 'contains');
  }

  downloadTemplate(documentRequest: IDocumentRequest) {


    this.EDMService.downloadFile(documentRequest.idEDM).subscribe(
            (blobFile: Blob) => {
                this.document = downloadFile(
                    blobFile,
                    documentRequest.collaborator.firstName+"_"+documentRequest.type.label+WORD_EXTENSION
                );
            }
        );
    }
}
