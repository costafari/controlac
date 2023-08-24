import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClientesAuditComponent } from './list/clientes-audit.component';
import { ClientesAuditDetailComponent } from './detail/clientes-audit-detail.component';
import { ClientesAuditUpdateComponent } from './update/clientes-audit-update.component';
import ClientesAuditResolve from './route/clientes-audit-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const clientesAuditRoute: Routes = [
  {
    path: '',
    component: ClientesAuditComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClientesAuditDetailComponent,
    resolve: {
      clientesAudit: ClientesAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClientesAuditUpdateComponent,
    resolve: {
      clientesAudit: ClientesAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClientesAuditUpdateComponent,
    resolve: {
      clientesAudit: ClientesAuditResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default clientesAuditRoute;
