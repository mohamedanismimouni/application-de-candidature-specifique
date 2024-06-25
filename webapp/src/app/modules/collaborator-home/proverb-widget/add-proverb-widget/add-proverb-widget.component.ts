import { Component, Input, OnInit } from "@angular/core";
import {
    FormBuilder,
    FormControl,
    FormGroup,
    Validators,
} from "@angular/forms";
import { NbDialogRef } from "@nebular/theme";
import { LocalStorageService } from "ngx-webstorage";
import { StorageKeyEnum } from "src/app/enumerations/storage-key.enum";
import { ToastrStatusEnum } from "src/app/enumerations/toastr-status.enum";
import { ICollaborator } from "src/app/models/collaborator.model";
import { IErrorResponse } from "src/app/models/error-response.model";
import { IProverb } from "src/app/models/proverb.model";
import { FormService } from "src/app/services/form.service";
import { ProverbService } from "src/app/services/proverb.service";
import { ScoreService } from "src/app/services/score.service";
import { StorageService } from "src/app/services/storage.service";
import { ToastrService } from "src/app/services/toastr.service";
import { UserService } from "src/app/services/user.service";

@Component({
    selector: "app-add-proverb-widget",
    templateUrl: "./add-proverb-widget.component.html",
    styleUrls: ["./add-proverb-widget.component.scss"],
})
export class AddProverbWidgetComponent implements OnInit {
    @Input()
    score: any;
    loadingState = false;
    savedProverb: IProverb;
    currentUser: ICollaborator;

    constructor(
        private formBuilder: FormBuilder,
        private proverbService: ProverbService,
        private toastrService: ToastrService,
        private localStorageService: LocalStorageService,
        public formService: FormService,
        private storageService: StorageService,
        private userService: UserService,
        private dialogRef: NbDialogRef<AddProverbWidgetComponent>,
        private scoreService: ScoreService
    ) {}

    ngOnInit(): void {
        this.loadConnectedUser();
    }

    addProverbFormGroup: FormGroup = this.formBuilder.group({
        text: [
            "",
            [
                Validators.required,
                Validators.maxLength(255),
                this.noWhitespaceValidator,
            ],
        ],
        author: [
            "",
            [
                Validators.required,
                Validators.maxLength(255),
                this.noWhitespaceValidator,
            ],
        ],
        creator: ["", Validators.required],
        bySystem: ["", Validators.required],
    });
    /**
     * close dialog
     */
    onCancel() {
        this.dialogRef.close();
    }
    /**
     * load connected user and set createdb action
     */
    loadConnectedUser() {
        this.userService
            .getUserById(
                this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id
            )
            .subscribe((currentUser: ICollaborator) => {
                this.currentUser = currentUser;
                this.addProverbFormGroup.controls["creator"].setValue(
                    this.currentUser
                );
                this.addProverbFormGroup.controls["bySystem"].setValue(false);
            });
    }
    /**
     * add proverb
     */
    onSubmit() {
        this.proverbService.addEvent(this.addProverbFormGroup.value).subscribe(
            
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    "Proverb created successfully," +
                        this.score +
                        " points will be added to your score",
                    ToastrStatusEnum.SUCCESS
                );
         
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                if (error.status >= 400 && error.status < 500) {
                    this.toastrService.showStatusToastr(
                        "There was a problem to adding a proverb",
                        ToastrStatusEnum.DANGER
                    );
                }
                this.dialogRef.close(true);
            },
            ()=>{
                this.dialogRef.close(true);
                this.reloadScore();
            }
        );
    }
    /**
     * reload score
     */
    reloadScore() {
        this.scoreService
            .getScore(this.currentUser.email)
            .subscribe((score: number) => {
                this.localStorageService.store("curentScore", score);
            });
    }
    /**
     * whiteespace validator
     * @param control
     * @returns
     */
    noWhitespaceValidator(control: FormControl) {
        const isWhitespace =
            (
                (control && control.value && control.value.toString()) ||
                ""
            ).trim().length === 0;
        const isValid = !isWhitespace;
        return isValid ? null : { whitespace: true };
    }
}
