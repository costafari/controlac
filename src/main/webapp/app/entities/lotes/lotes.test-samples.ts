import dayjs from 'dayjs/esm';

import { ILotes, NewLotes } from './lotes.model';

export const sampleWithRequiredData: ILotes = {
  id: 25310,
};

export const sampleWithPartialData: ILotes = {
  id: 10327,
  cantidad: 5698,
  fechaEntrada: dayjs('2023-08-09'),
  lote: 'Decoración Distrito Corporativo',
};

export const sampleWithFullData: ILotes = {
  id: 11623,
  cantidad: 8622,
  fechaEntrada: dayjs('2023-08-09'),
  lote: 'Agente',
};

export const sampleWithNewData: NewLotes = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
