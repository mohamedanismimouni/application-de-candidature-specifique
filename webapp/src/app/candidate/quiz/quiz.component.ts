import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { TestService } from '../service/test.service';
import {Test} from '../class/test';
import {ActivatedRoute, Router} from '@angular/router';
import {TestQuestion} from '../class/test-question';
import {TestResponses} from '../class/test-responses';
import {Response} from '../class/response';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {


    test?: Test;
    idTest?: number;
    questions: Array<TestQuestion> = [];
    question?: TestQuestion;
    duration?: number;
    RealDuration?: number;
    showCode?: boolean;
    tempsTotal?: any;
    timeOver = false;
    showQuestions =  true;
    lastQuart = false;
    index = 0;
    result: TestResponses = {
        test: {},
        question: {},
        responses: [],
        index: 0,
        testDuration: 0,
        timeOver: false
    };
    selectedResponses: Array<Response> = [];
    counter?: string;
    constructor(private testService: TestService, private route: ActivatedRoute, private router: Router, private http: HttpClient ) {
    }
    ngOnInit(): void {
        this.idTest = this.route.snapshot.params.id;
        this.testService.getTestById(this.idTest).subscribe((data) => {
            if (data.done === true || data.expired === true) {
                this.router.navigate(['/timeout/' + this.idTest]);
            } else {
            this.test = data;
                // tslint:disable-next-line:no-shadowed-variable
            this.testService.getAllTestQuestions(this.idTest).subscribe((question) => {
                    this.questions = question;
                    this.question = this.questions[0];
                    this.duration = this.question.test.testDuration;
                    this.RealDuration = this.duration;
                    if (this.question.question.code !== undefined) {
                        this.showCode = true;
                    } else {
                        this.showCode = false ;
                    }
                    this.tempsTotal = this.secondsToTime(this.RealDuration);
                    setInterval(() => {
                        this.lastQuarter(this.RealDuration);
                        this.TimeOut();
                        this.duration--;
                        this.counter = this.secondsToTime(this.duration);
                    }, 1000);
                });
            }
        });

    }

    secondsToTime(secs) {
        const secNum = parseInt(secs, 10);
        const hours = Math.floor(secNum / 3600);
        const minutes = Math.floor(secNum / 60) % 60;
        const seconds = secNum % 60;
        return [hours, minutes, seconds]
            .map(v => v < 10 ? '0' + v : v)
            .filter((v, i) => v !== '00' || i > 0)
            .join(':');
    }
    lastQuarter(duration) {
        const q = Math.floor(duration / 4);
        if (this.duration <= q) {
            this.lastQuart = true;
            return true;
        }
    }
    TimeOut() {
        if (this.duration === 0) {
            this.timeOver = true;
            this.showQuestions = false;
            this.sendResponse();
            this.router.navigate(['/timeout/' + this.idTest]);
            return true;
        }
    }
    sendResponse() {
        this.result.timeOver = this.timeOver;
        this.result.index = this.index + 1;
        this.result.test = this.question.test;
        this.result.responses = this.selectedResponses;
        this.selectedResponses = [];
        this.result.question = this.question.question;
        this.result.testDuration = this.RealDuration - this.duration;
        this.http.post<TestResponses>('http://localhost:8081/result/create', this.result).subscribe((data) => {
            console.log(data);
        });
    }
    getNextQuestion2() {
        this.sendResponse();
        this.index ++;
        if (this.index === this.questions.length) {
            this.showQuestions = false;
            this.router.navigate(['/timeout/' + this.idTest]);
        }
        this.question = this.questions[this.index];
    }
}
