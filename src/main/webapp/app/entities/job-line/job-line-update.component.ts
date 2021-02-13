import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJobLine, JobLine } from 'app/shared/model/job-line.model';
import { JobLineService } from './job-line.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job/job.service';

@Component({
  selector: 'jhi-job-line-update',
  templateUrl: './job-line-update.component.html',
})
export class JobLineUpdateComponent implements OnInit {
  isSaving = false;
  jobs: IJob[] = [];

  editForm = this.fb.group({
    id: [],
    material: [],
    quantity: [],
    unitCost: [],
    jobId: [],
  });

  constructor(
    protected jobLineService: JobLineService,
    protected jobService: JobService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobLine }) => {
      this.updateForm(jobLine);

      this.jobService.query().subscribe((res: HttpResponse<IJob[]>) => (this.jobs = res.body || []));
    });
  }

  updateForm(jobLine: IJobLine): void {
    this.editForm.patchValue({
      id: jobLine.id,
      material: jobLine.material,
      quantity: jobLine.quantity,
      unitCost: jobLine.unitCost,
      jobId: jobLine.jobId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobLine = this.createFromForm();
    if (jobLine.id !== undefined) {
      this.subscribeToSaveResponse(this.jobLineService.update(jobLine));
    } else {
      this.subscribeToSaveResponse(this.jobLineService.create(jobLine));
    }
  }

  private createFromForm(): IJobLine {
    return {
      ...new JobLine(),
      id: this.editForm.get(['id'])!.value,
      material: this.editForm.get(['material'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      unitCost: this.editForm.get(['unitCost'])!.value,
      jobId: this.editForm.get(['jobId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobLine>>): void {
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

  trackById(index: number, item: IJob): any {
    return item.id;
  }
}
