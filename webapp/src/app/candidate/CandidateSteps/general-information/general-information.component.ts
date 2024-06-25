import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-general-information',
  templateUrl: './general-information.component.html',
  styleUrls: ['./general-information.component.scss']
})
export class GeneralInformationComponent implements OnInit {

    value?: Array<string> = JSON.parse(localStorage.skills);
    firstName = '';
    lastName = '';
    email?: string = localStorage.getItem('email');
    phoneNumber = localStorage.getItem('phone');
    male?: string;

  constructor(private router: Router) { }

  ngOnInit(): void {

  }
    nextPage() {
        this.router.navigate(['candidacy/Validation']);
        localStorage.setItem('skills', JSON.stringify(this.value));
        localStorage.setItem('firstName', this.firstName);
        localStorage.setItem('lastName', this.lastName);
        localStorage.setItem('phoneNumber', JSON.stringify(this.phoneNumber));
        localStorage.setItem('email', this.email);

    }

    prevPage() {
        this.router.navigate(['candidacy/CvUpload']);
    }

}
