import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPosition, Position } from 'app/shared/model/position.model';
import { PositionService } from './position.service';
import { INumber } from 'app/shared/model/number.model';
import { NumberService } from 'app/entities/number/number.service';

@Component({
  selector: 'jhi-position-update',
  templateUrl: './position-update.component.html',
})
export class PositionUpdateComponent implements OnInit {
  isSaving = false;
  numbers: INumber[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    numberId: [],
  });

  constructor(
    protected positionService: PositionService,
    protected numberService: NumberService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ position }) => {
      this.updateForm(position);

      this.numberService.query().subscribe((res: HttpResponse<INumber[]>) => (this.numbers = res.body || []));
    });
  }

  updateForm(position: IPosition): void {
    this.editForm.patchValue({
      id: position.id,
      value: position.value,
      numberId: position.numberId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const position = this.createFromForm();
    if (position.id !== undefined) {
      this.subscribeToSaveResponse(this.positionService.update(position));
    } else {
      this.subscribeToSaveResponse(this.positionService.create(position));
    }
  }

  private createFromForm(): IPosition {
    return {
      ...new Position(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      numberId: this.editForm.get(['numberId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPosition>>): void {
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

  trackById(index: number, item: INumber): any {
    return item.id;
  }
}
