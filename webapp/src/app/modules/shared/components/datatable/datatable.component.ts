import { Component, OnChanges, OnDestroy, Input, OnInit, ViewChild, ElementRef, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl, Validators } from '@angular/forms';

import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

import { NbMenuService, NbMenuBag } from '@nebular/theme';

import { IDatatableConfiguration } from './models/datatable-configuration.model';

import { ProfileTypeEnum } from 'src/app/enumerations/profile-type.enum';
import { UserService } from 'src/app/services/user.service';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ToastrService } from 'src/app/services/toastr.service';
import { FormService } from 'src/app/services/form.service';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { ICollaborator } from 'src/app/models/collaborator.model';
@Component({
    selector: 'app-datatable',
    templateUrl: './datatable.component.html',
    styleUrls: [ './datatable.component.scss' ]
})
export class DatatableComponent implements OnChanges, OnInit, OnDestroy {

    @Input()
    configuration: IDatatableConfiguration;

    @Input()
    data: Array<any>;

    @Input()
    inputStatus:boolean;

    @Input()
    userSelected:ICollaborator;

    @Output() userChanged: EventEmitter<ICollaborator> =   new EventEmitter();

    filteredData: Array<any> = [];

    datatableHeaderFormGroup: FormGroup = this.formBuilder.group({
        keyword: [ '' ],
        filters: this.formBuilder.array([])
    });

    get filters() {
        return this.datatableHeaderFormGroup.get('filters') as FormArray;
    }

    filtersInformation: Array<any> = [];

    search = false;

    pageIndex = 0;
    pageFrom = 0;
    pageTo = 0;
    page: Array<any> = [];

    rowContextMenuSubscription: Subscription;
    triggeredRowContextMenuTag: string;

    updateUserFormGroup: FormGroup ;
    firstName:string="";
    lastName:string="";
    email:string="";
    profileType: ProfileTypeEnum;

    constructor(
        private formBuilder: FormBuilder,
        private menuService: NbMenuService,
        private userService: UserService,
        private toastrService: ToastrService,
        public formService: FormService
    ) {


    }

    ngOnChanges() {
        this.filteredData = this.data ? [ ...this.data ] : [];
        this.initializeDatatableHeader();
        this.initializePage();

        this.firstName=this.userSelected.firstName;
        this.lastName= this.userSelected.lastName;
        this.email = this.userSelected.email;
        this.profileType= this.userSelected.profileType;
    }

    ngOnInit() {
        this.rowContextMenuSubscription = this.menuService.onItemClick().pipe(
            filter((menuBag: NbMenuBag) => menuBag.tag === this.triggeredRowContextMenuTag)
        ).subscribe((menuBag: NbMenuBag) => {
            (menuBag.item as any).action(menuBag.tag);
            this.triggeredRowContextMenuTag = '';
        });

        this.updateUserFormGroup=new FormGroup({
            firstName : new FormControl(' ',[Validators.required,this.noWhitespaceValidator]),
            lastName : new FormControl('',[Validators.required,this.noWhitespaceValidator]),
            email : new FormControl('',[Validators.required,this.noWhitespaceValidator,Validators.pattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$")]),
            profileType : new FormControl('')
          });


    }

    noWhitespaceValidator(control: FormControl) {
        const isWhitespace = (control && control.value && control.value.toString() || '').trim().length === 0;
        const isValid = !isWhitespace;
        return isValid ? null : { 'whitespace': true };
      }

    public cancel() : void {
        this.inputStatus=false;
        this.userChanged.emit(this.userSelected);
    }

    public processChanges(form) : void {
      if (form.valid) {
        if ( this.email !== this.userSelected!.email || this.userSelected!.profileType !== this.profileType ||
            this.userSelected!.firstName  !== this.firstName || this.userSelected!.lastName  !== this.lastName ) {
                this.inputStatus=false;
                this.userSelected!.email = this.email;
                this.userSelected!.profileType = this.profileType;
                this.userSelected!.firstName  = this.firstName;
                this.userSelected!.lastName  = this.lastName;
                    this.userService.updateUser(this.userSelected).subscribe(
                    () => {
                        this.toastrService.showStatusToastr(
                         'User updated successfully',
                        ToastrStatusEnum.SUCCESS);
                        this.userChanged.emit(this.userSelected);
                   },

                   (error: IErrorResponse) => {
                    this.userChanged.emit(this.userSelected);
                    switch (error.status) {
                        case 400:
                        this.toastrService.showStatusToastr(
                            'Something is not right, please verify '
                            + 'your input and try again later',
                            ToastrStatusEnum.DANGER);
                        break;
                        case 403:
                            this.toastrService.showStatusToastr(
                                'Something is not right, please verify '
                                + 'your input and try again later',
                                ToastrStatusEnum.DANGER);
                            break;
                       case 409:
                        this.toastrService.showStatusToastr(
                            'This email already exists',
                            ToastrStatusEnum.DANGER);
                        break;

                                    }

                });
        }
        else{
            this.inputStatus=false;
            this.userChanged.emit(this.userSelected);
        }

    }
    else{
        this.toastrService.showStatusToastr(
            'Your Form is not valid, please verify'
            + 'your Form and try again later',
            ToastrStatusEnum.DANGER);
        }
    }

    initializeDatatableHeader() {
        if (!this.configuration) {
            return;
        }
        this.datatableHeaderFormGroup.reset();
        this.filters.clear();
        this.filtersInformation = [];
        this.search = false;
        this.configuration.columns.forEach((column) => {
            if (column.filterValues && column.filterValues.length > 0) {
                this.filters.push(this.formBuilder.control(''));
                this.filtersInformation.push({
                    id: column.id,
                    placeholder: column.name,
                    values: column.filterValues
                });
            }
        });
    }

    toggleSearch() {
        this.search = !this.search;
        if (!this.search) {
            this.datatableHeaderFormGroup.get('keyword').reset();
            this.onDatatableHeaderChange();
        }
    }

    onDatatableHeaderChange() {

        const keyword = this.datatableHeaderFormGroup.get('keyword').value;

        this.filteredData = this.data.filter(
            (item: any) => {
                let include = true;
                if (keyword && keyword.trim() !== '') {
                    let searchString = '';
                    this.configuration.columns.forEach((column) => {
                        if (column.searchable) {
                            searchString = searchString + item[column.id].toLowerCase();
                        }
                    });
                    if (searchString !== '' && !searchString.includes(keyword.trim().toLowerCase())) {
                        include = false;
                    }
                }
                for (let i = 0; i < this.filtersInformation.length; i++) {
                    const filterValue = this.filters.at(i).value;
                    if (filterValue && filterValue !== '' && item[this.filtersInformation[i].id] !== filterValue) {
                        include = false;
                    }
                }
                return include;
            }
        );

        this.initializePage();

    }

    initializePage() {
        //this.pageIndex = this.pageFrom = this.pageTo = 0;
        this.page = [];
        if (this.filteredData.length > 0) {
            this.calculatePage();
        }
    }

    onNextPage() {
        if (this.pageTo >= this.filteredData.length - 1) {
            return;
        }
        this.pageIndex += 1;
        this.calculatePage();
    }

    onPreviousPage() {
        if (this.pageFrom <= 0) {
            return;
        }
        this.pageIndex -= 1;
        this.calculatePage();
    }
    calculatePage() {
        this.pageFrom = this.pageIndex * this.configuration.pageSize;
        this.pageTo = Math.min(this.pageFrom + this.configuration.pageSize - 1, this.filteredData.length - 1);
        this.page = this.filteredData.slice(this.pageFrom, this.pageTo + 1);
    }

    ngOnDestroy() {
        if (this.rowContextMenuSubscription) {
            this.rowContextMenuSubscription.unsubscribe();
        }
    }

}
