import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { NbThemeService, NbMenuService, NbMenuBag, NbDialogService } from '@nebular/theme';

import { StorageService } from '../../services/storage.service';
import { AuthenticationService } from '../../services/authentication.service';

import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';

import { formatProfileType } from '../../utils/formaters.util';

import { IDialogData } from '../../models/dialog-data.model';

import { StorageKeyEnum } from '../../enumerations/storage-key.enum';

import { THEME_DEFAULT } from '../../constants/common.constant';
import { ICollaborator } from 'src/app/models/collaborator.model';

import { KeycloakService } from 'keycloak-angular';


@Component({
    templateUrl: './page-layout.component.html',
    styleUrls: [ './page-layout.component.scss' ]
})
export class PageLayoutComponent implements OnInit, OnDestroy {

    themeSubscription: Subscription;
    currentTheme: string;

    authenticationStatusSubscription: Subscription;
    authenticationStatus: boolean;

    userContextMenuSubscription: Subscription;
    userContextMenuTag = 'user-context-menu';
    userContextMenuItems = [
        {
            icon: 'log-out-outline',
            title: 'Sign out',
            action: () => this.onSignOut()
        }
    ];

    currentUser: ICollaborator;

    constructor(
        private router: Router,
        private themeService: NbThemeService,
        private menuService: NbMenuService,
        private dialogService: NbDialogService,
        private storageService: StorageService,
        private authenticationService: AuthenticationService,
        private keycloakService: KeycloakService
    ) { }

    ngOnInit() {

        this.themeSubscription = this.themeService.getJsTheme().subscribe(
            (themeOptions) => this.currentTheme = themeOptions.name,
            () => this.currentTheme = THEME_DEFAULT
        );

        this.loadCurrentUser();

        this.authenticationStatusSubscription = this.authenticationService.getAuthenticationStatus().subscribe(
            (authenticationStatus: boolean) => {
                this.authenticationStatus = authenticationStatus;
                if (this.authenticationStatus) {
                    this.loadCurrentUser();
                } else {
                    this.currentUser = undefined;
                }
            }
        );

        this.userContextMenuSubscription = this.menuService.onItemClick().pipe(
            filter((menuBag: NbMenuBag) => menuBag.tag === this.userContextMenuTag),
            map((menuBag: NbMenuBag) => menuBag.item)
        ).subscribe((menuItem: any) => menuItem.action());

    }

    loadCurrentUser() {
        this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
        if (this.currentUser) {
            this.currentUser.profileType = formatProfileType(this.currentUser.profileType) as any;
        }
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
                            this.authenticationService.setAuthenticationStatus(false);
                        }
                    );
                }
            }
        );
    }

    ngOnDestroy() {
        if (this.themeSubscription) {
            this.themeSubscription.unsubscribe();
        }
        if (this.authenticationStatusSubscription) {
            this.authenticationStatusSubscription.unsubscribe();
        }
        if (this.userContextMenuSubscription) {
            this.userContextMenuSubscription.unsubscribe();
        }
    }

}
