import { Component, HostListener, Input, OnInit, Output, ViewChild } from '@angular/core';

import { NbDialogService } from '@nebular/theme';

import { UserService } from '../../services/user.service';
import { ToastrService } from '../../services/toastr.service';

import { CreateUserDialogComponent } from './create-user-dialog/create-user-dialog.component';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';

import { formatProfileType, formatAccountStatus } from '../../utils/formaters.util';


import { ISingleChartData } from '../../models/single-chart-data.model';
import { IDialogData } from '../../models/dialog-data.model';
import { IErrorResponse } from '../../models/error-response.model';
import { IDatatableConfiguration } from '../shared/components/datatable/models/datatable-configuration.model';

import { ProfileTypeEnum } from '../../enumerations/profile-type.enum';
import { AccountStatusEnum } from '../../enumerations/account-status.enum';
import { ToastrStatusEnum } from '../../enumerations/toastr-status.enum';

import { CHART_COLOR_SCHEME } from '../../constants/common.constant';
import { ICollaborator } from 'src/app/models/collaborator.model';


@Component({
    templateUrl: './users-management.component.html',
    styleUrls: [ './users-management.component.scss' ]
})
export class UsersManagementComponent implements OnInit {

    users: Array<ICollaborator> = [];

    usersByProfileTypeChartData: Array<ISingleChartData> = [];
    usersByAccountStatusChartData: Array<ISingleChartData> = [];

    configuration: IDatatableConfiguration = {
        title: 'Users',
        rowId: 'id',
        columns: [
            {
                id: 'name',
                name: 'Name',
                searchable: true,

            },
            {
                id: 'email',
                name: 'Email',
                searchable: true,

            },
            {
                id: 'profileType',
                name: 'Profile type',
                searchable: false,
                filterValues: Object.values(ProfileTypeEnum)
                    .map((profileType) => formatProfileType(profileType)),

            },
            {
                id: 'accountStatus',
                name: 'Account status',
                searchable: false,
                filterValues: Object.values(AccountStatusEnum)
                    .map((accountStatus) => formatAccountStatus(accountStatus)),


            }
        ],
        rowContextMenuItems: [
            {
                icon: 'email-outline',
                title: 'Resend activation mail',
                action: (userId) => this.onResendAccountActivationMail(userId)
            },
            {
                icon: 'activity-outline',
                title: 'Update account status',
                action: (userId) => this.onUpdateAccountStatus(userId)
            },
            {
                icon: 'edit-outline',
                title: 'Update user account',
                action: (user) => this.onUpdateUserAccount(user)
            },
            {
                icon: 'trash-2-outline',
                title: 'Delete account',
                action: (userId) => this.onDeleteUser(userId)
            }
        ],
        datatableActions: [
            {
                icon: 'plus-outline',
                title: 'Add user',
                action: () => this.onAddUser()
            }
        ],
        pageSize: 5
    };

    data: Array<any> = [];

    inputStatus: boolean;
    userSelected:any= {};

    constructor(
        private dialogService: NbDialogService,
        private userService: UserService,
        private toastrService: ToastrService
    ) {   }

    ngOnInit() {
      this.loadUsers();
    }

    loadUsers() {
        this.userService.getUsers().subscribe(
            (users: ICollaborator[]) => {
                this.users = users;
                this.initializeChartsData();
                this.initializeDatatableData();
            }
        );
    }

    initializeChartsData() {
        this.usersByProfileTypeChartData = Object.values(ProfileTypeEnum).map((value, index) => ({
            id: value,
            name: formatProfileType(value),
            value: 0,
            color: CHART_COLOR_SCHEME.domain[index]
        }));
        this.usersByAccountStatusChartData = Object.values(AccountStatusEnum).map((value, index) => ({
            id: value,
            name: formatAccountStatus(value),
            value: 0,
            color: CHART_COLOR_SCHEME.domain[index]
        }));
        this.users.forEach(
            (user) => {
                for (const chartData of this.usersByProfileTypeChartData) {
                    if (user.profileType === chartData.id) {
                        chartData.value += 1;
                        break;
                    }
                }
                for (const chartData of this.usersByAccountStatusChartData) {
                    if (user.accountStatus === chartData.id) {
                        chartData.value += 1;
                        break;
                    }
                }
            }
        );
    }

    initializeDatatableData() {
        this.data = this.users.map((user:ICollaborator) => ({
            id: user.id,
            name: user.firstName + ' ' + user.lastName,
            email: user.email,
            profileType: formatProfileType(user.profileType),
            accountStatus: formatAccountStatus(user.accountStatus)
        }));
    }

    onResendAccountActivationMail(userId: number) {
        const user: ICollaborator = this.users.find((value) => value.id === userId);
        if (!user) {
            return;
        }
        this.userService.sendAccountActivationMail(user.id).subscribe(
            () => {
                this.toastrService.showStatusToastr(
                    'Account activation mail sended successfully',
                    ToastrStatusEnum.SUCCESS);
            },
            (error: IErrorResponse) => {
                switch (error.status) {
                    case 403:
                        this.toastrService.showStatusToastr(
                            'Account activation mail can only be '
                            + 'sent to users with inactive accounts',
                            ToastrStatusEnum.DANGER);
                        break;
                    case 404:
                        this.toastrService.showStatusToastr(
                            'Oups user is not found, seems like you are seeing '
                            + 'some outdated data. Users list is now refreshed!',
                            ToastrStatusEnum.DANGER);
                        this.loadUsers();
                        break;
                }
            }
        );
    }

    onUpdateAccountStatus(userId: number) {
        const user: ICollaborator = this.users.find((value) => value.id === userId);
        if (!user) {
            return;
        }
        let dialogDataContent: string;
        switch (user.accountStatus) {
            case AccountStatusEnum.INACTIVE:
                this.toastrService.showStatusToastr(
                    'This account is yet inactive and its status cannot be updated',
                    ToastrStatusEnum.DANGER
                );
                return;
            case AccountStatusEnum.ACTIVE:
                dialogDataContent = 'You\'re about to suspend this user\'s account '
                    + 'activity, do you whish to proceed?';
                break;
            case AccountStatusEnum.SUSPENDED:
                dialogDataContent = 'You\'re about to activate this user\'s account, '
                    + 'do you whish to proceed?';
                break;
        }
        const dialogData: IDialogData = {
            title: 'Update user\'s account status',
            content: dialogDataContent
        };
        this.dialogService.open(
            ConfirmationDialogComponent,
            { context: { data: dialogData } }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.userService.updateUserAccountStatus(user.id).subscribe(
                        () => {
                            this.toastrService.showStatusToastr(
                                'User\'s account status updated successfully',
                                ToastrStatusEnum.SUCCESS);
                            this.loadUsers();
                        },
                        (error: IErrorResponse) => {
                            switch (error.status) {
                                case 403:
                                    this.toastrService.showStatusToastr(
                                        'This account is yet inactive and its status cannot be updated',
                                        ToastrStatusEnum.DANGER);
                                    break;
                                case 404:
                                    this.toastrService.showStatusToastr(
                                        'Oups user is not found, seems like you are seeing '
                                        + 'some outdated data. Users list is now refreshed!',
                                        ToastrStatusEnum.DANGER);
                                    this.loadUsers();
                                    break;
                            }
                        }
                    );
                }
            }
        );
    }

    userChanged() {
        this.loadUsers();
        this.inputStatus=false;
    }
    onUpdateUserAccount(userId){
         const user: ICollaborator = this.users.find((value) => value.id === userId);
        if (!user) {
            return;
        }
         this.userSelected=user;
       this.inputStatus=true;
    }
    onDeleteUser(userId: number) {
        const user: ICollaborator = this.users.find((value) => value.id === userId);
        if (!user) {
            return;
        }
        const dialogData: IDialogData = {
            title: 'Delete user\'s account',
            content: 'You\'re about to delete a user\'s account permanently, do you wish to proceed?'
        };
        this.dialogService.open(
            ConfirmationDialogComponent,
            { context: { data: dialogData } }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.userService.deleteUser(user.id).subscribe(
                        () => {
                            this.toastrService.showStatusToastr(
                                'User\'s account deleted successfully',
                                ToastrStatusEnum.SUCCESS);
                            this.loadUsers();
                        },
                        (error: IErrorResponse) => {
                            switch (error.status) {
                                case 400:
                                    this.toastrService.showStatusToastr(
                                        'User\'s account connot be deleted',
                                        ToastrStatusEnum.DANGER);
                                    break;
                                case 404:
                                    this.toastrService.showStatusToastr(
                                        'Oups user is not found, seems like you are seeing '
                                        + 'some outdated data. Users list is now refreshed!',
                                        ToastrStatusEnum.DANGER);
                                    this.loadUsers();
                                    break;
                            }
                        }
                    );
                }
            }
        );
    }

    onAddUser() {
        this.dialogService.open(CreateUserDialogComponent).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.loadUsers();
                }
            }
        );
    }

}
