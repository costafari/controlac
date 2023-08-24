import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProveedorAuditComponent } from './list/proveedor-audit.component';
import { ProveedorAuditDetailComponent } from './detail/proveedor-audit-detail.component';
import { ProveedorAuditUpdateComponent } from './update/proveedor-audit-update.component';
import ProveedorAuditResolve from './route/proveedor-audit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const proveedorAuditRoute: Routes = [
  {
    path: '',
    component: ProveedorAuditComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProveedorAuditDetailComponent,
    resolve: {
      proveedorAudit: ProveedorAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProveedorAuditUpdateComponent,
    resolve: {
      proveedorAudit: ProveedorAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProveedorAuditUpdateComponent,
    resolve: {
      proveedorAudit: ProveedorAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default proveedorAuditRoute;
