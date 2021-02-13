import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStreet, Street } from 'app/shared/model/street.model';
import { StreetService } from './street.service';
import { ITown } from 'app/shared/model/town.model';
import { TownService } from 'app/entities/town/town.service';

@Component({
  selector: 'jhi-street-update',
  templateUrl: './street-update.component.html',
})
export class StreetUpdateComponent implements OnInit {
  isSaving = false;
  towns: ITown[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    townId: [],
  });

  constructor(
    protected streetService: StreetService,
    protected townService: TownService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ street }) => {
      this.updateForm(street);

      this.townService.query().subscribe((res: HttpResponse<ITown[]>) => (this.towns = res.body || []));
    });
  }

  updateForm(street: IStreet): void {
    this.editForm.patchValue({
      id: street.id,
      value: street.value,
      townId: street.townId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const street = this.createFromForm();
    if (street.id !== undefined) {
      this.subscribeToSaveResponse(this.streetService.update(street));
    } else {
      this.subscribeToSaveResponse(this.streetService.create(street));
    }
  }

  private createFromForm(): IStreet {
    return {
      ...new Street(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      townId: this.editForm.get(['townId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStreet>>): void {
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

  trackById(index: number, item: ITown): any {
    return item.id;
  }
}
