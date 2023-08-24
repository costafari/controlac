import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AbonosFormService } from './abonos-form.service';
import { AbonosService } from '../service/abonos.service';
import { IAbonos } from '../abonos.model';
import { IFacturas } from 'app/entities/facturas/facturas.model';
import { FacturasService } from 'app/entities/facturas/service/facturas.service';

import { AbonosUpdateComponent } from './abonos-update.component';

describe('Abonos Management Update Component', () => {
  let comp: AbonosUpdateComponent;
  let fixture: ComponentFixture<AbonosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let abonosFormService: AbonosFormService;
  let abonosService: AbonosService;
  let facturasService: FacturasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), AbonosUpdateComponent],
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
      .overrideTemplate(AbonosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AbonosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    abonosFormService = TestBed.inject(AbonosFormService);
    abonosService = TestBed.inject(AbonosService);
    facturasService = TestBed.inject(FacturasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Facturas query and add missing value', () => {
      const abonos: IAbonos = { id: 456 };
      const facturas: IFacturas = { id: 12502 };
      abonos.facturas = facturas;

      const facturasCollection: IFacturas[] = [{ id: 3004 }];
      jest.spyOn(facturasService, 'query').mockReturnValue(of(new HttpResponse({ body: facturasCollection })));
      const additionalFacturas = [facturas];
      const expectedCollection: IFacturas[] = [...additionalFacturas, ...facturasCollection];
      jest.spyOn(facturasService, 'addFacturasToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ abonos });
      comp.ngOnInit();

      expect(facturasService.query).toHaveBeenCalled();
      expect(facturasService.addFacturasToCollectionIfMissing).toHaveBeenCalledWith(
        facturasCollection,
        ...additionalFacturas.map(expect.objectContaining)
      );
      expect(comp.facturasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const abonos: IAbonos = { id: 456 };
      const facturas: IFacturas = { id: 4789 };
      abonos.facturas = facturas;

      activatedRoute.data = of({ abonos });
      comp.ngOnInit();

      expect(comp.facturasSharedCollection).toContain(facturas);
      expect(comp.abonos).toEqual(abonos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonos>>();
      const abonos = { id: 123 };
      jest.spyOn(abonosFormService, 'getAbonos').mockReturnValue(abonos);
      jest.spyOn(abonosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abonos }));
      saveSubject.complete();

      // THEN
      expect(abonosFormService.getAbonos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(abonosService.update).toHaveBeenCalledWith(expect.objectContaining(abonos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonos>>();
      const abonos = { id: 123 };
      jest.spyOn(abonosFormService, 'getAbonos').mockReturnValue({ id: null });
      jest.spyOn(abonosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: abonos }));
      saveSubject.complete();

      // THEN
      expect(abonosFormService.getAbonos).toHaveBeenCalled();
      expect(abonosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAbonos>>();
      const abonos = { id: 123 };
      jest.spyOn(abonosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ abonos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(abonosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFacturas', () => {
      it('Should forward to facturasService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(facturasService, 'compareFacturas');
        comp.compareFacturas(entity, entity2);
        expect(facturasService.compareFacturas).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
