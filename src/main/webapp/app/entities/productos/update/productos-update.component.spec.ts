import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductosFormService } from './productos-form.service';
import { ProductosService } from '../service/productos.service';
import { IProductos } from '../productos.model';
import { IProveedores } from 'app/entities/proveedores/proveedores.model';
import { ProveedoresService } from 'app/entities/proveedores/service/proveedores.service';

import { ProductosUpdateComponent } from './productos-update.component';

describe('Productos Management Update Component', () => {
  let comp: ProductosUpdateComponent;
  let fixture: ComponentFixture<ProductosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productosFormService: ProductosFormService;
  let productosService: ProductosService;
  let proveedoresService: ProveedoresService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProductosUpdateComponent],
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
      .overrideTemplate(ProductosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productosFormService = TestBed.inject(ProductosFormService);
    productosService = TestBed.inject(ProductosService);
    proveedoresService = TestBed.inject(ProveedoresService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Proveedores query and add missing value', () => {
      const productos: IProductos = { id: 456 };
      const proveedores: IProveedores = { id: 4384 };
      productos.proveedores = proveedores;

      const proveedoresCollection: IProveedores[] = [{ id: 15086 }];
      jest.spyOn(proveedoresService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedoresCollection })));
      const additionalProveedores = [proveedores];
      const expectedCollection: IProveedores[] = [...additionalProveedores, ...proveedoresCollection];
      jest.spyOn(proveedoresService, 'addProveedoresToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productos });
      comp.ngOnInit();

      expect(proveedoresService.query).toHaveBeenCalled();
      expect(proveedoresService.addProveedoresToCollectionIfMissing).toHaveBeenCalledWith(
        proveedoresCollection,
        ...additionalProveedores.map(expect.objectContaining)
      );
      expect(comp.proveedoresSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productos: IProductos = { id: 456 };
      const proveedores: IProveedores = { id: 6719 };
      productos.proveedores = proveedores;

      activatedRoute.data = of({ productos });
      comp.ngOnInit();

      expect(comp.proveedoresSharedCollection).toContain(proveedores);
      expect(comp.productos).toEqual(productos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductos>>();
      const productos = { id: 123 };
      jest.spyOn(productosFormService, 'getProductos').mockReturnValue(productos);
      jest.spyOn(productosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productos }));
      saveSubject.complete();

      // THEN
      expect(productosFormService.getProductos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productosService.update).toHaveBeenCalledWith(expect.objectContaining(productos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductos>>();
      const productos = { id: 123 };
      jest.spyOn(productosFormService, 'getProductos').mockReturnValue({ id: null });
      jest.spyOn(productosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productos }));
      saveSubject.complete();

      // THEN
      expect(productosFormService.getProductos).toHaveBeenCalled();
      expect(productosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductos>>();
      const productos = { id: 123 };
      jest.spyOn(productosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProveedores', () => {
      it('Should forward to proveedoresService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(proveedoresService, 'compareProveedores');
        comp.compareProveedores(entity, entity2);
        expect(proveedoresService.compareProveedores).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
