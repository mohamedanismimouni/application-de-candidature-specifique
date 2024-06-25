import { Component, OnInit } from '@angular/core';
import { IUsefulLink } from 'src/app/models/usefulLink.model';
import { UsefulLinkService } from 'src/app/services/usefulLink.service';

@Component({
  selector: 'app-useful-link-widget',
  templateUrl: './useful-link-widget.component.html',
  styleUrls: ['./useful-link-widget.component.scss']
})
export class UsefulLinkWidgetComponent implements OnInit {
  usefulLinksList: IUsefulLink[]= [];
  loading=true
    constructor(private usefulLinkService:UsefulLinkService) {

   }

  ngOnInit(): void {   
  this.loadUsefulLinks();    
  }   
  
  /**
   * load all useful links
   */
  loadUsefulLinks() {
    this.usefulLinkService.getUsefulLinks().subscribe((usefulusefulLink: IUsefulLink[])=>{
      this.usefulLinksList=usefulusefulLink;  
      this.loading=false
        
    });
   
}

}
