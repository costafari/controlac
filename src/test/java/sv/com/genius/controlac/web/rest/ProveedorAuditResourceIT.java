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
import sv.com.genius.controlac.domain.ProveedorAudit;
import sv.com.genius.controlac.repository.ProveedorAuditRepository;

/**
 * Integration tests for the {@link ProveedorAuditResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProveedorAuditResourceIT {

    private static final String DEFAULT_FECHA_AUDIT = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_AUDIT = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CRUD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CRUD = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PROVEEDOR = 1L;
    private static final Long UPDATED_ID_PROVEEDOR = 2L;

    private static final Long DEFAULT_ID_U_SUARIO = 1L;
    private static final Long UPDATED_ID_U_SUARIO = 2L;

    private static final String ENTITY_API_URL = "/api/proveedor-audits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProveedorAuditRepository proveedorAuditRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProveedorAuditMockMvc;

    private ProveedorAudit proveedorAudit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProveedorAudit createEntity(EntityManager em) {
        ProveedorAudit proveedorAudit = new ProveedorAudit()
            .fechaAudit(DEFAULT_FECHA_AUDIT)
            .tipoCrud(DEFAULT_TIPO_CRUD)
            .idProveedor(DEFAULT_ID_PROVEEDOR)
            .idUSuario(DEFAULT_ID_U_SUARIO);
        return proveedorAudit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProveedorAudit createUpdatedEntity(EntityManager em) {
        ProveedorAudit proveedorAudit = new ProveedorAudit()
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idProveedor(UPDATED_ID_PROVEEDOR)
            .idUSuario(UPDATED_ID_U_SUARIO);
        return proveedorAudit;
    }

    @BeforeEach
    public void initTest() {
        proveedorAudit = createEntity(em);
    }

    @Test
    @Transactional
    void createProveedorAudit() throws Exception {
        int databaseSizeBeforeCreate = proveedorAuditRepository.findAll().size();
        // Create the ProveedorAudit
        restProveedorAuditMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isCreated());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeCreate + 1);
        ProveedorAudit testProveedorAudit = proveedorAuditList.get(proveedorAuditList.size() - 1);
        assertThat(testProveedorAudit.getFechaAudit()).isEqualTo(DEFAULT_FECHA_AUDIT);
        assertThat(testProveedorAudit.getTipoCrud()).isEqualTo(DEFAULT_TIPO_CRUD);
        assertThat(testProveedorAudit.getIdProveedor()).isEqualTo(DEFAULT_ID_PROVEEDOR);
        assertThat(testProveedorAudit.getIdUSuario()).isEqualTo(DEFAULT_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void createProveedorAuditWithExistingId() throws Exception {
        // Create the ProveedorAudit with an existing ID
        proveedorAudit.setId(1L);

        int databaseSizeBeforeCreate = proveedorAuditRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProveedorAuditMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorAuditRepository.findAll().size();
        // set the field null
        proveedorAudit.setFechaAudit(null);

        // Create the ProveedorAudit, which fails.

        restProveedorAuditMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoCrudIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorAuditRepository.findAll().size();
        // set the field null
        proveedorAudit.setTipoCrud(null);

        // Create the ProveedorAudit, which fails.

        restProveedorAuditMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdProveedorIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorAuditRepository.findAll().size();
        // set the field null
        proveedorAudit.setIdProveedor(null);

        // Create the ProveedorAudit, which fails.

        restProveedorAuditMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdUSuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = proveedorAuditRepository.findAll().size();
        // set the field null
        proveedorAudit.setIdUSuario(null);

        // Create the ProveedorAudit, which fails.

        restProveedorAuditMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProveedorAudits() throws Exception {
        // Initialize the database
        proveedorAuditRepository.saveAndFlush(proveedorAudit);

        // Get all the proveedorAuditList
        restProveedorAuditMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedorAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaAudit").value(hasItem(DEFAULT_FECHA_AUDIT)))
            .andExpect(jsonPath("$.[*].tipoCrud").value(hasItem(DEFAULT_TIPO_CRUD)))
            .andExpect(jsonPath("$.[*].idProveedor").value(hasItem(DEFAULT_ID_PROVEEDOR.intValue())))
            .andExpect(jsonPath("$.[*].idUSuario").value(hasItem(DEFAULT_ID_U_SUARIO.intValue())));
    }

    @Test
    @Transactional
    void getProveedorAudit() throws Exception {
        // Initialize the database
        proveedorAuditRepository.saveAndFlush(proveedorAudit);

        // Get the proveedorAudit
        restProveedorAuditMockMvc
            .perform(get(ENTITY_API_URL_ID, proveedorAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proveedorAudit.getId().intValue()))
            .andExpect(jsonPath("$.fechaAudit").value(DEFAULT_FECHA_AUDIT))
            .andExpect(jsonPath("$.tipoCrud").value(DEFAULT_TIPO_CRUD))
            .andExpect(jsonPath("$.idProveedor").value(DEFAULT_ID_PROVEEDOR.intValue()))
            .andExpect(jsonPath("$.idUSuario").value(DEFAULT_ID_U_SUARIO.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProveedorAudit() throws Exception {
        // Get the proveedorAudit
        restProveedorAuditMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProveedorAudit() throws Exception {
        // Initialize the database
        proveedorAuditRepository.saveAndFlush(proveedorAudit);

        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();

        // Update the proveedorAudit
        ProveedorAudit updatedProveedorAudit = proveedorAuditRepository.findById(proveedorAudit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProveedorAudit are not directly saved in db
        em.detach(updatedProveedorAudit);
        updatedProveedorAudit
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idProveedor(UPDATED_ID_PROVEEDOR)
            .idUSuario(UPDATED_ID_U_SUARIO);

        restProveedorAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProveedorAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProveedorAudit))
            )
            .andExpect(status().isOk());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
        ProveedorAudit testProveedorAudit = proveedorAuditList.get(proveedorAuditList.size() - 1);
        assertThat(testProveedorAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testProveedorAudit.getTipoCrud()).isEqualTo(UPDATED_TIPO_CRUD);
        assertThat(testProveedorAudit.getIdProveedor()).isEqualTo(UPDATED_ID_PROVEEDOR);
        assertThat(testProveedorAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void putNonExistingProveedorAudit() throws Exception {
        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();
        proveedorAudit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proveedorAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProveedorAudit() throws Exception {
        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();
        proveedorAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProveedorAudit() throws Exception {
        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();
        proveedorAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorAuditMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(proveedorAudit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProveedorAuditWithPatch() throws Exception {
        // Initialize the database
        proveedorAuditRepository.saveAndFlush(proveedorAudit);

        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();

        // Update the proveedorAudit using partial update
        ProveedorAudit partialUpdatedProveedorAudit = new ProveedorAudit();
        partialUpdatedProveedorAudit.setId(proveedorAudit.getId());

        partialUpdatedProveedorAudit.fechaAudit(UPDATED_FECHA_AUDIT).idUSuario(UPDATED_ID_U_SUARIO);

        restProveedorAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProveedorAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProveedorAudit))
            )
            .andExpect(status().isOk());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
        ProveedorAudit testProveedorAudit = proveedorAuditList.get(proveedorAuditList.size() - 1);
        assertThat(testProveedorAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testProveedorAudit.getTipoCrud()).isEqualTo(DEFAULT_TIPO_CRUD);
        assertThat(testProveedorAudit.getIdProveedor()).isEqualTo(DEFAULT_ID_PROVEEDOR);
        assertThat(testProveedorAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void fullUpdateProveedorAuditWithPatch() throws Exception {
        // Initialize the database
        proveedorAuditRepository.saveAndFlush(proveedorAudit);

        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();

        // Update the proveedorAudit using partial update
        ProveedorAudit partialUpdatedProveedorAudit = new ProveedorAudit();
        partialUpdatedProveedorAudit.setId(proveedorAudit.getId());

        partialUpdatedProveedorAudit
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idProveedor(UPDATED_ID_PROVEEDOR)
            .idUSuario(UPDATED_ID_U_SUARIO);

        restProveedorAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProveedorAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProveedorAudit))
            )
            .andExpect(status().isOk());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
        ProveedorAudit testProveedorAudit = proveedorAuditList.get(proveedorAuditList.size() - 1);
        assertThat(testProveedorAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testProveedorAudit.getTipoCrud()).isEqualTo(UPDATED_TIPO_CRUD);
        assertThat(testProveedorAudit.getIdProveedor()).isEqualTo(UPDATED_ID_PROVEEDOR);
        assertThat(testProveedorAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingProveedorAudit() throws Exception {
        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();
        proveedorAudit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proveedorAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProveedorAudit() throws Exception {
        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();
        proveedorAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProveedorAudit() throws Exception {
        int databaseSizeBeforeUpdate = proveedorAuditRepository.findAll().size();
        proveedorAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorAuditMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(proveedorAudit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProveedorAudit in the database
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProveedorAudit() throws Exception {
        // Initialize the database
        proveedorAuditRepository.saveAndFlush(proveedorAudit);

        int databaseSizeBeforeDelete = proveedorAuditRepository.findAll().size();

        // Delete the proveedorAudit
        restProveedorAuditMockMvc
            .perform(delete(ENTITY_API_URL_ID, proveedorAudit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProveedorAudit> proveedorAuditList = proveedorAuditRepository.findAll();
        assertThat(proveedorAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
