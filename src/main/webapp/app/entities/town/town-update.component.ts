import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITown, Town } from 'app/shared/model/town.model';
import { TownService } from './town.service';
import { ICounty } from 'app/shared/model/county.model';
import { CountyService } from 'app/entities/county/county.service';

@Component({
  selector: 'jhi-town-update',
  templateUrl: './town-update.component.html',
})
export class TownUpdateComponent implements OnInit {
  isSaving = false;
  counties: ICounty[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    countyId: [],
  });

  constructor(
    protected townService: TownService,
    protected countyService: CountyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ town }) => {
      this.updateForm(town);

      this.countyService.query().subscribe((res: HttpResponse<ICounty[]>) => (this.counties = res.body || []));
    });
  }

  updateForm(town: ITown): void {
    this.editForm.patchValue({
      id: town.id,
      value: town.value,
      countyId: town.countyId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const town = this.createFromForm();
    if (town.id !== undefined) {
      this.subscribeToSaveResponse(this.townService.update(town));
    } else {
      this.subscribeToSaveResponse(this.townService.create(town));
    }
  }

  private createFromForm(): ITown {
    return {
      ...new Town(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      countyId: this.editForm.get(['countyId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITown>>): void {
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

  trackById(index: number, item: ICounty): any {
    return item.id;
  }
}
