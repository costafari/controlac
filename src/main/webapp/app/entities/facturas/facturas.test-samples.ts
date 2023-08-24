import dayjs from 'dayjs/esm';

import { IFacturas, NewFacturas } from './facturas.model';

export const sampleWithRequiredData: IFacturas = {
  id: 16583,
  numeroFactura: 9377,
  fechaFactura: dayjs('2023-08-09T19:27'),
  condicionPago: false,
  estadoFactura: true,
};

export const sampleWithPartialData: IFacturas = {
  id: 20803,
  numeroFactura: 31153,
  fechaFactura: dayjs('2023-08-09T19:15'),
  condicionPago: true,
  estadoFactura: false,
};

export const sampleWithFullData: IFacturas = {
  id: 4448,
  numeroFactura: 15655,
  fechaFactura: dayjs('2023-08-09T12:42'),
  condicionPago: true,
  estadoFactura: true,
};

export const sampleWithNewData: NewFacturas = {
  numeroFactura: 8787,
  fechaFactura: dayjs('2023-08-09T21:05'),
  condicionPago: true,
  estadoFactura: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
