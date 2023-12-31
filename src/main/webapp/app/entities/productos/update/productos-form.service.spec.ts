import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../productos.test-samples';

import { ProductosFormService } from './productos-form.service';

describe('Productos Form Service', () => {
  let service: ProductosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductosFormService);
  });

  describe('Service methods', () => {
    describe('createProductosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descipcion: expect.any(Object),
            nombre: expect.any(Object),
            precioU: expect.any(Object),
            precioC: expect.any(Object),
            notas: expect.any(Object),
            estadoProducto: expect.any(Object),
            fechaRegistro: expect.any(Object),
            fechaCaducidad: expect.any(Object),
            proveedores: expect.any(Object),
            detalles: expect.any(Object),
          })
        );
      });

      it('passing IProductos should create a new form with FormGroup', () => {
        const formGroup = service.createProductosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descipcion: expect.any(Object),
            nombre: expect.any(Object),
            precioU: expect.any(Object),
            precioC: expect.any(Object),
            notas: expect.any(Object),
            estadoProducto: expect.any(Object),
            fechaRegistro: expect.any(Object),
            fechaCaducidad: expect.any(Object),
            proveedores: expect.any(Object),
            detalles: expect.any(Object),
          })
        );
      });
    });

    describe('getProductos', () => {
      it('should return NewProductos for default Productos initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductosFormGroup(sampleWithNewData);

        const productos = service.getProductos(formGroup) as any;

        expect(productos).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductos for empty Productos initial value', () => {
        const formGroup = service.createProductosFormGroup();

        const productos = service.getProductos(formGroup) as any;

        expect(productos).toMatchObject({});
      });

      it('should return IProductos', () => {
        const formGroup = service.createProductosFormGroup(sampleWithRequiredData);

        const productos = service.getProductos(formGroup) as any;

        expect(productos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductos should not enable id FormControl', () => {
        const formGroup = service.createProductosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductos should disable id FormControl', () => {
        const formGroup = service.createProductosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
