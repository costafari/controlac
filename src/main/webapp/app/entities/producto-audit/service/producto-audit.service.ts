import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductoAudit, NewProductoAudit } from '../producto-audit.model';

export type PartialUpdateProductoAudit = Partial<IProductoAudit> & Pick<IProductoAudit, 'id'>;

export type EntityResponseType = HttpResponse<IProductoAudit>;
export type EntityArrayResponseType = HttpResponse<IProductoAudit[]>;

@Injectable({ providedIn: 'root' })
export class ProductoAuditService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/producto-audits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productoAudit: NewProductoAudit): Observable<EntityResponseType> {
    return this.http.post<IProductoAudit>(this.resourceUrl, productoAudit, { observe: 'response' });
  }

  update(productoAudit: IProductoAudit): Observable<EntityResponseType> {
    return this.http.put<IProductoAudit>(`${this.resourceUrl}/${this.getProductoAuditIdentifier(productoAudit)}`, productoAudit, {
      observe: 'response',
    });
  }

  partialUpdate(productoAudit: PartialUpdateProductoAudit): Observable<EntityResponseType> {
    return this.http.patch<IProductoAudit>(`${this.resourceUrl}/${this.getProductoAuditIdentifier(productoAudit)}`, productoAudit, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductoAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductoAudit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductoAuditIdentifier(productoAudit: Pick<IProductoAudit, 'id'>): number {
    return productoAudit.id;
  }

  compareProductoAudit(o1: Pick<IProductoAudit, 'id'> | null, o2: Pick<IProductoAudit, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductoAuditIdentifier(o1) === this.getProductoAuditIdentifier(o2) : o1 === o2;
  }

  addProductoAuditToCollectionIfMissing<Type extends Pick<IProductoAudit, 'id'>>(
    productoAuditCollection: Type[],
    ...productoAuditsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productoAudits: Type[] = productoAuditsToCheck.filter(isPresent);
    if (productoAudits.length > 0) {
      const productoAuditCollectionIdentifiers = productoAuditCollection.map(
        productoAuditItem => this.getProductoAuditIdentifier(productoAuditItem)!
      );
      const productoAuditsToAdd = productoAudits.filter(productoAuditItem => {
        const productoAuditIdentifier = this.getProductoAuditIdentifier(productoAuditItem);
        if (productoAuditCollectionIdentifiers.includes(productoAuditIdentifier)) {
          return false;
        }
        productoAuditCollectionIdentifiers.push(productoAuditIdentifier);
        return true;
      });
      return [...productoAuditsToAdd, ...productoAuditCollection];
    }
    return productoAuditCollection;
  }
}
