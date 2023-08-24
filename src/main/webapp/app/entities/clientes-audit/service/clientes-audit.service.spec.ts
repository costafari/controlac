import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IClientesAudit } from '../clientes-audit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../clientes-audit.test-samples';

import { ClientesAuditService } from './clientes-audit.service';

const requireRestSample: IClientesAudit = {
  ...sampleWithRequiredData,
};

describe('ClientesAudit Service', () => {
  let service: ClientesAuditService;
  let httpMock: HttpTestingController;
  let expectedResult: IClientesAudit | IClientesAudit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ClientesAuditService);
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

    it('should create a ClientesAudit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const clientesAudit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(clientesAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ClientesAudit', () => {
      const clientesAudit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(clientesAudit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ClientesAudit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ClientesAudit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ClientesAudit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addClientesAuditToCollectionIfMissing', () => {
      it('should add a ClientesAudit to an empty array', () => {
        const clientesAudit: IClientesAudit = sampleWithRequiredData;
        expectedResult = service.addClientesAuditToCollectionIfMissing([], clientesAudit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(clientesAudit);
      });

      it('should not add a ClientesAudit to an array that contains it', () => {
        const clientesAudit: IClientesAudit = sampleWithRequiredData;
        const clientesAuditCollection: IClientesAudit[] = [
          {
            ...clientesAudit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addClientesAuditToCollectionIfMissing(clientesAuditCollection, clientesAudit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ClientesAudit to an array that doesn't contain it", () => {
        const clientesAudit: IClientesAudit = sampleWithRequiredData;
        const clientesAuditCollection: IClientesAudit[] = [sampleWithPartialData];
        expectedResult = service.addClientesAuditToCollectionIfMissing(clientesAuditCollection, clientesAudit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(clientesAudit);
      });

      it('should add only unique ClientesAudit to an array', () => {
        const clientesAuditArray: IClientesAudit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const clientesAuditCollection: IClientesAudit[] = [sampleWithRequiredData];
        expectedResult = service.addClientesAuditToCollectionIfMissing(clientesAuditCollection, ...clientesAuditArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const clientesAudit: IClientesAudit = sampleWithRequiredData;
        const clientesAudit2: IClientesAudit = sampleWithPartialData;
        expectedResult = service.addClientesAuditToCollectionIfMissing([], clientesAudit, clientesAudit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(clientesAudit);
        expect(expectedResult).toContain(clientesAudit2);
      });

      it('should accept null and undefined values', () => {
        const clientesAudit: IClientesAudit = sampleWithRequiredData;
        expectedResult = service.addClientesAuditToCollectionIfMissing([], null, clientesAudit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(clientesAudit);
      });

      it('should return initial array if no ClientesAudit is added', () => {
        const clientesAuditCollection: IClientesAudit[] = [sampleWithRequiredData];
        expectedResult = service.addClientesAuditToCollectionIfMissing(clientesAuditCollection, undefined, null);
        expect(expectedResult).toEqual(clientesAuditCollection);
      });
    });

    describe('compareClientesAudit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareClientesAudit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareClientesAudit(entity1, entity2);
        const compareResult2 = service.compareClientesAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareClientesAudit(entity1, entity2);
        const compareResult2 = service.compareClientesAudit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareClientesAudit(entity1, entity2);
        const compareResult2 = service.compareClientesAudit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
