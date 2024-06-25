import { Component, OnInit } from '@angular/core';
import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { MoodCollabService } from 'src/app/services/moodCollab.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { IMoodCollab } from 'src/app/models/moodCollab.mode';
import { StorageService } from 'src/app/services/storage.service';
import { NbDialogService } from '@nebular/theme';
import { UpdateMood } from '../update-mood-collab/update-mood.component';
import { ScoreService } from 'src/app/services/score.service';
import { LocalStorageService } from 'ngx-webstorage';
import { ICollaborator } from 'src/app/models/collaborator.model';
import { UserService } from 'src/app/services/user.service';




@Component({
  selector: 'app-mood-widget',
  templateUrl: './mood-widget.component.html',
  styleUrls: ['./mood-widget.component.scss']
})
export class MoodCollab implements OnInit {
  score = 0;
  currentMood : IMoodCollab ;
  selectedMood =0 ;
constructor(private toastrService: ToastrService,
  private storageService: StorageService ,
  private MoodCollabService : MoodCollabService,
  private dialogService: NbDialogService,
  private scoreService: ScoreService,
  private localStorageService: LocalStorageService,
  private userService: UserService ) { }

  currentUser: ICollaborator;

 ngOnInit(): void {
  this.getCurrentMood()

  this.userService.getUserById(this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe((currentUser : ICollaborator) => {
    this.currentUser = currentUser;
});

   this.MoodCollabService.getMoodScore().subscribe((score)=>{
     this.score= score ;
   })
}

addMood(idMood: any)

{
  this.getCurrentMood()
   result : Boolean;
  this.MoodCollabService.IsMoodAdded(this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe(data=>
  { if (data==false)
    {
    const today =  new Date(new Date().setDate(new Date().getDate()));
    const mood: IMoodCollab ={
      idMood:idMood,
      date:today,
      idCollab:this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id
    }

    this.MoodCollabService.createMood(mood).subscribe(()=>{this.toastrService.showStatusToastr(
      'Your mood Request has been successfully updated',
      ToastrStatusEnum.SUCCESS)
      this.currentMood=mood;
     
      this.selectedMood=mood.idMood;
      setTimeout(() =>
      {
      this.reloadScore();
      }, 1000);

    
}
)

  }
   else {
     if (idMood===this.currentMood.idMood){

         this.toastrService.showStatusToastr(
           'This is already your current mood',
        ToastrStatusEnum.WARNING)
     }
     else {

      this.dialogService.open(
        UpdateMood,
        {
          context: { data: idMood }

         }  ).onClose.subscribe(
          (result: string) => {
            if(result)
            {
              this.toastrService.showStatusToastr(
                'Your mood Request has been successfully updated',
                ToastrStatusEnum.SUCCESS);
                this.selectedMood=idMood
                 this.getCurrentMoodRelaoad()
            }

          }
      );

     }


  }}
    )

}

getCurrentMood(){
  this.MoodCollabService.getActualMood(this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe((currentMood)=>{
    this.currentMood=currentMood;
    this.selectedMood=currentMood?.idMood;
  });

}
getCurrentMoodRelaoad(){
  this.MoodCollabService.getActualMood(this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id).subscribe((currentMood)=>{
    this.currentMood=currentMood;
  });

}



reloadScore(){
  this.scoreService.getScore(this.currentUser.email).subscribe(
      (score: number) => {
          this.localStorageService.store("curentScore",score);
      });
}
}
