import dayjs from 'dayjs/esm';

import { IFacturas, NewFacturas } from './facturas.model';

export const sampleWithRequiredData: IFacturas = {
  id: 11928,
  numeroFactura: 8240,
};

export const sampleWithPartialData: IFacturas = {
  id: 10751,
  numeroFactura: 20044,
  fechaFactura: dayjs('2023-08-09T22:38'),
  condicionPago: false,
};

export const sampleWithFullData: IFacturas = {
  id: 11731,
  numeroFactura: 14468,
  fechaFactura: dayjs('2023-08-09T05:21'),
  condicionPago: true,
};

export const sampleWithNewData: NewFacturas = {
  numeroFactura: 9700,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
