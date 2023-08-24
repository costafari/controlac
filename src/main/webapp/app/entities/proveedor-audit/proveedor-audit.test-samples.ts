import { IProveedorAudit, NewProveedorAudit } from './proveedor-audit.model';

export const sampleWithRequiredData: IProveedorAudit = {
  id: 2741,
  fechaAudit: 'Granito Inteligente Guapa',
  tipoCrud: 'Ladrillo Creativo',
  idProveedor: 20392,
  idUSuario: 5372,
};

export const sampleWithPartialData: IProveedorAudit = {
  id: 27186,
  fechaAudit: 'fritas Amarillo',
  tipoCrud: 'División Analista',
  idProveedor: 7724,
  idUSuario: 968,
};

export const sampleWithFullData: IProveedorAudit = {
  id: 15058,
  fechaAudit: 'migración',
  tipoCrud: 'Producto Investigación',
  idProveedor: 23675,
  idUSuario: 5492,
};

export const sampleWithNewData: NewProveedorAudit = {
  fechaAudit: 'Toallas Pequeño Montserrat',
  tipoCrud: 'Asociado Visionario',
  idProveedor: 4028,
  idUSuario: 27366,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
