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
import sv.com.genius.controlac.domain.Facturas;
import sv.com.genius.controlac.repository.FacturasRepository;
import sv.com.genius.controlac.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sv.com.genius.controlac.domain.Facturas}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FacturasResource {

    private final Logger log = LoggerFactory.getLogger(FacturasResource.class);

    private static final String ENTITY_NAME = "facturas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacturasRepository facturasRepository;

    public FacturasResource(FacturasRepository facturasRepository) {
        this.facturasRepository = facturasRepository;
    }

    /**
     * {@code POST  /facturas} : Create a new facturas.
     *
     * @param facturas the facturas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facturas, or with status {@code 400 (Bad Request)} if the facturas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facturas")
    public ResponseEntity<Facturas> createFacturas(@Valid @RequestBody Facturas facturas) throws URISyntaxException {
        log.debug("REST request to save Facturas : {}", facturas);
        if (facturas.getId() != null) {
            throw new BadRequestAlertException("A new facturas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facturas result = facturasRepository.save(facturas);
        return ResponseEntity
            .created(new URI("/api/facturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facturas/:id} : Updates an existing facturas.
     *
     * @param id the id of the facturas to save.
     * @param facturas the facturas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facturas,
     * or with status {@code 400 (Bad Request)} if the facturas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facturas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facturas/{id}")
    public ResponseEntity<Facturas> updateFacturas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Facturas facturas
    ) throws URISyntaxException {
        log.debug("REST request to update Facturas : {}, {}", id, facturas);
        if (facturas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facturas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facturasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Facturas result = facturasRepository.save(facturas);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facturas.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /facturas/:id} : Partial updates given fields of an existing facturas, field will ignore if it is null
     *
     * @param id the id of the facturas to save.
     * @param facturas the facturas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facturas,
     * or with status {@code 400 (Bad Request)} if the facturas is not valid,
     * or with status {@code 404 (Not Found)} if the facturas is not found,
     * or with status {@code 500 (Internal Server Error)} if the facturas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facturas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Facturas> partialUpdateFacturas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Facturas facturas
    ) throws URISyntaxException {
        log.debug("REST request to partial update Facturas partially : {}, {}", id, facturas);
        if (facturas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facturas.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facturasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Facturas> result = facturasRepository
            .findById(facturas.getId())
            .map(existingFacturas -> {
                if (facturas.getNumeroFactura() != null) {
                    existingFacturas.setNumeroFactura(facturas.getNumeroFactura());
                }
                if (facturas.getFechaFactura() != null) {
                    existingFacturas.setFechaFactura(facturas.getFechaFactura());
                }
                if (facturas.getCondicionPago() != null) {
                    existingFacturas.setCondicionPago(facturas.getCondicionPago());
                }
                if (facturas.getEstadoFactura() != null) {
                    existingFacturas.setEstadoFactura(facturas.getEstadoFactura());
                }

                return existingFacturas;
            })
            .map(facturasRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facturas.getId().toString())
        );
    }

    /**
     * {@code GET  /facturas} : get all the facturas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facturas in body.
     */
    @GetMapping("/facturas")
    public List<Facturas> getAllFacturas() {
        log.debug("REST request to get all Facturas");
        return facturasRepository.findAll();
    }

    /**
     * {@code GET  /facturas/:id} : get the "id" facturas.
     *
     * @param id the id of the facturas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facturas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facturas/{id}")
    public ResponseEntity<Facturas> getFacturas(@PathVariable Long id) {
        log.debug("REST request to get Facturas : {}", id);
        Optional<Facturas> facturas = facturasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(facturas);
    }

    /**
     * {@code DELETE  /facturas/:id} : delete the "id" facturas.
     *
     * @param id the id of the facturas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facturas/{id}")
    public ResponseEntity<Void> deleteFacturas(@PathVariable Long id) {
        log.debug("REST request to delete Facturas : {}", id);
        facturasRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
