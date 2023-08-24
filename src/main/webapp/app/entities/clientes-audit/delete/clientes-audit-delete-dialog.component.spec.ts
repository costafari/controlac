jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ClientesAuditService } from '../service/clientes-audit.service';

import { ClientesAuditDeleteDialogComponent } from './clientes-audit-delete-dialog.component';

describe('ClientesAudit Management Delete Component', () => {
  let comp: ClientesAuditDeleteDialogComponent;
  let fixture: ComponentFixture<ClientesAuditDeleteDialogComponent>;
  let service: ClientesAuditService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ClientesAuditDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ClientesAuditDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ClientesAuditDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ClientesAuditService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
