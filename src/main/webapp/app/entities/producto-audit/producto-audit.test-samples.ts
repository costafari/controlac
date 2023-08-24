import { IProductoAudit, NewProductoAudit } from './producto-audit.model';

export const sampleWithRequiredData: IProductoAudit = {
  id: 4455,
  fechaAudit: 'Bebes Pizza',
  tipoCrud: 'a Bosnia Práctico',
  idProducto: 13107,
  idUSuario: 3968,
};

export const sampleWithPartialData: IProductoAudit = {
  id: 16868,
  fechaAudit: 'intangible Heredado',
  tipoCrud: 'groupware definición',
  idProducto: 12611,
  idUSuario: 24475,
};

export const sampleWithFullData: IProductoAudit = {
  id: 4890,
  fechaAudit: 'Cataluña la Teclado',
  tipoCrud: 'Ergonómico Salchichas',
  idProducto: 10670,
  idUSuario: 28579,
};

export const sampleWithNewData: NewProductoAudit = {
  fechaAudit: 'Interfaz Azul',
  tipoCrud: 'Violeta Virtual Paradigma',
  idProducto: 6574,
  idUSuario: 19852,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
