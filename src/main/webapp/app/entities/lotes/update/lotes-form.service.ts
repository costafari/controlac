import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILotes, NewLotes } from '../lotes.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILotes for edit and NewLotesFormGroupInput for create.
 */
type LotesFormGroupInput = ILotes | PartialWithRequiredKeyOf<NewLotes>;

type LotesFormDefaults = Pick<NewLotes, 'id'>;

type LotesFormGroupContent = {
  id: FormControl<ILotes['id'] | NewLotes['id']>;
  cantidad: FormControl<ILotes['cantidad']>;
  fechaEntrada: FormControl<ILotes['fechaEntrada']>;
  lote: FormControl<ILotes['lote']>;
  estado: FormControl<ILotes['estado']>;
  notas: FormControl<ILotes['notas']>;
  proveedores: FormControl<ILotes['proveedores']>;
};

export type LotesFormGroup = FormGroup<LotesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LotesFormService {
  createLotesFormGroup(lotes: LotesFormGroupInput = { id: null }): LotesFormGroup {
    const lotesRawValue = {
      ...this.getFormDefaults(),
      ...lotes,
    };
    return new FormGroup<LotesFormGroupContent>({
      id: new FormControl(
        { value: lotesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cantidad: new FormControl(lotesRawValue.cantidad, {
        validators: [Validators.required],
      }),
      fechaEntrada: new FormControl(lotesRawValue.fechaEntrada, {
        validators: [Validators.required],
      }),
      lote: new FormControl(lotesRawValue.lote, {
        validators: [Validators.required],
      }),
      estado: new FormControl(lotesRawValue.estado, {
        validators: [Validators.required],
      }),
      notas: new FormControl(lotesRawValue.notas),
      proveedores: new FormControl(lotesRawValue.proveedores),
    });
  }

  getLotes(form: LotesFormGroup): ILotes | NewLotes {
    return form.getRawValue() as ILotes | NewLotes;
  }

  resetForm(form: LotesFormGroup, lotes: LotesFormGroupInput): void {
    const lotesRawValue = { ...this.getFormDefaults(), ...lotes };
    form.reset(
      {
        ...lotesRawValue,
        id: { value: lotesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LotesFormDefaults {
    return {
      id: null,
    };
  }
}
