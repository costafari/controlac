import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductoAuditFormService } from './producto-audit-form.service';
import { ProductoAuditService } from '../service/producto-audit.service';
import { IProductoAudit } from '../producto-audit.model';

import { ProductoAuditUpdateComponent } from './producto-audit-update.component';

describe('ProductoAudit Management Update Component', () => {
  let comp: ProductoAuditUpdateComponent;
  let fixture: ComponentFixture<ProductoAuditUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productoAuditFormService: ProductoAuditFormService;
  let productoAuditService: ProductoAuditService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProductoAuditUpdateComponent],
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
      .overrideTemplate(ProductoAuditUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductoAuditUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productoAuditFormService = TestBed.inject(ProductoAuditFormService);
    productoAuditService = TestBed.inject(ProductoAuditService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productoAudit: IProductoAudit = { id: 456 };

      activatedRoute.data = of({ productoAudit });
      comp.ngOnInit();

      expect(comp.productoAudit).toEqual(productoAudit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductoAudit>>();
      const productoAudit = { id: 123 };
      jest.spyOn(productoAuditFormService, 'getProductoAudit').mockReturnValue(productoAudit);
      jest.spyOn(productoAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productoAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productoAudit }));
      saveSubject.complete();

      // THEN
      expect(productoAuditFormService.getProductoAudit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productoAuditService.update).toHaveBeenCalledWith(expect.objectContaining(productoAudit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductoAudit>>();
      const productoAudit = { id: 123 };
      jest.spyOn(productoAuditFormService, 'getProductoAudit').mockReturnValue({ id: null });
      jest.spyOn(productoAuditService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productoAudit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productoAudit }));
      saveSubject.complete();

      // THEN
      expect(productoAuditFormService.getProductoAudit).toHaveBeenCalled();
      expect(productoAuditService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductoAudit>>();
      const productoAudit = { id: 123 };
      jest.spyOn(productoAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productoAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productoAuditService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
