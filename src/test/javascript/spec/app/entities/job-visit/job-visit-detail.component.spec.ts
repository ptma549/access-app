import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { JobVisitDetailComponent } from 'app/entities/job-visit/job-visit-detail.component';
import { JobVisit } from 'app/shared/model/job-visit.model';

describe('Component Tests', () => {
  describe('JobVisit Management Detail Component', () => {
    let comp: JobVisitDetailComponent;
    let fixture: ComponentFixture<JobVisitDetailComponent>;
    const route = ({ data: of({ jobVisit: new JobVisit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [JobVisitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobVisitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobVisitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobVisit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobVisit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
