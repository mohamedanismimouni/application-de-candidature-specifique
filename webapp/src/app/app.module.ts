import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { AppLibrariesModule } from './app-libraries.module';
import { AppServicesModule } from './app-services.module';
import { AppRoutingModule } from './app-routing.module';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';

import { AppComponent } from './app.component';
import { PageLayoutComponent } from './layouts/page-layout/page-layout.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { PasswordInitializationComponent } from './components/password-initialization/password-initialization.component';
import { UnassignedCollaboratorComponent } from './components/unassigned-collaborator/unassigned-collaborator.component';
import { UninitializedCollaboratorComponent } from './components/uninitialized-collaborator/uninitialized-collaborator.component';
import { OnboardingComponent } from './components/onboarding/onboarding.component';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { initializeKeycloak } from './init/keycloak-init.factory';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {CandidatePageLayoutComponent} from './candidate/candidate-page-layout/candidate-page-layout.component';
import {CarouselModule} from 'primeng/carousel';
import {OfferSelectComponent} from './candidate/offer-select/offer-select.component';
import {DragDropModule} from 'primeng/dragdrop';
import {TableModule} from 'primeng/table';
import {PanelModule} from 'primeng/panel';
import {StepsModule} from 'primeng/steps';
import {ToastModule} from 'primeng/toast';
import {CandidacyStepsComponent} from './candidate/CandidateSteps/candidacy-steps/candidacy-steps.component';
import {CandidacyService} from './candidate/service/candidacy.service';
import {CvUploadComponent} from './candidate/CandidateSteps/cv-upload/cv-upload.component';
import {GeneralInformationComponent} from './candidate/CandidateSteps/general-information/general-information.component';
import {OfferSelectionComponent} from './candidate/CandidateSteps/offer-selection/offer-selection.component';
import { CaptchaModule} from 'primeng/captcha';
import {SidebarModule} from 'primeng/sidebar';
import {ButtonModule} from 'primeng/button';
import {ConfirmationService, MessageService} from 'primeng/api';
import {ChipsModule} from 'primeng/chips';
import {FormsModule} from '@angular/forms';
import {ToastrService} from './services/toastr.service';
import {RadioButtonModule} from 'primeng/radiobutton';
import {LastStepComponent} from './candidate/last-step/last-step.component';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {DropdownModule} from 'primeng/dropdown';
import { QuizComponent } from './candidate/quiz/quiz.component';
import {CheckboxModule} from 'primeng/checkbox';
import {TimeoutComponent} from './candidate/timeout/timeout.component';
import {QuizHomePageComponent} from './candidate/quiz-home-page/quiz-home-page.component';

import { MatTableModule } from '@angular/material/table';
import { CdkTableModule} from '@angular/cdk/table';

import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {DialogService, DynamicDialogModule} from "primeng/dynamicdialog";
import {CommonModule} from "@angular/common";
import {DialogModule} from "primeng/dialog";
import {ResultDetailsComponent} from "./candidate/result-details/result-details.component";
import {CandidateDetailsComponent} from "./candidate/candidate-details/candidate-details.component";
import {TabViewModule} from "primeng/tabview";
import {MatChipsModule} from '@angular/material/chips';
import {CardModule} from 'primeng/card';
import {TimelineModule} from 'primeng/timeline';


@NgModule({
    declarations: [
        CandidateDetailsComponent,
        ResultDetailsComponent,
        QuizHomePageComponent,
        TimeoutComponent,
        LastStepComponent,
        AppComponent,
        PageLayoutComponent,
        CandidatePageLayoutComponent,
        SignInComponent,
        PasswordInitializationComponent,
        UnassignedCollaboratorComponent,
        UninitializedCollaboratorComponent,
        OnboardingComponent,
        AppLayoutComponent,
        ConfirmationDialogComponent,
        FooterComponent,
        OfferSelectComponent,
        CandidacyStepsComponent,
        CvUploadComponent,
        GeneralInformationComponent,
        OfferSelectionComponent,
        QuizComponent
    ],
    imports: [
        TabViewModule,
        DialogModule,
        CommonModule,
        DynamicDialogModule,
        TableModule,
        ConfirmDialogModule,
        CheckboxModule,
        ScrollPanelModule,
        DropdownModule,
        ChipsModule,
        RadioButtonModule,
        ButtonModule,
        SidebarModule,
        Ng2SearchPipeModule,
        CaptchaModule,
        BrowserModule,
        CdkTableModule,
        FormsModule,
        DragDropModule,
        BrowserAnimationsModule,
        AppLibrariesModule,
        AppServicesModule,
        AppRoutingModule,
        ZXingScannerModule,
        KeycloakAngularModule,
        NgxWebstorageModule.forRoot(),
        CarouselModule,
        StepsModule,
        ToastModule,
        TableModule,
        PanelModule,
        MatTableModule,
        BrowserAnimationsModule,
        MatChipsModule,
        CardModule,
        TimelineModule

    ],  providers: [
        {
          provide: APP_INITIALIZER,
          useFactory: initializeKeycloak,
          multi: true,
          deps: [KeycloakService],
        },
        CandidacyService,
        MessageService,
        ToastrService,
        ConfirmationService,
        DialogService
      ],
    bootstrap: [ AppComponent ]
})
export class AppModule { }
