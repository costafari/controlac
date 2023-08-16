import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AbonosDetailComponent } from './abonos-detail.component';

describe('Abonos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AbonosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AbonosDetailComponent,
              resolve: { abonos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(AbonosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load abonos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AbonosDetailComponent);

      // THEN
      expect(instance.abonos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
