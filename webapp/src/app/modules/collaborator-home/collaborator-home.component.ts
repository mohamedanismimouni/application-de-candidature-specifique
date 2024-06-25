import { Component, OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';

import { RequestStatusEnum } from 'src/app/enumerations/request-status.enum';
import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { IDocumentRequest } from 'src/app/models/document-request';
import { IDocumentResuestsStatics } from 'src/app/models/DocumentResuestsStatics.model';
import { ITeam } from 'src/app/models/team.model';
import { DocumentRequestServiceService } from 'src/app/services/document-request-service.service';
 import { StorageService } from 'src/app/services/storage.service';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-collaborator-home',
  templateUrl: './collaborator-home.component.html',
  styleUrls: ['./collaborator-home.component.scss']
})

export class CollaboratorHomeComponent implements OnInit {
 
  loading=true
  products: any[]=[];
  requests: IDocumentRequest[];
  inwaiting={requestStatus:"", sum: 0};
  validated={requestStatus:"", sum: 0};
  received={requestStatus:"", sum: 0};
  l2={requestStatus:"", sum: 0};
  team:any
  fistLetters:string;
  inwaitingRequestsNumber: number=0;
  validatedRequestsNumber: number=0;
  receivedRequestsNumber: number=0;
  collaboratorInfotmation:any;
  object={requestStatus:"", sum: 0};

list=[
{requestStatus:"2", sum: 3},
{requestStatus:"3", sum: 4}]

responsiveOptions;
  
staticsList: IDocumentResuestsStatics[];
responsePlaceholder : string=""
  constructor(private documentService: DocumentRequestServiceService ,
              private storageService: StorageService  ) {
                this.responsiveOptions = [
                  {
                      breakpoint: '1024px',
                      numVisible: 3,
                      numScroll: 3
                  },
                  {
                      breakpoint: '768px',
                      numVisible: 2,
                      numScroll: 2
                  },
                  {
                      breakpoint: '560px',
                      numVisible: 1,
                      numScroll: 1
                  }
              ];
   }
   
currentUser: any;
 
ngOnInit(): void {
  
    this.carouselStatics();
    this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
   
    
  }
 
   carouselStatics(){
    this.documentService.getCollabsRequestsStatics(this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe(
      (data: IDocumentResuestsStatics[]) => {
       this.staticsList=data;
       this.loading=false
   });
  }
}
