import { Component, OnInit } from '@angular/core';

import { StorageService } from '../../../services/storage.service';

import { SurveyTypeEnum } from '../../../enumerations/survey-type.enum';
import { StorageKeyEnum } from '../../../enumerations/storage-key.enum';


@Component({
    templateUrl: './awareness-survey.component.html',
    styleUrls: [ './awareness-survey.component.scss' ]
})
export class AwarenessSurveyComponent implements OnInit {

    collaboratorId: string;

    awarenessSurveyType = SurveyTypeEnum.AWARENESS;

    constructor(
        private storageService: StorageService
    ) { }

    ngOnInit() {
        this.collaboratorId = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id;
    }

}
