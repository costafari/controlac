import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../proveedor-audit.test-samples';

import { ProveedorAuditFormService } from './proveedor-audit-form.service';

describe('ProveedorAudit Form Service', () => {
  let service: ProveedorAuditFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProveedorAuditFormService);
  });

  describe('Service methods', () => {
    describe('createProveedorAuditFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProveedorAuditFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaAudit: expect.any(Object),
            tipoCrud: expect.any(Object),
            idProveedor: expect.any(Object),
            idUSuario: expect.any(Object),
          })
        );
      });

      it('passing IProveedorAudit should create a new form with FormGroup', () => {
        const formGroup = service.createProveedorAuditFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaAudit: expect.any(Object),
            tipoCrud: expect.any(Object),
            idProveedor: expect.any(Object),
            idUSuario: expect.any(Object),
          })
        );
      });
    });

    describe('getProveedorAudit', () => {
      it('should return NewProveedorAudit for default ProveedorAudit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProveedorAuditFormGroup(sampleWithNewData);

        const proveedorAudit = service.getProveedorAudit(formGroup) as any;

        expect(proveedorAudit).toMatchObject(sampleWithNewData);
      });

      it('should return NewProveedorAudit for empty ProveedorAudit initial value', () => {
        const formGroup = service.createProveedorAuditFormGroup();

        const proveedorAudit = service.getProveedorAudit(formGroup) as any;

        expect(proveedorAudit).toMatchObject({});
      });

      it('should return IProveedorAudit', () => {
        const formGroup = service.createProveedorAuditFormGroup(sampleWithRequiredData);

        const proveedorAudit = service.getProveedorAudit(formGroup) as any;

        expect(proveedorAudit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProveedorAudit should not enable id FormControl', () => {
        const formGroup = service.createProveedorAuditFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProveedorAudit should disable id FormControl', () => {
        const formGroup = service.createProveedorAuditFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
