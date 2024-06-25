import { Component, OnInit } from '@angular/core';
import {VERSION_MAJOR ,VERSION_MINOR ,VERSION_PATCH} from '../../constants/api-urls.constant'
@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  version : string
  constructor() { }

  ngOnInit(): void {
    this.version=VERSION_MAJOR+'.'+VERSION_MINOR+'.'+VERSION_PATCH;
  }

}
