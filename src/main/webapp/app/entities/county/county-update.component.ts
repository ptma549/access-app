import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICounty, County } from 'app/shared/model/county.model';
import { CountyService } from './county.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-county-update',
  templateUrl: './county-update.component.html',
})
export class CountyUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    clientId: [],
  });

  constructor(
    protected countyService: CountyService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ county }) => {
      this.updateForm(county);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(county: ICounty): void {
    this.editForm.patchValue({
      id: county.id,
      name: county.name,
      clientId: county.clientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const county = this.createFromForm();
    if (county.id !== undefined) {
      this.subscribeToSaveResponse(this.countyService.update(county));
    } else {
      this.subscribeToSaveResponse(this.countyService.create(county));
    }
  }

  private createFromForm(): ICounty {
    return {
      ...new County(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICounty>>): void {
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

  trackById(index: number, item: IClient): any {
    return item.id;
  }
}
