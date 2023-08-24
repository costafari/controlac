import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProveedorAuditService } from '../service/proveedor-audit.service';

import { ProveedorAuditComponent } from './proveedor-audit.component';

describe('ProveedorAudit Management Component', () => {
  let comp: ProveedorAuditComponent;
  let fixture: ComponentFixture<ProveedorAuditComponent>;
  let service: ProveedorAuditService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'proveedor-audit', component: ProveedorAuditComponent }]),
        HttpClientTestingModule,
        ProveedorAuditComponent,
      ],
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
      .overrideTemplate(ProveedorAuditComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProveedorAuditComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProveedorAuditService);

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
    expect(comp.proveedorAudits?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to proveedorAuditService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProveedorAuditIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProveedorAuditIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
