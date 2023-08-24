import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProveedorAudit, NewProveedorAudit } from '../proveedor-audit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProveedorAudit for edit and NewProveedorAuditFormGroupInput for create.
 */
type ProveedorAuditFormGroupInput = IProveedorAudit | PartialWithRequiredKeyOf<NewProveedorAudit>;

type ProveedorAuditFormDefaults = Pick<NewProveedorAudit, 'id'>;

type ProveedorAuditFormGroupContent = {
  id: FormControl<IProveedorAudit['id'] | NewProveedorAudit['id']>;
  fechaAudit: FormControl<IProveedorAudit['fechaAudit']>;
  tipoCrud: FormControl<IProveedorAudit['tipoCrud']>;
  idProveedor: FormControl<IProveedorAudit['idProveedor']>;
  idUSuario: FormControl<IProveedorAudit['idUSuario']>;
};

export type ProveedorAuditFormGroup = FormGroup<ProveedorAuditFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProveedorAuditFormService {
  createProveedorAuditFormGroup(proveedorAudit: ProveedorAuditFormGroupInput = { id: null }): ProveedorAuditFormGroup {
    const proveedorAuditRawValue = {
      ...this.getFormDefaults(),
      ...proveedorAudit,
    };
    return new FormGroup<ProveedorAuditFormGroupContent>({
      id: new FormControl(
        { value: proveedorAuditRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fechaAudit: new FormControl(proveedorAuditRawValue.fechaAudit, {
        validators: [Validators.required],
      }),
      tipoCrud: new FormControl(proveedorAuditRawValue.tipoCrud, {
        validators: [Validators.required],
      }),
      idProveedor: new FormControl(proveedorAuditRawValue.idProveedor, {
        validators: [Validators.required],
      }),
      idUSuario: new FormControl(proveedorAuditRawValue.idUSuario, {
        validators: [Validators.required],
      }),
    });
  }

  getProveedorAudit(form: ProveedorAuditFormGroup): IProveedorAudit | NewProveedorAudit {
    return form.getRawValue() as IProveedorAudit | NewProveedorAudit;
  }

  resetForm(form: ProveedorAuditFormGroup, proveedorAudit: ProveedorAuditFormGroupInput): void {
    const proveedorAuditRawValue = { ...this.getFormDefaults(), ...proveedorAudit };
    form.reset(
      {
        ...proveedorAuditRawValue,
        id: { value: proveedorAuditRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProveedorAuditFormDefaults {
    return {
      id: null,
    };
  }
}
