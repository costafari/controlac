import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ProveedorAuditFormService, ProveedorAuditFormGroup } from './proveedor-audit-form.service';
import { IProveedorAudit } from '../proveedor-audit.model';
import { ProveedorAuditService } from '../service/proveedor-audit.service';

@Component({
  standalone: true,
  selector: 'jhi-proveedor-audit-update',
  templateUrl: './proveedor-audit-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProveedorAuditUpdateComponent implements OnInit {
  isSaving = false;
  proveedorAudit: IProveedorAudit | null = null;

  editForm: ProveedorAuditFormGroup = this.proveedorAuditFormService.createProveedorAuditFormGroup();

  constructor(
    protected proveedorAuditService: ProveedorAuditService,
    protected proveedorAuditFormService: ProveedorAuditFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proveedorAudit }) => {
      this.proveedorAudit = proveedorAudit;
      if (proveedorAudit) {
        this.updateForm(proveedorAudit);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proveedorAudit = this.proveedorAuditFormService.getProveedorAudit(this.editForm);
    if (proveedorAudit.id !== null) {
      this.subscribeToSaveResponse(this.proveedorAuditService.update(proveedorAudit));
    } else {
      this.subscribeToSaveResponse(this.proveedorAuditService.create(proveedorAudit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedorAudit>>): void {
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

  protected updateForm(proveedorAudit: IProveedorAudit): void {
    this.proveedorAudit = proveedorAudit;
    this.proveedorAuditFormService.resetForm(this.editForm, proveedorAudit);
  }
}
