import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
    selector: 'app-last-step',
    templateUrl: './last-step.component.html',
    styleUrls: ['./last-step.component.scss']
})
export class LastStepComponent implements OnInit {

    selectedOffers?: any = JSON.parse(localStorage.getItem('selectedOffers'));
    constructor(private router : Router) { }

    ngOnInit(): void {
    }
    goHome(){
        this.router.navigate(['/process']);
    }

}
