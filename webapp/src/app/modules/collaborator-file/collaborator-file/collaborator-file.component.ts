import { Component, OnInit } from '@angular/core';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { IFunction } from 'src/app/models/function.model';
import { CollaboratorService } from 'src/app/services/collaborator.service';
import { FunctionService } from 'src/app/services/function.service';
import { ToastrService } from 'src/app/services/toastr.service';

@Component({
  selector: 'app-collaborator-file',
  templateUrl: './collaborator-file.component.html',
  styleUrls: ['./collaborator-file.component.scss']
})
export class CollaboratorFileComponent implements OnInit {
  config: any;
  page:any;
  keyword :string;
  search = false;
  collabs: Array<ICollaborator> = [];
  loadingState = false;
  selectedCollab= false;
  functions: Array<IFunction> = [];
  selectedFunction : IFunction;
  loading=true;
  isEditing: boolean = false;
  enableEditIndex = null;
  closeDropDown: boolean = false

  
constructor(
private collabService: CollaboratorService, 
private toastrService: ToastrService,
private functionService: FunctionService
) {
  this.config = {
    itemsPerPage: 10,
    currentPage: 1,
    totalItems: this.collabs.length
  };
}

ngOnInit() {
  this.loadFunctions();
   this.loadCollabs();
}

pageChanged(event) {
  this.config.currentPage = event;
  this.closeDropDown=true;
  this.selectedFunction=null;
}
loadCollabs() {
     this.collabService.getCollabs().subscribe(
      (collabs: ICollaborator[]) => {
          this.collabs = collabs;
          this.loading=false
     
      });
}



toggleSearch() {
  this.search = !this.search;
   if (!this.search) {
      this.keyword = '';
  }
}

synchronizeCollaborators()
{
  this.loadingState = true;
  this.collabService.synchroCollabs().subscribe({

    next :(collabs: ICollaborator[]) => {
        this.collabs = collabs;
      },
    complete:()=>{
      this.loadingState = false;
      this.toastrService.showStatusToastr(
        'synchronization done successfully',
        ToastrStatusEnum.SUCCESS);
    }

  });
}

loadFunctions():IFunction[]{
  this.functionService.getFunctions().subscribe(
    (f) => {
        this.functions = f;
    }

);
return this.functions

}

switchEditMode(i) {
  this.isEditing = true;
  this.enableEditIndex = i;
  this.closeDropDown=false;
  
}
cancel(){
  this.selectedFunction=null;
  this.closeDropDown = true
}
onSubmit(collabId, libelle){
  //this.selectedCollab=true;
   this.collabService.updateCollabFunction(collabId, libelle).subscribe(
    () => {
           this.toastrService.showStatusToastr(
            'Collaborator\'s function updated successfully',
            ToastrStatusEnum.SUCCESS);
            this.loadCollabs();
            this.isEditing = false;
            this.selectedFunction=null;
            this.closeDropDown = true;
            
    },
    (error: IErrorResponse) => {
      this.toastrService.showStatusToastr(
        'This collaborator\'s function cannot be updated',
        ToastrStatusEnum.DANGER);
    }
); 
}
}
