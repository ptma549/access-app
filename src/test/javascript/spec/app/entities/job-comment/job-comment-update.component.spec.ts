import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { JobCommentUpdateComponent } from 'app/entities/job-comment/job-comment-update.component';
import { JobCommentService } from 'app/entities/job-comment/job-comment.service';
import { JobComment } from 'app/shared/model/job-comment.model';

describe('Component Tests', () => {
  describe('JobComment Management Update Component', () => {
    let comp: JobCommentUpdateComponent;
    let fixture: ComponentFixture<JobCommentUpdateComponent>;
    let service: JobCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [JobCommentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobCommentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobCommentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobComment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobComment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
