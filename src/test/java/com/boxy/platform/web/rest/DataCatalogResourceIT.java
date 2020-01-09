package com.boxy.platform.web.rest;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.domain.DataCatalog;
import com.boxy.platform.domain.DatabaseConnection;
import com.boxy.platform.repository.DataCatalogRepository;
import com.boxy.platform.service.DataCatalogService;
import com.boxy.platform.web.rest.errors.ExceptionTranslator;
import com.boxy.platform.service.dto.DataCatalogCriteria;
import com.boxy.platform.service.DataCatalogQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.boxy.platform.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DataCatalogResource} REST controller.
 */
@SpringBootTest(classes = PlatformApp.class)
public class DataCatalogResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TABLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_RELATION_GRAPH = "AAAAAAAAAA";
    private static final String UPDATED_RELATION_GRAPH = "BBBBBBBBBB";

    @Autowired
    private DataCatalogRepository dataCatalogRepository;

    @Autowired
    private DataCatalogService dataCatalogService;

    @Autowired
    private DataCatalogQueryService dataCatalogQueryService;

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

    private MockMvc restDataCatalogMockMvc;

    private DataCatalog dataCatalog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataCatalogResource dataCatalogResource = new DataCatalogResource(dataCatalogService, dataCatalogQueryService);
        this.restDataCatalogMockMvc = MockMvcBuilders.standaloneSetup(dataCatalogResource)
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
    public static DataCatalog createEntity(EntityManager em) {
        DataCatalog dataCatalog = new DataCatalog()
            .title(DEFAULT_TITLE)
            .type(DEFAULT_TYPE)
            .icon(DEFAULT_ICON)
            .tableName(DEFAULT_TABLE_NAME)
            .tableType(DEFAULT_TABLE_TYPE)
            .remarks(DEFAULT_REMARKS)
            .relationGraph(DEFAULT_RELATION_GRAPH);
        return dataCatalog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataCatalog createUpdatedEntity(EntityManager em) {
        DataCatalog dataCatalog = new DataCatalog()
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .icon(UPDATED_ICON)
            .tableName(UPDATED_TABLE_NAME)
            .tableType(UPDATED_TABLE_TYPE)
            .remarks(UPDATED_REMARKS)
            .relationGraph(UPDATED_RELATION_GRAPH);
        return dataCatalog;
    }

    @BeforeEach
    public void initTest() {
        dataCatalog = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataCatalog() throws Exception {
        int databaseSizeBeforeCreate = dataCatalogRepository.findAll().size();

        // Create the DataCatalog
        restDataCatalogMockMvc.perform(post("/api/data-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCatalog)))
            .andExpect(status().isCreated());

        // Validate the DataCatalog in the database
        List<DataCatalog> dataCatalogList = dataCatalogRepository.findAll();
        assertThat(dataCatalogList).hasSize(databaseSizeBeforeCreate + 1);
        DataCatalog testDataCatalog = dataCatalogList.get(dataCatalogList.size() - 1);
        assertThat(testDataCatalog.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDataCatalog.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDataCatalog.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testDataCatalog.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testDataCatalog.getTableType()).isEqualTo(DEFAULT_TABLE_TYPE);
        assertThat(testDataCatalog.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testDataCatalog.getRelationGraph()).isEqualTo(DEFAULT_RELATION_GRAPH);
    }

    @Test
    @Transactional
    public void createDataCatalogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataCatalogRepository.findAll().size();

        // Create the DataCatalog with an existing ID
        dataCatalog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataCatalogMockMvc.perform(post("/api/data-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCatalog)))
            .andExpect(status().isBadRequest());

        // Validate the DataCatalog in the database
        List<DataCatalog> dataCatalogList = dataCatalogRepository.findAll();
        assertThat(dataCatalogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataCatalogs() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList
        restDataCatalogMockMvc.perform(get("/api/data-catalogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataCatalog.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].tableType").value(hasItem(DEFAULT_TABLE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].relationGraph").value(hasItem(DEFAULT_RELATION_GRAPH.toString())));
    }
    
    @Test
    @Transactional
    public void getDataCatalog() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get the dataCatalog
        restDataCatalogMockMvc.perform(get("/api/data-catalogs/{id}", dataCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataCatalog.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME.toString()))
            .andExpect(jsonPath("$.tableType").value(DEFAULT_TABLE_TYPE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.relationGraph").value(DEFAULT_RELATION_GRAPH.toString()));
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where title equals to DEFAULT_TITLE
        defaultDataCatalogShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the dataCatalogList where title equals to UPDATED_TITLE
        defaultDataCatalogShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultDataCatalogShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the dataCatalogList where title equals to UPDATED_TITLE
        defaultDataCatalogShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where title is not null
        defaultDataCatalogShouldBeFound("title.specified=true");

        // Get all the dataCatalogList where title is null
        defaultDataCatalogShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where type equals to DEFAULT_TYPE
        defaultDataCatalogShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dataCatalogList where type equals to UPDATED_TYPE
        defaultDataCatalogShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDataCatalogShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dataCatalogList where type equals to UPDATED_TYPE
        defaultDataCatalogShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where type is not null
        defaultDataCatalogShouldBeFound("type.specified=true");

        // Get all the dataCatalogList where type is null
        defaultDataCatalogShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where icon equals to DEFAULT_ICON
        defaultDataCatalogShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the dataCatalogList where icon equals to UPDATED_ICON
        defaultDataCatalogShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByIconIsInShouldWork() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultDataCatalogShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the dataCatalogList where icon equals to UPDATED_ICON
        defaultDataCatalogShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where icon is not null
        defaultDataCatalogShouldBeFound("icon.specified=true");

        // Get all the dataCatalogList where icon is null
        defaultDataCatalogShouldNotBeFound("icon.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTableNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where tableName equals to DEFAULT_TABLE_NAME
        defaultDataCatalogShouldBeFound("tableName.equals=" + DEFAULT_TABLE_NAME);

        // Get all the dataCatalogList where tableName equals to UPDATED_TABLE_NAME
        defaultDataCatalogShouldNotBeFound("tableName.equals=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTableNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where tableName in DEFAULT_TABLE_NAME or UPDATED_TABLE_NAME
        defaultDataCatalogShouldBeFound("tableName.in=" + DEFAULT_TABLE_NAME + "," + UPDATED_TABLE_NAME);

        // Get all the dataCatalogList where tableName equals to UPDATED_TABLE_NAME
        defaultDataCatalogShouldNotBeFound("tableName.in=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTableNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where tableName is not null
        defaultDataCatalogShouldBeFound("tableName.specified=true");

        // Get all the dataCatalogList where tableName is null
        defaultDataCatalogShouldNotBeFound("tableName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTableTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where tableType equals to DEFAULT_TABLE_TYPE
        defaultDataCatalogShouldBeFound("tableType.equals=" + DEFAULT_TABLE_TYPE);

        // Get all the dataCatalogList where tableType equals to UPDATED_TABLE_TYPE
        defaultDataCatalogShouldNotBeFound("tableType.equals=" + UPDATED_TABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTableTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where tableType in DEFAULT_TABLE_TYPE or UPDATED_TABLE_TYPE
        defaultDataCatalogShouldBeFound("tableType.in=" + DEFAULT_TABLE_TYPE + "," + UPDATED_TABLE_TYPE);

        // Get all the dataCatalogList where tableType equals to UPDATED_TABLE_TYPE
        defaultDataCatalogShouldNotBeFound("tableType.in=" + UPDATED_TABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByTableTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where tableType is not null
        defaultDataCatalogShouldBeFound("tableType.specified=true");

        // Get all the dataCatalogList where tableType is null
        defaultDataCatalogShouldNotBeFound("tableType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where remarks equals to DEFAULT_REMARKS
        defaultDataCatalogShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the dataCatalogList where remarks equals to UPDATED_REMARKS
        defaultDataCatalogShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultDataCatalogShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the dataCatalogList where remarks equals to UPDATED_REMARKS
        defaultDataCatalogShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);

        // Get all the dataCatalogList where remarks is not null
        defaultDataCatalogShouldBeFound("remarks.specified=true");

        // Get all the dataCatalogList where remarks is null
        defaultDataCatalogShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllDataCatalogsByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);
        DataCatalog children = DataCatalogResourceIT.createEntity(em);
        em.persist(children);
        em.flush();
        dataCatalog.addChildren(children);
        dataCatalogRepository.saveAndFlush(dataCatalog);
        Long childrenId = children.getId();

        // Get all the dataCatalogList where children equals to childrenId
        defaultDataCatalogShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the dataCatalogList where children equals to childrenId + 1
        defaultDataCatalogShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }


    @Test
    @Transactional
    public void getAllDataCatalogsByDbConnectionIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);
        DatabaseConnection dbConnection = DatabaseConnectionResourceIT.createEntity(em);
        em.persist(dbConnection);
        em.flush();
        dataCatalog.setDbConnection(dbConnection);
        dataCatalogRepository.saveAndFlush(dataCatalog);
        Long dbConnectionId = dbConnection.getId();

        // Get all the dataCatalogList where dbConnection equals to dbConnectionId
        defaultDataCatalogShouldBeFound("dbConnectionId.equals=" + dbConnectionId);

        // Get all the dataCatalogList where dbConnection equals to dbConnectionId + 1
        defaultDataCatalogShouldNotBeFound("dbConnectionId.equals=" + (dbConnectionId + 1));
    }


    @Test
    @Transactional
    public void getAllDataCatalogsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        dataCatalogRepository.saveAndFlush(dataCatalog);
        DataCatalog parent = DataCatalogResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        dataCatalog.setParent(parent);
        dataCatalogRepository.saveAndFlush(dataCatalog);
        Long parentId = parent.getId();

        // Get all the dataCatalogList where parent equals to parentId
        defaultDataCatalogShouldBeFound("parentId.equals=" + parentId);

        // Get all the dataCatalogList where parent equals to parentId + 1
        defaultDataCatalogShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataCatalogShouldBeFound(String filter) throws Exception {
        restDataCatalogMockMvc.perform(get("/api/data-catalogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataCatalog.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].tableType").value(hasItem(DEFAULT_TABLE_TYPE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].relationGraph").value(hasItem(DEFAULT_RELATION_GRAPH.toString())));

        // Check, that the count call also returns 1
        restDataCatalogMockMvc.perform(get("/api/data-catalogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataCatalogShouldNotBeFound(String filter) throws Exception {
        restDataCatalogMockMvc.perform(get("/api/data-catalogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataCatalogMockMvc.perform(get("/api/data-catalogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataCatalog() throws Exception {
        // Get the dataCatalog
        restDataCatalogMockMvc.perform(get("/api/data-catalogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataCatalog() throws Exception {
        // Initialize the database
        dataCatalogService.save(dataCatalog);

        int databaseSizeBeforeUpdate = dataCatalogRepository.findAll().size();

        // Update the dataCatalog
        DataCatalog updatedDataCatalog = dataCatalogRepository.findById(dataCatalog.getId()).get();
        // Disconnect from session so that the updates on updatedDataCatalog are not directly saved in db
        em.detach(updatedDataCatalog);
        updatedDataCatalog
            .title(UPDATED_TITLE)
            .type(UPDATED_TYPE)
            .icon(UPDATED_ICON)
            .tableName(UPDATED_TABLE_NAME)
            .tableType(UPDATED_TABLE_TYPE)
            .remarks(UPDATED_REMARKS)
            .relationGraph(UPDATED_RELATION_GRAPH);

        restDataCatalogMockMvc.perform(put("/api/data-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataCatalog)))
            .andExpect(status().isOk());

        // Validate the DataCatalog in the database
        List<DataCatalog> dataCatalogList = dataCatalogRepository.findAll();
        assertThat(dataCatalogList).hasSize(databaseSizeBeforeUpdate);
        DataCatalog testDataCatalog = dataCatalogList.get(dataCatalogList.size() - 1);
        assertThat(testDataCatalog.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDataCatalog.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDataCatalog.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDataCatalog.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testDataCatalog.getTableType()).isEqualTo(UPDATED_TABLE_TYPE);
        assertThat(testDataCatalog.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testDataCatalog.getRelationGraph()).isEqualTo(UPDATED_RELATION_GRAPH);
    }

    @Test
    @Transactional
    public void updateNonExistingDataCatalog() throws Exception {
        int databaseSizeBeforeUpdate = dataCatalogRepository.findAll().size();

        // Create the DataCatalog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataCatalogMockMvc.perform(put("/api/data-catalogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCatalog)))
            .andExpect(status().isBadRequest());

        // Validate the DataCatalog in the database
        List<DataCatalog> dataCatalogList = dataCatalogRepository.findAll();
        assertThat(dataCatalogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataCatalog() throws Exception {
        // Initialize the database
        dataCatalogService.save(dataCatalog);

        int databaseSizeBeforeDelete = dataCatalogRepository.findAll().size();

        // Delete the dataCatalog
        restDataCatalogMockMvc.perform(delete("/api/data-catalogs/{id}", dataCatalog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataCatalog> dataCatalogList = dataCatalogRepository.findAll();
        assertThat(dataCatalogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCatalog.class);
        DataCatalog dataCatalog1 = new DataCatalog();
        dataCatalog1.setId(1L);
        DataCatalog dataCatalog2 = new DataCatalog();
        dataCatalog2.setId(dataCatalog1.getId());
        assertThat(dataCatalog1).isEqualTo(dataCatalog2);
        dataCatalog2.setId(2L);
        assertThat(dataCatalog1).isNotEqualTo(dataCatalog2);
        dataCatalog1.setId(null);
        assertThat(dataCatalog1).isNotEqualTo(dataCatalog2);
    }
}
