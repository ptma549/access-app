import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJobComment, JobComment } from 'app/shared/model/job-comment.model';
import { JobCommentService } from './job-comment.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job/job.service';

@Component({
  selector: 'jhi-job-comment-update',
  templateUrl: './job-comment-update.component.html',
})
export class JobCommentUpdateComponent implements OnInit {
  isSaving = false;
  jobs: IJob[] = [];

  editForm = this.fb.group({
    id: [],
    comment: [],
    jobId: [],
  });

  constructor(
    protected jobCommentService: JobCommentService,
    protected jobService: JobService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobComment }) => {
      this.updateForm(jobComment);

      this.jobService.query().subscribe((res: HttpResponse<IJob[]>) => (this.jobs = res.body || []));
    });
  }

  updateForm(jobComment: IJobComment): void {
    this.editForm.patchValue({
      id: jobComment.id,
      comment: jobComment.comment,
      jobId: jobComment.jobId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobComment = this.createFromForm();
    if (jobComment.id !== undefined) {
      this.subscribeToSaveResponse(this.jobCommentService.update(jobComment));
    } else {
      this.subscribeToSaveResponse(this.jobCommentService.create(jobComment));
    }
  }

  private createFromForm(): IJobComment {
    return {
      ...new JobComment(),
      id: this.editForm.get(['id'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      jobId: this.editForm.get(['jobId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobComment>>): void {
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
