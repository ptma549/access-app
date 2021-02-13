import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INumber } from 'app/shared/model/number.model';

@Component({
  selector: 'jhi-number-detail',
  templateUrl: './number-detail.component.html',
})
export class NumberDetailComponent implements OnInit {
  number: INumber | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ number }) => (this.number = number));
  }

  previousState(): void {
    window.history.back();
  }
}
