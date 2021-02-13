import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJob, Job } from 'app/shared/model/job.model';
import { JobService } from './job.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client/client.service';

@Component({
  selector: 'jhi-job-update',
  templateUrl: './job-update.component.html',
})
export class JobUpdateComponent implements OnInit {
  isSaving = false;
  clients: IClient[] = [];

  editForm = this.fb.group({
    id: [],
    reportedBy: [],
    clientOrderRef: [],
    raiseDate: [],
    priority: [],
    fault: [],
    instructions: [],
    occupier: [],
    homeTel: [],
    workTel: [],
    mobileTel: [],
    tenantName: [],
    complete: [],
    position: [],
    invoice: [],
    invoiceDetails: [],
    clientId: [],
  });

  constructor(
    protected jobService: JobService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ job }) => {
      this.updateForm(job);

      this.clientService.query().subscribe((res: HttpResponse<IClient[]>) => (this.clients = res.body || []));
    });
  }

  updateForm(job: IJob): void {
    this.editForm.patchValue({
      id: job.id,
      reportedBy: job.reportedBy,
      clientOrderRef: job.clientOrderRef,
      raiseDate: job.raiseDate,
      priority: job.priority,
      fault: job.fault,
      instructions: job.instructions,
      occupier: job.occupier,
      homeTel: job.homeTel,
      workTel: job.workTel,
      mobileTel: job.mobileTel,
      tenantName: job.tenantName,
      complete: job.complete,
      position: job.position,
      invoice: job.invoice,
      invoiceDetails: job.invoiceDetails,
      clientId: job.clientId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const job = this.createFromForm();
    if (job.id !== undefined) {
      this.subscribeToSaveResponse(this.jobService.update(job));
    } else {
      this.subscribeToSaveResponse(this.jobService.create(job));
    }
  }

  private createFromForm(): IJob {
    return {
      ...new Job(),
      id: this.editForm.get(['id'])!.value,
      reportedBy: this.editForm.get(['reportedBy'])!.value,
      clientOrderRef: this.editForm.get(['clientOrderRef'])!.value,
      raiseDate: this.editForm.get(['raiseDate'])!.value,
      priority: this.editForm.get(['priority'])!.value,
      fault: this.editForm.get(['fault'])!.value,
      instructions: this.editForm.get(['instructions'])!.value,
      occupier: this.editForm.get(['occupier'])!.value,
      homeTel: this.editForm.get(['homeTel'])!.value,
      workTel: this.editForm.get(['workTel'])!.value,
      mobileTel: this.editForm.get(['mobileTel'])!.value,
      tenantName: this.editForm.get(['tenantName'])!.value,
      complete: this.editForm.get(['complete'])!.value,
      position: this.editForm.get(['position'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
      invoiceDetails: this.editForm.get(['invoiceDetails'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJob>>): void {
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
