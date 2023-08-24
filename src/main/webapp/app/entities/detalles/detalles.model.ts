import { IProductos } from 'app/entities/productos/productos.model';

export interface IDetalles {
  id: number;
  cantidad?: number | null;
  impuestos?: number | null;
  descuento?: number | null;
  total?: number | null;
  productos?: Pick<IProductos, 'id'>[] | null;
}

export type NewDetalles = Omit<IDetalles, 'id'> & { id: null };
