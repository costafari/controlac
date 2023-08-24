import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProductoAuditDetailComponent } from './producto-audit-detail.component';

describe('ProductoAudit Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductoAuditDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProductoAuditDetailComponent,
              resolve: { productoAudit: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ProductoAuditDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load productoAudit on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProductoAuditDetailComponent);

      // THEN
      expect(instance.productoAudit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
