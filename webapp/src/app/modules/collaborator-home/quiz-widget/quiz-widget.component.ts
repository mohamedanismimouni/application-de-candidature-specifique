import { Component, OnInit } from '@angular/core';
import { QuizService } from 'src/app/services/quiz.service';
import { StorageService } from 'src/app/services/storage.service';
import { StorageKeyEnum } from 'src/app/enumerations/storage-key.enum';
import { ToastrService } from 'src/app/services/toastr.service';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ScoreService } from 'src/app/services/score.service';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-quiz-widget',
  templateUrl: './quiz-widget.component.html',
  styleUrls: ['./quiz-widget.component.scss']
})
export class QuizWidgetComponent implements OnInit {
  hasNext : Boolean =true
  hasPrevious : Boolean = false
  response =""  
  currentUser: any;
  allQuiz : any[];
  currentQuiz : any=0
  quiz : any="" ;
  currentChar = [];
  score =0;
  loading=true;

  constructor( private toastrService: ToastrService, 
    private storageService: StorageService,
    private quizService: QuizService,
    private scoreService: ScoreService,
    private localStorageService: LocalStorageService) { }

  ngOnInit(): void {
    this.currentUser = this.storageService.getItem(StorageKeyEnum.CURRENT_USER_KEY);
    this.quizService.getQuiz().subscribe((quizs)=>{
     this.score=quizs.score
      if (quizs.quiz.length>0)
   {  this.currentQuiz=0
    this.allQuiz=quizs.quiz   
     this.quiz=quizs.quiz[0]
  
     if (this.allQuiz.length -1 >this.currentQuiz+1){
      this.hasNext = true
     }
     if (this.allQuiz.length === 1 ){
      this.hasNext = false
      this.hasPrevious=false
     }
    }
        this.loading=false
              
  })
  }
  nextQuiz(){
    this.currentQuiz++
    this.quiz= this.allQuiz[this.currentQuiz];
    this.deleteAlphabet()
    if (this.allQuiz.length -1 <this.currentQuiz+1){
      this.hasNext = false
      this.hasPrevious=true
    }else {
      this.hasNext = true
      this.hasPrevious=true
    }
  }
  previousQuiz(){
    this.currentQuiz--
    this.quiz= this.allQuiz[this.currentQuiz];
    this.deleteAlphabet()
     if (this.currentQuiz===0 ){
      
      this.hasPrevious=false
      if (this.allQuiz.length===1){
        this.hasNext = false
      }else {
        this.hasNext = true
      }
    }else {
      this.hasNext = true
      this.hasPrevious=true
    }
  }
  addAlphabet(alphabet : string, index ){
    this.quiz.disabled[index]=true
    this.response=this.response+alphabet
    this.currentChar.push(index)
  }
  deleteAlphabet(){
    this.currentChar=[]
    if (this.quiz){
      for (let i=0;i<this.quiz.disabled.length;i++){
        this.quiz.disabled[i]=false
      }
      this.response=""
    }

  }

  submitQuiz(){
    
      
      this.quizService.submitAnswer(this.response, this.quiz.id).subscribe((quizs)=>{
        if (quizs){
        this.toastrService.showStatusToastr(
          'Congratulations ! '+this.score+ ' points will be added to your score',
          ToastrStatusEnum.SUCCESS)
          
        this.response=""
        setTimeout(() => {
          this.reloadScore();}, 1000);
        if (quizs.quiz.length>0)
        {  this.currentQuiz=0
         this.allQuiz=quizs.quiz   
          this.quiz=quizs.quiz[0]
        this.hasPrevious=false
          if (this.allQuiz.length  >1){
            this.hasNext = true
 
           }else
         {
           this.hasNext = false
           }
         }  else 
         this.quiz=null 
        }else {
          this.toastrService.showStatusToastr(
            '',
            ToastrStatusEnum.WRONG)
            this.deleteAlphabet()

         }    
      })
     
  

  }
  deleteChar(){
    if (this.currentChar.length>0){
    this.response=this.response.substr(0,this.response.length-1)
    this.quiz.disabled[this.currentChar[this.currentChar.length-1]]=false
    this.currentChar.pop();}

  }

  reloadScore(){
    this.scoreService.getScore(this.currentUser.email).subscribe(
        (score: number) => {
            this.localStorageService.store("curentScore",score);            
        });
  }
}
