import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ClientesAuditService } from '../service/clientes-audit.service';

import { ClientesAuditComponent } from './clientes-audit.component';

describe('ClientesAudit Management Component', () => {
  let comp: ClientesAuditComponent;
  let fixture: ComponentFixture<ClientesAuditComponent>;
  let service: ClientesAuditService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'clientes-audit', component: ClientesAuditComponent }]),
        HttpClientTestingModule,
        ClientesAuditComponent,
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
      .overrideTemplate(ClientesAuditComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientesAuditComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ClientesAuditService);

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
    expect(comp.clientesAudits?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to clientesAuditService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getClientesAuditIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getClientesAuditIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
