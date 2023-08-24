import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductoAudit } from '../producto-audit.model';
import { ProductoAuditService } from '../service/producto-audit.service';

export const productoAuditResolve = (route: ActivatedRouteSnapshot): Observable<null | IProductoAudit> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProductoAuditService)
      .find(id)
      .pipe(
        mergeMap((productoAudit: HttpResponse<IProductoAudit>) => {
          if (productoAudit.body) {
            return of(productoAudit.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default productoAuditResolve;
