import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClientesAudit } from '../clientes-audit.model';
import { ClientesAuditService } from '../service/clientes-audit.service';

export const clientesAuditResolve = (route: ActivatedRouteSnapshot): Observable<null | IClientesAudit> => {
  const id = route.params['id'];
  if (id) {
    return inject(ClientesAuditService)
      .find(id)
      .pipe(
        mergeMap((clientesAudit: HttpResponse<IClientesAudit>) => {
          if (clientesAudit.body) {
            return of(clientesAudit.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default clientesAuditResolve;
