import { IDetalles, NewDetalles } from './detalles.model';

export const sampleWithRequiredData: IDetalles = {
  id: 17253,
  cantidad: 9327,
};

export const sampleWithPartialData: IDetalles = {
  id: 32116,
  cantidad: 25089,
};

export const sampleWithFullData: IDetalles = {
  id: 21051,
  cantidad: 13410,
  total: 13752,
};

export const sampleWithNewData: NewDetalles = {
  cantidad: 4325,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
