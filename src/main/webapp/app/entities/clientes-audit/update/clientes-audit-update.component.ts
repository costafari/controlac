import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ClientesAuditFormService, ClientesAuditFormGroup } from './clientes-audit-form.service';
import { IClientesAudit } from '../clientes-audit.model';
import { ClientesAuditService } from '../service/clientes-audit.service';

@Component({
  standalone: true,
  selector: 'jhi-clientes-audit-update',
  templateUrl: './clientes-audit-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ClientesAuditUpdateComponent implements OnInit {
  isSaving = false;
  clientesAudit: IClientesAudit | null = null;

  editForm: ClientesAuditFormGroup = this.clientesAuditFormService.createClientesAuditFormGroup();

  constructor(
    protected clientesAuditService: ClientesAuditService,
    protected clientesAuditFormService: ClientesAuditFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientesAudit }) => {
      this.clientesAudit = clientesAudit;
      if (clientesAudit) {
        this.updateForm(clientesAudit);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const clientesAudit = this.clientesAuditFormService.getClientesAudit(this.editForm);
    if (clientesAudit.id !== null) {
      this.subscribeToSaveResponse(this.clientesAuditService.update(clientesAudit));
    } else {
      this.subscribeToSaveResponse(this.clientesAuditService.create(clientesAudit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientesAudit>>): void {
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

  protected updateForm(clientesAudit: IClientesAudit): void {
    this.clientesAudit = clientesAudit;
    this.clientesAuditFormService.resetForm(this.editForm, clientesAudit);
  }
}
