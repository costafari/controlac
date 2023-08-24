import { IProductos, NewProductos } from './productos.model';

export const sampleWithRequiredData: IProductos = {
  id: 14947,
  descipcion: 'Rojo software',
  nombre: 'Andalucía Nacional',
  precioU: 31931,
  precioC: 19744,
  estadoProducto: 'Cantabria Programable',
  fechaRegistro: 'Negro Refinado',
  fechaCaducidad: 'desafío Queso Verde',
};

export const sampleWithPartialData: IProductos = {
  id: 19570,
  descipcion: 'Azul Productor Federico',
  nombre: 'Papelería',
  precioU: 14712,
  precioC: 8313,
  notas: 'Bebes Corporativo Ladrillo',
  estadoProducto: 'estatica Fantástico',
  fechaRegistro: 'Galicia',
  fechaCaducidad: 'de International Violeta',
};

export const sampleWithFullData: IProductos = {
  id: 10285,
  descipcion: 'Valenciana Inteligente Amarillo',
  nombre: 'Relacciones',
  precioU: 31002,
  precioC: 13808,
  notas: 'Guapa Soluciones Mesa',
  estadoProducto: 'Valenciana Asistente Dinánmico',
  fechaRegistro: 'País multitarea Inteligente',
  fechaCaducidad: 'Compatible Director',
};

export const sampleWithNewData: NewProductos = {
  descipcion: 'aprovechar Optimización',
  nombre: 'Costa Maldivas Pública',
  precioU: 8336,
  precioC: 7282,
  estadoProducto: 'Guantes',
  fechaRegistro: 'Diseñador',
  fechaCaducidad: 'implementación Humano metódica',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
