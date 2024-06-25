import { Component, OnInit } from '@angular/core';

import { StorageService } from '../../../services/storage.service';

import { SurveyTypeEnum } from '../../../enumerations/survey-type.enum';
import { StorageKeyEnum } from '../../../enumerations/storage-key.enum';


@Component({
    templateUrl: './evaluation.component.html',
    styleUrls: [ './evaluation.component.scss' ]
})
export class EvaluationComponent implements OnInit {

    collaboratorId: string;

    evaluationSurveyType = SurveyTypeEnum.EVALUATION;

    constructor(
        private storageService: StorageService
    ) { }

    ngOnInit() {
        this.collaboratorId = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id;
    }

}
