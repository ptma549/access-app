import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobLine } from 'app/shared/model/job-line.model';

@Component({
  selector: 'jhi-job-line-detail',
  templateUrl: './job-line-detail.component.html',
})
export class JobLineDetailComponent implements OnInit {
  jobLine: IJobLine | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobLine }) => (this.jobLine = jobLine));
  }

  previousState(): void {
    window.history.back();
  }
}
