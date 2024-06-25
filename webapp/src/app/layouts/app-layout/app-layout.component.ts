import { Component, OnInit, OnDestroy,ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NbThemeService, NbMenuService, NbMenuBag, NbDialogService, NbSidebarService } from '@nebular/theme';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';
import { formatProfileType } from '../../utils/formaters.util';
import { IDialogData } from '../../models/dialog-data.model';
import { IUserAppLayoutSettings, UserAppLayoutSettings, ParameterKeyEnum } from '../../models/user-app-layout-settings.model';
import { PDF_EXTENSION, THEME_DEFAULT } from '../../constants/common.constant';
import { IRequestType } from 'src/app/models/request-type';
import { EDMService } from 'src/app/services/EDM.service';
import { downloadFile } from "src/app/modules/shared/EDM/DownloadFile";
import { IErrorResponse } from 'src/app/models/error-response.model';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ToastrService } from 'src/app/services/toastr.service';
import { RequestTypeService } from 'src/app/services/requestType.service';
import { RequestTypeEnum } from 'src/app/enumerations/request-type.enum';
import { AuthenticationService } from '../../services/authentication.service';
import { StorageService } from '../../services/storage.service';
import { UserService } from '../../services/user.service';
import { getRoutePrefixFromProfileType } from '../../utils/navigation.util';
import { ITeam } from '../../models/team.model';
import { ICareerPosition } from '../../models/career-position.model';
import { StorageKeyEnum } from '../../enumerations/storage-key.enum';
import { ProfileTypeEnum } from '../../enumerations/profile-type.enum';
import { CareerPositionStatusEnum } from '../../enumerations/career-position-status.enum';
import { CareerPositionService } from  '../../services/career-position.service';
import { OnboardingService } from '../../services/onboarding.service';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { KeycloakService } from 'keycloak-angular';
import { ScoreService } from 'src/app/services/score.service';
import {LocalStorageService} from 'ngx-webstorage';

@Component({
    templateUrl: './app-layout.component.html',
    styleUrls: [ './app-layout.component.scss' ]
})
export class AppLayoutComponent implements OnInit, OnDestroy {

    themeSubscription: Subscription;
    currentTheme: string;

    userContextMenuSubscription: Subscription;
    userContextMenuTag = 'user-context-menu';
    userContextMenuItems = [
        {
            icon: 'download-outline',
            title: 'User Guide',
            action: () => this.downloadUserGuide()
        },
        {
            icon: 'email-outline',
            title: 'Feedback',
            action: ()=>this.mailMe()
        },
        {
            icon: 'log-out-outline',
            title: 'Sign out',
            action: () => this.onSignOut()
        }
 ];

    currentUser: ICollaborator;
    fistLetters:string
    userAppLayoutSettings: IUserAppLayoutSettings;

    sidebarTag = 'sidebar-tag';
    score: number;
    requestType: IRequestType;
    constructor(
        private router: Router,
        private themeService: NbThemeService,
        private menuService: NbMenuService,
        private dialogService: NbDialogService,
        public sidebarService: NbSidebarService,
        private storageService: StorageService,
        private authenticationService: AuthenticationService,
        private EDMService: EDMService,
        private toastrService: ToastrService,
        private requestTypeService: RequestTypeService,
        private careerPositionService: CareerPositionService,
        private onboardingService: OnboardingService,
        private userService: UserService,
        private keycloakService: KeycloakService,
        private scoreService: ScoreService,
        private localStorageService: LocalStorageService

    ) { }

     ngOnInit() {
        if (this.router.url === '/app'){
            this.postSignInNavigation('/app/home')
        }

        this.themeSubscription = this.themeService.getJsTheme().subscribe(
            (themeOptions) => this.currentTheme = themeOptions.name,
            () => this.currentTheme = THEME_DEFAULT
        );

        this.userContextMenuSubscription = this.menuService.onItemClick().pipe(
            filter((menuBag: NbMenuBag) => menuBag.tag === this.userContextMenuTag),
            map((menuBag: NbMenuBag) => menuBag.item)
        ).subscribe((menuItem: any) => menuItem.action());

        this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
        this.fistLetters=this.currentUser.firstName[0]+this.currentUser.lastName[0];

            this.collaboratorPostSignInNavigation(this.currentUser );
      
            this.userAppLayoutSettings =  UserAppLayoutSettings.fromProfileType(
                this.currentUser.profileType,
                new Map().set(ParameterKeyEnum.COLLABORATOR_ID, this.currentUser.id)
            );
        this.currentUser.profileType = formatProfileType(this.currentUser.profileType) as any;
       
        this.scoreService.getScore(this.currentUser.email).subscribe(
            (score: number) => {
                this.localStorageService.store("curentscore",score);
               
            });
            this.localStorageService.observe("curentscore").subscribe((score) => {
                this.score= this.localStorageService.retrieve("curentscore");
        });    
           
            

    }
/*  */
    toggleSidebar() {
        this.sidebarService.toggle(true, this.sidebarTag);
    }
    mailMe(){
      
        let mail = document.createElement("a");
        
        mail.href = "mailto:support-applirh@talan.com";
        mail.click();
    }
    onSignOut() {
        const dialogData: IDialogData = {
            title: 'Sign out',
            content: 'You\'re about to sign out, do you wish to proceed?'
        };
        this.dialogService.open(
            ConfirmationDialogComponent,
            { context: { data: dialogData } }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.keycloakService.logout(window.location.origin + '/app').then(
                        () => {
                            this.storageService.clearAuthenticationItems();
                        }
                    );
                }
            }
        );
    }

    isActionItemActive(actionItemLink: string) {
        return this.router.url.startsWith(actionItemLink);
    }

    ngOnDestroy() {
        if (this.themeSubscription) {
            this.themeSubscription.unsubscribe();
        }
        if (this.userContextMenuSubscription) {
            this.userContextMenuSubscription.unsubscribe();
        }
    }

    downloadUserGuide(){
        this.requestTypeService.getTypeByLabel(RequestTypeEnum.USER_GUIDE).subscribe(
            (requestType: IRequestType) => {
                this.requestType = requestType;
            this.EDMService.downloadFile(requestType.idEDM).subscribe(
                (blobFile: Blob) => {
                    downloadFile(blobFile, requestType.label + PDF_EXTENSION);
                }
                ,
                (error: IErrorResponse) => {

                    this.toastrService.showStatusToastr(
                        " 'User guide connot be downloaded'",
                        ToastrStatusEnum.DANGER
                    );
                },
            );}
        );
    }
    downloadTemplate(requestType: IRequestType) {
        if(requestType.label=="USER_GUIDE"){
            this.EDMService.downloadFile(requestType.idEDM).subscribe(
                (blobFile: Blob) => {
                    downloadFile(blobFile, requestType.label + PDF_EXTENSION);
                },
                (error: IErrorResponse) => {

                    this.toastrService.showStatusToastr(
                        " 'User guide connot be downloaded'",
                        ToastrStatusEnum.DANGER
                    );
                },
            );
        }
    }
    collaboratorPostSignInNavigation(collaborator, redirectUrl?: string) {
        this.userService.getCollaboratorTeam(collaborator.id).subscribe(
            (team: ITeam) => {
                if (!team) {
                    this.postSignInNavigation('/new/unassigned-collaborator');
                } else {
                    this.careerPositionService.getCareerPositionsByCollaboratorIdAndStatus(
                        collaborator.id,
                        CareerPositionStatusEnum.CURRENT
                    ).subscribe(
                        (careerPositions: Array<ICareerPosition>) => {

                            if (careerPositions.length <= 0) {
                                this.postSignInNavigation('/new/uninitialized-collaborator');
                            } else {
                                if (collaborator.passedOnboardingProcess) {

                                   // this.defaultPostSignInNavigation(ProfileTypeEnum.COLLABORATOR, redirectUrl);
                                } else {
                                    this.onboardingService.getOnboardingHint(collaborator.id).subscribe(
                                        () => this.postSignInNavigation('/new/onboarding'),
                                      //  () => this.defaultPostSignInNavigation(ProfileTypeEnum.COLLABORATOR, redirectUrl)
                                    );
                                }
                            }
                        }
                    );
                }
            }
        );
    }

    defaultPostSignInNavigation(profileType: ProfileTypeEnum, redirectUrl?: string) {
        let url: string;
        if (redirectUrl) {
            url = redirectUrl;
            this.storageService.removeItem(StorageKeyEnum.REDIRECT_URL_KEY);
        } else {
            url = getRoutePrefixFromProfileType(profileType);
        }
        this.postSignInNavigation(url);
    }

    postSignInNavigation(url: string) {
        this.router.navigate([url]);
        // this.loadingState = false;
        // this.toastrService.showSimpleToastr('Hello and welcome to Sirius HR!');
    }
}
