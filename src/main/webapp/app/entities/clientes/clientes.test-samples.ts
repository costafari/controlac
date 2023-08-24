import { IClientes, NewClientes } from './clientes.model';

export const sampleWithRequiredData: IClientes = {
  id: 949,
  estadoCliente: true,
  nombresContacto: 'Guapa',
  apellidoContacto: 'Quinta Rojo Senior',
  direccion: 'Galicia Artesanal',
  email: 'Marcela70@gmail.com',
  telefonoFijo: 11957,
  telefonoMovil: 3370,
  fechaRegistro: 'bidireccional',
  fechaUltimaC: 'Madrid',
};

export const sampleWithPartialData: IClientes = {
  id: 23000,
  estadoCliente: true,
  nombresContacto: 'Futuro',
  apellidoContacto: 'Optimización Amarillo Pescado',
  direccion: 'metódica',
  email: 'Leonor_RoquePalacios95@yahoo.com',
  nombreEmpresa: 'Negro Negro',
  regFiscal: 'protocolo',
  giro: 'multitarea Marca Cine',
  notas: 'Barein',
  telefonoFijo: 2736,
  telefonoFijo2: 18744,
  telefonoMovil: 16306,
  fechaRegistro: 'Marroquinería Ergonómico Gris',
  fechaUltimaC: 'Azul Vía Acero',
};

export const sampleWithFullData: IClientes = {
  id: 4714,
  estadoCliente: true,
  nombresContacto: 'Heredado',
  apellidoContacto: 'Jefe',
  direccion: 'Gris Contabilidad',
  email: 'Gerardo_GuevaraOrtega56@hotmail.com',
  nombreEmpresa: 'Calle',
  regFiscal: 'Bricolaje',
  giro: 'Gris Negro',
  notas: 'Sri Implementación',
  sitioWeb: 'Verde Zapatos',
  telefonoFijo: 16502,
  telefonoFijo2: 32075,
  telefonoMovil: 30669,
  telefonoMovil2: 22159,
  fechaRegistro: 'Corporativo',
  fechaUltimaC: 'uniforme Acero Opcional',
};

export const sampleWithNewData: NewClientes = {
  estadoCliente: false,
  nombresContacto: 'Baleares Expandido Negro',
  apellidoContacto: 'Silvia Diseñador Camino',
  direccion: 'Progresivo Facilitador',
  email: 'Antonia_OteroAguilera@yahoo.com',
  telefonoFijo: 22139,
  telefonoMovil: 26864,
  fechaRegistro: 'capacidad',
  fechaUltimaC: 'Planificador Galicia',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
