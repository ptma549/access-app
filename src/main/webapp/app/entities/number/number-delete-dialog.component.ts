import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INumber } from 'app/shared/model/number.model';
import { NumberService } from './number.service';

@Component({
  templateUrl: './number-delete-dialog.component.html',
})
export class NumberDeleteDialogComponent {
  number?: INumber;

  constructor(protected numberService: NumberService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.numberService.delete(id).subscribe(() => {
      this.eventManager.broadcast('numberListModification');
      this.activeModal.close();
    });
  }
}
