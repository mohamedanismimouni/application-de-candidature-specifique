import { Component, OnInit } from '@angular/core';
import {TestService} from "../../candidate/service/test.service";
import {JobServiceService} from "../../candidate/service/job-service.service";
import {IOffers} from "../../candidate/class/ioffers";
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.scss']
})
export class JobsComponent implements OnInit {

    offers?: IOffers[];
    offerDialog: boolean;
    offer?: IOffers;
    submitted?: boolean;
    statuses = [];
      
  
   
  constructor(private jobService: JobServiceService,private messageService: MessageService,private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
      this.jobService.getAllJobs().subscribe((offers) => {
            this.offers = offers;
            this.statuses = [
              {label: 'WEB', value: 'WEB'},
              {label: 'IA', value: 'IA'},
              {label: 'CLOUD', value: 'CLOUD'}
          ];
      });
  }
    editProduct(offer:IOffers) {
      this.offer = {...offer};
      this.offerDialog = true;
      

    }




   saveProduct() {
      this.submitted = true;
         
      if (this.offer.reference.trim()) {
          if (this.offer.id) {
           this.jobService.updateJob(this.offer).subscribe((data) => {
              this.offer = data;
              console.log(this.offer)
             this.offers[this.findIndexById(this.offer.id)] = this.offer;
             console.log(this.offer)
              this.messageService.add({severity:'success', summary: 'Successful', detail: 'Job Updated', life: 3000});
            });
            }
          else {
              this.offer.id = this.createId();
              console.log(this.offer)
               this.jobService.saveJob(this.offer).subscribe((data) => {
                this.offer = data;
                this.offers.push(this.offer);
                this.messageService.add({severity:'success', summary: 'Successful', detail: 'Job Created', life: 3000});
            });
             
          }

          this.offers = [...this.offers];
          this.offerDialog = false;
          this.offer = {};
      }
  }




  findIndexById(id: number): number {
    let index = -1;
    for (let i = 0; i < this.offers.length; i++) {
        if (this.offers[i].id === id) {
            index = i;
            break;
        }
    }

    return index;
}

createId(): number {
    let id = '';
    var chars = '123456789123456789';
    for ( var i = 0; i < 5; i++ ) {
        id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return parseInt(id);
}



deleteProduct(offer:IOffers) {
  let idOffer=offer.id
  this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + offer.reference + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        
        
          this.offers = this.offers.filter(val => val.id !== offer.id);
          console.log(this.offers)
          this.jobService.deleteJob(idOffer).subscribe(
            data=>{
              console.log(data)
            
          });
         
          this.offer = {};
          this.messageService.add({severity:'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
         
    
         
         
     
        }

  });
 
}


openNew() {
  this.offer = {};
  this.submitted = false;
  this.offerDialog = true;
}


hideDialog() {
  this.offerDialog = false;
  this.submitted = false;
}

}
