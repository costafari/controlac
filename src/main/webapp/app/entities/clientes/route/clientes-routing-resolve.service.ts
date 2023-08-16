import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClientes } from '../clientes.model';
import { ClientesService } from '../service/clientes.service';

export const clientesResolve = (route: ActivatedRouteSnapshot): Observable<null | IClientes> => {
  const id = route.params['id'];
  if (id) {
    return inject(ClientesService)
      .find(id)
      .pipe(
        mergeMap((clientes: HttpResponse<IClientes>) => {
          if (clientes.body) {
            return of(clientes.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default clientesResolve;
