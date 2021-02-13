import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobVisit } from 'app/shared/model/job-visit.model';
import { JobVisitService } from './job-visit.service';

@Component({
  templateUrl: './job-visit-delete-dialog.component.html',
})
export class JobVisitDeleteDialogComponent {
  jobVisit?: IJobVisit;

  constructor(protected jobVisitService: JobVisitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobVisitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobVisitListModification');
      this.activeModal.close();
    });
  }
}
