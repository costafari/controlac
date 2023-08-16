import { IAbonos, NewAbonos } from './abonos.model';

export const sampleWithRequiredData: IAbonos = {
  id: 4781,
  saldoAnterior: 12157,
  abono: 15759,
};

export const sampleWithPartialData: IAbonos = {
  id: 12184,
  saldoAnterior: 2272,
  abono: 24718,
};

export const sampleWithFullData: IAbonos = {
  id: 15932,
  saldoAnterior: 8836,
  abono: 24287,
  nuevoSaldo: 3331,
};

export const sampleWithNewData: NewAbonos = {
  saldoAnterior: 888,
  abono: 9901,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
