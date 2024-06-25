import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../../app-libraries.module';
import { SharedModule } from '../../shared/shared.module';
import { DocumentRoutingModule } from './document-routing.module';

import { DocumentComponent } from './document.component';
import { AddDocumentRequestComponent } from './add-document-request/add-document-request.component';
import { HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';
import { NbLayoutModule, NbUserModule } from '@nebular/theme';
import { CancelDocumentRequestComponent } from './cancel-document-request/cancel-document-request.component';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { QrCodeScannerComponent } from './qr-code-scanner/qr-code-scanner.component';
import { FormsModule } from '@angular/forms';


@NgModule({
    declarations: [
        DocumentComponent,
        AddDocumentRequestComponent,
        CancelDocumentRequestComponent,
        QrCodeScannerComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        NbLayoutModule,
        NbUserModule,
        SharedModule,
        DocumentRoutingModule,
        NgxPaginationModule,
        HttpClientModule,
        ZXingScannerModule,
        FormsModule

    ]
})
export class DocumentModule { }
