import { IProveedores, NewProveedores } from './proveedores.model';

export const sampleWithRequiredData: IProveedores = {
  id: 10838,
};

export const sampleWithPartialData: IProveedores = {
  id: 6937,
  direccion: 'Violeta Exclusivo Jardines',
  nombreContacto: 'motivadora',
  nombreEmpresa: 'Violeta Refinado Jardines',
};

export const sampleWithFullData: IProveedores = {
  id: 22394,
  direccion: 'Bacon',
  nombreContacto: 'Obligatorio',
  nombreEmpresa: 'Música Identidad',
  notas: 'Guantes Asistente Gris',
  sitioWeb: 'Dinánmico',
  telefonoFijo: 14340,
  telefonoFijo2: 27694,
  telefonoMovil: 4813,
  telefonoMovil2: 2501,
};

export const sampleWithNewData: NewProveedores = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
