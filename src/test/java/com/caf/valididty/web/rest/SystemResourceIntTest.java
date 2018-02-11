package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.System;
import com.caf.valididty.repository.SystemRepository;
import com.caf.valididty.repository.search.SystemSearchRepository;
import com.caf.valididty.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SystemResource REST controller.
 *
 * @see SystemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class SystemResourceIntTest {

    private static final String DEFAULT_SYSTEMNAME = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEMNAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_USERDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_USERDATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private SystemSearchRepository systemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSystemMockMvc;

    private System system;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemResource systemResource = new SystemResource(systemRepository, systemSearchRepository);
        this.restSystemMockMvc = MockMvcBuilders.standaloneSetup(systemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createEntity(EntityManager em) {
        System system = new System()
            .systemname(DEFAULT_SYSTEMNAME)
            .user(DEFAULT_USER)
            .userdate(DEFAULT_USERDATE);
        return system;
    }

    @Before
    public void initTest() {
        systemSearchRepository.deleteAll();
        system = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystem() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // Create the System
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(system)))
            .andExpect(status().isCreated());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate + 1);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getSystemname()).isEqualTo(DEFAULT_SYSTEMNAME);
        assertThat(testSystem.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testSystem.getUserdate()).isEqualTo(DEFAULT_USERDATE);

        // Validate the System in Elasticsearch
        System systemEs = systemSearchRepository.findOne(testSystem.getId());
        assertThat(systemEs).isEqualToComparingFieldByField(testSystem);
    }

    @Test
    @Transactional
    public void createSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // Create the System with an existing ID
        system.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(system)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSystems() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList
        restSystemMockMvc.perform(get("/api/systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].systemname").value(hasItem(DEFAULT_SYSTEMNAME.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(DEFAULT_USERDATE.toString())));
    }

    @Test
    @Transactional
    public void getSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(system.getId().intValue()))
            .andExpect(jsonPath("$.systemname").value(DEFAULT_SYSTEMNAME.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.userdate").value(DEFAULT_USERDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSystem() throws Exception {
        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        systemSearchRepository.save(system);
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Update the system
        System updatedSystem = systemRepository.findOne(system.getId());
        updatedSystem
            .systemname(UPDATED_SYSTEMNAME)
            .user(UPDATED_USER)
            .userdate(UPDATED_USERDATE);

        restSystemMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSystem)))
            .andExpect(status().isOk());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getSystemname()).isEqualTo(UPDATED_SYSTEMNAME);
        assertThat(testSystem.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testSystem.getUserdate()).isEqualTo(UPDATED_USERDATE);

        // Validate the System in Elasticsearch
        System systemEs = systemSearchRepository.findOne(testSystem.getId());
        assertThat(systemEs).isEqualToComparingFieldByField(testSystem);
    }

    @Test
    @Transactional
    public void updateNonExistingSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Create the System

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSystemMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(system)))
            .andExpect(status().isCreated());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        systemSearchRepository.save(system);
        int databaseSizeBeforeDelete = systemRepository.findAll().size();

        // Get the system
        restSystemMockMvc.perform(delete("/api/systems/{id}", system.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean systemExistsInEs = systemSearchRepository.exists(system.getId());
        assertThat(systemExistsInEs).isFalse();

        // Validate the database is empty
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        systemSearchRepository.save(system);

        // Search the system
        restSystemMockMvc.perform(get("/api/_search/systems?query=id:" + system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].systemname").value(hasItem(DEFAULT_SYSTEMNAME.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(DEFAULT_USERDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(System.class);
        System system1 = new System();
        system1.setId(1L);
        System system2 = new System();
        system2.setId(system1.getId());
        assertThat(system1).isEqualTo(system2);
        system2.setId(2L);
        assertThat(system1).isNotEqualTo(system2);
        system1.setId(null);
        assertThat(system1).isNotEqualTo(system2);
    }
}
