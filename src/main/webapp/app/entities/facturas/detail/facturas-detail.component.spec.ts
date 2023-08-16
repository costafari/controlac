import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { FacturasDetailComponent } from './facturas-detail.component';

describe('Facturas Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FacturasDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FacturasDetailComponent,
              resolve: { facturas: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(FacturasDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load facturas on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FacturasDetailComponent);

      // THEN
      expect(instance.facturas).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
