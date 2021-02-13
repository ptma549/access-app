import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { JobCommentDetailComponent } from 'app/entities/job-comment/job-comment-detail.component';
import { JobComment } from 'app/shared/model/job-comment.model';

describe('Component Tests', () => {
  describe('JobComment Management Detail Component', () => {
    let comp: JobCommentDetailComponent;
    let fixture: ComponentFixture<JobCommentDetailComponent>;
    const route = ({ data: of({ jobComment: new JobComment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [JobCommentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobComment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobComment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
