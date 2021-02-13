import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { JobVisitComponent } from './job-visit.component';
import { JobVisitDetailComponent } from './job-visit-detail.component';
import { JobVisitUpdateComponent } from './job-visit-update.component';
import { JobVisitDeleteDialogComponent } from './job-visit-delete-dialog.component';
import { jobVisitRoute } from './job-visit.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(jobVisitRoute)],
  declarations: [JobVisitComponent, JobVisitDetailComponent, JobVisitUpdateComponent, JobVisitDeleteDialogComponent],
  entryComponents: [JobVisitDeleteDialogComponent],
})
export class AccessAppJobVisitModule {}
