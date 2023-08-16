import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetallesComponent } from './list/detalles.component';
import { DetallesDetailComponent } from './detail/detalles-detail.component';
import { DetallesUpdateComponent } from './update/detalles-update.component';
import DetallesResolve from './route/detalles-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const detallesRoute: Routes = [
  {
    path: '',
    component: DetallesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetallesDetailComponent,
    resolve: {
      detalles: DetallesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetallesUpdateComponent,
    resolve: {
      detalles: DetallesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetallesUpdateComponent,
    resolve: {
      detalles: DetallesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default detallesRoute;
