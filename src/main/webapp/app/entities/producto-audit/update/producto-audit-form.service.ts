import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductoAudit, NewProductoAudit } from '../producto-audit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductoAudit for edit and NewProductoAuditFormGroupInput for create.
 */
type ProductoAuditFormGroupInput = IProductoAudit | PartialWithRequiredKeyOf<NewProductoAudit>;

type ProductoAuditFormDefaults = Pick<NewProductoAudit, 'id'>;

type ProductoAuditFormGroupContent = {
  id: FormControl<IProductoAudit['id'] | NewProductoAudit['id']>;
  fechaAudit: FormControl<IProductoAudit['fechaAudit']>;
  tipoCrud: FormControl<IProductoAudit['tipoCrud']>;
  idProducto: FormControl<IProductoAudit['idProducto']>;
  idUSuario: FormControl<IProductoAudit['idUSuario']>;
};

export type ProductoAuditFormGroup = FormGroup<ProductoAuditFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductoAuditFormService {
  createProductoAuditFormGroup(productoAudit: ProductoAuditFormGroupInput = { id: null }): ProductoAuditFormGroup {
    const productoAuditRawValue = {
      ...this.getFormDefaults(),
      ...productoAudit,
    };
    return new FormGroup<ProductoAuditFormGroupContent>({
      id: new FormControl(
        { value: productoAuditRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      fechaAudit: new FormControl(productoAuditRawValue.fechaAudit, {
        validators: [Validators.required],
      }),
      tipoCrud: new FormControl(productoAuditRawValue.tipoCrud, {
        validators: [Validators.required],
      }),
      idProducto: new FormControl(productoAuditRawValue.idProducto, {
        validators: [Validators.required],
      }),
      idUSuario: new FormControl(productoAuditRawValue.idUSuario, {
        validators: [Validators.required],
      }),
    });
  }

  getProductoAudit(form: ProductoAuditFormGroup): IProductoAudit | NewProductoAudit {
    return form.getRawValue() as IProductoAudit | NewProductoAudit;
  }

  resetForm(form: ProductoAuditFormGroup, productoAudit: ProductoAuditFormGroupInput): void {
    const productoAuditRawValue = { ...this.getFormDefaults(), ...productoAudit };
    form.reset(
      {
        ...productoAuditRawValue,
        id: { value: productoAuditRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductoAuditFormDefaults {
    return {
      id: null,
    };
  }
}
