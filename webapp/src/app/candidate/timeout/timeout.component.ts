import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TestService} from '../service/test.service';
import {Test} from '../class/test';
import {Candidate} from '../class/candidate';

@Component({
  selector: 'app-timeout',
  templateUrl: './timeout.component.html',
  styleUrls: ['./timeout.component.scss']
})
export class TimeoutComponent implements OnInit {

  constructor(private route: ActivatedRoute , private  testService: TestService) { }

    idTest?: number;
    test?: Test;
    candidate?: Candidate;
    testDuration = '';
  ngOnInit(): void {
      this.idTest = this.route.snapshot.params.id;
      this.testService.getTestById(this.idTest).subscribe((data) => {
          this.test = data;
          this.testDuration = this.secondsToTime(data.passageDuration);
          this.testService.getCandidateByTestId(this.idTest).subscribe((candidate) => {
                this.candidate = candidate;
            });
      });
  }
    secondsToTime(secs) {
        const secNum = parseInt(secs, 10);
        const hours = Math.floor(secNum / 3600);
        const minutes = Math.floor(secNum / 60) % 60;
        const seconds = secNum % 60;
        let str = '';
        if (hours !== 0) {
            str = '' + hours + 'heures , ' + minutes + ' et ' + seconds + 'secondes';
        } else {
            if(hours !== 0) {
                str = '' + minutes + ' minutes  et ' + seconds + 'secondes';
            } else {
                str = '' + seconds + ' secondes';
            }
        }
        return str;
    }

}
