import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobVisit } from 'app/shared/model/job-visit.model';

@Component({
  selector: 'jhi-job-visit-detail',
  templateUrl: './job-visit-detail.component.html',
})
export class JobVisitDetailComponent implements OnInit {
  jobVisit: IJobVisit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobVisit }) => (this.jobVisit = jobVisit));
  }

  previousState(): void {
    window.history.back();
  }
}
