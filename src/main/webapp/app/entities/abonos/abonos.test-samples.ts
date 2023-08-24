import { IAbonos, NewAbonos } from './abonos.model';

export const sampleWithRequiredData: IAbonos = {
  id: 9679,
  saldoAnterior: 18514,
  abono: 20829,
  fechaRegistro: 'Pequeño Director uniforme',
  fechaAbono: 'Guantes',
};

export const sampleWithPartialData: IAbonos = {
  id: 21797,
  saldoAnterior: 56,
  abono: 21981,
  fechaRegistro: 'Seguro',
  fechaAbono: 'Zapatos Morado Oficial',
};

export const sampleWithFullData: IAbonos = {
  id: 4671,
  saldoAnterior: 24785,
  abono: 24576,
  nuevoSaldo: 17633,
  fechaRegistro: 'Configurable',
  fechaAbono: 'Práctico',
};

export const sampleWithNewData: NewAbonos = {
  saldoAnterior: 30603,
  abono: 11203,
  fechaRegistro: 'moderador Clonado',
  fechaAbono: 'Madera Blanco',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
