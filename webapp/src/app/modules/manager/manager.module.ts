import { NgModule } from '@angular/core';

import { ManagerRoutingModule } from './manager-routing.module';

import { ManagerComponent } from './manager.component';


@NgModule({
    declarations: [
        ManagerComponent
    ],
    imports: [
        ManagerRoutingModule
    ]
})
export class ManagerModule { }
