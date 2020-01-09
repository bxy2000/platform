package com.boxy.platform.web.rest;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.domain.DataPrimaryKey;
import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.repository.DataPrimaryKeyRepository;
import com.boxy.platform.service.DataPrimaryKeyService;
import com.boxy.platform.web.rest.errors.ExceptionTranslator;
import com.boxy.platform.service.dto.DataPrimaryKeyCriteria;
import com.boxy.platform.service.DataPrimaryKeyQueryService;

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
 * Integration tests for the {@link DataPrimaryKeyResource} REST controller.
 */
@SpringBootTest(classes = PlatformApp.class)
public class DataPrimaryKeyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_FIELDS = "BBBBBBBBBB";

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
    private DataPrimaryKeyRepository dataPrimaryKeyRepository;

    @Autowired
    private DataPrimaryKeyService dataPrimaryKeyService;

    @Autowired
    private DataPrimaryKeyQueryService dataPrimaryKeyQueryService;

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

    private MockMvc restDataPrimaryKeyMockMvc;

    private DataPrimaryKey dataPrimaryKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataPrimaryKeyResource dataPrimaryKeyResource = new DataPrimaryKeyResource(dataPrimaryKeyService, dataPrimaryKeyQueryService);
        this.restDataPrimaryKeyMockMvc = MockMvcBuilders.standaloneSetup(dataPrimaryKeyResource)
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
    public static DataPrimaryKey createEntity(EntityManager em) {
        DataPrimaryKey dataPrimaryKey = new DataPrimaryKey()
            .name(DEFAULT_NAME)
            .fields(DEFAULT_FIELDS)
            .remarks(DEFAULT_REMARKS)
            .stop(DEFAULT_STOP)
            .createDate(DEFAULT_CREATE_DATE)
            .updateDate(DEFAULT_UPDATE_DATE)
            .modifyDate(DEFAULT_MODIFY_DATE);
        return dataPrimaryKey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataPrimaryKey createUpdatedEntity(EntityManager em) {
        DataPrimaryKey dataPrimaryKey = new DataPrimaryKey()
            .name(UPDATED_NAME)
            .fields(UPDATED_FIELDS)
            .remarks(UPDATED_REMARKS)
            .stop(UPDATED_STOP)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .modifyDate(UPDATED_MODIFY_DATE);
        return dataPrimaryKey;
    }

    @BeforeEach
    public void initTest() {
        dataPrimaryKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataPrimaryKey() throws Exception {
        int databaseSizeBeforeCreate = dataPrimaryKeyRepository.findAll().size();

        // Create the DataPrimaryKey
        restDataPrimaryKeyMockMvc.perform(post("/api/data-primary-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataPrimaryKey)))
            .andExpect(status().isCreated());

        // Validate the DataPrimaryKey in the database
        List<DataPrimaryKey> dataPrimaryKeyList = dataPrimaryKeyRepository.findAll();
        assertThat(dataPrimaryKeyList).hasSize(databaseSizeBeforeCreate + 1);
        DataPrimaryKey testDataPrimaryKey = dataPrimaryKeyList.get(dataPrimaryKeyList.size() - 1);
        assertThat(testDataPrimaryKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataPrimaryKey.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testDataPrimaryKey.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testDataPrimaryKey.isStop()).isEqualTo(DEFAULT_STOP);
        assertThat(testDataPrimaryKey.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testDataPrimaryKey.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testDataPrimaryKey.getModifyDate()).isEqualTo(DEFAULT_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void createDataPrimaryKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataPrimaryKeyRepository.findAll().size();

        // Create the DataPrimaryKey with an existing ID
        dataPrimaryKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataPrimaryKeyMockMvc.perform(post("/api/data-primary-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataPrimaryKey)))
            .andExpect(status().isBadRequest());

        // Validate the DataPrimaryKey in the database
        List<DataPrimaryKey> dataPrimaryKeyList = dataPrimaryKeyRepository.findAll();
        assertThat(dataPrimaryKeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataPrimaryKeys() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList
        restDataPrimaryKeyMockMvc.perform(get("/api/data-primary-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataPrimaryKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(DEFAULT_FIELDS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].stop").value(hasItem(DEFAULT_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifyDate").value(hasItem(DEFAULT_MODIFY_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDataPrimaryKey() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get the dataPrimaryKey
        restDataPrimaryKeyMockMvc.perform(get("/api/data-primary-keys/{id}", dataPrimaryKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataPrimaryKey.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fields").value(DEFAULT_FIELDS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.stop").value(DEFAULT_STOP.booleanValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.modifyDate").value(DEFAULT_MODIFY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where name equals to DEFAULT_NAME
        defaultDataPrimaryKeyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataPrimaryKeyList where name equals to UPDATED_NAME
        defaultDataPrimaryKeyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataPrimaryKeyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataPrimaryKeyList where name equals to UPDATED_NAME
        defaultDataPrimaryKeyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where name is not null
        defaultDataPrimaryKeyShouldBeFound("name.specified=true");

        // Get all the dataPrimaryKeyList where name is null
        defaultDataPrimaryKeyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByFieldsIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where fields equals to DEFAULT_FIELDS
        defaultDataPrimaryKeyShouldBeFound("fields.equals=" + DEFAULT_FIELDS);

        // Get all the dataPrimaryKeyList where fields equals to UPDATED_FIELDS
        defaultDataPrimaryKeyShouldNotBeFound("fields.equals=" + UPDATED_FIELDS);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByFieldsIsInShouldWork() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where fields in DEFAULT_FIELDS or UPDATED_FIELDS
        defaultDataPrimaryKeyShouldBeFound("fields.in=" + DEFAULT_FIELDS + "," + UPDATED_FIELDS);

        // Get all the dataPrimaryKeyList where fields equals to UPDATED_FIELDS
        defaultDataPrimaryKeyShouldNotBeFound("fields.in=" + UPDATED_FIELDS);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByFieldsIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where fields is not null
        defaultDataPrimaryKeyShouldBeFound("fields.specified=true");

        // Get all the dataPrimaryKeyList where fields is null
        defaultDataPrimaryKeyShouldNotBeFound("fields.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where remarks equals to DEFAULT_REMARKS
        defaultDataPrimaryKeyShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the dataPrimaryKeyList where remarks equals to UPDATED_REMARKS
        defaultDataPrimaryKeyShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultDataPrimaryKeyShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the dataPrimaryKeyList where remarks equals to UPDATED_REMARKS
        defaultDataPrimaryKeyShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where remarks is not null
        defaultDataPrimaryKeyShouldBeFound("remarks.specified=true");

        // Get all the dataPrimaryKeyList where remarks is null
        defaultDataPrimaryKeyShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByStopIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where stop equals to DEFAULT_STOP
        defaultDataPrimaryKeyShouldBeFound("stop.equals=" + DEFAULT_STOP);

        // Get all the dataPrimaryKeyList where stop equals to UPDATED_STOP
        defaultDataPrimaryKeyShouldNotBeFound("stop.equals=" + UPDATED_STOP);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByStopIsInShouldWork() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where stop in DEFAULT_STOP or UPDATED_STOP
        defaultDataPrimaryKeyShouldBeFound("stop.in=" + DEFAULT_STOP + "," + UPDATED_STOP);

        // Get all the dataPrimaryKeyList where stop equals to UPDATED_STOP
        defaultDataPrimaryKeyShouldNotBeFound("stop.in=" + UPDATED_STOP);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByStopIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where stop is not null
        defaultDataPrimaryKeyShouldBeFound("stop.specified=true");

        // Get all the dataPrimaryKeyList where stop is null
        defaultDataPrimaryKeyShouldNotBeFound("stop.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByCreateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where createDate equals to DEFAULT_CREATE_DATE
        defaultDataPrimaryKeyShouldBeFound("createDate.equals=" + DEFAULT_CREATE_DATE);

        // Get all the dataPrimaryKeyList where createDate equals to UPDATED_CREATE_DATE
        defaultDataPrimaryKeyShouldNotBeFound("createDate.equals=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByCreateDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where createDate in DEFAULT_CREATE_DATE or UPDATED_CREATE_DATE
        defaultDataPrimaryKeyShouldBeFound("createDate.in=" + DEFAULT_CREATE_DATE + "," + UPDATED_CREATE_DATE);

        // Get all the dataPrimaryKeyList where createDate equals to UPDATED_CREATE_DATE
        defaultDataPrimaryKeyShouldNotBeFound("createDate.in=" + UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByCreateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where createDate is not null
        defaultDataPrimaryKeyShouldBeFound("createDate.specified=true");

        // Get all the dataPrimaryKeyList where createDate is null
        defaultDataPrimaryKeyShouldNotBeFound("createDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByUpdateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where updateDate equals to DEFAULT_UPDATE_DATE
        defaultDataPrimaryKeyShouldBeFound("updateDate.equals=" + DEFAULT_UPDATE_DATE);

        // Get all the dataPrimaryKeyList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDataPrimaryKeyShouldNotBeFound("updateDate.equals=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByUpdateDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where updateDate in DEFAULT_UPDATE_DATE or UPDATED_UPDATE_DATE
        defaultDataPrimaryKeyShouldBeFound("updateDate.in=" + DEFAULT_UPDATE_DATE + "," + UPDATED_UPDATE_DATE);

        // Get all the dataPrimaryKeyList where updateDate equals to UPDATED_UPDATE_DATE
        defaultDataPrimaryKeyShouldNotBeFound("updateDate.in=" + UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByUpdateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where updateDate is not null
        defaultDataPrimaryKeyShouldBeFound("updateDate.specified=true");

        // Get all the dataPrimaryKeyList where updateDate is null
        defaultDataPrimaryKeyShouldNotBeFound("updateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByModifyDateIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where modifyDate equals to DEFAULT_MODIFY_DATE
        defaultDataPrimaryKeyShouldBeFound("modifyDate.equals=" + DEFAULT_MODIFY_DATE);

        // Get all the dataPrimaryKeyList where modifyDate equals to UPDATED_MODIFY_DATE
        defaultDataPrimaryKeyShouldNotBeFound("modifyDate.equals=" + UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByModifyDateIsInShouldWork() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where modifyDate in DEFAULT_MODIFY_DATE or UPDATED_MODIFY_DATE
        defaultDataPrimaryKeyShouldBeFound("modifyDate.in=" + DEFAULT_MODIFY_DATE + "," + UPDATED_MODIFY_DATE);

        // Get all the dataPrimaryKeyList where modifyDate equals to UPDATED_MODIFY_DATE
        defaultDataPrimaryKeyShouldNotBeFound("modifyDate.in=" + UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByModifyDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);

        // Get all the dataPrimaryKeyList where modifyDate is not null
        defaultDataPrimaryKeyShouldBeFound("modifyDate.specified=true");

        // Get all the dataPrimaryKeyList where modifyDate is null
        defaultDataPrimaryKeyShouldNotBeFound("modifyDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataPrimaryKeysByDataCatalogIsEqualToSomething() throws Exception {
        // Initialize the database
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);
        DataCatalog dataCatalog = DataCatalogResourceIT.createEntity(em);
        em.persist(dataCatalog);
        em.flush();
        dataPrimaryKey.setDataCatalog(dataCatalog);
        dataPrimaryKeyRepository.saveAndFlush(dataPrimaryKey);
        Long dataCatalogId = dataCatalog.getId();

        // Get all the dataPrimaryKeyList where dataCatalog equals to dataCatalogId
        defaultDataPrimaryKeyShouldBeFound("dataCatalogId.equals=" + dataCatalogId);

        // Get all the dataPrimaryKeyList where dataCatalog equals to dataCatalogId + 1
        defaultDataPrimaryKeyShouldNotBeFound("dataCatalogId.equals=" + (dataCatalogId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataPrimaryKeyShouldBeFound(String filter) throws Exception {
        restDataPrimaryKeyMockMvc.perform(get("/api/data-primary-keys?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataPrimaryKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(DEFAULT_FIELDS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].stop").value(hasItem(DEFAULT_STOP.booleanValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifyDate").value(hasItem(DEFAULT_MODIFY_DATE.toString())));

        // Check, that the count call also returns 1
        restDataPrimaryKeyMockMvc.perform(get("/api/data-primary-keys/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataPrimaryKeyShouldNotBeFound(String filter) throws Exception {
        restDataPrimaryKeyMockMvc.perform(get("/api/data-primary-keys?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataPrimaryKeyMockMvc.perform(get("/api/data-primary-keys/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataPrimaryKey() throws Exception {
        // Get the dataPrimaryKey
        restDataPrimaryKeyMockMvc.perform(get("/api/data-primary-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataPrimaryKey() throws Exception {
        // Initialize the database
        dataPrimaryKeyService.save(dataPrimaryKey);

        int databaseSizeBeforeUpdate = dataPrimaryKeyRepository.findAll().size();

        // Update the dataPrimaryKey
        DataPrimaryKey updatedDataPrimaryKey = dataPrimaryKeyRepository.findById(dataPrimaryKey.getId()).get();
        // Disconnect from session so that the updates on updatedDataPrimaryKey are not directly saved in db
        em.detach(updatedDataPrimaryKey);
        updatedDataPrimaryKey
            .name(UPDATED_NAME)
            .fields(UPDATED_FIELDS)
            .remarks(UPDATED_REMARKS)
            .stop(UPDATED_STOP)
            .createDate(UPDATED_CREATE_DATE)
            .updateDate(UPDATED_UPDATE_DATE)
            .modifyDate(UPDATED_MODIFY_DATE);

        restDataPrimaryKeyMockMvc.perform(put("/api/data-primary-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataPrimaryKey)))
            .andExpect(status().isOk());

        // Validate the DataPrimaryKey in the database
        List<DataPrimaryKey> dataPrimaryKeyList = dataPrimaryKeyRepository.findAll();
        assertThat(dataPrimaryKeyList).hasSize(databaseSizeBeforeUpdate);
        DataPrimaryKey testDataPrimaryKey = dataPrimaryKeyList.get(dataPrimaryKeyList.size() - 1);
        assertThat(testDataPrimaryKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataPrimaryKey.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testDataPrimaryKey.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testDataPrimaryKey.isStop()).isEqualTo(UPDATED_STOP);
        assertThat(testDataPrimaryKey.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testDataPrimaryKey.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testDataPrimaryKey.getModifyDate()).isEqualTo(UPDATED_MODIFY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataPrimaryKey() throws Exception {
        int databaseSizeBeforeUpdate = dataPrimaryKeyRepository.findAll().size();

        // Create the DataPrimaryKey

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataPrimaryKeyMockMvc.perform(put("/api/data-primary-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataPrimaryKey)))
            .andExpect(status().isBadRequest());

        // Validate the DataPrimaryKey in the database
        List<DataPrimaryKey> dataPrimaryKeyList = dataPrimaryKeyRepository.findAll();
        assertThat(dataPrimaryKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataPrimaryKey() throws Exception {
        // Initialize the database
        dataPrimaryKeyService.save(dataPrimaryKey);

        int databaseSizeBeforeDelete = dataPrimaryKeyRepository.findAll().size();

        // Delete the dataPrimaryKey
        restDataPrimaryKeyMockMvc.perform(delete("/api/data-primary-keys/{id}", dataPrimaryKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataPrimaryKey> dataPrimaryKeyList = dataPrimaryKeyRepository.findAll();
        assertThat(dataPrimaryKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataPrimaryKey.class);
        DataPrimaryKey dataPrimaryKey1 = new DataPrimaryKey();
        dataPrimaryKey1.setId(1L);
        DataPrimaryKey dataPrimaryKey2 = new DataPrimaryKey();
        dataPrimaryKey2.setId(dataPrimaryKey1.getId());
        assertThat(dataPrimaryKey1).isEqualTo(dataPrimaryKey2);
        dataPrimaryKey2.setId(2L);
        assertThat(dataPrimaryKey1).isNotEqualTo(dataPrimaryKey2);
        dataPrimaryKey1.setId(null);
        assertThat(dataPrimaryKey1).isNotEqualTo(dataPrimaryKey2);
    }
}
