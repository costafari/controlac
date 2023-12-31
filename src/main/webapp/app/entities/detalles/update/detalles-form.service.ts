import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDetalles, NewDetalles } from '../detalles.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDetalles for edit and NewDetallesFormGroupInput for create.
 */
type DetallesFormGroupInput = IDetalles | PartialWithRequiredKeyOf<NewDetalles>;

type DetallesFormDefaults = Pick<NewDetalles, 'id' | 'productos'>;

type DetallesFormGroupContent = {
  id: FormControl<IDetalles['id'] | NewDetalles['id']>;
  cantidad: FormControl<IDetalles['cantidad']>;
  impuestos: FormControl<IDetalles['impuestos']>;
  descuento: FormControl<IDetalles['descuento']>;
  total: FormControl<IDetalles['total']>;
  productos: FormControl<IDetalles['productos']>;
};

export type DetallesFormGroup = FormGroup<DetallesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DetallesFormService {
  createDetallesFormGroup(detalles: DetallesFormGroupInput = { id: null }): DetallesFormGroup {
    const detallesRawValue = {
      ...this.getFormDefaults(),
      ...detalles,
    };
    return new FormGroup<DetallesFormGroupContent>({
      id: new FormControl(
        { value: detallesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cantidad: new FormControl(detallesRawValue.cantidad, {
        validators: [Validators.required],
      }),
      impuestos: new FormControl(detallesRawValue.impuestos, {
        validators: [Validators.required],
      }),
      descuento: new FormControl(detallesRawValue.descuento, {
        validators: [Validators.required],
      }),
      total: new FormControl(detallesRawValue.total),
      productos: new FormControl(detallesRawValue.productos ?? []),
    });
  }

  getDetalles(form: DetallesFormGroup): IDetalles | NewDetalles {
    return form.getRawValue() as IDetalles | NewDetalles;
  }

  resetForm(form: DetallesFormGroup, detalles: DetallesFormGroupInput): void {
    const detallesRawValue = { ...this.getFormDefaults(), ...detalles };
    form.reset(
      {
        ...detallesRawValue,
        id: { value: detallesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DetallesFormDefaults {
    return {
      id: null,
      productos: [],
    };
  }
}
