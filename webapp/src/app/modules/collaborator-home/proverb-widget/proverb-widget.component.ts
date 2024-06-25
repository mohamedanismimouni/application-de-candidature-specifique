import { Component, OnInit } from '@angular/core';
import { NbDialogService } from '@nebular/theme';
import { LocalStorageService } from 'ngx-webstorage';
import { ConfirmationDialogComponent } from 'src/app/components/confirmation-dialog/confirmation-dialog.component';
import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { IDialogData } from 'src/app/models/dialog-data.model';
import { IErrorResponse } from 'src/app/models/error-response.model';
import { IProverb } from 'src/app/models/proverb.model';
import { ProverbService } from 'src/app/services/proverb.service';
import { ScoreService } from 'src/app/services/score.service';
import { StorageService } from 'src/app/services/storage.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { UserService } from 'src/app/services/user.service';
import { AddProverbWidgetComponent } from './add-proverb-widget/add-proverb-widget.component';

@Component({
  selector: 'app-proverb-widget',
  templateUrl: './proverb-widget.component.html',
  styleUrls: ['./proverb-widget.component.scss']
})
export class ProverbWidgetComponent implements OnInit {
    proverbList: IProverb[];
    dispalyProverbList: IProverb[];
    existNewProverb :Boolean;
    proverbOfDay : IProverb;
    currentUser: ICollaborator;
    score = 0;
    loading=true;
    constructor(
        private proverbService: ProverbService,
        private dialogService: NbDialogService,
        private userService: UserService,
        private storageService: StorageService,
        private toastService: ToastrService,
       private  scoreService:ScoreService,
       private localStorageService:LocalStorageService
    ) {}

    ngOnInit(): void {
        this.existProverOfDay();
        this.loadProverbs();
        this.loadProverbScore();
        this.loadConnectedUser();
    }

    /**
     * get proverb Score
     */
    loadProverbScore() {
        this.proverbService.getProverbScore().subscribe((score) => {
            this.score = score;
        });
    }

        /**
     * get proverb of the day
     */
         loadProverbOfTheDay() {
            this.proverbService.getProverbOfTheDay().subscribe((proverb) => {
                this.proverbOfDay=proverb;

            });
        }


        /**
     * Check if exsit proverb of the day
     */
         existProverOfDay() {
            this.proverbService.existProverOfDay().subscribe((existProverbOfDay) => {
                  this.existNewProverb=existProverbOfDay
            });
        }


    /**
     * get all proverbs
     */
    loadProverbs() {
        this.proverbService
            .getProverbs()
            .subscribe({
            next:(proverbList: IProverb[]) => {
                this.proverbList = proverbList;

            },
            complete:()=>{
                this.proverbService.existProverOfDay().subscribe({
                    next:(existProverbOfDay) => {
                        this.existNewProverb=existProverbOfDay
                    },
                    complete:()=>{

                        if (!this.existNewProverb)
                        {
                            this.proverbService.getProverbOfTheDay().subscribe(
                                {
                                next: (proverb) => {
                                this.proverbOfDay=proverb;

                            },
                            complete:()=>{
                                this.dispalyProverbList= this.proverbList;
                                this.dispalyProverbList.splice(0, 0,this.proverbOfDay);
                            }
                        });
                        }else{
                            this.dispalyProverbList= this.proverbList;
                        }
                       this.loading=false
                    }


              });


            }
        });
    }



    onAddProverb() {
        this.dialogService
            .open(AddProverbWidgetComponent, {
                hasScroll: true,
                context: {
                    score: this.score,
                },
                closeOnBackdropClick: true,
                closeOnEsc: true,
            })
            .onClose.subscribe((result: boolean) => {
                if (result) {
                    this.loadProverbs();
                    this.loadProverbOfTheDay()
                }
            });
    }
    loadConnectedUser() {
        this.userService
            .getUserById(
                this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id
            )
            .subscribe((currentUser: ICollaborator) => {
                this.currentUser = currentUser;
            });
    }

    /**
     * delete proverb
     * @param idProverb
     */
    deleteProverb(idProverb: number) {
        const dialogData: IDialogData = {
            title: "Delete Proverb",
            content:
                "You're about to delete Proverb permanently, do you wish to proceed?",
        };
        this.dialogService
            .open(ConfirmationDialogComponent, {
                context: { data: dialogData },
            })
            .onClose.subscribe((result: boolean) => {
                if (result) {
                    //delete document
                    this.proverbService.deleteProverb(idProverb).subscribe(
                        () => {
                            this.toastService.showStatusToastr(
                                " Proverb deleted successfully",
                                ToastrStatusEnum.SUCCESS
                            );
                            this.loadProverbs();
                            this.loadProverbOfTheDay()
                            this.reloadScore();
                        },
                        (error: IErrorResponse) => {
                            this.toastService.showStatusToastr(
                                " Proverb's Template connot be deleted'",
                                ToastrStatusEnum.DANGER
                            );
                        }
                    );
                }
            });
    }


reloadScore(){
    this.scoreService.getScore(this.currentUser.email).subscribe(
        (score: number) => {
            this.localStorageService.store("curentScore",score);
        });
}
}
