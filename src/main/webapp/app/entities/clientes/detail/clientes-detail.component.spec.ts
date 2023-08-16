import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ClientesDetailComponent } from './clientes-detail.component';

describe('Clientes Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientesDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ClientesDetailComponent,
              resolve: { clientes: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ClientesDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load clientes on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ClientesDetailComponent);

      // THEN
      expect(instance.clientes).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
