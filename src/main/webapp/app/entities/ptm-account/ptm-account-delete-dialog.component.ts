import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPtmAccount } from 'app/shared/model/ptm-account.model';
import { PtmAccountService } from './ptm-account.service';

@Component({
  templateUrl: './ptm-account-delete-dialog.component.html',
})
export class PtmAccountDeleteDialogComponent {
  ptmAccount?: IPtmAccount;

  constructor(
    protected ptmAccountService: PtmAccountService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ptmAccountService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ptmAccountListModification');
      this.activeModal.close();
    });
  }
}
