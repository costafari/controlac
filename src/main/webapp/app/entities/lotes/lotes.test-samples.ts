import dayjs from 'dayjs/esm';

import { ILotes, NewLotes } from './lotes.model';

export const sampleWithRequiredData: ILotes = {
  id: 1781,
  cantidad: 32670,
  fechaEntrada: dayjs('2023-08-09'),
  lote: 'precios Identidad Camiseta',
  estado: 'Diseñador',
};

export const sampleWithPartialData: ILotes = {
  id: 32158,
  cantidad: 31372,
  fechaEntrada: dayjs('2023-08-09'),
  lote: 'asíncrona Verde',
  estado: 'Director Andorra Morado',
  notas: 'Amarillo',
};

export const sampleWithFullData: ILotes = {
  id: 14698,
  cantidad: 24414,
  fechaEntrada: dayjs('2023-08-09'),
  lote: 'objetos Cantabria',
  estado: 'Coordinador Relacciones',
  notas: 'Mejorado',
};

export const sampleWithNewData: NewLotes = {
  cantidad: 5025,
  fechaEntrada: dayjs('2023-08-09'),
  lote: 'Informática Arrabal Senior',
  estado: 'Pizza Heredado Baleares',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
