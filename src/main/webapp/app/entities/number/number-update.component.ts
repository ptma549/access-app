import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INumber, Number } from 'app/shared/model/number.model';
import { NumberService } from './number.service';
import { IStreet } from 'app/shared/model/street.model';
import { StreetService } from 'app/entities/street/street.service';

@Component({
  selector: 'jhi-number-update',
  templateUrl: './number-update.component.html',
})
export class NumberUpdateComponent implements OnInit {
  isSaving = false;
  streets: IStreet[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    building: [],
    postcode: [],
    streetId: [],
  });

  constructor(
    protected numberService: NumberService,
    protected streetService: StreetService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ number }) => {
      this.updateForm(number);

      this.streetService.query().subscribe((res: HttpResponse<IStreet[]>) => (this.streets = res.body || []));
    });
  }

  updateForm(number: INumber): void {
    this.editForm.patchValue({
      id: number.id,
      value: number.value,
      building: number.building,
      postcode: number.postcode,
      streetId: number.streetId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const number = this.createFromForm();
    if (number.id !== undefined) {
      this.subscribeToSaveResponse(this.numberService.update(number));
    } else {
      this.subscribeToSaveResponse(this.numberService.create(number));
    }
  }

  private createFromForm(): INumber {
    return {
      ...new Number(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      building: this.editForm.get(['building'])!.value,
      postcode: this.editForm.get(['postcode'])!.value,
      streetId: this.editForm.get(['streetId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INumber>>): void {
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

  trackById(index: number, item: IStreet): any {
    return item.id;
  }
}
