import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJobVisit, JobVisit } from 'app/shared/model/job-visit.model';
import { JobVisitService } from './job-visit.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job/job.service';

@Component({
  selector: 'jhi-job-visit-update',
  templateUrl: './job-visit-update.component.html',
})
export class JobVisitUpdateComponent implements OnInit {
  isSaving = false;
  jobs: IJob[] = [];

  editForm = this.fb.group({
    id: [],
    arrived: [],
    departed: [],
    charge: [],
    workCarriedOut: [],
    jobId: [],
  });

  constructor(
    protected jobVisitService: JobVisitService,
    protected jobService: JobService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobVisit }) => {
      if (!jobVisit.id) {
        const today = moment().startOf('day');
        jobVisit.arrived = today;
        jobVisit.departed = today;
      }

      this.updateForm(jobVisit);

      this.jobService.query().subscribe((res: HttpResponse<IJob[]>) => (this.jobs = res.body || []));
    });
  }

  updateForm(jobVisit: IJobVisit): void {
    this.editForm.patchValue({
      id: jobVisit.id,
      arrived: jobVisit.arrived ? jobVisit.arrived.format(DATE_TIME_FORMAT) : null,
      departed: jobVisit.departed ? jobVisit.departed.format(DATE_TIME_FORMAT) : null,
      charge: jobVisit.charge,
      workCarriedOut: jobVisit.workCarriedOut,
      jobId: jobVisit.jobId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobVisit = this.createFromForm();
    if (jobVisit.id !== undefined) {
      this.subscribeToSaveResponse(this.jobVisitService.update(jobVisit));
    } else {
      this.subscribeToSaveResponse(this.jobVisitService.create(jobVisit));
    }
  }

  private createFromForm(): IJobVisit {
    return {
      ...new JobVisit(),
      id: this.editForm.get(['id'])!.value,
      arrived: this.editForm.get(['arrived'])!.value ? moment(this.editForm.get(['arrived'])!.value, DATE_TIME_FORMAT) : undefined,
      departed: this.editForm.get(['departed'])!.value ? moment(this.editForm.get(['departed'])!.value, DATE_TIME_FORMAT) : undefined,
      charge: this.editForm.get(['charge'])!.value,
      workCarriedOut: this.editForm.get(['workCarriedOut'])!.value,
      jobId: this.editForm.get(['jobId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobVisit>>): void {
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
