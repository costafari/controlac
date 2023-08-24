import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../clientes-audit.test-samples';

import { ClientesAuditFormService } from './clientes-audit-form.service';

describe('ClientesAudit Form Service', () => {
  let service: ClientesAuditFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientesAuditFormService);
  });

  describe('Service methods', () => {
    describe('createClientesAuditFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createClientesAuditFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaAudit: expect.any(Object),
            tipoCrud: expect.any(Object),
            idCliente: expect.any(Object),
            idUSuario: expect.any(Object),
          })
        );
      });

      it('passing IClientesAudit should create a new form with FormGroup', () => {
        const formGroup = service.createClientesAuditFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaAudit: expect.any(Object),
            tipoCrud: expect.any(Object),
            idCliente: expect.any(Object),
            idUSuario: expect.any(Object),
          })
        );
      });
    });

    describe('getClientesAudit', () => {
      it('should return NewClientesAudit for default ClientesAudit initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createClientesAuditFormGroup(sampleWithNewData);

        const clientesAudit = service.getClientesAudit(formGroup) as any;

        expect(clientesAudit).toMatchObject(sampleWithNewData);
      });

      it('should return NewClientesAudit for empty ClientesAudit initial value', () => {
        const formGroup = service.createClientesAuditFormGroup();

        const clientesAudit = service.getClientesAudit(formGroup) as any;

        expect(clientesAudit).toMatchObject({});
      });

      it('should return IClientesAudit', () => {
        const formGroup = service.createClientesAuditFormGroup(sampleWithRequiredData);

        const clientesAudit = service.getClientesAudit(formGroup) as any;

        expect(clientesAudit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IClientesAudit should not enable id FormControl', () => {
        const formGroup = service.createClientesAuditFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewClientesAudit should disable id FormControl', () => {
        const formGroup = service.createClientesAuditFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
