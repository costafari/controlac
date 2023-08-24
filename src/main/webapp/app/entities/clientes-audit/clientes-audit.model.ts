export interface IClientesAudit {
  id: number;
  fechaAudit?: string | null;
  tipoCrud?: string | null;
  idCliente?: number | null;
  idUSuario?: number | null;
}

export type NewClientesAudit = Omit<IClientesAudit, 'id'> & { id: null };
