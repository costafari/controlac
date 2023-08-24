import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClientesAudit, NewClientesAudit } from '../clientes-audit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClientesAudit for edit and NewClientesAuditFormGroupInput for create.
 */
type ClientesAuditFormGroupInput = IClientesAudit | PartialWithRequiredKeyOf<NewClientesAudit>;

type ClientesAuditFormDefaults = Pick<NewClientesAudit, 'id'>;

type ClientesAuditFormGroupContent = {
  id: FormControl<IClientesAudit['id'] | NewClientesAudit['id']>;
  fechaAudit: FormControl<IClientesAudit['fechaAudit']>;
  tipoCrud: FormControl<IClientesAudit['tipoCrud']>;
  idCliente: FormControl<IClientesAudit['idCliente']>;
  idUSuario: FormControl<IClientesAudit['idUSuario']>;
};

export type ClientesAuditFormGroup = FormGroup<ClientesAuditFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientesAuditFormService {
  createClientesAuditFormGroup(clientesAudit: ClientesAuditFormGroupInput = { id: null }): ClientesAuditFormGroup {
    const clientesAuditRawValue = {
      ...this.getFormDefaults(),
      ...clientesAudit,
    };
    return new FormGroup<ClientesAuditFormGroupContent>({
      id: new FormControl(
        { value: clientesAuditRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fechaAudit: new FormControl(clientesAuditRawValue.fechaAudit, {
        validators: [Validators.required],
      }),
      tipoCrud: new FormControl(clientesAuditRawValue.tipoCrud, {
        validators: [Validators.required],
      }),
      idCliente: new FormControl(clientesAuditRawValue.idCliente, {
        validators: [Validators.required],
      }),
      idUSuario: new FormControl(clientesAuditRawValue.idUSuario, {
        validators: [Validators.required],
      }),
    });
  }

  getClientesAudit(form: ClientesAuditFormGroup): IClientesAudit | NewClientesAudit {
    return form.getRawValue() as IClientesAudit | NewClientesAudit;
  }

  resetForm(form: ClientesAuditFormGroup, clientesAudit: ClientesAuditFormGroupInput): void {
    const clientesAuditRawValue = { ...this.getFormDefaults(), ...clientesAudit };
    form.reset(
      {
        ...clientesAuditRawValue,
        id: { value: clientesAuditRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ClientesAuditFormDefaults {
    return {
      id: null,
    };
  }
}
