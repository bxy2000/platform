package com.boxy.platform.web.rest;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.domain.DataFields;
import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.repository.DataFieldsRepository;
import com.boxy.platform.service.DataFieldsService;
import com.boxy.platform.web.rest.errors.ExceptionTranslator;
import com.boxy.platform.service.dto.DataFieldsCriteria;
import com.boxy.platform.service.DataFieldsQueryService;

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
 * Integration tests for the {@link DataFieldsResource} REST controller.
 */
@SpringBootTest(classes = PlatformApp.class)
public class DataFieldsResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;
    private static final Integer SMALLER_LENGTH = 1 - 1;

    private static final Integer DEFAULT_SCALE = 1;
    private static final Integer UPDATED_SCALE = 2;
    private static final Integer SMALLER_SCALE = 1 - 1;

    private static final Boolean DEFAULT_ALLOW_NULL = false;
    private static final Boolean UPDATED_ALLOW_NULL = true;

    private static final Boolean DEFAULT_PRIMARY_KEY = false;
    private static final Boolean UPDATED_PRIMARY_KEY = true;

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
    private DataFieldsRepository dataFieldsRepository;

    @Autowired
    private DataFieldsService dataFieldsService;

    @Autowired
    private DataFieldsQueryService dataFieldsQueryService;

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

    private MockMvc restDataFieldsMockMvc;

    private DataFields dataFields;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataFieldsResource dataFieldsResource = new DataFieldsResource(dataFieldsService, dataFieldsQueryService);
        this.restDataFieldsMockMvc = MockMvcBuilders.standaloneSetup(dataFieldsResource)
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
    public static DataFields createEntity(EntityManager em) {
        DataFields dataFields = new DataFields()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldType(DEFAULT_FIELD_TYPE)
            .length(DEFAULT_LENGTH)
            .scale(DEFAULT_SCALE)
            .allowNull(DEFAULT_ALLOW_NULL)
            .primaryKey(DEFAULT_PRIMARY_KEY)
            .remarks(DEFAULT_REMARKS)
            .stop(DEFAULT_STOP)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .modifyDate(DEFAULT_MODIFY_DATE);
        return dataFields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataFields createUpdatedEntity(EntityManager em) {
        DataFields dataFields = new DataFields()
            .fieldName(UPDATED_FIELD_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .length(UPDATED_LENGTH)
            .scale(UPDATED_SCALE)
            .allowNull(UPDATED_ALLOW_NULL)
            .primaryKey(UPDATED_PRIMARY_KEY)
            .remarks(UPDATED_REMARKS)
            .stop(UPDATED_STOP)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .modifyDate(UPDATED_MODIFY_DATE);
        return dataFields;
    }

    @BeforeEach
    public void initTest() {
        dataFields = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataFields() throws Exception {
        int databaseSizeBeforeCreate = dataFieldsRepository.findAll().size();

        // Create the DataFields
        restDataFieldsMockMvc.perform(post("/api/data-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataFields)))
            .andExpect(status().isCreated());

        // Validate the DataFields in the database
        List<DataFields> dataFieldsList = dataFieldsRepository.findAll();
        assertThat(dataFieldsList).hasSize(databaseSizeBeforeCreate + 1);
        DataFields testDataFields = dataFieldsList.get(dataFieldsList.size() - 1);
        assertThat(testDataFields.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testDataFields.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testDataFields.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testDataFields.getScale()).isEqualTo(DEFAULT_SCALE);
        assertThat(testDataFields.isAllowNull()).isEqualTo(DEFAULT_ALLOW_NULL);
        assertThat(testDataFields.isPrimaryKey()).isEqualTo(DEFAULT_PRIMARY_KEY);
        assertThat(testDataFields.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testDataFields.isStop()).isEqualTo(DEFAULT_STOP);
        assertThat(testDataFields.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDataFields.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDataFields.getModifyDate()).isEqualTo(DEFAULT_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void createDataFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataFieldsRepository.findAll().size();

        // Create the DataFields with an existing ID
        dataFields.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataFieldsMockMvc.perform(post("/api/data-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataFields)))
            .andExpect(status().isBadRequest());

        // Validate the DataFields in the database
        List<DataFields> dataFieldsList = dataFieldsRepository.findAll();
        assertThat(dataFieldsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataFields() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList
        restDataFieldsMockMvc.perform(get("/api/data-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME.toString())))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE)))
            .andExpect(jsonPath("$.[*].allowNull").value(hasItem(DEFAULT_ALLOW_NULL.booleanValue())))
            .andExpect(jsonPath("$.[*].primaryKey").value(hasItem(DEFAULT_PRIMARY_KEY.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].stop").value(hasItem(DEFAULT_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifyDate").value(hasItem(DEFAULT_MODIFY_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDataFields() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get the dataFields
        restDataFieldsMockMvc.perform(get("/api/data-fields/{id}", dataFields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataFields.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME.toString()))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE.toString()))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.scale").value(DEFAULT_SCALE))
            .andExpect(jsonPath("$.allowNull").value(DEFAULT_ALLOW_NULL.booleanValue()))
            .andExpect(jsonPath("$.primaryKey").value(DEFAULT_PRIMARY_KEY.booleanValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.stop").value(DEFAULT_STOP.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.modifyDate").value(DEFAULT_MODIFY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllDataFieldsByFieldNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where fieldName equals to DEFAULT_FIELD_NAME
        defaultDataFieldsShouldBeFound("fieldName.equals=" + DEFAULT_FIELD_NAME);

        // Get all the dataFieldsList where fieldName equals to UPDATED_FIELD_NAME
        defaultDataFieldsShouldNotBeFound("fieldName.equals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByFieldNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where fieldName in DEFAULT_FIELD_NAME or UPDATED_FIELD_NAME
        defaultDataFieldsShouldBeFound("fieldName.in=" + DEFAULT_FIELD_NAME + "," + UPDATED_FIELD_NAME);

        // Get all the dataFieldsList where fieldName equals to UPDATED_FIELD_NAME
        defaultDataFieldsShouldNotBeFound("fieldName.in=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByFieldNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where fieldName is not null
        defaultDataFieldsShouldBeFound("fieldName.specified=true");

        // Get all the dataFieldsList where fieldName is null
        defaultDataFieldsShouldNotBeFound("fieldName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByFieldTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where fieldType equals to DEFAULT_FIELD_TYPE
        defaultDataFieldsShouldBeFound("fieldType.equals=" + DEFAULT_FIELD_TYPE);

        // Get all the dataFieldsList where fieldType equals to UPDATED_FIELD_TYPE
        defaultDataFieldsShouldNotBeFound("fieldType.equals=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByFieldTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where fieldType in DEFAULT_FIELD_TYPE or UPDATED_FIELD_TYPE
        defaultDataFieldsShouldBeFound("fieldType.in=" + DEFAULT_FIELD_TYPE + "," + UPDATED_FIELD_TYPE);

        // Get all the dataFieldsList where fieldType equals to UPDATED_FIELD_TYPE
        defaultDataFieldsShouldNotBeFound("fieldType.in=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByFieldTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where fieldType is not null
        defaultDataFieldsShouldBeFound("fieldType.specified=true");

        // Get all the dataFieldsList where fieldType is null
        defaultDataFieldsShouldNotBeFound("fieldType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where length equals to DEFAULT_LENGTH
        defaultDataFieldsShouldBeFound("length.equals=" + DEFAULT_LENGTH);

        // Get all the dataFieldsList where length equals to UPDATED_LENGTH
        defaultDataFieldsShouldNotBeFound("length.equals=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByLengthIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where length in DEFAULT_LENGTH or UPDATED_LENGTH
        defaultDataFieldsShouldBeFound("length.in=" + DEFAULT_LENGTH + "," + UPDATED_LENGTH);

        // Get all the dataFieldsList where length equals to UPDATED_LENGTH
        defaultDataFieldsShouldNotBeFound("length.in=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where length is not null
        defaultDataFieldsShouldBeFound("length.specified=true");

        // Get all the dataFieldsList where length is null
        defaultDataFieldsShouldNotBeFound("length.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where length is greater than or equal to DEFAULT_LENGTH
        defaultDataFieldsShouldBeFound("length.greaterThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the dataFieldsList where length is greater than or equal to UPDATED_LENGTH
        defaultDataFieldsShouldNotBeFound("length.greaterThanOrEqual=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where length is less than or equal to DEFAULT_LENGTH
        defaultDataFieldsShouldBeFound("length.lessThanOrEqual=" + DEFAULT_LENGTH);

        // Get all the dataFieldsList where length is less than or equal to SMALLER_LENGTH
        defaultDataFieldsShouldNotBeFound("length.lessThanOrEqual=" + SMALLER_LENGTH);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where length is less than DEFAULT_LENGTH
        defaultDataFieldsShouldNotBeFound("length.lessThan=" + DEFAULT_LENGTH);

        // Get all the dataFieldsList where length is less than UPDATED_LENGTH
        defaultDataFieldsShouldBeFound("length.lessThan=" + UPDATED_LENGTH);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where length is greater than DEFAULT_LENGTH
        defaultDataFieldsShouldNotBeFound("length.greaterThan=" + DEFAULT_LENGTH);

        // Get all the dataFieldsList where length is greater than SMALLER_LENGTH
        defaultDataFieldsShouldBeFound("length.greaterThan=" + SMALLER_LENGTH);
    }


    @Test
    @Transactional
    public void getAllDataFieldsByScaleIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where scale equals to DEFAULT_SCALE
        defaultDataFieldsShouldBeFound("scale.equals=" + DEFAULT_SCALE);

        // Get all the dataFieldsList where scale equals to UPDATED_SCALE
        defaultDataFieldsShouldNotBeFound("scale.equals=" + UPDATED_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByScaleIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where scale in DEFAULT_SCALE or UPDATED_SCALE
        defaultDataFieldsShouldBeFound("scale.in=" + DEFAULT_SCALE + "," + UPDATED_SCALE);

        // Get all the dataFieldsList where scale equals to UPDATED_SCALE
        defaultDataFieldsShouldNotBeFound("scale.in=" + UPDATED_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByScaleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where scale is not null
        defaultDataFieldsShouldBeFound("scale.specified=true");

        // Get all the dataFieldsList where scale is null
        defaultDataFieldsShouldNotBeFound("scale.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByScaleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where scale is greater than or equal to DEFAULT_SCALE
        defaultDataFieldsShouldBeFound("scale.greaterThanOrEqual=" + DEFAULT_SCALE);

        // Get all the dataFieldsList where scale is greater than or equal to UPDATED_SCALE
        defaultDataFieldsShouldNotBeFound("scale.greaterThanOrEqual=" + UPDATED_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByScaleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where scale is less than or equal to DEFAULT_SCALE
        defaultDataFieldsShouldBeFound("scale.lessThanOrEqual=" + DEFAULT_SCALE);

        // Get all the dataFieldsList where scale is less than or equal to SMALLER_SCALE
        defaultDataFieldsShouldNotBeFound("scale.lessThanOrEqual=" + SMALLER_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByScaleIsLessThanSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where scale is less than DEFAULT_SCALE
        defaultDataFieldsShouldNotBeFound("scale.lessThan=" + DEFAULT_SCALE);

        // Get all the dataFieldsList where scale is less than UPDATED_SCALE
        defaultDataFieldsShouldBeFound("scale.lessThan=" + UPDATED_SCALE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByScaleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where scale is greater than DEFAULT_SCALE
        defaultDataFieldsShouldNotBeFound("scale.greaterThan=" + DEFAULT_SCALE);

        // Get all the dataFieldsList where scale is greater than SMALLER_SCALE
        defaultDataFieldsShouldBeFound("scale.greaterThan=" + SMALLER_SCALE);
    }


    @Test
    @Transactional
    public void getAllDataFieldsByAllowNullIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where allowNull equals to DEFAULT_ALLOW_NULL
        defaultDataFieldsShouldBeFound("allowNull.equals=" + DEFAULT_ALLOW_NULL);

        // Get all the dataFieldsList where allowNull equals to UPDATED_ALLOW_NULL
        defaultDataFieldsShouldNotBeFound("allowNull.equals=" + UPDATED_ALLOW_NULL);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByAllowNullIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where allowNull in DEFAULT_ALLOW_NULL or UPDATED_ALLOW_NULL
        defaultDataFieldsShouldBeFound("allowNull.in=" + DEFAULT_ALLOW_NULL + "," + UPDATED_ALLOW_NULL);

        // Get all the dataFieldsList where allowNull equals to UPDATED_ALLOW_NULL
        defaultDataFieldsShouldNotBeFound("allowNull.in=" + UPDATED_ALLOW_NULL);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByAllowNullIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where allowNull is not null
        defaultDataFieldsShouldBeFound("allowNull.specified=true");

        // Get all the dataFieldsList where allowNull is null
        defaultDataFieldsShouldNotBeFound("allowNull.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByPrimaryKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where primaryKey equals to DEFAULT_PRIMARY_KEY
        defaultDataFieldsShouldBeFound("primaryKey.equals=" + DEFAULT_PRIMARY_KEY);

        // Get all the dataFieldsList where primaryKey equals to UPDATED_PRIMARY_KEY
        defaultDataFieldsShouldNotBeFound("primaryKey.equals=" + UPDATED_PRIMARY_KEY);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByPrimaryKeyIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where primaryKey in DEFAULT_PRIMARY_KEY or UPDATED_PRIMARY_KEY
        defaultDataFieldsShouldBeFound("primaryKey.in=" + DEFAULT_PRIMARY_KEY + "," + UPDATED_PRIMARY_KEY);

        // Get all the dataFieldsList where primaryKey equals to UPDATED_PRIMARY_KEY
        defaultDataFieldsShouldNotBeFound("primaryKey.in=" + UPDATED_PRIMARY_KEY);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByPrimaryKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where primaryKey is not null
        defaultDataFieldsShouldBeFound("primaryKey.specified=true");

        // Get all the dataFieldsList where primaryKey is null
        defaultDataFieldsShouldNotBeFound("primaryKey.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where remarks equals to DEFAULT_REMARKS
        defaultDataFieldsShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the dataFieldsList where remarks equals to UPDATED_REMARKS
        defaultDataFieldsShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultDataFieldsShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the dataFieldsList where remarks equals to UPDATED_REMARKS
        defaultDataFieldsShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where remarks is not null
        defaultDataFieldsShouldBeFound("remarks.specified=true");

        // Get all the dataFieldsList where remarks is null
        defaultDataFieldsShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByStopIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where stop equals to DEFAULT_STOP
        defaultDataFieldsShouldBeFound("stop.equals=" + DEFAULT_STOP);

        // Get all the dataFieldsList where stop equals to UPDATED_STOP
        defaultDataFieldsShouldNotBeFound("stop.equals=" + UPDATED_STOP);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByStopIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where stop in DEFAULT_STOP or UPDATED_STOP
        defaultDataFieldsShouldBeFound("stop.in=" + DEFAULT_STOP + "," + UPDATED_STOP);

        // Get all the dataFieldsList where stop equals to UPDATED_STOP
        defaultDataFieldsShouldNotBeFound("stop.in=" + UPDATED_STOP);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByStopIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where stop is not null
        defaultDataFieldsShouldBeFound("stop.specified=true");

        // Get all the dataFieldsList where stop is null
        defaultDataFieldsShouldNotBeFound("stop.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where createDate equals to DEFAULT_CREATE_DATE
        defaultDataFieldsShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the dataFieldsList where createDate equals to UPDATED_CREATE_DATE
        defaultDataFieldsShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultDataFieldsShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the dataFieldsList where createDate equals to UPDATED_CREATE_DATE
        defaultDataFieldsShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where createDate is not null
        defaultDataFieldsShouldBeFound("createDate.specified=true");

        // Get all the dataFieldsList where createDate is null
        defaultDataFieldsShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultDataFieldsShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the dataFieldsList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDataFieldsShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultDataFieldsShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the dataFieldsList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDataFieldsShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where updateDate is not null
        defaultDataFieldsShouldBeFound("updateDate.specified=true");

        // Get all the dataFieldsList where updateDate is null
        defaultDataFieldsShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByModifyDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where modifyDate equals to DEFAULT_MODIFY_DATE
        defaultDataFieldsShouldBeFound("modifyDate.equals=" + DEFAULT_MODIFY_DATE);

        // Get all the dataFieldsList where modifyDate equals to UPDATED_MODIFY_DATE
        defaultDataFieldsShouldNotBeFound("modifyDate.equals=" + UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByModifyDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where modifyDate in DEFAULT_MODIFY_DATE or UPDATED_MODIFY_DATE
        defaultDataFieldsShouldBeFound("modifyDate.in=" + DEFAULT_MODIFY_DATE + "," + UPDATED_MODIFY_DATE);

        // Get all the dataFieldsList where modifyDate equals to UPDATED_MODIFY_DATE
        defaultDataFieldsShouldNotBeFound("modifyDate.in=" + UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void getAllDataFieldsByModifyDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);

        // Get all the dataFieldsList where modifyDate is not null
        defaultDataFieldsShouldBeFound("modifyDate.specified=true");

        // Get all the dataFieldsList where modifyDate is null
        defaultDataFieldsShouldNotBeFound("modifyDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataFieldsByDataCatalogIsEqualToSomething() throws Exception {
        // Initialize the database
        dataFieldsRepository.saveAndFlush(dataFields);
        DataCatalog dataCatalog = DataCatalogResourceIT.createEntity(em);
        em.persist(dataCatalog);
        em.flush();
        dataFields.setDataCatalog(dataCatalog);
        dataFieldsRepository.saveAndFlush(dataFields);
        Long dataCatalogId = dataCatalog.getId();

        // Get all the dataFieldsList where dataCatalog equals to dataCatalogId
        defaultDataFieldsShouldBeFound("dataCatalogId.equals=" + dataCatalogId);

        // Get all the dataFieldsList where dataCatalog equals to dataCatalogId + 1
        defaultDataFieldsShouldNotBeFound("dataCatalogId.equals=" + (dataCatalogId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataFieldsShouldBeFound(String filter) throws Exception {
        restDataFieldsMockMvc.perform(get("/api/data-fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataFields.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE)))
            .andExpect(jsonPath("$.[*].allowNull").value(hasItem(DEFAULT_ALLOW_NULL.booleanValue())))
            .andExpect(jsonPath("$.[*].primaryKey").value(hasItem(DEFAULT_PRIMARY_KEY.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].stop").value(hasItem(DEFAULT_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifyDate").value(hasItem(DEFAULT_MODIFY_DATE.toString())));

        // Check, that the count call also returns 1
        restDataFieldsMockMvc.perform(get("/api/data-fields/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataFieldsShouldNotBeFound(String filter) throws Exception {
        restDataFieldsMockMvc.perform(get("/api/data-fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataFieldsMockMvc.perform(get("/api/data-fields/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataFields() throws Exception {
        // Get the dataFields
        restDataFieldsMockMvc.perform(get("/api/data-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataFields() throws Exception {
        // Initialize the database
        dataFieldsService.save(dataFields);

        int databaseSizeBeforeUpdate = dataFieldsRepository.findAll().size();

        // Update the dataFields
        DataFields updatedDataFields = dataFieldsRepository.findById(dataFields.getId()).get();
        // Disconnect from session so that the updates on updatedDataFields are not directly saved in db
        em.detach(updatedDataFields);
        updatedDataFields
            .fieldName(UPDATED_FIELD_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .length(UPDATED_LENGTH)
            .scale(UPDATED_SCALE)
            .allowNull(UPDATED_ALLOW_NULL)
            .primaryKey(UPDATED_PRIMARY_KEY)
            .remarks(UPDATED_REMARKS)
            .stop(UPDATED_STOP)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .modifyDate(UPDATED_MODIFY_DATE);

        restDataFieldsMockMvc.perform(put("/api/data-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataFields)))
            .andExpect(status().isOk());

        // Validate the DataFields in the database
        List<DataFields> dataFieldsList = dataFieldsRepository.findAll();
        assertThat(dataFieldsList).hasSize(databaseSizeBeforeUpdate);
        DataFields testDataFields = dataFieldsList.get(dataFieldsList.size() - 1);
        assertThat(testDataFields.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testDataFields.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testDataFields.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testDataFields.getScale()).isEqualTo(UPDATED_SCALE);
        assertThat(testDataFields.isAllowNull()).isEqualTo(UPDATED_ALLOW_NULL);
        assertThat(testDataFields.isPrimaryKey()).isEqualTo(UPDATED_PRIMARY_KEY);
        assertThat(testDataFields.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testDataFields.isStop()).isEqualTo(UPDATED_STOP);
        assertThat(testDataFields.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDataFields.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDataFields.getModifyDate()).isEqualTo(UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataFields() throws Exception {
        int databaseSizeBeforeUpdate = dataFieldsRepository.findAll().size();

        // Create the DataFields

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataFieldsMockMvc.perform(put("/api/data-fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataFields)))
            .andExpect(status().isBadRequest());

        // Validate the DataFields in the database
        List<DataFields> dataFieldsList = dataFieldsRepository.findAll();
        assertThat(dataFieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataFields() throws Exception {
        // Initialize the database
        dataFieldsService.save(dataFields);

        int databaseSizeBeforeDelete = dataFieldsRepository.findAll().size();

        // Delete the dataFields
        restDataFieldsMockMvc.perform(delete("/api/data-fields/{id}", dataFields.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataFields> dataFieldsList = dataFieldsRepository.findAll();
        assertThat(dataFieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataFields.class);
        DataFields dataFields1 = new DataFields();
        dataFields1.setId(1L);
        DataFields dataFields2 = new DataFields();
        dataFields2.setId(dataFields1.getId());
        assertThat(dataFields1).isEqualTo(dataFields2);
        dataFields2.setId(2L);
        assertThat(dataFields1).isNotEqualTo(dataFields2);
        dataFields1.setId(null);
        assertThat(dataFields1).isNotEqualTo(dataFields2);
    }
}
