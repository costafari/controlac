import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IProveedorAudit } from '../proveedor-audit.model';
import { ProveedorAuditService } from '../service/proveedor-audit.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './proveedor-audit-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProveedorAuditDeleteDialogComponent {
  proveedorAudit?: IProveedorAudit;

  constructor(protected proveedorAuditService: ProveedorAuditService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proveedorAuditService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
