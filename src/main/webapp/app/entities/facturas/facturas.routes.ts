import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FacturasComponent } from './list/facturas.component';
import { FacturasDetailComponent } from './detail/facturas-detail.component';
import { FacturasUpdateComponent } from './update/facturas-update.component';
import FacturasResolve from './route/facturas-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const facturasRoute: Routes = [
  {
    path: '',
    component: FacturasComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacturasDetailComponent,
    resolve: {
      facturas: FacturasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacturasUpdateComponent,
    resolve: {
      facturas: FacturasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacturasUpdateComponent,
    resolve: {
      facturas: FacturasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default facturasRoute;
