import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ProductosFormService, ProductosFormGroup } from './productos-form.service';
import { IProductos } from '../productos.model';
import { ProductosService } from '../service/productos.service';
import { IProveedores } from 'app/entities/proveedores/proveedores.model';
import { ProveedoresService } from 'app/entities/proveedores/service/proveedores.service';

@Component({
  standalone: true,
  selector: 'jhi-productos-update',
  templateUrl: './productos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProductosUpdateComponent implements OnInit {
  isSaving = false;
  productos: IProductos | null = null;

  proveedoresSharedCollection: IProveedores[] = [];

  editForm: ProductosFormGroup = this.productosFormService.createProductosFormGroup();

  constructor(
    protected productosService: ProductosService,
    protected productosFormService: ProductosFormService,
    protected proveedoresService: ProveedoresService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProveedores = (o1: IProveedores | null, o2: IProveedores | null): boolean => this.proveedoresService.compareProveedores(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productos }) => {
      this.productos = productos;
      if (productos) {
        this.updateForm(productos);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productos = this.productosFormService.getProductos(this.editForm);
    if (productos.id !== null) {
      this.subscribeToSaveResponse(this.productosService.update(productos));
    } else {
      this.subscribeToSaveResponse(this.productosService.create(productos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductos>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(productos: IProductos): void {
    this.productos = productos;
    this.productosFormService.resetForm(this.editForm, productos);

    this.proveedoresSharedCollection = this.proveedoresService.addProveedoresToCollectionIfMissing<IProveedores>(
      this.proveedoresSharedCollection,
      productos.proveedores
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proveedoresService
      .query()
      .pipe(map((res: HttpResponse<IProveedores[]>) => res.body ?? []))
      .pipe(
        map((proveedores: IProveedores[]) =>
          this.proveedoresService.addProveedoresToCollectionIfMissing<IProveedores>(proveedores, this.productos?.proveedores)
        )
      )
      .subscribe((proveedores: IProveedores[]) => (this.proveedoresSharedCollection = proveedores));
  }
}
