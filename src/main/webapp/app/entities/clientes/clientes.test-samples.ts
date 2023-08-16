import { IClientes, NewClientes } from './clientes.model';

export const sampleWithRequiredData: IClientes = {
  id: 10441,
};

export const sampleWithPartialData: IClientes = {
  id: 13853,
  activo: false,
  apellidos: 'implementación didactica Mesa',
  direcion: 'Dinánmico',
  nombreContacto: 'Teclado Madera amplio',
  nombreEmpresa: 'fases innovadora Mobilidad',
  notas: 'Artesanal',
  sitioWeb: 'España Queso',
  telefonoFijo2: 26122,
};

export const sampleWithFullData: IClientes = {
  id: 22650,
  activo: true,
  apellidos: 'Kiribati',
  direcion: 'Murcia Amarillo Marca',
  email: 'Sancho2@hotmail.com',
  nombreContacto: 'Soluciones',
  nombreEmpresa: 'generación País',
  nombres: 'Programa Decoración de',
  notas: 'Dinánmico',
  sitioWeb: 'Pequeño',
  telefonoFijo: 16459,
  telefonoFijo2: 5563,
  telefonoMovil: 13126,
  telefonoMovil2: 6176,
};

export const sampleWithNewData: NewClientes = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
