import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CollaboratorHomeRoutingModule } from './collaborator-home-routing.module';
import { CollaboratorHomeComponent } from './collaborator-home.component';
import { EventWidgetComponent } from './event-widget/event-widget.component';
import { AddEventDialogComponent } from './event-widget/add-event-dialog/add-event-dialog.component';
import { AppLibrariesModule } from 'src/app/app-libraries.module';
import {CarouselModule} from 'primeng/carousel';
import { FormsModule } from '@angular/forms';
import { UsefulDocumentWidgetComponent } from './useful-document-widget/useful-document-widget.component';
import { UsefulLinkWidgetComponent } from './useful-link-widget/useful-link-widget.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import {UpdateMood} from './update-mood-collab/update-mood.component';
import { MoodCollab } from './mood-widget/mood-widget.component';
import { ProverbWidgetComponent } from './proverb-widget/proverb-widget.component';
import { QuizWidgetComponent } from './quiz-widget/quiz-widget.component';
import { AddProverbWidgetComponent } from './proverb-widget/add-proverb-widget/add-proverb-widget.component';
import {FileUploadModule} from 'primeng/fileupload';
import { ParticipantsListComponent } from './event-widget/participants-list/participants-list.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import {ToggleButtonModule} from 'primeng/togglebutton';
import { ButtonModule } from 'primeng/button';
import { DeleteEventComponent } from './event-widget/delete-event/delete-event.component';
import { UpdateEventComponent } from './event-widget/update-event/update-event.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [CollaboratorHomeComponent, UsefulDocumentWidgetComponent, UsefulLinkWidgetComponent, ProverbWidgetComponent,UpdateMood,MoodCollab, AddEventDialogComponent, EventWidgetComponent, QuizWidgetComponent, AddProverbWidgetComponent, ParticipantsListComponent, DeleteEventComponent, UpdateEventComponent],
  imports: [
    CommonModule,
    CollaboratorHomeRoutingModule,
    AppLibrariesModule,
    SharedModule,
    CarouselModule,
    FormsModule,
    ScrollingModule,
    FileUploadModule,
    NgxPaginationModule,
    Ng2SearchPipeModule,
    ToggleButtonModule,
    ButtonModule



  ]
})
export class CollaboratorHomeModule { }
