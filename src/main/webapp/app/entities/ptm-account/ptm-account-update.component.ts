import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPtmAccount, PtmAccount } from 'app/shared/model/ptm-account.model';
import { PtmAccountService } from './ptm-account.service';

@Component({
  selector: 'jhi-ptm-account-update',
  templateUrl: './ptm-account-update.component.html',
})
export class PtmAccountUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    colour: [],
  });

  constructor(protected ptmAccountService: PtmAccountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ptmAccount }) => {
      this.updateForm(ptmAccount);
    });
  }

  updateForm(ptmAccount: IPtmAccount): void {
    this.editForm.patchValue({
      id: ptmAccount.id,
      name: ptmAccount.name,
      colour: ptmAccount.colour,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ptmAccount = this.createFromForm();
    if (ptmAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.ptmAccountService.update(ptmAccount));
    } else {
      this.subscribeToSaveResponse(this.ptmAccountService.create(ptmAccount));
    }
  }

  private createFromForm(): IPtmAccount {
    return {
      ...new PtmAccount(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      colour: this.editForm.get(['colour'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPtmAccount>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
