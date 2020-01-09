package com.boxy.platform.web.rest;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.domain.DatabaseConnection;
import com.boxy.platform.repository.DatabaseConnectionRepository;
import com.boxy.platform.service.DatabaseConnectionService;
import com.boxy.platform.web.rest.errors.ExceptionTranslator;
import com.boxy.platform.service.dto.DatabaseConnectionCriteria;
import com.boxy.platform.service.DatabaseConnectionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.boxy.platform.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DatabaseConnectionResource} REST controller.
 */
@SpringBootTest(classes = PlatformApp.class)
public class DatabaseConnectionResourceIT {

    private static final String DEFAULT_CONNECTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_PORT = "AAAAAAAAAA";
    private static final String UPDATED_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DRIVER = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TEST = false;
    private static final Boolean UPDATED_TEST = true;

    @Autowired
    private DatabaseConnectionRepository databaseConnectionRepository;

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Autowired
    private DatabaseConnectionQueryService databaseConnectionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDatabaseConnectionMockMvc;

    private DatabaseConnection databaseConnection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatabaseConnectionResource databaseConnectionResource = new DatabaseConnectionResource(databaseConnectionService, databaseConnectionQueryService);
        this.restDatabaseConnectionMockMvc = MockMvcBuilders.standaloneSetup(databaseConnectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatabaseConnection createEntity(EntityManager em) {
        DatabaseConnection databaseConnection = new DatabaseConnection()
            .connectionName(DEFAULT_CONNECTION_NAME)
            .host(DEFAULT_HOST)
            .port(DEFAULT_PORT)
            .databaseName(DEFAULT_DATABASE_NAME)
            .params(DEFAULT_PARAMS)
            .type(DEFAULT_TYPE)
            .driver(DEFAULT_DRIVER)
            .url(DEFAULT_URL)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .test(DEFAULT_TEST);
        return databaseConnection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatabaseConnection createUpdatedEntity(EntityManager em) {
        DatabaseConnection databaseConnection = new DatabaseConnection()
            .connectionName(UPDATED_CONNECTION_NAME)
            .host(UPDATED_HOST)
            .port(UPDATED_PORT)
            .databaseName(UPDATED_DATABASE_NAME)
            .params(UPDATED_PARAMS)
            .type(UPDATED_TYPE)
            .driver(UPDATED_DRIVER)
            .url(UPDATED_URL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .test(UPDATED_TEST);
        return databaseConnection;
    }

    @BeforeEach
    public void initTest() {
        databaseConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatabaseConnection() throws Exception {
        int databaseSizeBeforeCreate = databaseConnectionRepository.findAll().size();

        // Create the DatabaseConnection
        restDatabaseConnectionMockMvc.perform(post("/api/database-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(databaseConnection)))
            .andExpect(status().isCreated());

        // Validate the DatabaseConnection in the database
        List<DatabaseConnection> databaseConnectionList = databaseConnectionRepository.findAll();
        assertThat(databaseConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        DatabaseConnection testDatabaseConnection = databaseConnectionList.get(databaseConnectionList.size() - 1);
        assertThat(testDatabaseConnection.getConnectionName()).isEqualTo(DEFAULT_CONNECTION_NAME);
        assertThat(testDatabaseConnection.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testDatabaseConnection.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testDatabaseConnection.getDatabaseName()).isEqualTo(DEFAULT_DATABASE_NAME);
        assertThat(testDatabaseConnection.getParams()).isEqualTo(DEFAULT_PARAMS);
        assertThat(testDatabaseConnection.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDatabaseConnection.getDriver()).isEqualTo(DEFAULT_DRIVER);
        assertThat(testDatabaseConnection.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDatabaseConnection.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDatabaseConnection.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDatabaseConnection.isTest()).isEqualTo(DEFAULT_TEST);
    }

    @Test
    @Transactional
    public void createDatabaseConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = databaseConnectionRepository.findAll().size();

        // Create the DatabaseConnection with an existing ID
        databaseConnection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatabaseConnectionMockMvc.perform(post("/api/database-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(databaseConnection)))
            .andExpect(status().isBadRequest());

        // Validate the DatabaseConnection in the database
        List<DatabaseConnection> databaseConnectionList = databaseConnectionRepository.findAll();
        assertThat(databaseConnectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDatabaseConnections() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList
        restDatabaseConnectionMockMvc.perform(get("/api/database-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(databaseConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].connectionName").value(hasItem(DEFAULT_CONNECTION_NAME.toString())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT.toString())))
            .andExpect(jsonPath("$.[*].databaseName").value(hasItem(DEFAULT_DATABASE_NAME.toString())))
            .andExpect(jsonPath("$.[*].params").value(hasItem(DEFAULT_PARAMS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDatabaseConnection() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get the databaseConnection
        restDatabaseConnectionMockMvc.perform(get("/api/database-connections/{id}", databaseConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(databaseConnection.getId().intValue()))
            .andExpect(jsonPath("$.connectionName").value(DEFAULT_CONNECTION_NAME.toString()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT.toString()))
            .andExpect(jsonPath("$.databaseName").value(DEFAULT_DATABASE_NAME.toString()))
            .andExpect(jsonPath("$.params").value(DEFAULT_PARAMS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.driver").value(DEFAULT_DRIVER.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByConnectionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where connectionName equals to DEFAULT_CONNECTION_NAME
        defaultDatabaseConnectionShouldBeFound("connectionName.equals=" + DEFAULT_CONNECTION_NAME);

        // Get all the databaseConnectionList where connectionName equals to UPDATED_CONNECTION_NAME
        defaultDatabaseConnectionShouldNotBeFound("connectionName.equals=" + UPDATED_CONNECTION_NAME);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByConnectionNameIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where connectionName in DEFAULT_CONNECTION_NAME or UPDATED_CONNECTION_NAME
        defaultDatabaseConnectionShouldBeFound("connectionName.in=" + DEFAULT_CONNECTION_NAME + "," + UPDATED_CONNECTION_NAME);

        // Get all the databaseConnectionList where connectionName equals to UPDATED_CONNECTION_NAME
        defaultDatabaseConnectionShouldNotBeFound("connectionName.in=" + UPDATED_CONNECTION_NAME);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByConnectionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where connectionName is not null
        defaultDatabaseConnectionShouldBeFound("connectionName.specified=true");

        // Get all the databaseConnectionList where connectionName is null
        defaultDatabaseConnectionShouldNotBeFound("connectionName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByHostIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where host equals to DEFAULT_HOST
        defaultDatabaseConnectionShouldBeFound("host.equals=" + DEFAULT_HOST);

        // Get all the databaseConnectionList where host equals to UPDATED_HOST
        defaultDatabaseConnectionShouldNotBeFound("host.equals=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByHostIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where host in DEFAULT_HOST or UPDATED_HOST
        defaultDatabaseConnectionShouldBeFound("host.in=" + DEFAULT_HOST + "," + UPDATED_HOST);

        // Get all the databaseConnectionList where host equals to UPDATED_HOST
        defaultDatabaseConnectionShouldNotBeFound("host.in=" + UPDATED_HOST);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByHostIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where host is not null
        defaultDatabaseConnectionShouldBeFound("host.specified=true");

        // Get all the databaseConnectionList where host is null
        defaultDatabaseConnectionShouldNotBeFound("host.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByPortIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where port equals to DEFAULT_PORT
        defaultDatabaseConnectionShouldBeFound("port.equals=" + DEFAULT_PORT);

        // Get all the databaseConnectionList where port equals to UPDATED_PORT
        defaultDatabaseConnectionShouldNotBeFound("port.equals=" + UPDATED_PORT);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByPortIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where port in DEFAULT_PORT or UPDATED_PORT
        defaultDatabaseConnectionShouldBeFound("port.in=" + DEFAULT_PORT + "," + UPDATED_PORT);

        // Get all the databaseConnectionList where port equals to UPDATED_PORT
        defaultDatabaseConnectionShouldNotBeFound("port.in=" + UPDATED_PORT);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByPortIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where port is not null
        defaultDatabaseConnectionShouldBeFound("port.specified=true");

        // Get all the databaseConnectionList where port is null
        defaultDatabaseConnectionShouldNotBeFound("port.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByDatabaseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where databaseName equals to DEFAULT_DATABASE_NAME
        defaultDatabaseConnectionShouldBeFound("databaseName.equals=" + DEFAULT_DATABASE_NAME);

        // Get all the databaseConnectionList where databaseName equals to UPDATED_DATABASE_NAME
        defaultDatabaseConnectionShouldNotBeFound("databaseName.equals=" + UPDATED_DATABASE_NAME);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByDatabaseNameIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where databaseName in DEFAULT_DATABASE_NAME or UPDATED_DATABASE_NAME
        defaultDatabaseConnectionShouldBeFound("databaseName.in=" + DEFAULT_DATABASE_NAME + "," + UPDATED_DATABASE_NAME);

        // Get all the databaseConnectionList where databaseName equals to UPDATED_DATABASE_NAME
        defaultDatabaseConnectionShouldNotBeFound("databaseName.in=" + UPDATED_DATABASE_NAME);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByDatabaseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where databaseName is not null
        defaultDatabaseConnectionShouldBeFound("databaseName.specified=true");

        // Get all the databaseConnectionList where databaseName is null
        defaultDatabaseConnectionShouldNotBeFound("databaseName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where params equals to DEFAULT_PARAMS
        defaultDatabaseConnectionShouldBeFound("params.equals=" + DEFAULT_PARAMS);

        // Get all the databaseConnectionList where params equals to UPDATED_PARAMS
        defaultDatabaseConnectionShouldNotBeFound("params.equals=" + UPDATED_PARAMS);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByParamsIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where params in DEFAULT_PARAMS or UPDATED_PARAMS
        defaultDatabaseConnectionShouldBeFound("params.in=" + DEFAULT_PARAMS + "," + UPDATED_PARAMS);

        // Get all the databaseConnectionList where params equals to UPDATED_PARAMS
        defaultDatabaseConnectionShouldNotBeFound("params.in=" + UPDATED_PARAMS);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where params is not null
        defaultDatabaseConnectionShouldBeFound("params.specified=true");

        // Get all the databaseConnectionList where params is null
        defaultDatabaseConnectionShouldNotBeFound("params.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where type equals to DEFAULT_TYPE
        defaultDatabaseConnectionShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the databaseConnectionList where type equals to UPDATED_TYPE
        defaultDatabaseConnectionShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDatabaseConnectionShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the databaseConnectionList where type equals to UPDATED_TYPE
        defaultDatabaseConnectionShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where type is not null
        defaultDatabaseConnectionShouldBeFound("type.specified=true");

        // Get all the databaseConnectionList where type is null
        defaultDatabaseConnectionShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByDriverIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where driver equals to DEFAULT_DRIVER
        defaultDatabaseConnectionShouldBeFound("driver.equals=" + DEFAULT_DRIVER);

        // Get all the databaseConnectionList where driver equals to UPDATED_DRIVER
        defaultDatabaseConnectionShouldNotBeFound("driver.equals=" + UPDATED_DRIVER);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByDriverIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where driver in DEFAULT_DRIVER or UPDATED_DRIVER
        defaultDatabaseConnectionShouldBeFound("driver.in=" + DEFAULT_DRIVER + "," + UPDATED_DRIVER);

        // Get all the databaseConnectionList where driver equals to UPDATED_DRIVER
        defaultDatabaseConnectionShouldNotBeFound("driver.in=" + UPDATED_DRIVER);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByDriverIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where driver is not null
        defaultDatabaseConnectionShouldBeFound("driver.specified=true");

        // Get all the databaseConnectionList where driver is null
        defaultDatabaseConnectionShouldNotBeFound("driver.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where url equals to DEFAULT_URL
        defaultDatabaseConnectionShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the databaseConnectionList where url equals to UPDATED_URL
        defaultDatabaseConnectionShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where url in DEFAULT_URL or UPDATED_URL
        defaultDatabaseConnectionShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the databaseConnectionList where url equals to UPDATED_URL
        defaultDatabaseConnectionShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where url is not null
        defaultDatabaseConnectionShouldBeFound("url.specified=true");

        // Get all the databaseConnectionList where url is null
        defaultDatabaseConnectionShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where username equals to DEFAULT_USERNAME
        defaultDatabaseConnectionShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the databaseConnectionList where username equals to UPDATED_USERNAME
        defaultDatabaseConnectionShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultDatabaseConnectionShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the databaseConnectionList where username equals to UPDATED_USERNAME
        defaultDatabaseConnectionShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where username is not null
        defaultDatabaseConnectionShouldBeFound("username.specified=true");

        // Get all the databaseConnectionList where username is null
        defaultDatabaseConnectionShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where password equals to DEFAULT_PASSWORD
        defaultDatabaseConnectionShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the databaseConnectionList where password equals to UPDATED_PASSWORD
        defaultDatabaseConnectionShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultDatabaseConnectionShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the databaseConnectionList where password equals to UPDATED_PASSWORD
        defaultDatabaseConnectionShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where password is not null
        defaultDatabaseConnectionShouldBeFound("password.specified=true");

        // Get all the databaseConnectionList where password is null
        defaultDatabaseConnectionShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByTestIsEqualToSomething() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where test equals to DEFAULT_TEST
        defaultDatabaseConnectionShouldBeFound("test.equals=" + DEFAULT_TEST);

        // Get all the databaseConnectionList where test equals to UPDATED_TEST
        defaultDatabaseConnectionShouldNotBeFound("test.equals=" + UPDATED_TEST);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByTestIsInShouldWork() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where test in DEFAULT_TEST or UPDATED_TEST
        defaultDatabaseConnectionShouldBeFound("test.in=" + DEFAULT_TEST + "," + UPDATED_TEST);

        // Get all the databaseConnectionList where test equals to UPDATED_TEST
        defaultDatabaseConnectionShouldNotBeFound("test.in=" + UPDATED_TEST);
    }

    @Test
    @Transactional
    public void getAllDatabaseConnectionsByTestIsNullOrNotNull() throws Exception {
        // Initialize the database
        databaseConnectionRepository.saveAndFlush(databaseConnection);

        // Get all the databaseConnectionList where test is not null
        defaultDatabaseConnectionShouldBeFound("test.specified=true");

        // Get all the databaseConnectionList where test is null
        defaultDatabaseConnectionShouldNotBeFound("test.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDatabaseConnectionShouldBeFound(String filter) throws Exception {
        restDatabaseConnectionMockMvc.perform(get("/api/database-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(databaseConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].connectionName").value(hasItem(DEFAULT_CONNECTION_NAME)))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].databaseName").value(hasItem(DEFAULT_DATABASE_NAME)))
            .andExpect(jsonPath("$.[*].params").value(hasItem(DEFAULT_PARAMS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].driver").value(hasItem(DEFAULT_DRIVER)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.booleanValue())));

        // Check, that the count call also returns 1
        restDatabaseConnectionMockMvc.perform(get("/api/database-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDatabaseConnectionShouldNotBeFound(String filter) throws Exception {
        restDatabaseConnectionMockMvc.perform(get("/api/database-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDatabaseConnectionMockMvc.perform(get("/api/database-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDatabaseConnection() throws Exception {
        // Get the databaseConnection
        restDatabaseConnectionMockMvc.perform(get("/api/database-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatabaseConnection() throws Exception {
        // Initialize the database
        databaseConnectionService.save(databaseConnection);

        int databaseSizeBeforeUpdate = databaseConnectionRepository.findAll().size();

        // Update the databaseConnection
        DatabaseConnection updatedDatabaseConnection = databaseConnectionRepository.findById(databaseConnection.getId()).get();
        // Disconnect from session so that the updates on updatedDatabaseConnection are not directly saved in db
        em.detach(updatedDatabaseConnection);
        updatedDatabaseConnection
            .connectionName(UPDATED_CONNECTION_NAME)
            .host(UPDATED_HOST)
            .port(UPDATED_PORT)
            .databaseName(UPDATED_DATABASE_NAME)
            .params(UPDATED_PARAMS)
            .type(UPDATED_TYPE)
            .driver(UPDATED_DRIVER)
            .url(UPDATED_URL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .test(UPDATED_TEST);

        restDatabaseConnectionMockMvc.perform(put("/api/database-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDatabaseConnection)))
            .andExpect(status().isOk());

        // Validate the DatabaseConnection in the database
        List<DatabaseConnection> databaseConnectionList = databaseConnectionRepository.findAll();
        assertThat(databaseConnectionList).hasSize(databaseSizeBeforeUpdate);
        DatabaseConnection testDatabaseConnection = databaseConnectionList.get(databaseConnectionList.size() - 1);
        assertThat(testDatabaseConnection.getConnectionName()).isEqualTo(UPDATED_CONNECTION_NAME);
        assertThat(testDatabaseConnection.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testDatabaseConnection.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testDatabaseConnection.getDatabaseName()).isEqualTo(UPDATED_DATABASE_NAME);
        assertThat(testDatabaseConnection.getParams()).isEqualTo(UPDATED_PARAMS);
        assertThat(testDatabaseConnection.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDatabaseConnection.getDriver()).isEqualTo(UPDATED_DRIVER);
        assertThat(testDatabaseConnection.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDatabaseConnection.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDatabaseConnection.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDatabaseConnection.isTest()).isEqualTo(UPDATED_TEST);
    }

    @Test
    @Transactional
    public void updateNonExistingDatabaseConnection() throws Exception {
        int databaseSizeBeforeUpdate = databaseConnectionRepository.findAll().size();

        // Create the DatabaseConnection

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatabaseConnectionMockMvc.perform(put("/api/database-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(databaseConnection)))
            .andExpect(status().isBadRequest());

        // Validate the DatabaseConnection in the database
        List<DatabaseConnection> databaseConnectionList = databaseConnectionRepository.findAll();
        assertThat(databaseConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatabaseConnection() throws Exception {
        // Initialize the database
        databaseConnectionService.save(databaseConnection);

        int databaseSizeBeforeDelete = databaseConnectionRepository.findAll().size();

        // Delete the databaseConnection
        restDatabaseConnectionMockMvc.perform(delete("/api/database-connections/{id}", databaseConnection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatabaseConnection> databaseConnectionList = databaseConnectionRepository.findAll();
        assertThat(databaseConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatabaseConnection.class);
        DatabaseConnection databaseConnection1 = new DatabaseConnection();
        databaseConnection1.setId(1L);
        DatabaseConnection databaseConnection2 = new DatabaseConnection();
        databaseConnection2.setId(databaseConnection1.getId());
        assertThat(databaseConnection1).isEqualTo(databaseConnection2);
        databaseConnection2.setId(2L);
        assertThat(databaseConnection1).isNotEqualTo(databaseConnection2);
        databaseConnection1.setId(null);
        assertThat(databaseConnection1).isNotEqualTo(databaseConnection2);
    }
}
