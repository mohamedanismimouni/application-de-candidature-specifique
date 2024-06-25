import { Component, OnInit } from '@angular/core';
import {IOffers} from '../../class/ioffers';
import {Skill} from '../../class/skill';
import {Candidate} from '../../class/candidate';
import {HttpClient} from '@angular/common/http';
import {MessageService} from 'primeng/api';
import {Router} from '@angular/router';
import { byte } from '@zxing/library/esm/customTypings';
import { Text } from '@angular/compiler';

@Component({
  selector: 'app-offer-selection',
  templateUrl: './offer-selection.component.html',
  styleUrls: ['./offer-selection.component.scss']
})
export class OfferSelectionComponent implements OnInit {
    selectedOffers?: Array<IOffers> = JSON.parse(localStorage.selectedOffers);
    sk?: Array<string> = JSON.parse(localStorage.skills);
    skills?: Array<Skill> = [];
    firstName?: string = localStorage.getItem('firstName');
    lastName?: string = localStorage.getItem('lastName');
    phoneNumber?: number = JSON.parse(localStorage.getItem('phoneNumber'));
    email?: string = localStorage.getItem('email');
    idCv?: string = localStorage.getItem('id_cv');
    image?: string = localStorage.getItem('img');


  constructor(private http: HttpClient, private messageService: MessageService , private router: Router) { }

  ngOnInit(): void {}

  saveCandidate() {
      for (let i = 0 ; i <= this.sk.length; i++) {
          const skill: Skill = {
              skillName: this.sk[i]
          };
          this.skills.push(skill);
      }
      const candidate: Candidate = {
            firstName: this.firstName,
            lastName: this.lastName,
            phoneNumber: this.phoneNumber,
            email:   this.email,
            id_cv: this.idCv,
            candidateSkills: this.skills,
            offerEntities: this.selectedOffers,
            candidateImg: this.image

       };
      this.http.post('http://localhost:8081/candidate', candidate).subscribe(Response => {
        console.log(candidate.candidateImg);

      });

      this.messageService.add({severity: 'success', summary: 'Félicitations', detail: 'Votre candidature a été envoyé avec succées '});
      this.router.navigate(['lastStep']);


  }

}
