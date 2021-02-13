import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobComment } from 'app/shared/model/job-comment.model';

@Component({
  selector: 'jhi-job-comment-detail',
  templateUrl: './job-comment-detail.component.html',
})
export class JobCommentDetailComponent implements OnInit {
  jobComment: IJobComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobComment }) => (this.jobComment = jobComment));
  }

  previousState(): void {
    window.history.back();
  }
}
