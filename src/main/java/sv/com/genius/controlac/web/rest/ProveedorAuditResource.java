package sv.com.genius.controlac.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sv.com.genius.controlac.domain.ProveedorAudit;
import sv.com.genius.controlac.repository.ProveedorAuditRepository;
import sv.com.genius.controlac.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sv.com.genius.controlac.domain.ProveedorAudit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProveedorAuditResource {

    private final Logger log = LoggerFactory.getLogger(ProveedorAuditResource.class);

    private static final String ENTITY_NAME = "proveedorAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProveedorAuditRepository proveedorAuditRepository;

    public ProveedorAuditResource(ProveedorAuditRepository proveedorAuditRepository) {
        this.proveedorAuditRepository = proveedorAuditRepository;
    }

    /**
     * {@code POST  /proveedor-audits} : Create a new proveedorAudit.
     *
     * @param proveedorAudit the proveedorAudit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proveedorAudit, or with status {@code 400 (Bad Request)} if the proveedorAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proveedor-audits")
    public ResponseEntity<ProveedorAudit> createProveedorAudit(@Valid @RequestBody ProveedorAudit proveedorAudit)
        throws URISyntaxException {
        log.debug("REST request to save ProveedorAudit : {}", proveedorAudit);
        if (proveedorAudit.getId() != null) {
            throw new BadRequestAlertException("A new proveedorAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProveedorAudit result = proveedorAuditRepository.save(proveedorAudit);
        return ResponseEntity
            .created(new URI("/api/proveedor-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proveedor-audits/:id} : Updates an existing proveedorAudit.
     *
     * @param id the id of the proveedorAudit to save.
     * @param proveedorAudit the proveedorAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proveedorAudit,
     * or with status {@code 400 (Bad Request)} if the proveedorAudit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proveedorAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proveedor-audits/{id}")
    public ResponseEntity<ProveedorAudit> updateProveedorAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProveedorAudit proveedorAudit
    ) throws URISyntaxException {
        log.debug("REST request to update ProveedorAudit : {}, {}", id, proveedorAudit);
        if (proveedorAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proveedorAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proveedorAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProveedorAudit result = proveedorAuditRepository.save(proveedorAudit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proveedorAudit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /proveedor-audits/:id} : Partial updates given fields of an existing proveedorAudit, field will ignore if it is null
     *
     * @param id the id of the proveedorAudit to save.
     * @param proveedorAudit the proveedorAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proveedorAudit,
     * or with status {@code 400 (Bad Request)} if the proveedorAudit is not valid,
     * or with status {@code 404 (Not Found)} if the proveedorAudit is not found,
     * or with status {@code 500 (Internal Server Error)} if the proveedorAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/proveedor-audits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProveedorAudit> partialUpdateProveedorAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProveedorAudit proveedorAudit
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProveedorAudit partially : {}, {}", id, proveedorAudit);
        if (proveedorAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, proveedorAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!proveedorAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProveedorAudit> result = proveedorAuditRepository
            .findById(proveedorAudit.getId())
            .map(existingProveedorAudit -> {
                if (proveedorAudit.getFechaAudit() != null) {
                    existingProveedorAudit.setFechaAudit(proveedorAudit.getFechaAudit());
                }
                if (proveedorAudit.getTipoCrud() != null) {
                    existingProveedorAudit.setTipoCrud(proveedorAudit.getTipoCrud());
                }
                if (proveedorAudit.getIdProveedor() != null) {
                    existingProveedorAudit.setIdProveedor(proveedorAudit.getIdProveedor());
                }
                if (proveedorAudit.getIdUSuario() != null) {
                    existingProveedorAudit.setIdUSuario(proveedorAudit.getIdUSuario());
                }

                return existingProveedorAudit;
            })
            .map(proveedorAuditRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proveedorAudit.getId().toString())
        );
    }

    /**
     * {@code GET  /proveedor-audits} : get all the proveedorAudits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proveedorAudits in body.
     */
    @GetMapping("/proveedor-audits")
    public List<ProveedorAudit> getAllProveedorAudits() {
        log.debug("REST request to get all ProveedorAudits");
        return proveedorAuditRepository.findAll();
    }

    /**
     * {@code GET  /proveedor-audits/:id} : get the "id" proveedorAudit.
     *
     * @param id the id of the proveedorAudit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proveedorAudit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proveedor-audits/{id}")
    public ResponseEntity<ProveedorAudit> getProveedorAudit(@PathVariable Long id) {
        log.debug("REST request to get ProveedorAudit : {}", id);
        Optional<ProveedorAudit> proveedorAudit = proveedorAuditRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(proveedorAudit);
    }

    /**
     * {@code DELETE  /proveedor-audits/:id} : delete the "id" proveedorAudit.
     *
     * @param id the id of the proveedorAudit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proveedor-audits/{id}")
    public ResponseEntity<Void> deleteProveedorAudit(@PathVariable Long id) {
        log.debug("REST request to delete ProveedorAudit : {}", id);
        proveedorAuditRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
