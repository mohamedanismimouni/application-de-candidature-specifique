import { Component, Input } from '@angular/core';

import { NbDialogRef } from '@nebular/theme';

import { IDialogData } from '../../models/dialog-data.model';


@Component({
    templateUrl: './confirmation-dialog.component.html',
    styleUrls: [ './confirmation-dialog.component.scss' ]
})
export class ConfirmationDialogComponent {

    @Input()
    data: IDialogData;

    constructor(
        private dialogRef: NbDialogRef<ConfirmationDialogComponent>,
    ) { }

    close(status: boolean) {
        this.dialogRef.close(status);
    }

}
