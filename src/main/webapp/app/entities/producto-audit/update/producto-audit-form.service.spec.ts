import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../producto-audit.test-samples';

import { ProductoAuditFormService } from './producto-audit-form.service';

describe('ProductoAudit Form Service', () => {
  let service: ProductoAuditFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductoAuditFormService);
  });

  describe('Service methods', () => {
    describe('createProductoAuditFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductoAuditFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaAudit: expect.any(Object),
            tipoCrud: expect.any(Object),
            idProducto: expect.any(Object),
            idUSuario: expect.any(Object),
          })
        );
      });

      it('passing IProductoAudit should create a new form with FormGroup', () => {
        const formGroup = service.createProductoAuditFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaAudit: expect.any(Object),
            tipoCrud: expect.any(Object),
            idProducto: expect.any(Object),
            idUSuario: expect.any(Object),
          })
        );
      });
    });

    describe('getProductoAudit', () => {
      it('should return NewProductoAudit for default ProductoAudit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductoAuditFormGroup(sampleWithNewData);

        const productoAudit = service.getProductoAudit(formGroup) as any;

        expect(productoAudit).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductoAudit for empty ProductoAudit initial value', () => {
        const formGroup = service.createProductoAuditFormGroup();

        const productoAudit = service.getProductoAudit(formGroup) as any;

        expect(productoAudit).toMatchObject({});
      });

      it('should return IProductoAudit', () => {
        const formGroup = service.createProductoAuditFormGroup(sampleWithRequiredData);

        const productoAudit = service.getProductoAudit(formGroup) as any;

        expect(productoAudit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductoAudit should not enable id FormControl', () => {
        const formGroup = service.createProductoAuditFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductoAudit should disable id FormControl', () => {
        const formGroup = service.createProductoAuditFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
