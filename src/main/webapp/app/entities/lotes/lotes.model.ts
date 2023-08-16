import dayjs from 'dayjs/esm';
import { IProveedores } from 'app/entities/proveedores/proveedores.model';

export interface ILotes {
  id: number;
  cantidad?: number | null;
  fechaEntrada?: dayjs.Dayjs | null;
  lote?: string | null;
  proveedores?: Pick<IProveedores, 'id'> | null;
}

export type NewLotes = Omit<ILotes, 'id'> & { id: null };
