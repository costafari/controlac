import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LotesDetailComponent } from './lotes-detail.component';

describe('Lotes Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LotesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: LotesDetailComponent,
              resolve: { lotes: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(LotesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load lotes on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LotesDetailComponent);

      // THEN
      expect(instance.lotes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
