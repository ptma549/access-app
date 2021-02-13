import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobComment } from 'app/shared/model/job-comment.model';
import { JobCommentService } from './job-comment.service';

@Component({
  templateUrl: './job-comment-delete-dialog.component.html',
})
export class JobCommentDeleteDialogComponent {
  jobComment?: IJobComment;

  constructor(
    protected jobCommentService: JobCommentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobCommentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobCommentListModification');
      this.activeModal.close();
    });
  }
}
