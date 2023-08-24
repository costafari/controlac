import { IProveedores } from 'app/entities/proveedores/proveedores.model';
import { IDetalles } from 'app/entities/detalles/detalles.model';

export interface IProductos {
  id: number;
  descipcion?: string | null;
  nombre?: string | null;
  precioU?: number | null;
  precioC?: number | null;
  notas?: string | null;
  estadoProducto?: string | null;
  fechaRegistro?: string | null;
  fechaCaducidad?: string | null;
  proveedores?: Pick<IProveedores, 'id'> | null;
  detalles?: Pick<IDetalles, 'id'>[] | null;
}

export type NewProductos = Omit<IProductos, 'id'> & { id: null };
