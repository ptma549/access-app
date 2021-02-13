import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { JobLineComponent } from './job-line.component';
import { JobLineDetailComponent } from './job-line-detail.component';
import { JobLineUpdateComponent } from './job-line-update.component';
import { JobLineDeleteDialogComponent } from './job-line-delete-dialog.component';
import { jobLineRoute } from './job-line.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(jobLineRoute)],
  declarations: [JobLineComponent, JobLineDetailComponent, JobLineUpdateComponent, JobLineDeleteDialogComponent],
  entryComponents: [JobLineDeleteDialogComponent],
})
export class AccessAppJobLineModule {}
