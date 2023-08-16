import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProveedoresDetailComponent } from './proveedores-detail.component';

describe('Proveedores Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProveedoresDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProveedoresDetailComponent,
              resolve: { proveedores: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ProveedoresDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load proveedores on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProveedoresDetailComponent);

      // THEN
      expect(instance.proveedores).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
