import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductos, NewProductos } from '../productos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductos for edit and NewProductosFormGroupInput for create.
 */
type ProductosFormGroupInput = IProductos | PartialWithRequiredKeyOf<NewProductos>;

type ProductosFormDefaults = Pick<NewProductos, 'id' | 'detalles'>;

type ProductosFormGroupContent = {
  id: FormControl<IProductos['id'] | NewProductos['id']>;
  descipcion: FormControl<IProductos['descipcion']>;
  nombre: FormControl<IProductos['nombre']>;
  precioU: FormControl<IProductos['precioU']>;
  precioC: FormControl<IProductos['precioC']>;
  notas: FormControl<IProductos['notas']>;
  estadoProducto: FormControl<IProductos['estadoProducto']>;
  fechaRegistro: FormControl<IProductos['fechaRegistro']>;
  fechaCaducidad: FormControl<IProductos['fechaCaducidad']>;
  proveedores: FormControl<IProductos['proveedores']>;
  detalles: FormControl<IProductos['detalles']>;
};

export type ProductosFormGroup = FormGroup<ProductosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductosFormService {
  createProductosFormGroup(productos: ProductosFormGroupInput = { id: null }): ProductosFormGroup {
    const productosRawValue = {
      ...this.getFormDefaults(),
      ...productos,
    };
    return new FormGroup<ProductosFormGroupContent>({
      id: new FormControl(
        { value: productosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descipcion: new FormControl(productosRawValue.descipcion, {
        validators: [Validators.required],
      }),
      nombre: new FormControl(productosRawValue.nombre, {
        validators: [Validators.required],
      }),
      precioU: new FormControl(productosRawValue.precioU, {
        validators: [Validators.required],
      }),
      precioC: new FormControl(productosRawValue.precioC, {
        validators: [Validators.required],
      }),
      notas: new FormControl(productosRawValue.notas),
      estadoProducto: new FormControl(productosRawValue.estadoProducto, {
        validators: [Validators.required],
      }),
      fechaRegistro: new FormControl(productosRawValue.fechaRegistro, {
        validators: [Validators.required],
      }),
      fechaCaducidad: new FormControl(productosRawValue.fechaCaducidad, {
        validators: [Validators.required],
      }),
      proveedores: new FormControl(productosRawValue.proveedores),
      detalles: new FormControl(productosRawValue.detalles ?? []),
    });
  }

  getProductos(form: ProductosFormGroup): IProductos | NewProductos {
    return form.getRawValue() as IProductos | NewProductos;
  }

  resetForm(form: ProductosFormGroup, productos: ProductosFormGroupInput): void {
    const productosRawValue = { ...this.getFormDefaults(), ...productos };
    form.reset(
      {
        ...productosRawValue,
        id: { value: productosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductosFormDefaults {
    return {
      id: null,
      detalles: [],
    };
  }
}
