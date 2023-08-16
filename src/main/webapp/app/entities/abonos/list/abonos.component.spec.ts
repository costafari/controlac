import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AbonosService } from '../service/abonos.service';

import { AbonosComponent } from './abonos.component';

describe('Abonos Management Component', () => {
  let comp: AbonosComponent;
  let fixture: ComponentFixture<AbonosComponent>;
  let service: AbonosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'abonos', component: AbonosComponent }]), HttpClientTestingModule, AbonosComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AbonosComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbonosComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AbonosService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.abonos?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to abonosService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAbonosIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAbonosIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
