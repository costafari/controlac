export interface IClientes {
  id: number;
  estadoCliente?: boolean | null;
  nombresContacto?: string | null;
  apellidoContacto?: string | null;
  direccion?: string | null;
  email?: string | null;
  nombreEmpresa?: string | null;
  regFiscal?: string | null;
  giro?: string | null;
  notas?: string | null;
  sitioWeb?: string | null;
  telefonoFijo?: number | null;
  telefonoFijo2?: number | null;
  telefonoMovil?: number | null;
  telefonoMovil2?: number | null;
  fechaRegistro?: string | null;
  fechaUltimaC?: string | null;
}

export type NewClientes = Omit<IClientes, 'id'> & { id: null };
