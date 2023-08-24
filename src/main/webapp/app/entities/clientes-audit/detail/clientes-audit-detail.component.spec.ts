import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ClientesAuditDetailComponent } from './clientes-audit-detail.component';

describe('ClientesAudit Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientesAuditDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ClientesAuditDetailComponent,
              resolve: { clientesAudit: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ClientesAuditDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load clientesAudit on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ClientesAuditDetailComponent);

      // THEN
      expect(instance.clientesAudit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
