import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProveedorAudit } from '../proveedor-audit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../proveedor-audit.test-samples';

import { ProveedorAuditService } from './proveedor-audit.service';

const requireRestSample: IProveedorAudit = {
  ...sampleWithRequiredData,
};

describe('ProveedorAudit Service', () => {
  let service: ProveedorAuditService;
  let httpMock: HttpTestingController;
  let expectedResult: IProveedorAudit | IProveedorAudit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProveedorAuditService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProveedorAudit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const proveedorAudit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(proveedorAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProveedorAudit', () => {
      const proveedorAudit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(proveedorAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProveedorAudit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProveedorAudit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProveedorAudit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProveedorAuditToCollectionIfMissing', () => {
      it('should add a ProveedorAudit to an empty array', () => {
        const proveedorAudit: IProveedorAudit = sampleWithRequiredData;
        expectedResult = service.addProveedorAuditToCollectionIfMissing([], proveedorAudit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(proveedorAudit);
      });

      it('should not add a ProveedorAudit to an array that contains it', () => {
        const proveedorAudit: IProveedorAudit = sampleWithRequiredData;
        const proveedorAuditCollection: IProveedorAudit[] = [
          {
            ...proveedorAudit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProveedorAuditToCollectionIfMissing(proveedorAuditCollection, proveedorAudit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProveedorAudit to an array that doesn't contain it", () => {
        const proveedorAudit: IProveedorAudit = sampleWithRequiredData;
        const proveedorAuditCollection: IProveedorAudit[] = [sampleWithPartialData];
        expectedResult = service.addProveedorAuditToCollectionIfMissing(proveedorAuditCollection, proveedorAudit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(proveedorAudit);
      });

      it('should add only unique ProveedorAudit to an array', () => {
        const proveedorAuditArray: IProveedorAudit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const proveedorAuditCollection: IProveedorAudit[] = [sampleWithRequiredData];
        expectedResult = service.addProveedorAuditToCollectionIfMissing(proveedorAuditCollection, ...proveedorAuditArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const proveedorAudit: IProveedorAudit = sampleWithRequiredData;
        const proveedorAudit2: IProveedorAudit = sampleWithPartialData;
        expectedResult = service.addProveedorAuditToCollectionIfMissing([], proveedorAudit, proveedorAudit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(proveedorAudit);
        expect(expectedResult).toContain(proveedorAudit2);
      });

      it('should accept null and undefined values', () => {
        const proveedorAudit: IProveedorAudit = sampleWithRequiredData;
        expectedResult = service.addProveedorAuditToCollectionIfMissing([], null, proveedorAudit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(proveedorAudit);
      });

      it('should return initial array if no ProveedorAudit is added', () => {
        const proveedorAuditCollection: IProveedorAudit[] = [sampleWithRequiredData];
        expectedResult = service.addProveedorAuditToCollectionIfMissing(proveedorAuditCollection, undefined, null);
        expect(expectedResult).toEqual(proveedorAuditCollection);
      });
    });

    describe('compareProveedorAudit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProveedorAudit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProveedorAudit(entity1, entity2);
        const compareResult2 = service.compareProveedorAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProveedorAudit(entity1, entity2);
        const compareResult2 = service.compareProveedorAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProveedorAudit(entity1, entity2);
        const compareResult2 = service.compareProveedorAudit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
