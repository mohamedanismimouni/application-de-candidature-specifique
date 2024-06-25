import { Component, Input, OnInit } from "@angular/core";
import { NbDialogRef } from "@nebular/theme";
import { ICollaborator } from "src/app/models/collaborator.model";
import { IEvent } from "src/app/models/event.model";

@Component({
    selector: "app-participants-list",
    templateUrl: "./participants-list.component.html",
    styleUrls: ["./participants-list.component.scss"],
})
export class ParticipantsListComponent implements OnInit {
    @Input()
    event: IEvent;
    config: any;
    page: any;
    keyword :string;
    search = false;

    currentUser: ICollaborator;

    constructor(private dialogRef: NbDialogRef<ParticipantsListComponent>) {
        
    }

    ngOnInit(): void {this.config = {
      itemsPerPage:4,
      currentPage: 1,
      totalItems:this.event.collaborators?.length
  };}
    /**
     * close dialog
     */
    onCancel() {
        this.dialogRef.close();
    }
    pageChanged(event) {
        this.config.currentPage = event;
    }

    
toggleSearch() {
  this.search = !this.search;
   if (!this.search) {
      this.keyword = '';
  }
}
}
