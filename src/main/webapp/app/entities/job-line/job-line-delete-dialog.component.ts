import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobLine } from 'app/shared/model/job-line.model';
import { JobLineService } from './job-line.service';

@Component({
  templateUrl: './job-line-delete-dialog.component.html',
})
export class JobLineDeleteDialogComponent {
  jobLine?: IJobLine;

  constructor(protected jobLineService: JobLineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jobLineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jobLineListModification');
      this.activeModal.close();
    });
  }
}
