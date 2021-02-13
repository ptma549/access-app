import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { JobVisitUpdateComponent } from 'app/entities/job-visit/job-visit-update.component';
import { JobVisitService } from 'app/entities/job-visit/job-visit.service';
import { JobVisit } from 'app/shared/model/job-visit.model';

describe('Component Tests', () => {
  describe('JobVisit Management Update Component', () => {
    let comp: JobVisitUpdateComponent;
    let fixture: ComponentFixture<JobVisitUpdateComponent>;
    let service: JobVisitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [JobVisitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobVisitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobVisitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobVisitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobVisit(123);
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
        const entity = new JobVisit();
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
