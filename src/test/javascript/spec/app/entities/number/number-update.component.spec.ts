import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { NumberUpdateComponent } from 'app/entities/number/number-update.component';
import { NumberService } from 'app/entities/number/number.service';
import { Number } from 'app/shared/model/number.model';

describe('Component Tests', () => {
  describe('Number Management Update Component', () => {
    let comp: NumberUpdateComponent;
    let fixture: ComponentFixture<NumberUpdateComponent>;
    let service: NumberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [NumberUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NumberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NumberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NumberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Number(123);
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
        const entity = new Number();
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
