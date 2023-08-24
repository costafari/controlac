import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProveedorAudit } from '../proveedor-audit.model';
import { ProveedorAuditService } from '../service/proveedor-audit.service';

export const proveedorAuditResolve = (route: ActivatedRouteSnapshot): Observable<null | IProveedorAudit> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProveedorAuditService)
      .find(id)
      .pipe(
        mergeMap((proveedorAudit: HttpResponse<IProveedorAudit>) => {
          if (proveedorAudit.body) {
            return of(proveedorAudit.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default proveedorAuditResolve;
