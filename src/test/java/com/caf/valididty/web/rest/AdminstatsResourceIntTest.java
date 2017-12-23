package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.Adminstats;
import com.caf.valididty.repository.AdminstatsRepository;
import com.caf.valididty.repository.search.AdminstatsSearchRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdminstatsResource REST controller.
 *
 * @see AdminstatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class AdminstatsResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Integer DEFAULT_USERCOUNT = 1;
    private static final Integer UPDATED_USERCOUNT = 2;

    @Autowired
    private AdminstatsRepository adminstatsRepository;

    @Autowired
    private AdminstatsSearchRepository adminstatsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdminstatsMockMvc;

    private Adminstats adminstats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdminstatsResource adminstatsResource = new AdminstatsResource(adminstatsRepository, adminstatsSearchRepository);
        this.restAdminstatsMockMvc = MockMvcBuilders.standaloneSetup(adminstatsResource)
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
    public static Adminstats createEntity(EntityManager em) {
        Adminstats adminstats = new Adminstats()
            .user(DEFAULT_USER)
            .usercount(DEFAULT_USERCOUNT);
        return adminstats;
    }

    @Before
    public void initTest() {
        adminstatsSearchRepository.deleteAll();
        adminstats = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdminstats() throws Exception {
        int databaseSizeBeforeCreate = adminstatsRepository.findAll().size();

        // Create the Adminstats
        restAdminstatsMockMvc.perform(post("/api/adminstats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminstats)))
            .andExpect(status().isCreated());

        // Validate the Adminstats in the database
        List<Adminstats> adminstatsList = adminstatsRepository.findAll();
        assertThat(adminstatsList).hasSize(databaseSizeBeforeCreate + 1);
        Adminstats testAdminstats = adminstatsList.get(adminstatsList.size() - 1);
        assertThat(testAdminstats.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testAdminstats.getUsercount()).isEqualTo(DEFAULT_USERCOUNT);

        // Validate the Adminstats in Elasticsearch
        Adminstats adminstatsEs = adminstatsSearchRepository.findOne(testAdminstats.getId());
        assertThat(adminstatsEs).isEqualToComparingFieldByField(testAdminstats);
    }

    @Test
    @Transactional
    public void createAdminstatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminstatsRepository.findAll().size();

        // Create the Adminstats with an existing ID
        adminstats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminstatsMockMvc.perform(post("/api/adminstats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminstats)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Adminstats> adminstatsList = adminstatsRepository.findAll();
        assertThat(adminstatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdminstats() throws Exception {
        // Initialize the database
        adminstatsRepository.saveAndFlush(adminstats);

        // Get all the adminstatsList
        restAdminstatsMockMvc.perform(get("/api/adminstats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminstats.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].usercount").value(hasItem(DEFAULT_USERCOUNT)));
    }

    @Test
    @Transactional
    public void getAdminstats() throws Exception {
        // Initialize the database
        adminstatsRepository.saveAndFlush(adminstats);

        // Get the adminstats
        restAdminstatsMockMvc.perform(get("/api/adminstats/{id}", adminstats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adminstats.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.usercount").value(DEFAULT_USERCOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingAdminstats() throws Exception {
        // Get the adminstats
        restAdminstatsMockMvc.perform(get("/api/adminstats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdminstats() throws Exception {
        // Initialize the database
        adminstatsRepository.saveAndFlush(adminstats);
        adminstatsSearchRepository.save(adminstats);
        int databaseSizeBeforeUpdate = adminstatsRepository.findAll().size();

        // Update the adminstats
        Adminstats updatedAdminstats = adminstatsRepository.findOne(adminstats.getId());
        updatedAdminstats
            .user(UPDATED_USER)
            .usercount(UPDATED_USERCOUNT);

        restAdminstatsMockMvc.perform(put("/api/adminstats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdminstats)))
            .andExpect(status().isOk());

        // Validate the Adminstats in the database
        List<Adminstats> adminstatsList = adminstatsRepository.findAll();
        assertThat(adminstatsList).hasSize(databaseSizeBeforeUpdate);
        Adminstats testAdminstats = adminstatsList.get(adminstatsList.size() - 1);
        assertThat(testAdminstats.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testAdminstats.getUsercount()).isEqualTo(UPDATED_USERCOUNT);

        // Validate the Adminstats in Elasticsearch
        Adminstats adminstatsEs = adminstatsSearchRepository.findOne(testAdminstats.getId());
        assertThat(adminstatsEs).isEqualToComparingFieldByField(testAdminstats);
    }

    @Test
    @Transactional
    public void updateNonExistingAdminstats() throws Exception {
        int databaseSizeBeforeUpdate = adminstatsRepository.findAll().size();

        // Create the Adminstats

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdminstatsMockMvc.perform(put("/api/adminstats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminstats)))
            .andExpect(status().isCreated());

        // Validate the Adminstats in the database
        List<Adminstats> adminstatsList = adminstatsRepository.findAll();
        assertThat(adminstatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdminstats() throws Exception {
        // Initialize the database
        adminstatsRepository.saveAndFlush(adminstats);
        adminstatsSearchRepository.save(adminstats);
        int databaseSizeBeforeDelete = adminstatsRepository.findAll().size();

        // Get the adminstats
        restAdminstatsMockMvc.perform(delete("/api/adminstats/{id}", adminstats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adminstatsExistsInEs = adminstatsSearchRepository.exists(adminstats.getId());
        assertThat(adminstatsExistsInEs).isFalse();

        // Validate the database is empty
        List<Adminstats> adminstatsList = adminstatsRepository.findAll();
        assertThat(adminstatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdminstats() throws Exception {
        // Initialize the database
        adminstatsRepository.saveAndFlush(adminstats);
        adminstatsSearchRepository.save(adminstats);

        // Search the adminstats
        restAdminstatsMockMvc.perform(get("/api/_search/adminstats?query=id:" + adminstats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminstats.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].usercount").value(hasItem(DEFAULT_USERCOUNT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adminstats.class);
        Adminstats adminstats1 = new Adminstats();
        adminstats1.setId(1L);
        Adminstats adminstats2 = new Adminstats();
        adminstats2.setId(adminstats1.getId());
        assertThat(adminstats1).isEqualTo(adminstats2);
        adminstats2.setId(2L);
        assertThat(adminstats1).isNotEqualTo(adminstats2);
        adminstats1.setId(null);
        assertThat(adminstats1).isNotEqualTo(adminstats2);
    }
}
