import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ProductoAuditFormService, ProductoAuditFormGroup } from './producto-audit-form.service';
import { IProductoAudit } from '../producto-audit.model';
import { ProductoAuditService } from '../service/producto-audit.service';

@Component({
  standalone: true,
  selector: 'jhi-producto-audit-update',
  templateUrl: './producto-audit-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProductoAuditUpdateComponent implements OnInit {
  isSaving = false;
  productoAudit: IProductoAudit | null = null;

  editForm: ProductoAuditFormGroup = this.productoAuditFormService.createProductoAuditFormGroup();

  constructor(
    protected productoAuditService: ProductoAuditService,
    protected productoAuditFormService: ProductoAuditFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productoAudit }) => {
      this.productoAudit = productoAudit;
      if (productoAudit) {
        this.updateForm(productoAudit);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productoAudit = this.productoAuditFormService.getProductoAudit(this.editForm);
    if (productoAudit.id !== null) {
      this.subscribeToSaveResponse(this.productoAuditService.update(productoAudit));
    } else {
      this.subscribeToSaveResponse(this.productoAuditService.create(productoAudit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoAudit>>): void {
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

  protected updateForm(productoAudit: IProductoAudit): void {
    this.productoAudit = productoAudit;
    this.productoAuditFormService.resetForm(this.editForm, productoAudit);
  }
}
