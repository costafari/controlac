import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LotesComponent } from './list/lotes.component';
import { LotesDetailComponent } from './detail/lotes-detail.component';
import { LotesUpdateComponent } from './update/lotes-update.component';
import LotesResolve from './route/lotes-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const lotesRoute: Routes = [
  {
    path: '',
    component: LotesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LotesDetailComponent,
    resolve: {
      lotes: LotesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LotesUpdateComponent,
    resolve: {
      lotes: LotesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LotesUpdateComponent,
    resolve: {
      lotes: LotesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default lotesRoute;
