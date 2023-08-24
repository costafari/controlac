import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProveedorAudit, NewProveedorAudit } from '../proveedor-audit.model';

export type PartialUpdateProveedorAudit = Partial<IProveedorAudit> & Pick<IProveedorAudit, 'id'>;

export type EntityResponseType = HttpResponse<IProveedorAudit>;
export type EntityArrayResponseType = HttpResponse<IProveedorAudit[]>;

@Injectable({ providedIn: 'root' })
export class ProveedorAuditService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/proveedor-audits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(proveedorAudit: NewProveedorAudit): Observable<EntityResponseType> {
    return this.http.post<IProveedorAudit>(this.resourceUrl, proveedorAudit, { observe: 'response' });
  }

  update(proveedorAudit: IProveedorAudit): Observable<EntityResponseType> {
    return this.http.put<IProveedorAudit>(`${this.resourceUrl}/${this.getProveedorAuditIdentifier(proveedorAudit)}`, proveedorAudit, {
      observe: 'response',
    });
  }

  partialUpdate(proveedorAudit: PartialUpdateProveedorAudit): Observable<EntityResponseType> {
    return this.http.patch<IProveedorAudit>(`${this.resourceUrl}/${this.getProveedorAuditIdentifier(proveedorAudit)}`, proveedorAudit, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProveedorAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProveedorAudit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProveedorAuditIdentifier(proveedorAudit: Pick<IProveedorAudit, 'id'>): number {
    return proveedorAudit.id;
  }

  compareProveedorAudit(o1: Pick<IProveedorAudit, 'id'> | null, o2: Pick<IProveedorAudit, 'id'> | null): boolean {
    return o1 && o2 ? this.getProveedorAuditIdentifier(o1) === this.getProveedorAuditIdentifier(o2) : o1 === o2;
  }

  addProveedorAuditToCollectionIfMissing<Type extends Pick<IProveedorAudit, 'id'>>(
    proveedorAuditCollection: Type[],
    ...proveedorAuditsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const proveedorAudits: Type[] = proveedorAuditsToCheck.filter(isPresent);
    if (proveedorAudits.length > 0) {
      const proveedorAuditCollectionIdentifiers = proveedorAuditCollection.map(
        proveedorAuditItem => this.getProveedorAuditIdentifier(proveedorAuditItem)!
      );
      const proveedorAuditsToAdd = proveedorAudits.filter(proveedorAuditItem => {
        const proveedorAuditIdentifier = this.getProveedorAuditIdentifier(proveedorAuditItem);
        if (proveedorAuditCollectionIdentifiers.includes(proveedorAuditIdentifier)) {
          return false;
        }
        proveedorAuditCollectionIdentifiers.push(proveedorAuditIdentifier);
        return true;
      });
      return [...proveedorAuditsToAdd, ...proveedorAuditCollection];
    }
    return proveedorAuditCollection;
  }
}
