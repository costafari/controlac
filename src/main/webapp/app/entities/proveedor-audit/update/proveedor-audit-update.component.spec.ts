import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProveedorAuditFormService } from './proveedor-audit-form.service';
import { ProveedorAuditService } from '../service/proveedor-audit.service';
import { IProveedorAudit } from '../proveedor-audit.model';

import { ProveedorAuditUpdateComponent } from './proveedor-audit-update.component';

describe('ProveedorAudit Management Update Component', () => {
  let comp: ProveedorAuditUpdateComponent;
  let fixture: ComponentFixture<ProveedorAuditUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let proveedorAuditFormService: ProveedorAuditFormService;
  let proveedorAuditService: ProveedorAuditService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProveedorAuditUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProveedorAuditUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProveedorAuditUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    proveedorAuditFormService = TestBed.inject(ProveedorAuditFormService);
    proveedorAuditService = TestBed.inject(ProveedorAuditService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const proveedorAudit: IProveedorAudit = { id: 456 };

      activatedRoute.data = of({ proveedorAudit });
      comp.ngOnInit();

      expect(comp.proveedorAudit).toEqual(proveedorAudit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProveedorAudit>>();
      const proveedorAudit = { id: 123 };
      jest.spyOn(proveedorAuditFormService, 'getProveedorAudit').mockReturnValue(proveedorAudit);
      jest.spyOn(proveedorAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proveedorAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proveedorAudit }));
      saveSubject.complete();

      // THEN
      expect(proveedorAuditFormService.getProveedorAudit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(proveedorAuditService.update).toHaveBeenCalledWith(expect.objectContaining(proveedorAudit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProveedorAudit>>();
      const proveedorAudit = { id: 123 };
      jest.spyOn(proveedorAuditFormService, 'getProveedorAudit').mockReturnValue({ id: null });
      jest.spyOn(proveedorAuditService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proveedorAudit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proveedorAudit }));
      saveSubject.complete();

      // THEN
      expect(proveedorAuditFormService.getProveedorAudit).toHaveBeenCalled();
      expect(proveedorAuditService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProveedorAudit>>();
      const proveedorAudit = { id: 123 };
      jest.spyOn(proveedorAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proveedorAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(proveedorAuditService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
