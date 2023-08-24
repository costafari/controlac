package sv.com.genius.controlac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sv.com.genius.controlac.IntegrationTest;
import sv.com.genius.controlac.domain.ClientesAudit;
import sv.com.genius.controlac.repository.ClientesAuditRepository;

/**
 * Integration tests for the {@link ClientesAuditResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientesAuditResourceIT {

    private static final String DEFAULT_FECHA_AUDIT = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_AUDIT = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CRUD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CRUD = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_CLIENTE = 1L;
    private static final Long UPDATED_ID_CLIENTE = 2L;

    private static final Long DEFAULT_ID_U_SUARIO = 1L;
    private static final Long UPDATED_ID_U_SUARIO = 2L;

    private static final String ENTITY_API_URL = "/api/clientes-audits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientesAuditRepository clientesAuditRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientesAuditMockMvc;

    private ClientesAudit clientesAudit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientesAudit createEntity(EntityManager em) {
        ClientesAudit clientesAudit = new ClientesAudit()
            .fechaAudit(DEFAULT_FECHA_AUDIT)
            .tipoCrud(DEFAULT_TIPO_CRUD)
            .idCliente(DEFAULT_ID_CLIENTE)
            .idUSuario(DEFAULT_ID_U_SUARIO);
        return clientesAudit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientesAudit createUpdatedEntity(EntityManager em) {
        ClientesAudit clientesAudit = new ClientesAudit()
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idCliente(UPDATED_ID_CLIENTE)
            .idUSuario(UPDATED_ID_U_SUARIO);
        return clientesAudit;
    }

    @BeforeEach
    public void initTest() {
        clientesAudit = createEntity(em);
    }

    @Test
    @Transactional
    void createClientesAudit() throws Exception {
        int databaseSizeBeforeCreate = clientesAuditRepository.findAll().size();
        // Create the ClientesAudit
        restClientesAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientesAudit)))
            .andExpect(status().isCreated());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeCreate + 1);
        ClientesAudit testClientesAudit = clientesAuditList.get(clientesAuditList.size() - 1);
        assertThat(testClientesAudit.getFechaAudit()).isEqualTo(DEFAULT_FECHA_AUDIT);
        assertThat(testClientesAudit.getTipoCrud()).isEqualTo(DEFAULT_TIPO_CRUD);
        assertThat(testClientesAudit.getIdCliente()).isEqualTo(DEFAULT_ID_CLIENTE);
        assertThat(testClientesAudit.getIdUSuario()).isEqualTo(DEFAULT_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void createClientesAuditWithExistingId() throws Exception {
        // Create the ClientesAudit with an existing ID
        clientesAudit.setId(1L);

        int databaseSizeBeforeCreate = clientesAuditRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientesAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientesAudit)))
            .andExpect(status().isBadRequest());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesAuditRepository.findAll().size();
        // set the field null
        clientesAudit.setFechaAudit(null);

        // Create the ClientesAudit, which fails.

        restClientesAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientesAudit)))
            .andExpect(status().isBadRequest());

        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoCrudIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesAuditRepository.findAll().size();
        // set the field null
        clientesAudit.setTipoCrud(null);

        // Create the ClientesAudit, which fails.

        restClientesAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientesAudit)))
            .andExpect(status().isBadRequest());

        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdClienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesAuditRepository.findAll().size();
        // set the field null
        clientesAudit.setIdCliente(null);

        // Create the ClientesAudit, which fails.

        restClientesAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientesAudit)))
            .andExpect(status().isBadRequest());

        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdUSuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesAuditRepository.findAll().size();
        // set the field null
        clientesAudit.setIdUSuario(null);

        // Create the ClientesAudit, which fails.

        restClientesAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientesAudit)))
            .andExpect(status().isBadRequest());

        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClientesAudits() throws Exception {
        // Initialize the database
        clientesAuditRepository.saveAndFlush(clientesAudit);

        // Get all the clientesAuditList
        restClientesAuditMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientesAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaAudit").value(hasItem(DEFAULT_FECHA_AUDIT)))
            .andExpect(jsonPath("$.[*].tipoCrud").value(hasItem(DEFAULT_TIPO_CRUD)))
            .andExpect(jsonPath("$.[*].idCliente").value(hasItem(DEFAULT_ID_CLIENTE.intValue())))
            .andExpect(jsonPath("$.[*].idUSuario").value(hasItem(DEFAULT_ID_U_SUARIO.intValue())));
    }

    @Test
    @Transactional
    void getClientesAudit() throws Exception {
        // Initialize the database
        clientesAuditRepository.saveAndFlush(clientesAudit);

        // Get the clientesAudit
        restClientesAuditMockMvc
            .perform(get(ENTITY_API_URL_ID, clientesAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientesAudit.getId().intValue()))
            .andExpect(jsonPath("$.fechaAudit").value(DEFAULT_FECHA_AUDIT))
            .andExpect(jsonPath("$.tipoCrud").value(DEFAULT_TIPO_CRUD))
            .andExpect(jsonPath("$.idCliente").value(DEFAULT_ID_CLIENTE.intValue()))
            .andExpect(jsonPath("$.idUSuario").value(DEFAULT_ID_U_SUARIO.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingClientesAudit() throws Exception {
        // Get the clientesAudit
        restClientesAuditMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientesAudit() throws Exception {
        // Initialize the database
        clientesAuditRepository.saveAndFlush(clientesAudit);

        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();

        // Update the clientesAudit
        ClientesAudit updatedClientesAudit = clientesAuditRepository.findById(clientesAudit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClientesAudit are not directly saved in db
        em.detach(updatedClientesAudit);
        updatedClientesAudit
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idCliente(UPDATED_ID_CLIENTE)
            .idUSuario(UPDATED_ID_U_SUARIO);

        restClientesAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientesAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientesAudit))
            )
            .andExpect(status().isOk());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
        ClientesAudit testClientesAudit = clientesAuditList.get(clientesAuditList.size() - 1);
        assertThat(testClientesAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testClientesAudit.getTipoCrud()).isEqualTo(UPDATED_TIPO_CRUD);
        assertThat(testClientesAudit.getIdCliente()).isEqualTo(UPDATED_ID_CLIENTE);
        assertThat(testClientesAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void putNonExistingClientesAudit() throws Exception {
        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();
        clientesAudit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientesAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientesAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientesAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientesAudit() throws Exception {
        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();
        clientesAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientesAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientesAudit() throws Exception {
        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();
        clientesAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesAuditMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientesAudit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientesAuditWithPatch() throws Exception {
        // Initialize the database
        clientesAuditRepository.saveAndFlush(clientesAudit);

        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();

        // Update the clientesAudit using partial update
        ClientesAudit partialUpdatedClientesAudit = new ClientesAudit();
        partialUpdatedClientesAudit.setId(clientesAudit.getId());

        partialUpdatedClientesAudit.idUSuario(UPDATED_ID_U_SUARIO);

        restClientesAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientesAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientesAudit))
            )
            .andExpect(status().isOk());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
        ClientesAudit testClientesAudit = clientesAuditList.get(clientesAuditList.size() - 1);
        assertThat(testClientesAudit.getFechaAudit()).isEqualTo(DEFAULT_FECHA_AUDIT);
        assertThat(testClientesAudit.getTipoCrud()).isEqualTo(DEFAULT_TIPO_CRUD);
        assertThat(testClientesAudit.getIdCliente()).isEqualTo(DEFAULT_ID_CLIENTE);
        assertThat(testClientesAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void fullUpdateClientesAuditWithPatch() throws Exception {
        // Initialize the database
        clientesAuditRepository.saveAndFlush(clientesAudit);

        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();

        // Update the clientesAudit using partial update
        ClientesAudit partialUpdatedClientesAudit = new ClientesAudit();
        partialUpdatedClientesAudit.setId(clientesAudit.getId());

        partialUpdatedClientesAudit
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idCliente(UPDATED_ID_CLIENTE)
            .idUSuario(UPDATED_ID_U_SUARIO);

        restClientesAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientesAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientesAudit))
            )
            .andExpect(status().isOk());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
        ClientesAudit testClientesAudit = clientesAuditList.get(clientesAuditList.size() - 1);
        assertThat(testClientesAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testClientesAudit.getTipoCrud()).isEqualTo(UPDATED_TIPO_CRUD);
        assertThat(testClientesAudit.getIdCliente()).isEqualTo(UPDATED_ID_CLIENTE);
        assertThat(testClientesAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingClientesAudit() throws Exception {
        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();
        clientesAudit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientesAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientesAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientesAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientesAudit() throws Exception {
        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();
        clientesAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientesAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientesAudit() throws Exception {
        int databaseSizeBeforeUpdate = clientesAuditRepository.findAll().size();
        clientesAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesAuditMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientesAudit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientesAudit in the database
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientesAudit() throws Exception {
        // Initialize the database
        clientesAuditRepository.saveAndFlush(clientesAudit);

        int databaseSizeBeforeDelete = clientesAuditRepository.findAll().size();

        // Delete the clientesAudit
        restClientesAuditMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientesAudit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientesAudit> clientesAuditList = clientesAuditRepository.findAll();
        assertThat(clientesAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
