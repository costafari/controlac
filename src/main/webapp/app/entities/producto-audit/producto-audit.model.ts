export interface IProductoAudit {
  id: number;
  fechaAudit?: string | null;
  tipoCrud?: string | null;
  idProducto?: number | null;
  idUSuario?: number | null;
}

export type NewProductoAudit = Omit<IProductoAudit, 'id'> & { id: null };
