import { Component, OnDestroy, OnInit } from '@angular/core';
import {CandidacyService} from '../service/candidacy.service';
import {Candidate} from '../class/candidate';
import {HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';
import {ResultDetailsComponent} from '../../candidate/result-details/result-details.component';
import {PrimeIcons} from 'primeng/api';


@Component({
  selector: 'app-candidate-details',
  templateUrl: './candidate-details.component.html',
  styleUrls: ['./candidate-details.component.scss']
})
export class CandidateDetailsComponent implements OnInit,OnDestroy {
    public candidates: Array<Candidate> = [];
    public candidat: Candidate = {firstName: '',
        lastName: '',
        email: '',
        phoneNumber: null,
        candidateSkills: [],
        offerEntities: [],
        dateNaissance: null,
        candidateImg: '',
        emailSecondaire: ''
    };
    candidateId?: number;
    ref: DynamicDialogRef;

    events: any[];
    

    constructor(private candidacyService: CandidacyService,private dialogService: DialogService, private route: ActivatedRoute,private router:Router,) { }

    ngOnInit(): void {
        this.candidateId = this.route.snapshot.params.id;
        this.Onclick()
 
        this.events = [
            {status: 'A qualifier ',date: '15/10/2020', icon: PrimeIcons.SHOPPING_CART, color: '#9C27B0'},
            {status: 'En cours',date: '15/10/2020', icon: PrimeIcons.SPINNER, color: '#673AB7'},
            {status: 'Désistement',date: '15/10/2020',icon: PrimeIcons.LOCK, color: '#FF9800'},
            {status: 'accepté',date: '15/10/2020', icon: PrimeIcons.THUMBS_UP, color: '#607D8B'},
            {status: 'rejeté',date: '15/10/2020', icon: PrimeIcons.THUMBS_DOWN, color: '#607D8B'}
        ];

    }
    Onclick() {

        this.candidacyService.getCandidateById(this.candidateId).subscribe((response: Candidate) => {
            this.candidat = response;
           this.candidat.candidateSkills.pop();
            
            ; }

        );


    }

   

    update() {
       
      this.candidacyService.updateCandidate(this.candidat).subscribe((response: Candidate) => {
                this.candidat = response;
                this.Onclick();         
    const element1:HTMLElement|null=document.getElementById('shadow');
    const element2:HTMLElement|null=document.getElementById('shadow2');
    element2.style.display='none'
    element1.style.display='block'
            },
            (error: HttpErrorResponse) => {alert(error.message); }
        );
    }


   


play(){
    const element1:HTMLElement|null=document.getElementById('1');
    const element2:HTMLElement|null=document.getElementById('2');
    const element3:HTMLElement|null=document.getElementById('3');
    const element4:HTMLElement|null=document.getElementById('4');
    
      element1.classList.add('nope-anim');
      element2.classList.add('nope-anim');
      element3.classList.add('nope-anim');
      element4.classList.add('nope-anim');
    }


    move(){
        const element1:HTMLElement|null=document.getElementById('carousel1');
        
          element1.style.display='flex'
         
        }

    moveWorkflow(){
            const element1:HTMLElement|null=document.getElementById('workflowCard');
            
              element1.style.display='flex'
             
            }

        show(idTest: number) {
            const element1:HTMLElement|null=document.getElementById('carousel1');
            element1.style.display='none'
            this.ref = this.dialogService.open(ResultDetailsComponent, {
                data: {
                    id: idTest
                },
                header: 'Quiz Details',
                width: '60%',
                
                style:{'position': 'relative'},
                contentStyle: {'max-height': '500px', 'overflow': 'auto'},
                baseZIndex: 10000
            });
        }


        ngOnDestroy() {
            if (this.ref) {
                this.ref.close();
            }
        }



        moveInput(){
            
    const element1:HTMLElement|null=document.getElementById('shadow');
    const element2:HTMLElement|null=document.getElementById('shadow2');
      element2.style.display='flex'
      element1.style.display='none'
        
              
              
            }
}

