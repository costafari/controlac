import { IDetalles, NewDetalles } from './detalles.model';

export const sampleWithRequiredData: IDetalles = {
  id: 14734,
  cantidad: 31934,
  impuestos: 19021,
  descuento: 27839,
};

export const sampleWithPartialData: IDetalles = {
  id: 29097,
  cantidad: 2869,
  impuestos: 31238,
  descuento: 5751,
  total: 1138,
};

export const sampleWithFullData: IDetalles = {
  id: 688,
  cantidad: 10409,
  impuestos: 6643,
  descuento: 4924,
  total: 25911,
};

export const sampleWithNewData: NewDetalles = {
  cantidad: 29000,
  impuestos: 3510,
  descuento: 27160,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
