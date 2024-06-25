import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppLibrariesModule } from '../../app-libraries.module';
import { DirectorRoutingModule } from './director-routing.module';

import { DirectorComponent } from './director.component';


@NgModule({
    declarations: [
        DirectorComponent
    ],
    imports: [
        CommonModule,
        AppLibrariesModule,
        DirectorRoutingModule
    ]
})
export class DirectorModule { }
