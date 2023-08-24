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
import sv.com.genius.controlac.domain.ProductoAudit;
import sv.com.genius.controlac.repository.ProductoAuditRepository;

/**
 * Integration tests for the {@link ProductoAuditResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoAuditResourceIT {

    private static final String DEFAULT_FECHA_AUDIT = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_AUDIT = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_CRUD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CRUD = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PRODUCTO = 1L;
    private static final Long UPDATED_ID_PRODUCTO = 2L;

    private static final Long DEFAULT_ID_U_SUARIO = 1L;
    private static final Long UPDATED_ID_U_SUARIO = 2L;

    private static final String ENTITY_API_URL = "/api/producto-audits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoAuditRepository productoAuditRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoAuditMockMvc;

    private ProductoAudit productoAudit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoAudit createEntity(EntityManager em) {
        ProductoAudit productoAudit = new ProductoAudit()
            .fechaAudit(DEFAULT_FECHA_AUDIT)
            .tipoCrud(DEFAULT_TIPO_CRUD)
            .idProducto(DEFAULT_ID_PRODUCTO)
            .idUSuario(DEFAULT_ID_U_SUARIO);
        return productoAudit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoAudit createUpdatedEntity(EntityManager em) {
        ProductoAudit productoAudit = new ProductoAudit()
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idProducto(UPDATED_ID_PRODUCTO)
            .idUSuario(UPDATED_ID_U_SUARIO);
        return productoAudit;
    }

    @BeforeEach
    public void initTest() {
        productoAudit = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoAudit() throws Exception {
        int databaseSizeBeforeCreate = productoAuditRepository.findAll().size();
        // Create the ProductoAudit
        restProductoAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoAudit)))
            .andExpect(status().isCreated());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoAudit testProductoAudit = productoAuditList.get(productoAuditList.size() - 1);
        assertThat(testProductoAudit.getFechaAudit()).isEqualTo(DEFAULT_FECHA_AUDIT);
        assertThat(testProductoAudit.getTipoCrud()).isEqualTo(DEFAULT_TIPO_CRUD);
        assertThat(testProductoAudit.getIdProducto()).isEqualTo(DEFAULT_ID_PRODUCTO);
        assertThat(testProductoAudit.getIdUSuario()).isEqualTo(DEFAULT_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void createProductoAuditWithExistingId() throws Exception {
        // Create the ProductoAudit with an existing ID
        productoAudit.setId(1L);

        int databaseSizeBeforeCreate = productoAuditRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoAudit)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaAuditIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoAuditRepository.findAll().size();
        // set the field null
        productoAudit.setFechaAudit(null);

        // Create the ProductoAudit, which fails.

        restProductoAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoAudit)))
            .andExpect(status().isBadRequest());

        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoCrudIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoAuditRepository.findAll().size();
        // set the field null
        productoAudit.setTipoCrud(null);

        // Create the ProductoAudit, which fails.

        restProductoAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoAudit)))
            .andExpect(status().isBadRequest());

        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdProductoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoAuditRepository.findAll().size();
        // set the field null
        productoAudit.setIdProducto(null);

        // Create the ProductoAudit, which fails.

        restProductoAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoAudit)))
            .andExpect(status().isBadRequest());

        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdUSuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoAuditRepository.findAll().size();
        // set the field null
        productoAudit.setIdUSuario(null);

        // Create the ProductoAudit, which fails.

        restProductoAuditMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoAudit)))
            .andExpect(status().isBadRequest());

        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductoAudits() throws Exception {
        // Initialize the database
        productoAuditRepository.saveAndFlush(productoAudit);

        // Get all the productoAuditList
        restProductoAuditMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaAudit").value(hasItem(DEFAULT_FECHA_AUDIT)))
            .andExpect(jsonPath("$.[*].tipoCrud").value(hasItem(DEFAULT_TIPO_CRUD)))
            .andExpect(jsonPath("$.[*].idProducto").value(hasItem(DEFAULT_ID_PRODUCTO.intValue())))
            .andExpect(jsonPath("$.[*].idUSuario").value(hasItem(DEFAULT_ID_U_SUARIO.intValue())));
    }

    @Test
    @Transactional
    void getProductoAudit() throws Exception {
        // Initialize the database
        productoAuditRepository.saveAndFlush(productoAudit);

        // Get the productoAudit
        restProductoAuditMockMvc
            .perform(get(ENTITY_API_URL_ID, productoAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoAudit.getId().intValue()))
            .andExpect(jsonPath("$.fechaAudit").value(DEFAULT_FECHA_AUDIT))
            .andExpect(jsonPath("$.tipoCrud").value(DEFAULT_TIPO_CRUD))
            .andExpect(jsonPath("$.idProducto").value(DEFAULT_ID_PRODUCTO.intValue()))
            .andExpect(jsonPath("$.idUSuario").value(DEFAULT_ID_U_SUARIO.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductoAudit() throws Exception {
        // Get the productoAudit
        restProductoAuditMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductoAudit() throws Exception {
        // Initialize the database
        productoAuditRepository.saveAndFlush(productoAudit);

        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();

        // Update the productoAudit
        ProductoAudit updatedProductoAudit = productoAuditRepository.findById(productoAudit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProductoAudit are not directly saved in db
        em.detach(updatedProductoAudit);
        updatedProductoAudit
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idProducto(UPDATED_ID_PRODUCTO)
            .idUSuario(UPDATED_ID_U_SUARIO);

        restProductoAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductoAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductoAudit))
            )
            .andExpect(status().isOk());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
        ProductoAudit testProductoAudit = productoAuditList.get(productoAuditList.size() - 1);
        assertThat(testProductoAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testProductoAudit.getTipoCrud()).isEqualTo(UPDATED_TIPO_CRUD);
        assertThat(testProductoAudit.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testProductoAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void putNonExistingProductoAudit() throws Exception {
        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();
        productoAudit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoAudit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoAudit() throws Exception {
        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();
        productoAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoAuditMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoAudit() throws Exception {
        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();
        productoAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoAuditMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoAudit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoAuditWithPatch() throws Exception {
        // Initialize the database
        productoAuditRepository.saveAndFlush(productoAudit);

        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();

        // Update the productoAudit using partial update
        ProductoAudit partialUpdatedProductoAudit = new ProductoAudit();
        partialUpdatedProductoAudit.setId(productoAudit.getId());

        partialUpdatedProductoAudit
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idProducto(UPDATED_ID_PRODUCTO)
            .idUSuario(UPDATED_ID_U_SUARIO);

        restProductoAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoAudit))
            )
            .andExpect(status().isOk());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
        ProductoAudit testProductoAudit = productoAuditList.get(productoAuditList.size() - 1);
        assertThat(testProductoAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testProductoAudit.getTipoCrud()).isEqualTo(UPDATED_TIPO_CRUD);
        assertThat(testProductoAudit.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testProductoAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void fullUpdateProductoAuditWithPatch() throws Exception {
        // Initialize the database
        productoAuditRepository.saveAndFlush(productoAudit);

        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();

        // Update the productoAudit using partial update
        ProductoAudit partialUpdatedProductoAudit = new ProductoAudit();
        partialUpdatedProductoAudit.setId(productoAudit.getId());

        partialUpdatedProductoAudit
            .fechaAudit(UPDATED_FECHA_AUDIT)
            .tipoCrud(UPDATED_TIPO_CRUD)
            .idProducto(UPDATED_ID_PRODUCTO)
            .idUSuario(UPDATED_ID_U_SUARIO);

        restProductoAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoAudit))
            )
            .andExpect(status().isOk());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
        ProductoAudit testProductoAudit = productoAuditList.get(productoAuditList.size() - 1);
        assertThat(testProductoAudit.getFechaAudit()).isEqualTo(UPDATED_FECHA_AUDIT);
        assertThat(testProductoAudit.getTipoCrud()).isEqualTo(UPDATED_TIPO_CRUD);
        assertThat(testProductoAudit.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testProductoAudit.getIdUSuario()).isEqualTo(UPDATED_ID_U_SUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingProductoAudit() throws Exception {
        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();
        productoAudit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoAudit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoAudit() throws Exception {
        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();
        productoAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoAuditMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoAudit))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoAudit() throws Exception {
        int databaseSizeBeforeUpdate = productoAuditRepository.findAll().size();
        productoAudit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoAuditMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productoAudit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoAudit in the database
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoAudit() throws Exception {
        // Initialize the database
        productoAuditRepository.saveAndFlush(productoAudit);

        int databaseSizeBeforeDelete = productoAuditRepository.findAll().size();

        // Delete the productoAudit
        restProductoAuditMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoAudit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoAudit> productoAuditList = productoAuditRepository.findAll();
        assertThat(productoAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
