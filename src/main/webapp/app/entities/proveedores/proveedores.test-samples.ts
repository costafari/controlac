import { IProveedores, NewProveedores } from './proveedores.model';

export const sampleWithRequiredData: IProveedores = {
  id: 31048,
  direccion: 'Granito analista',
  nombreContacto: 'Global Gris Plástico',
  nombreEmpresa: 'array',
  telefonoFijo: 27942,
  telefonoMovil: 9651,
};

export const sampleWithPartialData: IProveedores = {
  id: 16573,
  direccion: 'Metal',
  nombreContacto: 'Jefe Consultor',
  nombreEmpresa: 'Serbia',
  telefonoFijo: 31014,
  telefonoMovil: 22631,
};

export const sampleWithFullData: IProveedores = {
  id: 23968,
  direccion: 'Adela Global Pescado',
  nombreContacto: 'Asturias Verde',
  nombreEmpresa: 'Interacciones Luisa',
  notas: 'conjunto Verde',
  sitioWeb: 'Pública',
  telefonoFijo: 9151,
  telefonoFijo2: 16280,
  telefonoMovil: 3370,
  telefonoMovil2: 4276,
};

export const sampleWithNewData: NewProveedores = {
  direccion: 'de',
  nombreContacto: 'Artesanal Refinado',
  nombreEmpresa: 'Seguro',
  telefonoFijo: 5682,
  telefonoMovil: 30933,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
