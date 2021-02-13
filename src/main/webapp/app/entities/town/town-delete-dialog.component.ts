import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITown } from 'app/shared/model/town.model';
import { TownService } from './town.service';

@Component({
  templateUrl: './town-delete-dialog.component.html',
})
export class TownDeleteDialogComponent {
  town?: ITown;

  constructor(protected townService: TownService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.townService.delete(id).subscribe(() => {
      this.eventManager.broadcast('townListModification');
      this.activeModal.close();
    });
  }
}
