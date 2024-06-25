import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {JobsComponent} from '../jobs/jobs.component';

const routes: Routes = [  {
    path: '',
    component: JobsComponent
}, ];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class JobsRoutingModule {}
