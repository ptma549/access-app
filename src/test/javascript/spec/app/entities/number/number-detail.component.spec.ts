import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { NumberDetailComponent } from 'app/entities/number/number-detail.component';
import { Number } from 'app/shared/model/number.model';

describe('Component Tests', () => {
  describe('Number Management Detail Component', () => {
    let comp: NumberDetailComponent;
    let fixture: ComponentFixture<NumberDetailComponent>;
    const route = ({ data: of({ number: new Number(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [NumberDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NumberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NumberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load number on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.number).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
