import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FacturasFormService } from './facturas-form.service';
import { FacturasService } from '../service/facturas.service';
import { IFacturas } from '../facturas.model';
import { IDetalles } from 'app/entities/detalles/detalles.model';
import { DetallesService } from 'app/entities/detalles/service/detalles.service';
import { IClientes } from 'app/entities/clientes/clientes.model';
import { ClientesService } from 'app/entities/clientes/service/clientes.service';

import { FacturasUpdateComponent } from './facturas-update.component';

describe('Facturas Management Update Component', () => {
  let comp: FacturasUpdateComponent;
  let fixture: ComponentFixture<FacturasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let facturasFormService: FacturasFormService;
  let facturasService: FacturasService;
  let detallesService: DetallesService;
  let clientesService: ClientesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), FacturasUpdateComponent],
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
      .overrideTemplate(FacturasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FacturasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    facturasFormService = TestBed.inject(FacturasFormService);
    facturasService = TestBed.inject(FacturasService);
    detallesService = TestBed.inject(DetallesService);
    clientesService = TestBed.inject(ClientesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Detalles query and add missing value', () => {
      const facturas: IFacturas = { id: 456 };
      const detalles: IDetalles = { id: 16932 };
      facturas.detalles = detalles;

      const detallesCollection: IDetalles[] = [{ id: 30404 }];
      jest.spyOn(detallesService, 'query').mockReturnValue(of(new HttpResponse({ body: detallesCollection })));
      const additionalDetalles = [detalles];
      const expectedCollection: IDetalles[] = [...additionalDetalles, ...detallesCollection];
      jest.spyOn(detallesService, 'addDetallesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ facturas });
      comp.ngOnInit();

      expect(detallesService.query).toHaveBeenCalled();
      expect(detallesService.addDetallesToCollectionIfMissing).toHaveBeenCalledWith(
        detallesCollection,
        ...additionalDetalles.map(expect.objectContaining)
      );
      expect(comp.detallesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Clientes query and add missing value', () => {
      const facturas: IFacturas = { id: 456 };
      const clientes: IClientes = { id: 25353 };
      facturas.clientes = clientes;

      const clientesCollection: IClientes[] = [{ id: 25040 }];
      jest.spyOn(clientesService, 'query').mockReturnValue(of(new HttpResponse({ body: clientesCollection })));
      const additionalClientes = [clientes];
      const expectedCollection: IClientes[] = [...additionalClientes, ...clientesCollection];
      jest.spyOn(clientesService, 'addClientesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ facturas });
      comp.ngOnInit();

      expect(clientesService.query).toHaveBeenCalled();
      expect(clientesService.addClientesToCollectionIfMissing).toHaveBeenCalledWith(
        clientesCollection,
        ...additionalClientes.map(expect.objectContaining)
      );
      expect(comp.clientesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const facturas: IFacturas = { id: 456 };
      const detalles: IDetalles = { id: 14402 };
      facturas.detalles = detalles;
      const clientes: IClientes = { id: 23735 };
      facturas.clientes = clientes;

      activatedRoute.data = of({ facturas });
      comp.ngOnInit();

      expect(comp.detallesSharedCollection).toContain(detalles);
      expect(comp.clientesSharedCollection).toContain(clientes);
      expect(comp.facturas).toEqual(facturas);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFacturas>>();
      const facturas = { id: 123 };
      jest.spyOn(facturasFormService, 'getFacturas').mockReturnValue(facturas);
      jest.spyOn(facturasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ facturas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: facturas }));
      saveSubject.complete();

      // THEN
      expect(facturasFormService.getFacturas).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(facturasService.update).toHaveBeenCalledWith(expect.objectContaining(facturas));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFacturas>>();
      const facturas = { id: 123 };
      jest.spyOn(facturasFormService, 'getFacturas').mockReturnValue({ id: null });
      jest.spyOn(facturasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ facturas: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: facturas }));
      saveSubject.complete();

      // THEN
      expect(facturasFormService.getFacturas).toHaveBeenCalled();
      expect(facturasService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFacturas>>();
      const facturas = { id: 123 };
      jest.spyOn(facturasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ facturas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(facturasService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDetalles', () => {
      it('Should forward to detallesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(detallesService, 'compareDetalles');
        comp.compareDetalles(entity, entity2);
        expect(detallesService.compareDetalles).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareClientes', () => {
      it('Should forward to clientesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clientesService, 'compareClientes');
        comp.compareClientes(entity, entity2);
        expect(clientesService.compareClientes).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
