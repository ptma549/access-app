import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AccessAppTestModule } from '../../../test.module';
import { PtmAccountUpdateComponent } from 'app/entities/ptm-account/ptm-account-update.component';
import { PtmAccountService } from 'app/entities/ptm-account/ptm-account.service';
import { PtmAccount } from 'app/shared/model/ptm-account.model';

describe('Component Tests', () => {
  describe('PtmAccount Management Update Component', () => {
    let comp: PtmAccountUpdateComponent;
    let fixture: ComponentFixture<PtmAccountUpdateComponent>;
    let service: PtmAccountService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AccessAppTestModule],
        declarations: [PtmAccountUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PtmAccountUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PtmAccountUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PtmAccountService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PtmAccount(123);
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
        const entity = new PtmAccount();
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
