import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductosComponent } from './list/productos.component';
import { ProductosDetailComponent } from './detail/productos-detail.component';
import { ProductosUpdateComponent } from './update/productos-update.component';
import ProductosResolve from './route/productos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productosRoute: Routes = [
  {
    path: '',
    component: ProductosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductosDetailComponent,
    resolve: {
      productos: ProductosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductosUpdateComponent,
    resolve: {
      productos: ProductosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductosUpdateComponent,
    resolve: {
      productos: ProductosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default productosRoute;
