import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TestService} from '../service/test.service';
import {Test} from '../class/test';
import {ConfirmationService, ConfirmEventType, MessageService} from 'primeng/api';
import {Candidate} from '../class/candidate';
import {PrmTechnology} from '../class/prm-technology';

@Component({
  selector: 'app-quiz-home-page',
  templateUrl: './quiz-home-page.component.html',
  styleUrls: ['./quiz-home-page.component.scss']
})
export class QuizHomePageComponent implements OnInit {

    test?: Test;
    candidate?: Candidate;

    idTest?: number;
    technologies?: Array<PrmTechnology>;
    constructor(private testService: TestService, private route: ActivatedRoute, private router: Router , private messageService: MessageService, private confirmationService: ConfirmationService) {
    }
    ngOnInit(): void {
        this.idTest = this.route.snapshot.params.id;
        this.testService.getTestById(this.idTest).subscribe((data) => {
                this.test = data;
                this.testService.getCandidateByTestId(this.idTest).subscribe((candidate) => {
                this.candidate = candidate;
            });
        });
        this.testService.getTechnolgiesByTest(this.idTest).subscribe((technologies) => {
                this.technologies = technologies;
        });
    }
    confirm() {
        this.confirmationService.confirm({
            message: '  Vous avez une seule chance de passer le Quiz, vous êtes sur de vouloir continuer ?',
            header: 'Confirmation',
            icon: 'pi pi-exclamation-triangle',
            acceptLabel: 'oui',
            rejectLabel: 'non',
            accept: () => {
                this.messageService.add({severity: 'info', summary: 'Confirmed', detail: 'You have accepted'});
                this.router.navigate(['/quiz/' + this.idTest]);
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

}
