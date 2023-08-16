import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAbonos } from '../abonos.model';
import { AbonosService } from '../service/abonos.service';

export const abonosResolve = (route: ActivatedRouteSnapshot): Observable<null | IAbonos> => {
  const id = route.params['id'];
  if (id) {
    return inject(AbonosService)
      .find(id)
      .pipe(
        mergeMap((abonos: HttpResponse<IAbonos>) => {
          if (abonos.body) {
            return of(abonos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default abonosResolve;
