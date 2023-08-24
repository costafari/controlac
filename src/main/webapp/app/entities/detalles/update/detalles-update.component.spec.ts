import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DetallesFormService } from './detalles-form.service';
import { DetallesService } from '../service/detalles.service';
import { IDetalles } from '../detalles.model';
import { IProductos } from 'app/entities/productos/productos.model';
import { ProductosService } from 'app/entities/productos/service/productos.service';

import { DetallesUpdateComponent } from './detalles-update.component';

describe('Detalles Management Update Component', () => {
  let comp: DetallesUpdateComponent;
  let fixture: ComponentFixture<DetallesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let detallesFormService: DetallesFormService;
  let detallesService: DetallesService;
  let productosService: ProductosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DetallesUpdateComponent],
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
      .overrideTemplate(DetallesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DetallesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    detallesFormService = TestBed.inject(DetallesFormService);
    detallesService = TestBed.inject(DetallesService);
    productosService = TestBed.inject(ProductosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Productos query and add missing value', () => {
      const detalles: IDetalles = { id: 456 };
      const productos: IProductos[] = [{ id: 19287 }];
      detalles.productos = productos;

      const productosCollection: IProductos[] = [{ id: 783 }];
      jest.spyOn(productosService, 'query').mockReturnValue(of(new HttpResponse({ body: productosCollection })));
      const additionalProductos = [...productos];
      const expectedCollection: IProductos[] = [...additionalProductos, ...productosCollection];
      jest.spyOn(productosService, 'addProductosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ detalles });
      comp.ngOnInit();

      expect(productosService.query).toHaveBeenCalled();
      expect(productosService.addProductosToCollectionIfMissing).toHaveBeenCalledWith(
        productosCollection,
        ...additionalProductos.map(expect.objectContaining)
      );
      expect(comp.productosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const detalles: IDetalles = { id: 456 };
      const productos: IProductos = { id: 6585 };
      detalles.productos = [productos];

      activatedRoute.data = of({ detalles });
      comp.ngOnInit();

      expect(comp.productosSharedCollection).toContain(productos);
      expect(comp.detalles).toEqual(detalles);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalles>>();
      const detalles = { id: 123 };
      jest.spyOn(detallesFormService, 'getDetalles').mockReturnValue(detalles);
      jest.spyOn(detallesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalles }));
      saveSubject.complete();

      // THEN
      expect(detallesFormService.getDetalles).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(detallesService.update).toHaveBeenCalledWith(expect.objectContaining(detalles));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalles>>();
      const detalles = { id: 123 };
      jest.spyOn(detallesFormService, 'getDetalles').mockReturnValue({ id: null });
      jest.spyOn(detallesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalles: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: detalles }));
      saveSubject.complete();

      // THEN
      expect(detallesFormService.getDetalles).toHaveBeenCalled();
      expect(detallesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDetalles>>();
      const detalles = { id: 123 };
      jest.spyOn(detallesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ detalles });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(detallesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProductos', () => {
      it('Should forward to productosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productosService, 'compareProductos');
        comp.compareProductos(entity, entity2);
        expect(productosService.compareProductos).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
