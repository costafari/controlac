import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductoAudit } from '../producto-audit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../producto-audit.test-samples';

import { ProductoAuditService } from './producto-audit.service';

const requireRestSample: IProductoAudit = {
  ...sampleWithRequiredData,
};

describe('ProductoAudit Service', () => {
  let service: ProductoAuditService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductoAudit | IProductoAudit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductoAuditService);
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

    it('should create a ProductoAudit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productoAudit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productoAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductoAudit', () => {
      const productoAudit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productoAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductoAudit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductoAudit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductoAudit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductoAuditToCollectionIfMissing', () => {
      it('should add a ProductoAudit to an empty array', () => {
        const productoAudit: IProductoAudit = sampleWithRequiredData;
        expectedResult = service.addProductoAuditToCollectionIfMissing([], productoAudit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productoAudit);
      });

      it('should not add a ProductoAudit to an array that contains it', () => {
        const productoAudit: IProductoAudit = sampleWithRequiredData;
        const productoAuditCollection: IProductoAudit[] = [
          {
            ...productoAudit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductoAuditToCollectionIfMissing(productoAuditCollection, productoAudit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductoAudit to an array that doesn't contain it", () => {
        const productoAudit: IProductoAudit = sampleWithRequiredData;
        const productoAuditCollection: IProductoAudit[] = [sampleWithPartialData];
        expectedResult = service.addProductoAuditToCollectionIfMissing(productoAuditCollection, productoAudit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productoAudit);
      });

      it('should add only unique ProductoAudit to an array', () => {
        const productoAuditArray: IProductoAudit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productoAuditCollection: IProductoAudit[] = [sampleWithRequiredData];
        expectedResult = service.addProductoAuditToCollectionIfMissing(productoAuditCollection, ...productoAuditArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productoAudit: IProductoAudit = sampleWithRequiredData;
        const productoAudit2: IProductoAudit = sampleWithPartialData;
        expectedResult = service.addProductoAuditToCollectionIfMissing([], productoAudit, productoAudit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productoAudit);
        expect(expectedResult).toContain(productoAudit2);
      });

      it('should accept null and undefined values', () => {
        const productoAudit: IProductoAudit = sampleWithRequiredData;
        expectedResult = service.addProductoAuditToCollectionIfMissing([], null, productoAudit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productoAudit);
      });

      it('should return initial array if no ProductoAudit is added', () => {
        const productoAuditCollection: IProductoAudit[] = [sampleWithRequiredData];
        expectedResult = service.addProductoAuditToCollectionIfMissing(productoAuditCollection, undefined, null);
        expect(expectedResult).toEqual(productoAuditCollection);
      });
    });

    describe('compareProductoAudit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductoAudit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductoAudit(entity1, entity2);
        const compareResult2 = service.compareProductoAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductoAudit(entity1, entity2);
        const compareResult2 = service.compareProductoAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductoAudit(entity1, entity2);
        const compareResult2 = service.compareProductoAudit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
