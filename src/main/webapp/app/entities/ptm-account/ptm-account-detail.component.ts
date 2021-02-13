import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPtmAccount } from 'app/shared/model/ptm-account.model';

@Component({
  selector: 'jhi-ptm-account-detail',
  templateUrl: './ptm-account-detail.component.html',
})
export class PtmAccountDetailComponent implements OnInit {
  ptmAccount: IPtmAccount | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ptmAccount }) => (this.ptmAccount = ptmAccount));
  }

  previousState(): void {
    window.history.back();
  }
}
