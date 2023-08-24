import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClientes, NewClientes } from '../clientes.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClientes for edit and NewClientesFormGroupInput for create.
 */
type ClientesFormGroupInput = IClientes | PartialWithRequiredKeyOf<NewClientes>;

type ClientesFormDefaults = Pick<NewClientes, 'id' | 'estadoCliente'>;

type ClientesFormGroupContent = {
  id: FormControl<IClientes['id'] | NewClientes['id']>;
  estadoCliente: FormControl<IClientes['estadoCliente']>;
  nombresContacto: FormControl<IClientes['nombresContacto']>;
  apellidoContacto: FormControl<IClientes['apellidoContacto']>;
  direccion: FormControl<IClientes['direccion']>;
  email: FormControl<IClientes['email']>;
  nombreEmpresa: FormControl<IClientes['nombreEmpresa']>;
  regFiscal: FormControl<IClientes['regFiscal']>;
  giro: FormControl<IClientes['giro']>;
  notas: FormControl<IClientes['notas']>;
  sitioWeb: FormControl<IClientes['sitioWeb']>;
  telefonoFijo: FormControl<IClientes['telefonoFijo']>;
  telefonoFijo2: FormControl<IClientes['telefonoFijo2']>;
  telefonoMovil: FormControl<IClientes['telefonoMovil']>;
  telefonoMovil2: FormControl<IClientes['telefonoMovil2']>;
  fechaRegistro: FormControl<IClientes['fechaRegistro']>;
  fechaUltimaC: FormControl<IClientes['fechaUltimaC']>;
};

export type ClientesFormGroup = FormGroup<ClientesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClientesFormService {
  createClientesFormGroup(clientes: ClientesFormGroupInput = { id: null }): ClientesFormGroup {
    const clientesRawValue = {
      ...this.getFormDefaults(),
      ...clientes,
    };
    return new FormGroup<ClientesFormGroupContent>({
      id: new FormControl(
        { value: clientesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      estadoCliente: new FormControl(clientesRawValue.estadoCliente, {
        validators: [Validators.required],
      }),
      nombresContacto: new FormControl(clientesRawValue.nombresContacto, {
        validators: [Validators.required],
      }),
      apellidoContacto: new FormControl(clientesRawValue.apellidoContacto, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(clientesRawValue.direccion, {
        validators: [Validators.required],
      }),
      email: new FormControl(clientesRawValue.email, {
        validators: [Validators.required],
      }),
      nombreEmpresa: new FormControl(clientesRawValue.nombreEmpresa),
      regFiscal: new FormControl(clientesRawValue.regFiscal),
      giro: new FormControl(clientesRawValue.giro),
      notas: new FormControl(clientesRawValue.notas),
      sitioWeb: new FormControl(clientesRawValue.sitioWeb),
      telefonoFijo: new FormControl(clientesRawValue.telefonoFijo, {
        validators: [Validators.required],
      }),
      telefonoFijo2: new FormControl(clientesRawValue.telefonoFijo2),
      telefonoMovil: new FormControl(clientesRawValue.telefonoMovil, {
        validators: [Validators.required],
      }),
      telefonoMovil2: new FormControl(clientesRawValue.telefonoMovil2),
      fechaRegistro: new FormControl(clientesRawValue.fechaRegistro, {
        validators: [Validators.required],
      }),
      fechaUltimaC: new FormControl(clientesRawValue.fechaUltimaC, {
        validators: [Validators.required],
      }),
    });
  }

  getClientes(form: ClientesFormGroup): IClientes | NewClientes {
    return form.getRawValue() as IClientes | NewClientes;
  }

  resetForm(form: ClientesFormGroup, clientes: ClientesFormGroupInput): void {
    const clientesRawValue = { ...this.getFormDefaults(), ...clientes };
    form.reset(
      {
        ...clientesRawValue,
        id: { value: clientesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ClientesFormDefaults {
    return {
      id: null,
      estadoCliente: false,
    };
  }
}
