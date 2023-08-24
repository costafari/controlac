import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProveedorAuditDetailComponent } from './proveedor-audit-detail.component';

describe('ProveedorAudit Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProveedorAuditDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProveedorAuditDetailComponent,
              resolve: { proveedorAudit: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ProveedorAuditDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load proveedorAudit on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProveedorAuditDetailComponent);

      // THEN
      expect(instance.proveedorAudit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
