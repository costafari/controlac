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
import sv.com.genius.controlac.domain.Detalles;
import sv.com.genius.controlac.repository.DetallesRepository;
import sv.com.genius.controlac.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sv.com.genius.controlac.domain.Detalles}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetallesResource {

    private final Logger log = LoggerFactory.getLogger(DetallesResource.class);

    private static final String ENTITY_NAME = "detalles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetallesRepository detallesRepository;

    public DetallesResource(DetallesRepository detallesRepository) {
        this.detallesRepository = detallesRepository;
    }

    /**
     * {@code POST  /detalles} : Create a new detalles.
     *
     * @param detalles the detalles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detalles, or with status {@code 400 (Bad Request)} if the detalles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalles")
    public ResponseEntity<Detalles> createDetalles(@Valid @RequestBody Detalles detalles) throws URISyntaxException {
        log.debug("REST request to save Detalles : {}", detalles);
        if (detalles.getId() != null) {
            throw new BadRequestAlertException("A new detalles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Detalles result = detallesRepository.save(detalles);
        return ResponseEntity
            .created(new URI("/api/detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalles/:id} : Updates an existing detalles.
     *
     * @param id the id of the detalles to save.
     * @param detalles the detalles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalles,
     * or with status {@code 400 (Bad Request)} if the detalles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detalles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalles/{id}")
    public ResponseEntity<Detalles> updateDetalles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Detalles detalles
    ) throws URISyntaxException {
        log.debug("REST request to update Detalles : {}, {}", id, detalles);
        if (detalles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Detalles result = detallesRepository.save(detalles);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalles.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalles/:id} : Partial updates given fields of an existing detalles, field will ignore if it is null
     *
     * @param id the id of the detalles to save.
     * @param detalles the detalles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detalles,
     * or with status {@code 400 (Bad Request)} if the detalles is not valid,
     * or with status {@code 404 (Not Found)} if the detalles is not found,
     * or with status {@code 500 (Internal Server Error)} if the detalles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detalles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Detalles> partialUpdateDetalles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Detalles detalles
    ) throws URISyntaxException {
        log.debug("REST request to partial update Detalles partially : {}, {}", id, detalles);
        if (detalles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detalles.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Detalles> result = detallesRepository
            .findById(detalles.getId())
            .map(existingDetalles -> {
                if (detalles.getCantidad() != null) {
                    existingDetalles.setCantidad(detalles.getCantidad());
                }
                if (detalles.getImpuestos() != null) {
                    existingDetalles.setImpuestos(detalles.getImpuestos());
                }
                if (detalles.getDescuento() != null) {
                    existingDetalles.setDescuento(detalles.getDescuento());
                }
                if (detalles.getTotal() != null) {
                    existingDetalles.setTotal(detalles.getTotal());
                }

                return existingDetalles;
            })
            .map(detallesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detalles.getId().toString())
        );
    }

    /**
     * {@code GET  /detalles} : get all the detalles.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detalles in body.
     */
    @GetMapping("/detalles")
    public List<Detalles> getAllDetalles(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Detalles");
        if (eagerload) {
            return detallesRepository.findAllWithEagerRelationships();
        } else {
            return detallesRepository.findAll();
        }
    }

    /**
     * {@code GET  /detalles/:id} : get the "id" detalles.
     *
     * @param id the id of the detalles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detalles, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalles/{id}")
    public ResponseEntity<Detalles> getDetalles(@PathVariable Long id) {
        log.debug("REST request to get Detalles : {}", id);
        Optional<Detalles> detalles = detallesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(detalles);
    }

    /**
     * {@code DELETE  /detalles/:id} : delete the "id" detalles.
     *
     * @param id the id of the detalles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalles/{id}")
    public ResponseEntity<Void> deleteDetalles(@PathVariable Long id) {
        log.debug("REST request to delete Detalles : {}", id);
        detallesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
