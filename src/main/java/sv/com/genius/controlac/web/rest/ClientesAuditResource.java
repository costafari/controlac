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
import sv.com.genius.controlac.domain.ClientesAudit;
import sv.com.genius.controlac.repository.ClientesAuditRepository;
import sv.com.genius.controlac.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sv.com.genius.controlac.domain.ClientesAudit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientesAuditResource {

    private final Logger log = LoggerFactory.getLogger(ClientesAuditResource.class);

    private static final String ENTITY_NAME = "clientesAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientesAuditRepository clientesAuditRepository;

    public ClientesAuditResource(ClientesAuditRepository clientesAuditRepository) {
        this.clientesAuditRepository = clientesAuditRepository;
    }

    /**
     * {@code POST  /clientes-audits} : Create a new clientesAudit.
     *
     * @param clientesAudit the clientesAudit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientesAudit, or with status {@code 400 (Bad Request)} if the clientesAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clientes-audits")
    public ResponseEntity<ClientesAudit> createClientesAudit(@Valid @RequestBody ClientesAudit clientesAudit) throws URISyntaxException {
        log.debug("REST request to save ClientesAudit : {}", clientesAudit);
        if (clientesAudit.getId() != null) {
            throw new BadRequestAlertException("A new clientesAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientesAudit result = clientesAuditRepository.save(clientesAudit);
        return ResponseEntity
            .created(new URI("/api/clientes-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clientes-audits/:id} : Updates an existing clientesAudit.
     *
     * @param id the id of the clientesAudit to save.
     * @param clientesAudit the clientesAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientesAudit,
     * or with status {@code 400 (Bad Request)} if the clientesAudit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientesAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clientes-audits/{id}")
    public ResponseEntity<ClientesAudit> updateClientesAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClientesAudit clientesAudit
    ) throws URISyntaxException {
        log.debug("REST request to update ClientesAudit : {}, {}", id, clientesAudit);
        if (clientesAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientesAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientesAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientesAudit result = clientesAuditRepository.save(clientesAudit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientesAudit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clientes-audits/:id} : Partial updates given fields of an existing clientesAudit, field will ignore if it is null
     *
     * @param id the id of the clientesAudit to save.
     * @param clientesAudit the clientesAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientesAudit,
     * or with status {@code 400 (Bad Request)} if the clientesAudit is not valid,
     * or with status {@code 404 (Not Found)} if the clientesAudit is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientesAudit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clientes-audits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClientesAudit> partialUpdateClientesAudit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClientesAudit clientesAudit
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientesAudit partially : {}, {}", id, clientesAudit);
        if (clientesAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientesAudit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientesAuditRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClientesAudit> result = clientesAuditRepository
            .findById(clientesAudit.getId())
            .map(existingClientesAudit -> {
                if (clientesAudit.getFechaAudit() != null) {
                    existingClientesAudit.setFechaAudit(clientesAudit.getFechaAudit());
                }
                if (clientesAudit.getTipoCrud() != null) {
                    existingClientesAudit.setTipoCrud(clientesAudit.getTipoCrud());
                }
                if (clientesAudit.getIdCliente() != null) {
                    existingClientesAudit.setIdCliente(clientesAudit.getIdCliente());
                }
                if (clientesAudit.getIdUSuario() != null) {
                    existingClientesAudit.setIdUSuario(clientesAudit.getIdUSuario());
                }

                return existingClientesAudit;
            })
            .map(clientesAuditRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientesAudit.getId().toString())
        );
    }

    /**
     * {@code GET  /clientes-audits} : get all the clientesAudits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientesAudits in body.
     */
    @GetMapping("/clientes-audits")
    public List<ClientesAudit> getAllClientesAudits() {
        log.debug("REST request to get all ClientesAudits");
        return clientesAuditRepository.findAll();
    }

    /**
     * {@code GET  /clientes-audits/:id} : get the "id" clientesAudit.
     *
     * @param id the id of the clientesAudit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientesAudit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clientes-audits/{id}")
    public ResponseEntity<ClientesAudit> getClientesAudit(@PathVariable Long id) {
        log.debug("REST request to get ClientesAudit : {}", id);
        Optional<ClientesAudit> clientesAudit = clientesAuditRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clientesAudit);
    }

    /**
     * {@code DELETE  /clientes-audits/:id} : delete the "id" clientesAudit.
     *
     * @param id the id of the clientesAudit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clientes-audits/{id}")
    public ResponseEntity<Void> deleteClientesAudit(@PathVariable Long id) {
        log.debug("REST request to delete ClientesAudit : {}", id);
        clientesAuditRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
