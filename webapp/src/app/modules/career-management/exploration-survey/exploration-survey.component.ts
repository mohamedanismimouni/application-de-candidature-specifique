import { Component, OnInit } from '@angular/core';

import { StorageService } from '../../../services/storage.service';

import { SurveyTypeEnum } from '../../../enumerations/survey-type.enum';
import { StorageKeyEnum } from '../../../enumerations/storage-key.enum';


@Component({
    templateUrl: './exploration-survey.component.html',
    styleUrls: [ './exploration-survey.component.scss' ]
})
export class ExplorationSurveyComponent implements OnInit {

    collaboratorId: string;

    explorationSurveyType = SurveyTypeEnum.EXPLORATION;

    constructor(
        private storageService: StorageService
    ) { }

    ngOnInit() {
        this.collaboratorId = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id;
    }

}
