import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { JobLineDetailComponent } from 'app/entities/job-line/job-line-detail.component';
import { JobLine } from 'app/shared/model/job-line.model';

describe('Component Tests', () => {
  describe('JobLine Management Detail Component', () => {
    let comp: JobLineDetailComponent;
    let fixture: ComponentFixture<JobLineDetailComponent>;
    const route = ({ data: of({ jobLine: new JobLine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [JobLineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobLineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobLineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobLine on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobLine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
