import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'lotes',
        data: { pageTitle: 'controlacApp.lotes.home.title' },
        loadChildren: () => import('./lotes/lotes.routes'),
      },
      {
        path: 'clientes',
        data: { pageTitle: 'controlacApp.clientes.home.title' },
        loadChildren: () => import('./clientes/clientes.routes'),
      },
      {
        path: 'proveedores',
        data: { pageTitle: 'controlacApp.proveedores.home.title' },
        loadChildren: () => import('./proveedores/proveedores.routes'),
      },
      {
        path: 'facturas',
        data: { pageTitle: 'controlacApp.facturas.home.title' },
        loadChildren: () => import('./facturas/facturas.routes'),
      },
      {
        path: 'productos',
        data: { pageTitle: 'controlacApp.productos.home.title' },
        loadChildren: () => import('./productos/productos.routes'),
      },
      {
        path: 'abonos',
        data: { pageTitle: 'controlacApp.abonos.home.title' },
        loadChildren: () => import('./abonos/abonos.routes'),
      },
      {
        path: 'detalles',
        data: { pageTitle: 'controlacApp.detalles.home.title' },
        loadChildren: () => import('./detalles/detalles.routes'),
      },
      {
        path: 'proveedor-audit',
        data: { pageTitle: 'controlacApp.proveedorAudit.home.title' },
        loadChildren: () => import('./proveedor-audit/proveedor-audit.routes'),
      },
      {
        path: 'clientes-audit',
        data: { pageTitle: 'controlacApp.clientesAudit.home.title' },
        loadChildren: () => import('./clientes-audit/clientes-audit.routes'),
      },
      {
        path: 'producto-audit',
        data: { pageTitle: 'controlacApp.productoAudit.home.title' },
        loadChildren: () => import('./producto-audit/producto-audit.routes'),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
