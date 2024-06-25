import { Injectable } from '@angular/core';

import { NbToastrService } from '@nebular/theme';

import { ToastrStatusEnum } from '../enumerations/toastr-status.enum';


@Injectable()
export class ToastrService {

    constructor(
        private toastrService: NbToastrService
    ) { }

    showSimpleToastr(message: string) {
        this.toastrService.show('', message);
    }

    showStatusToastr(message: string, status: ToastrStatusEnum) {
        let title: string;
        let icon: string;
        switch (status) {
            case ToastrStatusEnum.SUCCESS:
                title = 'Operation succeeded';
                icon = 'checkmark-circle-2-outline';
                this.toastrService.success(message, title, { icon });
                break;
            case ToastrStatusEnum.DANGER:
                title = 'Operation failed';
                icon = 'close-circle-outline';
                this.toastrService.danger(message, title, { icon });
                break;
            case ToastrStatusEnum.WARNING:
                    title = 'Information ';
                    icon = 'info-outline';
                    this.toastrService.info(message, title, { icon });
                    break;
            case ToastrStatusEnum.WRONG:
                    title = 'Sorry ! wrong answer';
                    icon = 'close-circle-outline';
                    this.toastrService.danger(message, title, { icon });
                    break;

        }
       // this.toastrService.show(message, title, { icon });
    }

}
