import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DetallesFormService, DetallesFormGroup } from './detalles-form.service';
import { IDetalles } from '../detalles.model';
import { DetallesService } from '../service/detalles.service';

@Component({
  standalone: true,
  selector: 'jhi-detalles-update',
  templateUrl: './detalles-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DetallesUpdateComponent implements OnInit {
  isSaving = false;
  detalles: IDetalles | null = null;

  editForm: DetallesFormGroup = this.detallesFormService.createDetallesFormGroup();

  constructor(
    protected detallesService: DetallesService,
    protected detallesFormService: DetallesFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detalles }) => {
      this.detalles = detalles;
      if (detalles) {
        this.updateForm(detalles);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const detalles = this.detallesFormService.getDetalles(this.editForm);
    if (detalles.id !== null) {
      this.subscribeToSaveResponse(this.detallesService.update(detalles));
    } else {
      this.subscribeToSaveResponse(this.detallesService.create(detalles));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalles>>): void {
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

  protected updateForm(detalles: IDetalles): void {
    this.detalles = detalles;
    this.detallesFormService.resetForm(this.editForm, detalles);
  }
}
