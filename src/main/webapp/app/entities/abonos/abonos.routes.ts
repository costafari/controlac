import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AbonosComponent } from './list/abonos.component';
import { AbonosDetailComponent } from './detail/abonos-detail.component';
import { AbonosUpdateComponent } from './update/abonos-update.component';
import AbonosResolve from './route/abonos-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const abonosRoute: Routes = [
  {
    path: '',
    component: AbonosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AbonosDetailComponent,
    resolve: {
      abonos: AbonosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AbonosUpdateComponent,
    resolve: {
      abonos: AbonosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AbonosUpdateComponent,
    resolve: {
      abonos: AbonosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default abonosRoute;
