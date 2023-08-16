import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DetallesDetailComponent } from './detalles-detail.component';

describe('Detalles Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetallesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DetallesDetailComponent,
              resolve: { detalles: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(DetallesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load detalles on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DetallesDetailComponent);

      // THEN
      expect(instance.detalles).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
