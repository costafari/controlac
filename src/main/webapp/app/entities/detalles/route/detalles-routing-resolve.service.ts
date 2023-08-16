import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDetalles } from '../detalles.model';
import { DetallesService } from '../service/detalles.service';

export const detallesResolve = (route: ActivatedRouteSnapshot): Observable<null | IDetalles> => {
  const id = route.params['id'];
  if (id) {
    return inject(DetallesService)
      .find(id)
      .pipe(
        mergeMap((detalles: HttpResponse<IDetalles>) => {
          if (detalles.body) {
            return of(detalles.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default detallesResolve;
