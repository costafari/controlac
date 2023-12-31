import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { FacturasFormService, FacturasFormGroup } from './facturas-form.service';
import { IFacturas } from '../facturas.model';
import { FacturasService } from '../service/facturas.service';
import { IDetalles } from 'app/entities/detalles/detalles.model';
import { DetallesService } from 'app/entities/detalles/service/detalles.service';
import { IClientes } from 'app/entities/clientes/clientes.model';
import { ClientesService } from 'app/entities/clientes/service/clientes.service';

@Component({
  standalone: true,
  selector: 'jhi-facturas-update',
  templateUrl: './facturas-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FacturasUpdateComponent implements OnInit {
  isSaving = false;
  facturas: IFacturas | null = null;

  detallesSharedCollection: IDetalles[] = [];
  clientesSharedCollection: IClientes[] = [];

  editForm: FacturasFormGroup = this.facturasFormService.createFacturasFormGroup();

  constructor(
    protected facturasService: FacturasService,
    protected facturasFormService: FacturasFormService,
    protected detallesService: DetallesService,
    protected clientesService: ClientesService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDetalles = (o1: IDetalles | null, o2: IDetalles | null): boolean => this.detallesService.compareDetalles(o1, o2);

  compareClientes = (o1: IClientes | null, o2: IClientes | null): boolean => this.clientesService.compareClientes(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facturas }) => {
      this.facturas = facturas;
      if (facturas) {
        this.updateForm(facturas);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facturas = this.facturasFormService.getFacturas(this.editForm);
    if (facturas.id !== null) {
      this.subscribeToSaveResponse(this.facturasService.update(facturas));
    } else {
      this.subscribeToSaveResponse(this.facturasService.create(facturas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacturas>>): void {
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

  protected updateForm(facturas: IFacturas): void {
    this.facturas = facturas;
    this.facturasFormService.resetForm(this.editForm, facturas);

    this.detallesSharedCollection = this.detallesService.addDetallesToCollectionIfMissing<IDetalles>(
      this.detallesSharedCollection,
      facturas.detalles
    );
    this.clientesSharedCollection = this.clientesService.addClientesToCollectionIfMissing<IClientes>(
      this.clientesSharedCollection,
      facturas.clientes
    );
  }

  protected loadRelationshipsOptions(): void {
    this.detallesService
      .query()
      .pipe(map((res: HttpResponse<IDetalles[]>) => res.body ?? []))
      .pipe(
        map((detalles: IDetalles[]) => this.detallesService.addDetallesToCollectionIfMissing<IDetalles>(detalles, this.facturas?.detalles))
      )
      .subscribe((detalles: IDetalles[]) => (this.detallesSharedCollection = detalles));

    this.clientesService
      .query()
      .pipe(map((res: HttpResponse<IClientes[]>) => res.body ?? []))
      .pipe(
        map((clientes: IClientes[]) => this.clientesService.addClientesToCollectionIfMissing<IClientes>(clientes, this.facturas?.clientes))
      )
      .subscribe((clientes: IClientes[]) => (this.clientesSharedCollection = clientes));
  }
}
