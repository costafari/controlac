import dayjs from 'dayjs/esm';
import { IDetalles } from 'app/entities/detalles/detalles.model';
import { IClientes } from 'app/entities/clientes/clientes.model';

export interface IFacturas {
  id: number;
  numeroFactura?: number | null;
  fechaFactura?: dayjs.Dayjs | null;
  condicionPago?: boolean | null;
  estadoFactura?: boolean | null;
  detalles?: Pick<IDetalles, 'id'> | null;
  clientes?: Pick<IClientes, 'id'> | null;
}

export type NewFacturas = Omit<IFacturas, 'id'> & { id: null };
