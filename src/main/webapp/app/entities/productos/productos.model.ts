import { ILotes } from 'app/entities/lotes/lotes.model';

export interface IProductos {
  id: number;
  descipcion?: string | null;
  nombre?: string | null;
  notas?: string | null;
  lotes?: Pick<ILotes, 'id'> | null;
}

export type NewProductos = Omit<IProductos, 'id'> & { id: null };
