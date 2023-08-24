import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductoAuditComponent } from './list/producto-audit.component';
import { ProductoAuditDetailComponent } from './detail/producto-audit-detail.component';
import { ProductoAuditUpdateComponent } from './update/producto-audit-update.component';
import ProductoAuditResolve from './route/producto-audit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productoAuditRoute: Routes = [
  {
    path: '',
    component: ProductoAuditComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductoAuditDetailComponent,
    resolve: {
      productoAudit: ProductoAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductoAuditUpdateComponent,
    resolve: {
      productoAudit: ProductoAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductoAuditUpdateComponent,
    resolve: {
      productoAudit: ProductoAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default productoAuditRoute;
