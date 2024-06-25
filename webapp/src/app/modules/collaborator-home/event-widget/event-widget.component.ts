import { Component, OnInit } from "@angular/core";
import { IEvent } from "src/app/models/event.model";
import { EventService } from "src/app/services/event.service";
import { NbDialogService } from "@nebular/theme";
import { AddEventDialogComponent } from "./add-event-dialog/add-event-dialog.component";
import { EDMService } from "src/app/services/EDM.service";
import { ToastrService } from "src/app/services/toastr.service";
import { ICollaborator } from "src/app/models/collaborator.model";
import { ParticipantsListComponent } from "./participants-list/participants-list.component";
import { UserService } from "src/app/services/user.service";
import { StorageService } from "src/app/services/storage.service";
import { StorageKeyEnum } from "src/app/enumerations/storage-key.enum";
import { ToastrStatusEnum } from "src/app/enumerations/toastr-status.enum";
import { IErrorResponse } from "src/app/models/error-response.model";
import { ScoreService } from "src/app/services/score.service";
import { LocalStorageService } from "ngx-webstorage";
import { UpdateEventComponent } from "./update-event/update-event.component";
import { DeleteEventComponent } from "./delete-event/delete-event.component";

@Component({
    selector: "app-event-widget",
    templateUrl: "./event-widget.component.html",
    styleUrls: ["./event-widget.component.scss"],
})
export class EventWidgetComponent implements OnInit {
    upcomingEvents: IEvent[] = [];
    responsiveOptions;
    currentUser: ICollaborator;
    loadingState = false;
    loading = true;

    events: IEvent[] = [];
    currentId: number;
    edit: boolean = false;
    constructor(
        private eventService: EventService,
        private dialogService: NbDialogService,
        private EDMService: EDMService,
        private toastrService: ToastrService,
        private userService: UserService,
        private storageService: StorageService,
        private scoreService: ScoreService,
        private localStorageService: LocalStorageService
    ) {
        this.responsiveOptions = [
            {
                breakpoint: "1024px",
                numVisible: 3,
                numScroll: 3,
            },
            {
                breakpoint: "768px",
                numVisible: 2,
                numScroll: 2,
            },
            {
                breakpoint: "560px",
                numVisible: 1,
                numScroll: 1,
            },
        ];
    }
    score = 0;
    ngOnInit(): void {
        this.getScore();
        this.loadConnectedUser();
        this.getUpcomingEvents();
    }
    /**
     * get events
     */
    getUpcomingEvents() {
        let nb = 0;
        this.eventService.getUpcomingEvents().subscribe((data: IEvent[]) => {
            this.upcomingEvents = data;
            this.upcomingEvents.map((event) => {
                let nb = 0;
                console.log(event.collaborators);
                event.collaborators.map((collabvent) => {
                    if (collabvent.id == this.currentUser.id) {
                        nb++;
                    } else {
                    }
                });
                if (nb > 0) {
                    event.participated = false;
                } else {
                    event.participated = true;
                }
                if (event.idEDMImage != null) {
                    this.getImageFromBlob(event);
                }
            });
           this.loading=false

        }
        );
    }

    /**
     * get score
     */
    getScore() {
        this.eventService.getEventScore().subscribe((score) => {
            this.score = score;
        });
    }

    /**
     * connected User
     */
    loadConnectedUser() {
        this.userService
            .getUserById(
                this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id
            )
            .subscribe((currentUser: ICollaborator) => {
                this.currentUser = currentUser;
            });
    }
    /**add event */
    onAddEvent() {
        this.getUpcomingEvents();
        this.dialogService
            .open(AddEventDialogComponent, {
                hasScroll: true,
                context: {
                    data: this.score,
                },
                closeOnBackdropClick: true,
                closeOnEsc: true,
            })
            .onClose.subscribe((result: boolean) => {
                if (result) {
                    this.getUpcomingEvents();
                }
            });
    }

    /**get image */
    getImageFromBlob(event: IEvent) {
        this.EDMService.downloadFile(event.idEDMImage).subscribe(
            (blobFile: Blob) => {
                let reader = new FileReader();
                reader.addEventListener(
                    "load",
                    () => {
                        event.image = reader.result;
                        event.image = event.image.split(";").pop();
                        event.image =
                            "data:image/" +
                            event.imageExtension +
                            ";" +
                            event.image;
                    },
                    false
                );
                if (blobFile) {
                    reader.readAsDataURL(blobFile);
                }
            }
        );
    }
    /**
     *
     * @param event view participants list
     */
    viewParticipants(event: IEvent) {
        this.dialogService.open(ParticipantsListComponent, {
            hasScroll: true,
            context: {
                event: event,
            },
            closeOnBackdropClick: true,
            closeOnEsc: true,
        });
    }
    /**
     * participate to event
     * @param idEvent
     */
    participateToEvent(event: IEvent) {
        this.loadingState = true;
        this.eventService
            .participateToEvent(event.id, this.currentUser.id)
            .subscribe(
                () => {
                    this.loadingState = false;
                    this.toastrService.showStatusToastr(
                        "Successful participation in the event !",
                        ToastrStatusEnum.SUCCESS
                    );
                    this.getUpcomingEvents();
                    this.reloadScore();
                },
                (error: IErrorResponse) => {
                    this.loadingState = false;
                    this.toastrService.showStatusToastr(
                        "There was a problem to participate to event",
                        ToastrStatusEnum.DANGER
                    );
                }
            );
    }

    /**
     *
     * @param idEvent cancel event participation
     */
    cancelEvent(event: IEvent) {
        this.loadingState = true;
        this.eventService.cancelEvent(event.id, this.currentUser.id).subscribe(
            () => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    "Successful to cancel event participation !",
                    ToastrStatusEnum.SUCCESS
                );
                this.reloadScore();
                this.getUpcomingEvents();
            },
            (error: IErrorResponse) => {
                this.loadingState = false;
                this.toastrService.showStatusToastr(
                    "There was a problem to cancel event participation",
                    ToastrStatusEnum.DANGER
                );
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

    /**delete event */
    onDeleteEvent(event: IEvent) {
        this.dialogService
            .open(DeleteEventComponent, { context: { data: event } })
            .onClose.subscribe(() => {
                this.getUpcomingEvents();
                this.reloadScore();
                this.edit = false;
            });
    }
/**
 *
 * @param event update event
 */
    onUpdateEvent(event: IEvent) {
        this.dialogService
            .open(UpdateEventComponent, { context: { data: event } })
            .onClose.subscribe(() => {
                this.getUpcomingEvents();
                if (event.idEDMImage != null) {
                    this.getImageFromBlob(event);
                }
                this.edit = false;
            });
    }
/**
 * edit action
 */
    editEvent() {
        if (this.edit == false) {
            this.edit = true;
        } else {
            this.edit = false;
        }
    }
}
