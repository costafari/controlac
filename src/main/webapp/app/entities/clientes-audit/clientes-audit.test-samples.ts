import { IClientesAudit, NewClientesAudit } from './clientes-audit.model';

export const sampleWithRequiredData: IClientesAudit = {
  id: 2769,
  fechaAudit: 'Analista homogénea Kenia',
  tipoCrud: 'Silla Extremadura',
  idCliente: 27019,
  idUSuario: 11907,
};

export const sampleWithPartialData: IClientesAudit = {
  id: 1942,
  fechaAudit: 'Ladrillo Haiti colaboración',
  tipoCrud: 'César',
  idCliente: 22744,
  idUSuario: 6940,
};

export const sampleWithFullData: IClientesAudit = {
  id: 10200,
  fechaAudit: 'Técnico hibrida desafío',
  tipoCrud: 'Global Agente Cine',
  idCliente: 8279,
  idUSuario: 30759,
};

export const sampleWithNewData: NewClientesAudit = {
  fechaAudit: 'Director Fundamental Marca',
  tipoCrud: 'Gris aplicación Directo',
  idCliente: 20131,
  idUSuario: 2206,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
