import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { PtmAccountDetailComponent } from 'app/entities/ptm-account/ptm-account-detail.component';
import { PtmAccount } from 'app/shared/model/ptm-account.model';

describe('Component Tests', () => {
  describe('PtmAccount Management Detail Component', () => {
    let comp: PtmAccountDetailComponent;
    let fixture: ComponentFixture<PtmAccountDetailComponent>;
    const route = ({ data: of({ ptmAccount: new PtmAccount(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [PtmAccountDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PtmAccountDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PtmAccountDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ptmAccount on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ptmAccount).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
