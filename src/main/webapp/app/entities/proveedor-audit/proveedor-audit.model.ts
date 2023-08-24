export interface IProveedorAudit {
  id: number;
  fechaAudit?: string | null;
  tipoCrud?: string | null;
  idProveedor?: number | null;
  idUSuario?: number | null;
}

export type NewProveedorAudit = Omit<IProveedorAudit, 'id'> & { id: null };
