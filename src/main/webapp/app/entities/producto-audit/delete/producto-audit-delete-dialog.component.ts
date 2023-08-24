import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IProductoAudit } from '../producto-audit.model';
import { ProductoAuditService } from '../service/producto-audit.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './producto-audit-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProductoAuditDeleteDialogComponent {
  productoAudit?: IProductoAudit;

  constructor(protected productoAuditService: ProductoAuditService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productoAuditService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
