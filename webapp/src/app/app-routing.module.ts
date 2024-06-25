import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthenticationGuard } from './guards/authentication.guard';
import { AuthGuard } from './guards/auth.guard';
import { AuthorizationGuard } from './guards/authorization.guard';
import { UnassignedCollaboratorGuard } from './guards/unassigned-collaborator.guard';
import { AssignedCollaboratorGuard } from './guards/assigned-collaborator.guard';
import { UninitializedCollaboratorGuard } from './guards/uninitialized-collaborator.guard';
import { InitializedCollaboratorGuard } from './guards/initialized-collaborator.guard';
import { OnboardingProcessNotPassedGuard } from './guards/onboarding-process-not-passed.guard';
import { OnboardingProcessPassedGuard } from './guards/onboarding-process-passed.guard';

import { PageLayoutComponent } from './layouts/page-layout/page-layout.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { PasswordInitializationComponent } from './components/password-initialization/password-initialization.component';
import { UnassignedCollaboratorComponent } from './components/unassigned-collaborator/unassigned-collaborator.component';
import { UninitializedCollaboratorComponent } from './components/uninitialized-collaborator/uninitialized-collaborator.component';
import { OnboardingComponent } from './components/onboarding/onboarding.component';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
import {CandidatePageLayoutComponent} from './candidate/candidate-page-layout/candidate-page-layout.component';
import {OfferSelectComponent} from './candidate/offer-select/offer-select.component';
import {CandidacyStepsComponent} from './candidate/CandidateSteps/candidacy-steps/candidacy-steps.component';
import {CvUploadComponent} from './candidate/CandidateSteps/cv-upload/cv-upload.component';
import {GeneralInformationComponent} from './candidate/CandidateSteps/general-information/general-information.component';
import {OfferSelectionComponent} from './candidate/CandidateSteps/offer-selection/offer-selection.component';
import {LastStepComponent} from "./candidate/last-step/last-step.component";
import { QuizComponent } from './candidate/quiz/quiz.component';
import {TimeoutComponent} from './candidate/timeout/timeout.component';
import {QuizHomePageComponent} from "./candidate/quiz-home-page/quiz-home-page.component";


const APP_ROUTES: Routes = [
    {
        path: 'process',
        component: CandidatePageLayoutComponent
    },
    {
        path: 'quizHome/:id',
        component: QuizHomePageComponent
    },
    {
        path: 'quiz/:id',
        component: QuizComponent,
    },
    {
        path: 'timeout/:id',
        component: TimeoutComponent,
    },


    {
        path: 'candidate',
        component: OfferSelectComponent
    },
    {
        path: 'lastStep' ,
        component: LastStepComponent
    },
    {path: 'candidacy', component: CandidacyStepsComponent, children: [
            {path: 'offerSelection', component: OfferSelectComponent},
            {path: 'CvUpload', component: CvUploadComponent},
            {path: 'GeneralInformation', component: GeneralInformationComponent},
            {path: 'Validation', component: OfferSelectionComponent}
        ]},
        
    {
        path: 'app',
        component: AppLayoutComponent,
        canActivate: [AuthGuard ],
        children: [
            {
                path: 'home',
                loadChildren: () => import('./modules/collaborator-home/collaborator-home.module').then((m) => m.CollaboratorHomeModule),
                canActivate: [AuthGuard],
                data: { roles: ['HR_RESPONSIBLE', 'DIRECTOR', 'MANAGER', 'COLLABORATOR'] }
            },
            {
                path: 'director',
                loadChildren: () => import('./modules/director/director.module').then((m) => m.DirectorModule),
                canActivate: [AuthGuard],
                data: { roles: ['DIRECTOR'] }
            },
            {
                path: 'hr-responsible',
                loadChildren: () => import('./modules/hr-responsible/hr-responsible.module').then((m) => m.HRResponsibleModule),
                canActivate: [AuthGuard],
                data: { roles: ['HR_RESPONSIBLE', 'SUPPORT_ADMIN'] }
            },
            {
                path: 'manager',
                loadChildren: () => import('./modules/manager/manager.module').then((m) => m.ManagerModule),
                canActivate: [AuthGuard],
                data: { roles: ['MANAGER'] }
            },
            {
                path: 'collaborator',
                loadChildren: () => import('./modules/collaborator/collaborator.module').then((m) => m.CollaboratorModule),
                canActivate: [AuthGuard, AssignedCollaboratorGuard, InitializedCollaboratorGuard, OnboardingProcessPassedGuard ],
                data: { roles: ['COLLABORATOR'] }
            }
        ]
    },
     {
         path: 'new',
         component: PageLayoutComponent,
         children: [
    //         {
    //             path: 'sign-in',
    //             component: SignInComponent
    //         },
    //         {
    //             path: 'password-initialization/:session-type/:session-id',
    //             component: PasswordInitializationComponent
    //         },
             {
                 path: 'unassigned-collaborator',
                 component: UnassignedCollaboratorComponent,
                 canActivate: [ AuthGuard, UnassignedCollaboratorGuard ]
             },
             {
                 path: 'uninitialized-collaborator',
                 component: UninitializedCollaboratorComponent,
                 canActivate: [ AuthGuard, AssignedCollaboratorGuard, UninitializedCollaboratorGuard ]
             },
             {
                path: 'onboarding',
                 component: OnboardingComponent,
                 canActivate: [ AuthGuard, AssignedCollaboratorGuard, InitializedCollaboratorGuard, OnboardingProcessNotPassedGuard ]
             },
             {
                 path: '**',
                 redirectTo: 'app/home',
                 pathMatch: 'full'
             }
         ]
     },
     {
        path: 'app',
        redirectTo: 'app/home',
        pathMatch: 'full'
     },
     {
        path: '**',
        redirectTo: 'app/home',
        pathMatch: 'full'
     }

];

@NgModule({
    imports: [ RouterModule.forRoot(APP_ROUTES) ],
    exports: [ RouterModule ]
})
export class AppRoutingModule { }
