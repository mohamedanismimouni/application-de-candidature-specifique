import { Component, OnInit } from '@angular/core';
import {ResultTestQuestion} from '../class/result-test-question';
import {CandidacyService} from '../service/candidacy.service';
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-result-details',
  templateUrl: './result-details.component.html',
  styleUrls: ['./result-details.component.scss']
})
export class ResultDetailsComponent implements OnInit {

    resultTest?: Array<ResultTestQuestion>;
    constructor(private candidacyService: CandidacyService, public ref: DynamicDialogRef, public config: DynamicDialogConfig) { }

  ngOnInit(): void {
        const id = this.config.data.id;
        this.candidacyService.getResultDetail(id).subscribe((data) => {
            this.resultTest = data;
        });
  }

}
