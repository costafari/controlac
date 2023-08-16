import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProveedores } from '../proveedores.model';
import { ProveedoresService } from '../service/proveedores.service';

export const proveedoresResolve = (route: ActivatedRouteSnapshot): Observable<null | IProveedores> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProveedoresService)
      .find(id)
      .pipe(
        mergeMap((proveedores: HttpResponse<IProveedores>) => {
          if (proveedores.body) {
            return of(proveedores.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default proveedoresResolve;
