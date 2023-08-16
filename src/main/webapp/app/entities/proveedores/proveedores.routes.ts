import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProveedoresComponent } from './list/proveedores.component';
import { ProveedoresDetailComponent } from './detail/proveedores-detail.component';
import { ProveedoresUpdateComponent } from './update/proveedores-update.component';
import ProveedoresResolve from './route/proveedores-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const proveedoresRoute: Routes = [
  {
    path: '',
    component: ProveedoresComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProveedoresDetailComponent,
    resolve: {
      proveedores: ProveedoresResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProveedoresUpdateComponent,
    resolve: {
      proveedores: ProveedoresResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProveedoresUpdateComponent,
    resolve: {
      proveedores: ProveedoresResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default proveedoresRoute;
