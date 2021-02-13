import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { TownDetailComponent } from 'app/entities/town/town-detail.component';
import { Town } from 'app/shared/model/town.model';

describe('Component Tests', () => {
  describe('Town Management Detail Component', () => {
    let comp: TownDetailComponent;
    let fixture: ComponentFixture<TownDetailComponent>;
    const route = ({ data: of({ town: new Town(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [TownDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TownDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TownDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load town on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.town).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
