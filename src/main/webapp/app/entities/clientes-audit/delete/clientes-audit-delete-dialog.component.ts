import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IClientesAudit } from '../clientes-audit.model';
import { ClientesAuditService } from '../service/clientes-audit.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './clientes-audit-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ClientesAuditDeleteDialogComponent {
  clientesAudit?: IClientesAudit;

  constructor(protected clientesAuditService: ClientesAuditService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clientesAuditService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
