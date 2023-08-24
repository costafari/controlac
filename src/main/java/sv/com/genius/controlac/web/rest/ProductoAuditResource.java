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
import sv.com.genius.controlac.domain.ProductoAudit;
import sv.com.genius.controlac.repository.ProductoAuditRepository;
import sv.com.genius.controlac.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sv.com.genius.controlac.domain.ProductoAudit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductoAuditResource {

    private final Logger log = LoggerFactory.getLogger(ProductoAuditResource.class);

    private static final String ENTITY_NAME = "productoAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoAuditRepository productoAuditRepository;

    public ProductoAuditResource(ProductoAuditRepository productoAuditRepository) {
        this.productoAuditRepository = productoAuditRepository;
    }

    /**
     * {@code POST  /producto-audits} : Create a new productoAudit.
     *
     * @param productoAudit the productoAudit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoAudit, or with status {@code 400 (Bad Request)} if the productoAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-audits")
    public ResponseEntity<ProductoAudit> createProductoAudit(@Valid @RequestBody ProductoAudit productoAudit) throws URISyntaxException {
        log.debug("REST request to save ProductoAudit : {}", productoAudit);
        if (productoAudit.getId() != null) {
            throw new BadRequestAlertException("A new productoAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoAudit result = productoAuditRepository.save(productoAudit);
        return ResponseEntity
            .created(new URI("/api/producto-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-audits/:id} : Updates an existing productoAudit.
     *
     * @param id the id of the productoAudit to save.
     * @param productoAudit the productoAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoAudit,
     * or with status {@code 400 (Bad Request)} if the productoAudit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-audits/{id}")
    public ResponseEntity<ProductoAudit> updateProductoAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoAudit productoAudit
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoAudit : {}, {}", id, productoAudit);
        if (productoAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoAudit result = productoAuditRepository.save(productoAudit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoAudit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-audits/:id} : Partial updates given fields of an existing productoAudit, field will ignore if it is null
     *
     * @param id the id of the productoAudit to save.
     * @param productoAudit the productoAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoAudit,
     * or with status {@code 400 (Bad Request)} if the productoAudit is not valid,
     * or with status {@code 404 (Not Found)} if the productoAudit is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-audits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoAudit> partialUpdateProductoAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoAudit productoAudit
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoAudit partially : {}, {}", id, productoAudit);
        if (productoAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoAudit> result = productoAuditRepository
            .findById(productoAudit.getId())
            .map(existingProductoAudit -> {
                if (productoAudit.getFechaAudit() != null) {
                    existingProductoAudit.setFechaAudit(productoAudit.getFechaAudit());
                }
                if (productoAudit.getTipoCrud() != null) {
                    existingProductoAudit.setTipoCrud(productoAudit.getTipoCrud());
                }
                if (productoAudit.getIdProducto() != null) {
                    existingProductoAudit.setIdProducto(productoAudit.getIdProducto());
                }
                if (productoAudit.getIdUSuario() != null) {
                    existingProductoAudit.setIdUSuario(productoAudit.getIdUSuario());
                }

                return existingProductoAudit;
            })
            .map(productoAuditRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoAudit.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-audits} : get all the productoAudits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoAudits in body.
     */
    @GetMapping("/producto-audits")
    public List<ProductoAudit> getAllProductoAudits() {
        log.debug("REST request to get all ProductoAudits");
        return productoAuditRepository.findAll();
    }

    /**
     * {@code GET  /producto-audits/:id} : get the "id" productoAudit.
     *
     * @param id the id of the productoAudit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoAudit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-audits/{id}")
    public ResponseEntity<ProductoAudit> getProductoAudit(@PathVariable Long id) {
        log.debug("REST request to get ProductoAudit : {}", id);
        Optional<ProductoAudit> productoAudit = productoAuditRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(productoAudit);
    }

    /**
     * {@code DELETE  /producto-audits/:id} : delete the "id" productoAudit.
     *
     * @param id the id of the productoAudit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-audits/{id}")
    public ResponseEntity<Void> deleteProductoAudit(@PathVariable Long id) {
        log.debug("REST request to delete ProductoAudit : {}", id);
        productoAuditRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
