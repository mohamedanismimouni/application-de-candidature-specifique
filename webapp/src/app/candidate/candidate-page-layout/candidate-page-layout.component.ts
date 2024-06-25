import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NbThemeService } from '@nebular/theme';
import { Subscription } from 'rxjs';
import { THEME_DEFAULT } from 'src/app/constants/common.constant';
import {CarouselModule} from 'primeng/carousel';
@Component({
  selector: 'app-candidate-page-layout',
  templateUrl: './candidate-page-layout.component.html',
  styleUrls: ['./candidate-page-layout.component.scss']
})
export class CandidatePageLayoutComponent implements OnInit {

  themeSubscription: Subscription;
  currentTheme: string;
  constructor( private themeService: NbThemeService ) { }

  ngOnInit(): void {
    this.themeSubscription = this.themeService.getJsTheme().subscribe(
      (themeOptions) => this.currentTheme = themeOptions.name,
      () => this.currentTheme = THEME_DEFAULT
  );
  }

}
