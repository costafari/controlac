import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClientesAuditFormService } from './clientes-audit-form.service';
import { ClientesAuditService } from '../service/clientes-audit.service';
import { IClientesAudit } from '../clientes-audit.model';

import { ClientesAuditUpdateComponent } from './clientes-audit-update.component';

describe('ClientesAudit Management Update Component', () => {
  let comp: ClientesAuditUpdateComponent;
  let fixture: ComponentFixture<ClientesAuditUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let clientesAuditFormService: ClientesAuditFormService;
  let clientesAuditService: ClientesAuditService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ClientesAuditUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ClientesAuditUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClientesAuditUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    clientesAuditFormService = TestBed.inject(ClientesAuditFormService);
    clientesAuditService = TestBed.inject(ClientesAuditService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const clientesAudit: IClientesAudit = { id: 456 };

      activatedRoute.data = of({ clientesAudit });
      comp.ngOnInit();

      expect(comp.clientesAudit).toEqual(clientesAudit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientesAudit>>();
      const clientesAudit = { id: 123 };
      jest.spyOn(clientesAuditFormService, 'getClientesAudit').mockReturnValue(clientesAudit);
      jest.spyOn(clientesAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientesAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientesAudit }));
      saveSubject.complete();

      // THEN
      expect(clientesAuditFormService.getClientesAudit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(clientesAuditService.update).toHaveBeenCalledWith(expect.objectContaining(clientesAudit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientesAudit>>();
      const clientesAudit = { id: 123 };
      jest.spyOn(clientesAuditFormService, 'getClientesAudit').mockReturnValue({ id: null });
      jest.spyOn(clientesAuditService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientesAudit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: clientesAudit }));
      saveSubject.complete();

      // THEN
      expect(clientesAuditFormService.getClientesAudit).toHaveBeenCalled();
      expect(clientesAuditService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IClientesAudit>>();
      const clientesAudit = { id: 123 };
      jest.spyOn(clientesAuditService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ clientesAudit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(clientesAuditService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
