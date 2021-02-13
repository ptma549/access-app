import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITown } from 'app/shared/model/town.model';

@Component({
  selector: 'jhi-town-detail',
  templateUrl: './town-detail.component.html',
})
export class TownDetailComponent implements OnInit {
  town: ITown | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ town }) => (this.town = town));
  }

  previousState(): void {
    window.history.back();
  }
}
