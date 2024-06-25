import { Component, OnInit } from '@angular/core';
import {CandidacyService} from '../../service/candidacy.service';
import {MenuItem, MessageService} from 'primeng/api';
import {Subscription} from 'rxjs';
import {THEME_DEFAULT} from '../../../constants/common.constant';
import {NbThemeService} from '@nebular/theme';

@Component({
  selector: 'app-candidacy-steps',
  templateUrl: './candidacy-steps.component.html',
  styleUrls: ['./candidacy-steps.component.scss']
})
export class CandidacyStepsComponent implements OnInit {

    themeSubscription: Subscription;
    currentTheme: string;


    items!: MenuItem[];

    subscription!: Subscription;

    constructor(public messageService: MessageService, public ticketService: CandidacyService, private themeService: NbThemeService) {}

    ngOnInit() {
        this.items = [{
            label: 'Selectionner les offres',
            routerLink: 'offerSelection',


        },
            {
                label: 'Attacher votre CV',
                routerLink: 'CvUpload'
            },
            {
                label: 'Introduire vos  informations personnelles',
                routerLink: 'GeneralInformation'
            },
            {
                label: 'Valider votre Candidature',
                routerLink: 'Validation'
            }
        ];
        this.subscription = this.ticketService.paymentComplete$.subscribe((personalInformation) => {
            this.messageService.add({severity: 'success', summary: 'Order submitted', detail: 'Dear, ' + personalInformation.firstname + ' ' + personalInformation.lastname + ' your order completed.'});
        });
        this.themeSubscription = this.themeService.getJsTheme().subscribe(
            (themeOptions) => this.currentTheme = themeOptions.name,
            () => this.currentTheme = THEME_DEFAULT);


    }

    // tslint:disable-next-line:use-lifecycle-interface
    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }
}
