import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { JobLineUpdateComponent } from 'app/entities/job-line/job-line-update.component';
import { JobLineService } from 'app/entities/job-line/job-line.service';
import { JobLine } from 'app/shared/model/job-line.model';

describe('Component Tests', () => {
  describe('JobLine Management Update Component', () => {
    let comp: JobLineUpdateComponent;
    let fixture: ComponentFixture<JobLineUpdateComponent>;
    let service: JobLineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [JobLineUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobLineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobLineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobLineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobLine(123);
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
        const entity = new JobLine();
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
