import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessAppSharedModule } from 'app/shared/shared.module';
import { JobCommentComponent } from './job-comment.component';
import { JobCommentDetailComponent } from './job-comment-detail.component';
import { JobCommentUpdateComponent } from './job-comment-update.component';
import { JobCommentDeleteDialogComponent } from './job-comment-delete-dialog.component';
import { jobCommentRoute } from './job-comment.route';

@NgModule({
  imports: [AccessAppSharedModule, RouterModule.forChild(jobCommentRoute)],
  declarations: [JobCommentComponent, JobCommentDetailComponent, JobCommentUpdateComponent, JobCommentDeleteDialogComponent],
  entryComponents: [JobCommentDeleteDialogComponent],
})
export class AccessAppJobCommentModule {}
