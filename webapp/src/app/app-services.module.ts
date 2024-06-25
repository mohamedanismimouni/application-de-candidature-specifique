import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormService } from './services/form.service';
import { StorageService } from './services/storage.service';
import { QuizService } from './services/quiz.service';
import { ToastrService } from './services/toastr.service';
import { AuthenticationService } from './services/authentication.service';
import { UserService } from './services/user.service';
import { TeamService } from './services/team.service';
import { CareerStepService } from './services/career-step.service';
import { CareerPathService } from './services/career-path.service';
import { QuestionService } from './services/question.service';
import { SkillService } from './services/skill.service';
import { ProfileService } from './services/profile.service';
import { RequiredSkillService } from './services/required-skill.service';
import { CareerPositionService } from './services/career-position.service';
import { AcquiredSkillService } from './services/acquired-skill.service';
import { MentorshipService } from './services/mentorship.service';
import { OnboardingService } from './services/onboarding.service';
import { ResponseService } from './services/response.service';
import { EvaluationService } from './services/evaluation.service';
import { AuthenticationGuard } from './guards/authentication.guard';
import { AuthorizationGuard } from './guards/authorization.guard';
import { UnassignedCollaboratorGuard } from './guards/unassigned-collaborator.guard';
import { AssignedCollaboratorGuard } from './guards/assigned-collaborator.guard';
import { UninitializedCollaboratorGuard } from './guards/uninitialized-collaborator.guard';
import { InitializedCollaboratorGuard } from './guards/initialized-collaborator.guard';
import { OnboardingProcessNotPassedGuard } from './guards/onboarding-process-not-passed.guard';
import { OnboardingProcessPassedGuard } from './guards/onboarding-process-passed.guard';
import { AuthenticationInterceptor } from './interceptors/authentication.interceptor';
import { ErrorInterceptor } from './interceptors/error.interceptor';
import { RequestTypeService } from './services/requestType.service';
import { EDMService } from './services/EDM.service';
import { CollaboratorService } from './services/collaborator.service';
import { PayRollService } from './services/payRoll.service';
import { UsefulLinkService } from './services/usefulLink.service';
import { ProverbService } from './services/proverb.service';
import { EventService } from './services/event.service';


@NgModule({
    providers: [
        FormService,
        StorageService,
        ToastrService,
        AuthenticationService,
        UserService,
        TeamService,
        CareerStepService,
        CareerPathService,
        QuestionService,
        SkillService,
        ProfileService,
        RequiredSkillService,
        CareerPositionService,
        AcquiredSkillService,
        MentorshipService,
        OnboardingService,
        RequestTypeService,
        EDMService,
        PayRollService,
        ResponseService,
        EvaluationService,
        AuthenticationGuard,
        AuthorizationGuard,
        UnassignedCollaboratorGuard,
        AssignedCollaboratorGuard,
        UninitializedCollaboratorGuard,
        InitializedCollaboratorGuard,
        OnboardingProcessNotPassedGuard,
        OnboardingProcessPassedGuard,
        CollaboratorService,
        EventService,
        QuizService,
        UsefulLinkService,
        ProverbService,
        { provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
    ]
})
export class AppServicesModule { }
