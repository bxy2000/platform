package com.boxy.platform.web.rest;

import com.boxy.platform.PlatformApp;
import com.boxy.platform.domain.UserExtends;
import com.boxy.platform.domain.User;
import com.boxy.platform.domain.Role;
import com.boxy.platform.repository.UserExtendsRepository;
import com.boxy.platform.service.UserExtendsService;
import com.boxy.platform.web.rest.errors.ExceptionTranslator;
import com.boxy.platform.service.dto.UserExtendsCriteria;
import com.boxy.platform.service.UserExtendsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.boxy.platform.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.boxy.platform.domain.enumeration.Gender;
/**
 * Integration tests for the {@link UserExtendsResource} REST controller.
 */
@SpringBootTest(classes = PlatformApp.class)
public class UserExtendsResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    @Autowired
    private UserExtendsRepository userExtendsRepository;

    @Mock
    private UserExtendsRepository userExtendsRepositoryMock;

    @Mock
    private UserExtendsService userExtendsServiceMock;

    @Autowired
    private UserExtendsService userExtendsService;

    @Autowired
    private UserExtendsQueryService userExtendsQueryService;

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

    private MockMvc restUserExtendsMockMvc;

    private UserExtends userExtends;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserExtendsResource userExtendsResource = new UserExtendsResource(userExtendsService, userExtendsQueryService);
        this.restUserExtendsMockMvc = MockMvcBuilders.standaloneSetup(userExtendsResource)
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
    public static UserExtends createEntity(EntityManager em) {
        UserExtends userExtends = new UserExtends()
            .username(DEFAULT_USERNAME)
            .gender(DEFAULT_GENDER)
            .mobile(DEFAULT_MOBILE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userExtends.setUser(user);
        return userExtends;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtends createUpdatedEntity(EntityManager em) {
        UserExtends userExtends = new UserExtends()
            .username(UPDATED_USERNAME)
            .gender(UPDATED_GENDER)
            .mobile(UPDATED_MOBILE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userExtends.setUser(user);
        return userExtends;
    }

    @BeforeEach
    public void initTest() {
        userExtends = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExtends() throws Exception {
        int databaseSizeBeforeCreate = userExtendsRepository.findAll().size();

        // Create the UserExtends
        restUserExtendsMockMvc.perform(post("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtends)))
            .andExpect(status().isCreated());

        // Validate the UserExtends in the database
        List<UserExtends> userExtendsList = userExtendsRepository.findAll();
        assertThat(userExtendsList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtends testUserExtends = userExtendsList.get(userExtendsList.size() - 1);
        assertThat(testUserExtends.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserExtends.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserExtends.getMobile()).isEqualTo(DEFAULT_MOBILE);
    }

    @Test
    @Transactional
    public void createUserExtendsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtendsRepository.findAll().size();

        // Create the UserExtends with an existing ID
        userExtends.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtendsMockMvc.perform(post("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtends)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtends in the database
        List<UserExtends> userExtendsList = userExtendsRepository.findAll();
        assertThat(userExtendsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userExtendsRepository.findAll().size();
        // set the field null
        userExtends.setUsername(null);

        // Create the UserExtends, which fails.

        restUserExtendsMockMvc.perform(post("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtends)))
            .andExpect(status().isBadRequest());

        List<UserExtends> userExtendsList = userExtendsRepository.findAll();
        assertThat(userExtendsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserExtends() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList
        restUserExtendsMockMvc.perform(get("/api/user-extends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtends.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserExtendsWithEagerRelationshipsIsEnabled() throws Exception {
        UserExtendsResource userExtendsResource = new UserExtendsResource(userExtendsServiceMock, userExtendsQueryService);
        when(userExtendsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserExtendsMockMvc = MockMvcBuilders.standaloneSetup(userExtendsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserExtendsMockMvc.perform(get("/api/user-extends?eagerload=true"))
        .andExpect(status().isOk());

        verify(userExtendsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserExtendsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserExtendsResource userExtendsResource = new UserExtendsResource(userExtendsServiceMock, userExtendsQueryService);
            when(userExtendsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserExtendsMockMvc = MockMvcBuilders.standaloneSetup(userExtendsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserExtendsMockMvc.perform(get("/api/user-extends?eagerload=true"))
        .andExpect(status().isOk());

            verify(userExtendsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserExtends() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get the userExtends
        restUserExtendsMockMvc.perform(get("/api/user-extends/{id}", userExtends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExtends.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()));
    }

    @Test
    @Transactional
    public void getAllUserExtendsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where username equals to DEFAULT_USERNAME
        defaultUserExtendsShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the userExtendsList where username equals to UPDATED_USERNAME
        defaultUserExtendsShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllUserExtendsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultUserExtendsShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the userExtendsList where username equals to UPDATED_USERNAME
        defaultUserExtendsShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllUserExtendsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where username is not null
        defaultUserExtendsShouldBeFound("username.specified=true");

        // Get all the userExtendsList where username is null
        defaultUserExtendsShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserExtendsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where gender equals to DEFAULT_GENDER
        defaultUserExtendsShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the userExtendsList where gender equals to UPDATED_GENDER
        defaultUserExtendsShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserExtendsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultUserExtendsShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the userExtendsList where gender equals to UPDATED_GENDER
        defaultUserExtendsShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserExtendsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where gender is not null
        defaultUserExtendsShouldBeFound("gender.specified=true");

        // Get all the userExtendsList where gender is null
        defaultUserExtendsShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserExtendsByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where mobile equals to DEFAULT_MOBILE
        defaultUserExtendsShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the userExtendsList where mobile equals to UPDATED_MOBILE
        defaultUserExtendsShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserExtendsByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultUserExtendsShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the userExtendsList where mobile equals to UPDATED_MOBILE
        defaultUserExtendsShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserExtendsByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);

        // Get all the userExtendsList where mobile is not null
        defaultUserExtendsShouldBeFound("mobile.specified=true");

        // Get all the userExtendsList where mobile is null
        defaultUserExtendsShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserExtendsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = userExtends.getUser();
        userExtendsRepository.saveAndFlush(userExtends);
        Long userId = user.getId();

        // Get all the userExtendsList where user equals to userId
        defaultUserExtendsShouldBeFound("userId.equals=" + userId);

        // Get all the userExtendsList where user equals to userId + 1
        defaultUserExtendsShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllUserExtendsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        userExtendsRepository.saveAndFlush(userExtends);
        Role role = RoleResourceIT.createEntity(em);
        em.persist(role);
        em.flush();
        userExtends.addRole(role);
        userExtendsRepository.saveAndFlush(userExtends);
        Long roleId = role.getId();

        // Get all the userExtendsList where role equals to roleId
        defaultUserExtendsShouldBeFound("roleId.equals=" + roleId);

        // Get all the userExtendsList where role equals to roleId + 1
        defaultUserExtendsShouldNotBeFound("roleId.equals=" + (roleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserExtendsShouldBeFound(String filter) throws Exception {
        restUserExtendsMockMvc.perform(get("/api/user-extends?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtends.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)));

        // Check, that the count call also returns 1
        restUserExtendsMockMvc.perform(get("/api/user-extends/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserExtendsShouldNotBeFound(String filter) throws Exception {
        restUserExtendsMockMvc.perform(get("/api/user-extends?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserExtendsMockMvc.perform(get("/api/user-extends/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserExtends() throws Exception {
        // Get the userExtends
        restUserExtendsMockMvc.perform(get("/api/user-extends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExtends() throws Exception {
        // Initialize the database
        userExtendsService.save(userExtends);

        int databaseSizeBeforeUpdate = userExtendsRepository.findAll().size();

        // Update the userExtends
        UserExtends updatedUserExtends = userExtendsRepository.findById(userExtends.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtends are not directly saved in db
        em.detach(updatedUserExtends);
        updatedUserExtends
            .username(UPDATED_USERNAME)
            .gender(UPDATED_GENDER)
            .mobile(UPDATED_MOBILE);

        restUserExtendsMockMvc.perform(put("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExtends)))
            .andExpect(status().isOk());

        // Validate the UserExtends in the database
        List<UserExtends> userExtendsList = userExtendsRepository.findAll();
        assertThat(userExtendsList).hasSize(databaseSizeBeforeUpdate);
        UserExtends testUserExtends = userExtendsList.get(userExtendsList.size() - 1);
        assertThat(testUserExtends.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserExtends.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserExtends.getMobile()).isEqualTo(UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExtends() throws Exception {
        int databaseSizeBeforeUpdate = userExtendsRepository.findAll().size();

        // Create the UserExtends

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtendsMockMvc.perform(put("/api/user-extends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExtends)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtends in the database
        List<UserExtends> userExtendsList = userExtendsRepository.findAll();
        assertThat(userExtendsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserExtends() throws Exception {
        // Initialize the database
        userExtendsService.save(userExtends);

        int databaseSizeBeforeDelete = userExtendsRepository.findAll().size();

        // Delete the userExtends
        restUserExtendsMockMvc.perform(delete("/api/user-extends/{id}", userExtends.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtends> userExtendsList = userExtendsRepository.findAll();
        assertThat(userExtendsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserExtends.class);
        UserExtends userExtends1 = new UserExtends();
        userExtends1.setId(1L);
        UserExtends userExtends2 = new UserExtends();
        userExtends2.setId(userExtends1.getId());
        assertThat(userExtends1).isEqualTo(userExtends2);
        userExtends2.setId(2L);
        assertThat(userExtends1).isNotEqualTo(userExtends2);
        userExtends1.setId(null);
        assertThat(userExtends1).isNotEqualTo(userExtends2);
    }
}
