import { IProductos, NewProductos } from './productos.model';

export const sampleWithRequiredData: IProductos = {
  id: 21845,
};

export const sampleWithPartialData: IProductos = {
  id: 22671,
  nombre: 'Teclado',
};

export const sampleWithFullData: IProductos = {
  id: 7699,
  descipcion: 'Galicia Consultor',
  nombre: 'Arroyo Hormigon',
  notas: 'heurística Pedro',
};

export const sampleWithNewData: NewProductos = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
