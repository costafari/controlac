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
import sv.com.genius.controlac.domain.Clientes;
import sv.com.genius.controlac.repository.ClientesRepository;

/**
 * Integration tests for the {@link ClientesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientesResourceIT {

    private static final Boolean DEFAULT_ESTADO_CLIENTE = false;
    private static final Boolean UPDATED_ESTADO_CLIENTE = true;

    private static final String DEFAULT_NOMBRES_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_REG_FISCAL = "AAAAAAAAAA";
    private static final String UPDATED_REG_FISCAL = "BBBBBBBBBB";

    private static final String DEFAULT_GIRO = "AAAAAAAAAA";
    private static final String UPDATED_GIRO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTAS = "AAAAAAAAAA";
    private static final String UPDATED_NOTAS = "BBBBBBBBBB";

    private static final String DEFAULT_SITIO_WEB = "AAAAAAAAAA";
    private static final String UPDATED_SITIO_WEB = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONO_FIJO = 1;
    private static final Integer UPDATED_TELEFONO_FIJO = 2;

    private static final Integer DEFAULT_TELEFONO_FIJO_2 = 1;
    private static final Integer UPDATED_TELEFONO_FIJO_2 = 2;

    private static final Integer DEFAULT_TELEFONO_MOVIL = 1;
    private static final Integer UPDATED_TELEFONO_MOVIL = 2;

    private static final Integer DEFAULT_TELEFONO_MOVIL_2 = 1;
    private static final Integer UPDATED_TELEFONO_MOVIL_2 = 2;

    private static final String DEFAULT_FECHA_REGISTRO = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_REGISTRO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_ULTIMA_C = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_ULTIMA_C = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientesMockMvc;

    private Clientes clientes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clientes createEntity(EntityManager em) {
        Clientes clientes = new Clientes()
            .estadoCliente(DEFAULT_ESTADO_CLIENTE)
            .nombresContacto(DEFAULT_NOMBRES_CONTACTO)
            .apellidoContacto(DEFAULT_APELLIDO_CONTACTO)
            .direccion(DEFAULT_DIRECCION)
            .email(DEFAULT_EMAIL)
            .nombreEmpresa(DEFAULT_NOMBRE_EMPRESA)
            .regFiscal(DEFAULT_REG_FISCAL)
            .giro(DEFAULT_GIRO)
            .notas(DEFAULT_NOTAS)
            .sitioWeb(DEFAULT_SITIO_WEB)
            .telefonoFijo(DEFAULT_TELEFONO_FIJO)
            .telefonoFijo2(DEFAULT_TELEFONO_FIJO_2)
            .telefonoMovil(DEFAULT_TELEFONO_MOVIL)
            .telefonoMovil2(DEFAULT_TELEFONO_MOVIL_2)
            .fechaRegistro(DEFAULT_FECHA_REGISTRO)
            .fechaUltimaC(DEFAULT_FECHA_ULTIMA_C);
        return clientes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clientes createUpdatedEntity(EntityManager em) {
        Clientes clientes = new Clientes()
            .estadoCliente(UPDATED_ESTADO_CLIENTE)
            .nombresContacto(UPDATED_NOMBRES_CONTACTO)
            .apellidoContacto(UPDATED_APELLIDO_CONTACTO)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .regFiscal(UPDATED_REG_FISCAL)
            .giro(UPDATED_GIRO)
            .notas(UPDATED_NOTAS)
            .sitioWeb(UPDATED_SITIO_WEB)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .telefonoFijo2(UPDATED_TELEFONO_FIJO_2)
            .telefonoMovil(UPDATED_TELEFONO_MOVIL)
            .telefonoMovil2(UPDATED_TELEFONO_MOVIL_2)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaUltimaC(UPDATED_FECHA_ULTIMA_C);
        return clientes;
    }

    @BeforeEach
    public void initTest() {
        clientes = createEntity(em);
    }

    @Test
    @Transactional
    void createClientes() throws Exception {
        int databaseSizeBeforeCreate = clientesRepository.findAll().size();
        // Create the Clientes
        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isCreated());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeCreate + 1);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getEstadoCliente()).isEqualTo(DEFAULT_ESTADO_CLIENTE);
        assertThat(testClientes.getNombresContacto()).isEqualTo(DEFAULT_NOMBRES_CONTACTO);
        assertThat(testClientes.getApellidoContacto()).isEqualTo(DEFAULT_APELLIDO_CONTACTO);
        assertThat(testClientes.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testClientes.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClientes.getNombreEmpresa()).isEqualTo(DEFAULT_NOMBRE_EMPRESA);
        assertThat(testClientes.getRegFiscal()).isEqualTo(DEFAULT_REG_FISCAL);
        assertThat(testClientes.getGiro()).isEqualTo(DEFAULT_GIRO);
        assertThat(testClientes.getNotas()).isEqualTo(DEFAULT_NOTAS);
        assertThat(testClientes.getSitioWeb()).isEqualTo(DEFAULT_SITIO_WEB);
        assertThat(testClientes.getTelefonoFijo()).isEqualTo(DEFAULT_TELEFONO_FIJO);
        assertThat(testClientes.getTelefonoFijo2()).isEqualTo(DEFAULT_TELEFONO_FIJO_2);
        assertThat(testClientes.getTelefonoMovil()).isEqualTo(DEFAULT_TELEFONO_MOVIL);
        assertThat(testClientes.getTelefonoMovil2()).isEqualTo(DEFAULT_TELEFONO_MOVIL_2);
        assertThat(testClientes.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testClientes.getFechaUltimaC()).isEqualTo(DEFAULT_FECHA_ULTIMA_C);
    }

    @Test
    @Transactional
    void createClientesWithExistingId() throws Exception {
        // Create the Clientes with an existing ID
        clientes.setId(1L);

        int databaseSizeBeforeCreate = clientesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoClienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setEstadoCliente(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombresContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setNombresContacto(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidoContactoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setApellidoContacto(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setDireccion(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setEmail(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoFijoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setTelefonoFijo(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoMovilIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setTelefonoMovil(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaRegistroIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setFechaRegistro(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaUltimaCIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setFechaUltimaC(null);

        // Create the Clientes, which fails.

        restClientesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get all the clientesList
        restClientesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientes.getId().intValue())))
            .andExpect(jsonPath("$.[*].estadoCliente").value(hasItem(DEFAULT_ESTADO_CLIENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].nombresContacto").value(hasItem(DEFAULT_NOMBRES_CONTACTO)))
            .andExpect(jsonPath("$.[*].apellidoContacto").value(hasItem(DEFAULT_APELLIDO_CONTACTO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].nombreEmpresa").value(hasItem(DEFAULT_NOMBRE_EMPRESA)))
            .andExpect(jsonPath("$.[*].regFiscal").value(hasItem(DEFAULT_REG_FISCAL)))
            .andExpect(jsonPath("$.[*].giro").value(hasItem(DEFAULT_GIRO)))
            .andExpect(jsonPath("$.[*].notas").value(hasItem(DEFAULT_NOTAS)))
            .andExpect(jsonPath("$.[*].sitioWeb").value(hasItem(DEFAULT_SITIO_WEB)))
            .andExpect(jsonPath("$.[*].telefonoFijo").value(hasItem(DEFAULT_TELEFONO_FIJO)))
            .andExpect(jsonPath("$.[*].telefonoFijo2").value(hasItem(DEFAULT_TELEFONO_FIJO_2)))
            .andExpect(jsonPath("$.[*].telefonoMovil").value(hasItem(DEFAULT_TELEFONO_MOVIL)))
            .andExpect(jsonPath("$.[*].telefonoMovil2").value(hasItem(DEFAULT_TELEFONO_MOVIL_2)))
            .andExpect(jsonPath("$.[*].fechaRegistro").value(hasItem(DEFAULT_FECHA_REGISTRO)))
            .andExpect(jsonPath("$.[*].fechaUltimaC").value(hasItem(DEFAULT_FECHA_ULTIMA_C)));
    }

    @Test
    @Transactional
    void getClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get the clientes
        restClientesMockMvc
            .perform(get(ENTITY_API_URL_ID, clientes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientes.getId().intValue()))
            .andExpect(jsonPath("$.estadoCliente").value(DEFAULT_ESTADO_CLIENTE.booleanValue()))
            .andExpect(jsonPath("$.nombresContacto").value(DEFAULT_NOMBRES_CONTACTO))
            .andExpect(jsonPath("$.apellidoContacto").value(DEFAULT_APELLIDO_CONTACTO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.nombreEmpresa").value(DEFAULT_NOMBRE_EMPRESA))
            .andExpect(jsonPath("$.regFiscal").value(DEFAULT_REG_FISCAL))
            .andExpect(jsonPath("$.giro").value(DEFAULT_GIRO))
            .andExpect(jsonPath("$.notas").value(DEFAULT_NOTAS))
            .andExpect(jsonPath("$.sitioWeb").value(DEFAULT_SITIO_WEB))
            .andExpect(jsonPath("$.telefonoFijo").value(DEFAULT_TELEFONO_FIJO))
            .andExpect(jsonPath("$.telefonoFijo2").value(DEFAULT_TELEFONO_FIJO_2))
            .andExpect(jsonPath("$.telefonoMovil").value(DEFAULT_TELEFONO_MOVIL))
            .andExpect(jsonPath("$.telefonoMovil2").value(DEFAULT_TELEFONO_MOVIL_2))
            .andExpect(jsonPath("$.fechaRegistro").value(DEFAULT_FECHA_REGISTRO))
            .andExpect(jsonPath("$.fechaUltimaC").value(DEFAULT_FECHA_ULTIMA_C));
    }

    @Test
    @Transactional
    void getNonExistingClientes() throws Exception {
        // Get the clientes
        restClientesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Update the clientes
        Clientes updatedClientes = clientesRepository.findById(clientes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClientes are not directly saved in db
        em.detach(updatedClientes);
        updatedClientes
            .estadoCliente(UPDATED_ESTADO_CLIENTE)
            .nombresContacto(UPDATED_NOMBRES_CONTACTO)
            .apellidoContacto(UPDATED_APELLIDO_CONTACTO)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .regFiscal(UPDATED_REG_FISCAL)
            .giro(UPDATED_GIRO)
            .notas(UPDATED_NOTAS)
            .sitioWeb(UPDATED_SITIO_WEB)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .telefonoFijo2(UPDATED_TELEFONO_FIJO_2)
            .telefonoMovil(UPDATED_TELEFONO_MOVIL)
            .telefonoMovil2(UPDATED_TELEFONO_MOVIL_2)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaUltimaC(UPDATED_FECHA_ULTIMA_C);

        restClientesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientes))
            )
            .andExpect(status().isOk());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getEstadoCliente()).isEqualTo(UPDATED_ESTADO_CLIENTE);
        assertThat(testClientes.getNombresContacto()).isEqualTo(UPDATED_NOMBRES_CONTACTO);
        assertThat(testClientes.getApellidoContacto()).isEqualTo(UPDATED_APELLIDO_CONTACTO);
        assertThat(testClientes.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testClientes.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClientes.getNombreEmpresa()).isEqualTo(UPDATED_NOMBRE_EMPRESA);
        assertThat(testClientes.getRegFiscal()).isEqualTo(UPDATED_REG_FISCAL);
        assertThat(testClientes.getGiro()).isEqualTo(UPDATED_GIRO);
        assertThat(testClientes.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testClientes.getSitioWeb()).isEqualTo(UPDATED_SITIO_WEB);
        assertThat(testClientes.getTelefonoFijo()).isEqualTo(UPDATED_TELEFONO_FIJO);
        assertThat(testClientes.getTelefonoFijo2()).isEqualTo(UPDATED_TELEFONO_FIJO_2);
        assertThat(testClientes.getTelefonoMovil()).isEqualTo(UPDATED_TELEFONO_MOVIL);
        assertThat(testClientes.getTelefonoMovil2()).isEqualTo(UPDATED_TELEFONO_MOVIL_2);
        assertThat(testClientes.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testClientes.getFechaUltimaC()).isEqualTo(UPDATED_FECHA_ULTIMA_C);
    }

    @Test
    @Transactional
    void putNonExistingClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();
        clientes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();
        clientes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();
        clientes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientesWithPatch() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Update the clientes using partial update
        Clientes partialUpdatedClientes = new Clientes();
        partialUpdatedClientes.setId(clientes.getId());

        partialUpdatedClientes
            .estadoCliente(UPDATED_ESTADO_CLIENTE)
            .notas(UPDATED_NOTAS)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .telefonoFijo2(UPDATED_TELEFONO_FIJO_2)
            .telefonoMovil(UPDATED_TELEFONO_MOVIL)
            .telefonoMovil2(UPDATED_TELEFONO_MOVIL_2)
            .fechaUltimaC(UPDATED_FECHA_ULTIMA_C);

        restClientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientes))
            )
            .andExpect(status().isOk());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getEstadoCliente()).isEqualTo(UPDATED_ESTADO_CLIENTE);
        assertThat(testClientes.getNombresContacto()).isEqualTo(DEFAULT_NOMBRES_CONTACTO);
        assertThat(testClientes.getApellidoContacto()).isEqualTo(DEFAULT_APELLIDO_CONTACTO);
        assertThat(testClientes.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testClientes.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClientes.getNombreEmpresa()).isEqualTo(DEFAULT_NOMBRE_EMPRESA);
        assertThat(testClientes.getRegFiscal()).isEqualTo(DEFAULT_REG_FISCAL);
        assertThat(testClientes.getGiro()).isEqualTo(DEFAULT_GIRO);
        assertThat(testClientes.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testClientes.getSitioWeb()).isEqualTo(DEFAULT_SITIO_WEB);
        assertThat(testClientes.getTelefonoFijo()).isEqualTo(UPDATED_TELEFONO_FIJO);
        assertThat(testClientes.getTelefonoFijo2()).isEqualTo(UPDATED_TELEFONO_FIJO_2);
        assertThat(testClientes.getTelefonoMovil()).isEqualTo(UPDATED_TELEFONO_MOVIL);
        assertThat(testClientes.getTelefonoMovil2()).isEqualTo(UPDATED_TELEFONO_MOVIL_2);
        assertThat(testClientes.getFechaRegistro()).isEqualTo(DEFAULT_FECHA_REGISTRO);
        assertThat(testClientes.getFechaUltimaC()).isEqualTo(UPDATED_FECHA_ULTIMA_C);
    }

    @Test
    @Transactional
    void fullUpdateClientesWithPatch() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Update the clientes using partial update
        Clientes partialUpdatedClientes = new Clientes();
        partialUpdatedClientes.setId(clientes.getId());

        partialUpdatedClientes
            .estadoCliente(UPDATED_ESTADO_CLIENTE)
            .nombresContacto(UPDATED_NOMBRES_CONTACTO)
            .apellidoContacto(UPDATED_APELLIDO_CONTACTO)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL)
            .nombreEmpresa(UPDATED_NOMBRE_EMPRESA)
            .regFiscal(UPDATED_REG_FISCAL)
            .giro(UPDATED_GIRO)
            .notas(UPDATED_NOTAS)
            .sitioWeb(UPDATED_SITIO_WEB)
            .telefonoFijo(UPDATED_TELEFONO_FIJO)
            .telefonoFijo2(UPDATED_TELEFONO_FIJO_2)
            .telefonoMovil(UPDATED_TELEFONO_MOVIL)
            .telefonoMovil2(UPDATED_TELEFONO_MOVIL_2)
            .fechaRegistro(UPDATED_FECHA_REGISTRO)
            .fechaUltimaC(UPDATED_FECHA_ULTIMA_C);

        restClientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientes))
            )
            .andExpect(status().isOk());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getEstadoCliente()).isEqualTo(UPDATED_ESTADO_CLIENTE);
        assertThat(testClientes.getNombresContacto()).isEqualTo(UPDATED_NOMBRES_CONTACTO);
        assertThat(testClientes.getApellidoContacto()).isEqualTo(UPDATED_APELLIDO_CONTACTO);
        assertThat(testClientes.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testClientes.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClientes.getNombreEmpresa()).isEqualTo(UPDATED_NOMBRE_EMPRESA);
        assertThat(testClientes.getRegFiscal()).isEqualTo(UPDATED_REG_FISCAL);
        assertThat(testClientes.getGiro()).isEqualTo(UPDATED_GIRO);
        assertThat(testClientes.getNotas()).isEqualTo(UPDATED_NOTAS);
        assertThat(testClientes.getSitioWeb()).isEqualTo(UPDATED_SITIO_WEB);
        assertThat(testClientes.getTelefonoFijo()).isEqualTo(UPDATED_TELEFONO_FIJO);
        assertThat(testClientes.getTelefonoFijo2()).isEqualTo(UPDATED_TELEFONO_FIJO_2);
        assertThat(testClientes.getTelefonoMovil()).isEqualTo(UPDATED_TELEFONO_MOVIL);
        assertThat(testClientes.getTelefonoMovil2()).isEqualTo(UPDATED_TELEFONO_MOVIL_2);
        assertThat(testClientes.getFechaRegistro()).isEqualTo(UPDATED_FECHA_REGISTRO);
        assertThat(testClientes.getFechaUltimaC()).isEqualTo(UPDATED_FECHA_ULTIMA_C);
    }

    @Test
    @Transactional
    void patchNonExistingClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();
        clientes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();
        clientes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientes))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();
        clientes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeDelete = clientesRepository.findAll().size();

        // Delete the clientes
        restClientesMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
