import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {DragDropModule} from 'primeng/dragdrop';
import {Observable} from 'rxjs';
import {IOffers} from '../class/ioffers';
import {ConfirmationService, ConfirmEventType, MessageService} from 'primeng/api';
import {Router} from '@angular/router';


@Component({
  selector: 'app-offer-select',
  templateUrl: './offer-select.component.html',
  styleUrls: ['./offer-select.component.scss']
})
export class OfferSelectComponent implements OnInit {
    visibleSidebar2;
    public departments?: string[];
   public selectedDepartment = '';
   public  availableOffers?: IOffers[] ;
   public selectedOffers?: IOffers[] = [];
   public draggedOffers?: IOffers;
  constructor(private confirmationService: ConfirmationService,private http: HttpClient , public messageService: MessageService, private router: Router) { }

  ngOnInit(): void {
      this.getAllDepartment().subscribe((data) => {this.departments = data; });
      this.getAllOffer().subscribe((data) => {this.availableOffers = data; });
      console.log(this.availableOffers);
  }
  getByDepartment(): void {
        this.http.get<Array<IOffers>>('http://localhost:8081/offer/ByDepartment/' + this.selectedDepartment)
            .subscribe((data) => {this.availableOffers = data; });
  }
  getAllDepartment(): Observable<Array<string>> {
      return this.http.get<Array<string>>('http://localhost:8081/offer/AllDepartment');
  }
  getAllOffer(): Observable<Array<IOffers>> {
    return this.http.get<Array<IOffers>>('http://localhost:8081/offer/allOffers');
  }
    dragStart(event ,  offer: IOffers) {
        this.draggedOffers = offer;
    }

    drop(event) {

      if (this.draggedOffers) {
            const draggedOfferIndex = this.findIndex(this.draggedOffers);
            console.log(this.selectedOffers);
            this.selectedOffers = [...this.selectedOffers, this.draggedOffers];
            // tslint:disable-next-line:triple-equals
            this.availableOffers = this.availableOffers.filter((val, i) => i != draggedOfferIndex);
            this.draggedOffers = null;
        }
    }

    dragEnd(event) {
        this.draggedOffers = null;
    }

    findIndex(offer: IOffers) {
        let index = -1;
        for (let i = 0; i < this.availableOffers.length; i++) {
            if (offer.id === this.availableOffers[i].id) {
                index = i;
                break;
            }
        }
        return index;
    }
    removeOffer(offer: IOffers) {
        let index = -1;
        for (let i = 0; i < this.selectedOffers.length; i++) {
            if (offer.id === this.selectedOffers[i].id) {
                index = i;
                break;
            }
        }
        this.availableOffers = [...this.availableOffers, offer];
        // tslint:disable-next-line:triple-equals
        this.selectedOffers = this.selectedOffers.filter((val, i) => i != index);
    }
    showResponse(event) {
        this.messageService.add({severity: 'info', summary: 'Succees', detail: 'User Responded', sticky: true});
    }
    nextPage() {
      if(this.selectedOffers.length===0){
      
        this.confirmationService.confirm({
            message: '  Vous n avez pas selectionner des offres donc votre candidature sera considérée comme spontannée',
            header: 'Confirmation',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'oui',
            rejectLabel: 'non',
            accept: () => {
                this.router.navigate(['candidacy/CvUpload']);
        localStorage.setItem('selectedOffers', JSON.stringify(this.selectedOffers) );
            },
            reject: (type) => {
                switch (type) {
                    case ConfirmEventType.REJECT:
                        this.messageService.add({severity: 'error', summary: 'Rejected', detail: 'You have rejected'});
                        break;
                    case ConfirmEventType.CANCEL:
                        this.messageService.add({severity: 'warn', summary: 'Cancelled', detail: 'You have cancelled'});
                        break;
                }
            }
        });

    }

    else{
        this.router.navigate(['candidacy/CvUpload']);
        localStorage.setItem('selectedOffers', JSON.stringify(this.selectedOffers) ); 
    }
   
   
    }

}
