import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProductosDetailComponent } from './productos-detail.component';

describe('Productos Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ProductosDetailComponent,
              resolve: { productos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(ProductosDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load productos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ProductosDetailComponent);

      // THEN
      expect(instance.productos).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
