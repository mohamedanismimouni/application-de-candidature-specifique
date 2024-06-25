import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NbDialogRef } from '@nebular/theme';
import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { MoodCollabService } from 'src/app/services/moodCollab.service';
import { FormService } from 'src/app/services/form.service';
import { ToastrService } from 'src/app/services/toastr.service';
import { IMoodCollab } from 'src/app/models/moodCollab.mode';
import { StorageService } from 'src/app/services/storage.service';



@Component({
  selector: 'update-mood',
  templateUrl: './update-mood.component.html',
  styleUrls: ['./update-mood.component.scss']
})
export class UpdateMood implements OnInit {

  @Input()
  data: any;

  documentRequestFormGroup: FormGroup = this.formBuilder.group({
  requestMotif:['', [Validators.maxLength(255)]]
});



  constructor(private formBuilder: FormBuilder,
    public formService: FormService,
    private dialogRef: NbDialogRef<UpdateMood>,
    private toastrService: ToastrService,
    private storageService: StorageService,
    private MoodCollabService : MoodCollabService,
    
) { }

 ngOnInit(): void {
}

onCancel() {
this.dialogRef.close(false);
}

onSubmit(){

const today =  new Date(new Date().setDate(new Date().getDate()));

const mood: IMoodCollab ={
  idMood:this.data,
  date:today,
  idCollab:this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY).id
  
}

this.MoodCollabService.updateMood(mood,(mood.idCollab).toString()).subscribe()

this.dialogRef.close(true);
}

}
