import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClientesAudit, NewClientesAudit } from '../clientes-audit.model';

export type PartialUpdateClientesAudit = Partial<IClientesAudit> & Pick<IClientesAudit, 'id'>;

export type EntityResponseType = HttpResponse<IClientesAudit>;
export type EntityArrayResponseType = HttpResponse<IClientesAudit[]>;

@Injectable({ providedIn: 'root' })
export class ClientesAuditService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/clientes-audits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(clientesAudit: NewClientesAudit): Observable<EntityResponseType> {
    return this.http.post<IClientesAudit>(this.resourceUrl, clientesAudit, { observe: 'response' });
  }

  update(clientesAudit: IClientesAudit): Observable<EntityResponseType> {
    return this.http.put<IClientesAudit>(`${this.resourceUrl}/${this.getClientesAuditIdentifier(clientesAudit)}`, clientesAudit, {
      observe: 'response',
    });
  }

  partialUpdate(clientesAudit: PartialUpdateClientesAudit): Observable<EntityResponseType> {
    return this.http.patch<IClientesAudit>(`${this.resourceUrl}/${this.getClientesAuditIdentifier(clientesAudit)}`, clientesAudit, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClientesAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClientesAudit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getClientesAuditIdentifier(clientesAudit: Pick<IClientesAudit, 'id'>): number {
    return clientesAudit.id;
  }

  compareClientesAudit(o1: Pick<IClientesAudit, 'id'> | null, o2: Pick<IClientesAudit, 'id'> | null): boolean {
    return o1 && o2 ? this.getClientesAuditIdentifier(o1) === this.getClientesAuditIdentifier(o2) : o1 === o2;
  }

  addClientesAuditToCollectionIfMissing<Type extends Pick<IClientesAudit, 'id'>>(
    clientesAuditCollection: Type[],
    ...clientesAuditsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const clientesAudits: Type[] = clientesAuditsToCheck.filter(isPresent);
    if (clientesAudits.length > 0) {
      const clientesAuditCollectionIdentifiers = clientesAuditCollection.map(
        clientesAuditItem => this.getClientesAuditIdentifier(clientesAuditItem)!
      );
      const clientesAuditsToAdd = clientesAudits.filter(clientesAuditItem => {
        const clientesAuditIdentifier = this.getClientesAuditIdentifier(clientesAuditItem);
        if (clientesAuditCollectionIdentifiers.includes(clientesAuditIdentifier)) {
          return false;
        }
        clientesAuditCollectionIdentifiers.push(clientesAuditIdentifier);
        return true;
      });
      return [...clientesAuditsToAdd, ...clientesAuditCollection];
    }
    return clientesAuditCollection;
  }
}
