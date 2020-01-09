package com.boxy.platform.web.rest;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.domain.DataForeignKey;
import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.repository.DataForeignKeyRepository;
import com.boxy.platform.service.DataForeignKeyService;
import com.boxy.platform.web.rest.errors.ExceptionTranslator;
import com.boxy.platform.service.dto.DataForeignKeyCriteria;
import com.boxy.platform.service.DataForeignKeyQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.boxy.platform.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DataForeignKeyResource} REST controller.
 */
@SpringBootTest(classes = PlatformApp.class)
public class DataForeignKeyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STOP = false;
    private static final Boolean UPDATED_STOP = true;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATE_DATE = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_UPDATE_DATE = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_MODIFY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_MODIFY_DATE = Instant.ofEpochMilli(-1L);

    @Autowired
    private DataForeignKeyRepository dataForeignKeyRepository;

    @Autowired
    private DataForeignKeyService dataForeignKeyService;

    @Autowired
    private DataForeignKeyQueryService dataForeignKeyQueryService;

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

    private MockMvc restDataForeignKeyMockMvc;

    private DataForeignKey dataForeignKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataForeignKeyResource dataForeignKeyResource = new DataForeignKeyResource(dataForeignKeyService, dataForeignKeyQueryService);
        this.restDataForeignKeyMockMvc = MockMvcBuilders.standaloneSetup(dataForeignKeyResource)
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
    public static DataForeignKey createEntity(EntityManager em) {
        DataForeignKey dataForeignKey = new DataForeignKey()
            .name(DEFAULT_NAME)
            .field(DEFAULT_FIELD)
            .referenceTable(DEFAULT_REFERENCE_TABLE)
            .referenceField(DEFAULT_REFERENCE_FIELD)
            .remarks(DEFAULT_REMARKS)
            .stop(DEFAULT_STOP)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .modifyDate(DEFAULT_MODIFY_DATE);
        return dataForeignKey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataForeignKey createUpdatedEntity(EntityManager em) {
        DataForeignKey dataForeignKey = new DataForeignKey()
            .name(UPDATED_NAME)
            .field(UPDATED_FIELD)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .referenceField(UPDATED_REFERENCE_FIELD)
            .remarks(UPDATED_REMARKS)
            .stop(UPDATED_STOP)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .modifyDate(UPDATED_MODIFY_DATE);
        return dataForeignKey;
    }

    @BeforeEach
    public void initTest() {
        dataForeignKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataForeignKey() throws Exception {
        int databaseSizeBeforeCreate = dataForeignKeyRepository.findAll().size();

        // Create the DataForeignKey
        restDataForeignKeyMockMvc.perform(post("/api/data-foreign-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataForeignKey)))
            .andExpect(status().isCreated());

        // Validate the DataForeignKey in the database
        List<DataForeignKey> dataForeignKeyList = dataForeignKeyRepository.findAll();
        assertThat(dataForeignKeyList).hasSize(databaseSizeBeforeCreate + 1);
        DataForeignKey testDataForeignKey = dataForeignKeyList.get(dataForeignKeyList.size() - 1);
        assertThat(testDataForeignKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataForeignKey.getField()).isEqualTo(DEFAULT_FIELD);
        assertThat(testDataForeignKey.getReferenceTable()).isEqualTo(DEFAULT_REFERENCE_TABLE);
        assertThat(testDataForeignKey.getReferenceField()).isEqualTo(DEFAULT_REFERENCE_FIELD);
        assertThat(testDataForeignKey.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testDataForeignKey.isStop()).isEqualTo(DEFAULT_STOP);
        assertThat(testDataForeignKey.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDataForeignKey.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDataForeignKey.getModifyDate()).isEqualTo(DEFAULT_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void createDataForeignKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataForeignKeyRepository.findAll().size();

        // Create the DataForeignKey with an existing ID
        dataForeignKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataForeignKeyMockMvc.perform(post("/api/data-foreign-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataForeignKey)))
            .andExpect(status().isBadRequest());

        // Validate the DataForeignKey in the database
        List<DataForeignKey> dataForeignKeyList = dataForeignKeyRepository.findAll();
        assertThat(dataForeignKeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataForeignKeys() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList
        restDataForeignKeyMockMvc.perform(get("/api/data-foreign-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataForeignKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].field").value(hasItem(DEFAULT_FIELD.toString())))
            .andExpect(jsonPath("$.[*].referenceTable").value(hasItem(DEFAULT_REFERENCE_TABLE.toString())))
            .andExpect(jsonPath("$.[*].referenceField").value(hasItem(DEFAULT_REFERENCE_FIELD.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].stop").value(hasItem(DEFAULT_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifyDate").value(hasItem(DEFAULT_MODIFY_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDataForeignKey() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get the dataForeignKey
        restDataForeignKeyMockMvc.perform(get("/api/data-foreign-keys/{id}", dataForeignKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataForeignKey.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.field").value(DEFAULT_FIELD.toString()))
            .andExpect(jsonPath("$.referenceTable").value(DEFAULT_REFERENCE_TABLE.toString()))
            .andExpect(jsonPath("$.referenceField").value(DEFAULT_REFERENCE_FIELD.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.stop").value(DEFAULT_STOP.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.modifyDate").value(DEFAULT_MODIFY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where name equals to DEFAULT_NAME
        defaultDataForeignKeyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataForeignKeyList where name equals to UPDATED_NAME
        defaultDataForeignKeyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataForeignKeyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataForeignKeyList where name equals to UPDATED_NAME
        defaultDataForeignKeyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where name is not null
        defaultDataForeignKeyShouldBeFound("name.specified=true");

        // Get all the dataForeignKeyList where name is null
        defaultDataForeignKeyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where field equals to DEFAULT_FIELD
        defaultDataForeignKeyShouldBeFound("field.equals=" + DEFAULT_FIELD);

        // Get all the dataForeignKeyList where field equals to UPDATED_FIELD
        defaultDataForeignKeyShouldNotBeFound("field.equals=" + UPDATED_FIELD);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByFieldIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where field in DEFAULT_FIELD or UPDATED_FIELD
        defaultDataForeignKeyShouldBeFound("field.in=" + DEFAULT_FIELD + "," + UPDATED_FIELD);

        // Get all the dataForeignKeyList where field equals to UPDATED_FIELD
        defaultDataForeignKeyShouldNotBeFound("field.in=" + UPDATED_FIELD);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByFieldIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where field is not null
        defaultDataForeignKeyShouldBeFound("field.specified=true");

        // Get all the dataForeignKeyList where field is null
        defaultDataForeignKeyShouldNotBeFound("field.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByReferenceTableIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where referenceTable equals to DEFAULT_REFERENCE_TABLE
        defaultDataForeignKeyShouldBeFound("referenceTable.equals=" + DEFAULT_REFERENCE_TABLE);

        // Get all the dataForeignKeyList where referenceTable equals to UPDATED_REFERENCE_TABLE
        defaultDataForeignKeyShouldNotBeFound("referenceTable.equals=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByReferenceTableIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where referenceTable in DEFAULT_REFERENCE_TABLE or UPDATED_REFERENCE_TABLE
        defaultDataForeignKeyShouldBeFound("referenceTable.in=" + DEFAULT_REFERENCE_TABLE + "," + UPDATED_REFERENCE_TABLE);

        // Get all the dataForeignKeyList where referenceTable equals to UPDATED_REFERENCE_TABLE
        defaultDataForeignKeyShouldNotBeFound("referenceTable.in=" + UPDATED_REFERENCE_TABLE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByReferenceTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where referenceTable is not null
        defaultDataForeignKeyShouldBeFound("referenceTable.specified=true");

        // Get all the dataForeignKeyList where referenceTable is null
        defaultDataForeignKeyShouldNotBeFound("referenceTable.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByReferenceFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where referenceField equals to DEFAULT_REFERENCE_FIELD
        defaultDataForeignKeyShouldBeFound("referenceField.equals=" + DEFAULT_REFERENCE_FIELD);

        // Get all the dataForeignKeyList where referenceField equals to UPDATED_REFERENCE_FIELD
        defaultDataForeignKeyShouldNotBeFound("referenceField.equals=" + UPDATED_REFERENCE_FIELD);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByReferenceFieldIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where referenceField in DEFAULT_REFERENCE_FIELD or UPDATED_REFERENCE_FIELD
        defaultDataForeignKeyShouldBeFound("referenceField.in=" + DEFAULT_REFERENCE_FIELD + "," + UPDATED_REFERENCE_FIELD);

        // Get all the dataForeignKeyList where referenceField equals to UPDATED_REFERENCE_FIELD
        defaultDataForeignKeyShouldNotBeFound("referenceField.in=" + UPDATED_REFERENCE_FIELD);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByReferenceFieldIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where referenceField is not null
        defaultDataForeignKeyShouldBeFound("referenceField.specified=true");

        // Get all the dataForeignKeyList where referenceField is null
        defaultDataForeignKeyShouldNotBeFound("referenceField.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where remarks equals to DEFAULT_REMARKS
        defaultDataForeignKeyShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the dataForeignKeyList where remarks equals to UPDATED_REMARKS
        defaultDataForeignKeyShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultDataForeignKeyShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the dataForeignKeyList where remarks equals to UPDATED_REMARKS
        defaultDataForeignKeyShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where remarks is not null
        defaultDataForeignKeyShouldBeFound("remarks.specified=true");

        // Get all the dataForeignKeyList where remarks is null
        defaultDataForeignKeyShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByStopIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where stop equals to DEFAULT_STOP
        defaultDataForeignKeyShouldBeFound("stop.equals=" + DEFAULT_STOP);

        // Get all the dataForeignKeyList where stop equals to UPDATED_STOP
        defaultDataForeignKeyShouldNotBeFound("stop.equals=" + UPDATED_STOP);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByStopIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where stop in DEFAULT_STOP or UPDATED_STOP
        defaultDataForeignKeyShouldBeFound("stop.in=" + DEFAULT_STOP + "," + UPDATED_STOP);

        // Get all the dataForeignKeyList where stop equals to UPDATED_STOP
        defaultDataForeignKeyShouldNotBeFound("stop.in=" + UPDATED_STOP);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByStopIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where stop is not null
        defaultDataForeignKeyShouldBeFound("stop.specified=true");

        // Get all the dataForeignKeyList where stop is null
        defaultDataForeignKeyShouldNotBeFound("stop.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where createDate equals to DEFAULT_CREATE_DATE
        defaultDataForeignKeyShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the dataForeignKeyList where createDate equals to UPDATED_CREATE_DATE
        defaultDataForeignKeyShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultDataForeignKeyShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the dataForeignKeyList where createDate equals to UPDATED_CREATE_DATE
        defaultDataForeignKeyShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where createDate is not null
        defaultDataForeignKeyShouldBeFound("createDate.specified=true");

        // Get all the dataForeignKeyList where createDate is null
        defaultDataForeignKeyShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultDataForeignKeyShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the dataForeignKeyList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDataForeignKeyShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultDataForeignKeyShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the dataForeignKeyList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDataForeignKeyShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where updateDate is not null
        defaultDataForeignKeyShouldBeFound("updateDate.specified=true");

        // Get all the dataForeignKeyList where updateDate is null
        defaultDataForeignKeyShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByModifyDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where modifyDate equals to DEFAULT_MODIFY_DATE
        defaultDataForeignKeyShouldBeFound("modifyDate.equals=" + DEFAULT_MODIFY_DATE);

        // Get all the dataForeignKeyList where modifyDate equals to UPDATED_MODIFY_DATE
        defaultDataForeignKeyShouldNotBeFound("modifyDate.equals=" + UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByModifyDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where modifyDate in DEFAULT_MODIFY_DATE or UPDATED_MODIFY_DATE
        defaultDataForeignKeyShouldBeFound("modifyDate.in=" + DEFAULT_MODIFY_DATE + "," + UPDATED_MODIFY_DATE);

        // Get all the dataForeignKeyList where modifyDate equals to UPDATED_MODIFY_DATE
        defaultDataForeignKeyShouldNotBeFound("modifyDate.in=" + UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByModifyDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);

        // Get all the dataForeignKeyList where modifyDate is not null
        defaultDataForeignKeyShouldBeFound("modifyDate.specified=true");

        // Get all the dataForeignKeyList where modifyDate is null
        defaultDataForeignKeyShouldNotBeFound("modifyDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataForeignKeysByDataCatalogIsEqualToSomething() throws Exception {
        // Initialize the database
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);
        DataCatalog dataCatalog = DataCatalogResourceIT.createEntity(em);
        em.persist(dataCatalog);
        em.flush();
        dataForeignKey.setDataCatalog(dataCatalog);
        dataForeignKeyRepository.saveAndFlush(dataForeignKey);
        Long dataCatalogId = dataCatalog.getId();

        // Get all the dataForeignKeyList where dataCatalog equals to dataCatalogId
        defaultDataForeignKeyShouldBeFound("dataCatalogId.equals=" + dataCatalogId);

        // Get all the dataForeignKeyList where dataCatalog equals to dataCatalogId + 1
        defaultDataForeignKeyShouldNotBeFound("dataCatalogId.equals=" + (dataCatalogId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataForeignKeyShouldBeFound(String filter) throws Exception {
        restDataForeignKeyMockMvc.perform(get("/api/data-foreign-keys?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataForeignKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].field").value(hasItem(DEFAULT_FIELD)))
            .andExpect(jsonPath("$.[*].referenceTable").value(hasItem(DEFAULT_REFERENCE_TABLE)))
            .andExpect(jsonPath("$.[*].referenceField").value(hasItem(DEFAULT_REFERENCE_FIELD)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].stop").value(hasItem(DEFAULT_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifyDate").value(hasItem(DEFAULT_MODIFY_DATE.toString())));

        // Check, that the count call also returns 1
        restDataForeignKeyMockMvc.perform(get("/api/data-foreign-keys/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataForeignKeyShouldNotBeFound(String filter) throws Exception {
        restDataForeignKeyMockMvc.perform(get("/api/data-foreign-keys?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataForeignKeyMockMvc.perform(get("/api/data-foreign-keys/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataForeignKey() throws Exception {
        // Get the dataForeignKey
        restDataForeignKeyMockMvc.perform(get("/api/data-foreign-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataForeignKey() throws Exception {
        // Initialize the database
        dataForeignKeyService.save(dataForeignKey);

        int databaseSizeBeforeUpdate = dataForeignKeyRepository.findAll().size();

        // Update the dataForeignKey
        DataForeignKey updatedDataForeignKey = dataForeignKeyRepository.findById(dataForeignKey.getId()).get();
        // Disconnect from session so that the updates on updatedDataForeignKey are not directly saved in db
        em.detach(updatedDataForeignKey);
        updatedDataForeignKey
            .name(UPDATED_NAME)
            .field(UPDATED_FIELD)
            .referenceTable(UPDATED_REFERENCE_TABLE)
            .referenceField(UPDATED_REFERENCE_FIELD)
            .remarks(UPDATED_REMARKS)
            .stop(UPDATED_STOP)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .modifyDate(UPDATED_MODIFY_DATE);

        restDataForeignKeyMockMvc.perform(put("/api/data-foreign-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataForeignKey)))
            .andExpect(status().isOk());

        // Validate the DataForeignKey in the database
        List<DataForeignKey> dataForeignKeyList = dataForeignKeyRepository.findAll();
        assertThat(dataForeignKeyList).hasSize(databaseSizeBeforeUpdate);
        DataForeignKey testDataForeignKey = dataForeignKeyList.get(dataForeignKeyList.size() - 1);
        assertThat(testDataForeignKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataForeignKey.getField()).isEqualTo(UPDATED_FIELD);
        assertThat(testDataForeignKey.getReferenceTable()).isEqualTo(UPDATED_REFERENCE_TABLE);
        assertThat(testDataForeignKey.getReferenceField()).isEqualTo(UPDATED_REFERENCE_FIELD);
        assertThat(testDataForeignKey.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testDataForeignKey.isStop()).isEqualTo(UPDATED_STOP);
        assertThat(testDataForeignKey.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDataForeignKey.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDataForeignKey.getModifyDate()).isEqualTo(UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataForeignKey() throws Exception {
        int databaseSizeBeforeUpdate = dataForeignKeyRepository.findAll().size();

        // Create the DataForeignKey

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataForeignKeyMockMvc.perform(put("/api/data-foreign-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataForeignKey)))
            .andExpect(status().isBadRequest());

        // Validate the DataForeignKey in the database
        List<DataForeignKey> dataForeignKeyList = dataForeignKeyRepository.findAll();
        assertThat(dataForeignKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataForeignKey() throws Exception {
        // Initialize the database
        dataForeignKeyService.save(dataForeignKey);

        int databaseSizeBeforeDelete = dataForeignKeyRepository.findAll().size();

        // Delete the dataForeignKey
        restDataForeignKeyMockMvc.perform(delete("/api/data-foreign-keys/{id}", dataForeignKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataForeignKey> dataForeignKeyList = dataForeignKeyRepository.findAll();
        assertThat(dataForeignKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataForeignKey.class);
        DataForeignKey dataForeignKey1 = new DataForeignKey();
        dataForeignKey1.setId(1L);
        DataForeignKey dataForeignKey2 = new DataForeignKey();
        dataForeignKey2.setId(dataForeignKey1.getId());
        assertThat(dataForeignKey1).isEqualTo(dataForeignKey2);
        dataForeignKey2.setId(2L);
        assertThat(dataForeignKey1).isNotEqualTo(dataForeignKey2);
        dataForeignKey1.setId(null);
        assertThat(dataForeignKey1).isNotEqualTo(dataForeignKey2);
    }
}
