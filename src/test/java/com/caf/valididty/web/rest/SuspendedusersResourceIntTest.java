package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.Suspendedusers;
import com.caf.valididty.repository.SuspendedusersRepository;
import com.caf.valididty.repository.search.SuspendedusersSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.caf.valididty.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SuspendedusersResource REST controller.
 *
 * @see SuspendedusersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class SuspendedusersResourceIntTest {

    private static final Long DEFAULT_MOBILENUMBER = 1L;
    private static final Long UPDATED_MOBILENUMBER = 2L;

    private static final String DEFAULT_CENTRALBARCODE = "AAAAAAAAAA";
    private static final String UPDATED_CENTRALBARCODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_USERDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_USERDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SuspendedusersRepository suspendedusersRepository;

    @Autowired
    private SuspendedusersSearchRepository suspendedusersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSuspendedusersMockMvc;

    private Suspendedusers suspendedusers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuspendedusersResource suspendedusersResource = new SuspendedusersResource(suspendedusersRepository, suspendedusersSearchRepository);
        this.restSuspendedusersMockMvc = MockMvcBuilders.standaloneSetup(suspendedusersResource)
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
    public static Suspendedusers createEntity(EntityManager em) {
        Suspendedusers suspendedusers = new Suspendedusers()
            .mobilenumber(DEFAULT_MOBILENUMBER)
            .centralbarcode(DEFAULT_CENTRALBARCODE)
            .count(DEFAULT_COUNT)
            .user(DEFAULT_USER)
            .userdate(DEFAULT_USERDATE);
        return suspendedusers;
    }

    @Before
    public void initTest() {
        suspendedusersSearchRepository.deleteAll();
        suspendedusers = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuspendedusers() throws Exception {
        int databaseSizeBeforeCreate = suspendedusersRepository.findAll().size();

        // Create the Suspendedusers
        restSuspendedusersMockMvc.perform(post("/api/suspendedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suspendedusers)))
            .andExpect(status().isCreated());

        // Validate the Suspendedusers in the database
        List<Suspendedusers> suspendedusersList = suspendedusersRepository.findAll();
        assertThat(suspendedusersList).hasSize(databaseSizeBeforeCreate + 1);
        Suspendedusers testSuspendedusers = suspendedusersList.get(suspendedusersList.size() - 1);
        assertThat(testSuspendedusers.getMobilenumber()).isEqualTo(DEFAULT_MOBILENUMBER);
        assertThat(testSuspendedusers.getCentralbarcode()).isEqualTo(DEFAULT_CENTRALBARCODE);
        assertThat(testSuspendedusers.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testSuspendedusers.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testSuspendedusers.getUserdate()).isEqualTo(DEFAULT_USERDATE);

        // Validate the Suspendedusers in Elasticsearch
        Suspendedusers suspendedusersEs = suspendedusersSearchRepository.findOne(testSuspendedusers.getId());
        assertThat(suspendedusersEs).isEqualToComparingFieldByField(testSuspendedusers);
    }

    @Test
    @Transactional
    public void createSuspendedusersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suspendedusersRepository.findAll().size();

        // Create the Suspendedusers with an existing ID
        suspendedusers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuspendedusersMockMvc.perform(post("/api/suspendedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suspendedusers)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Suspendedusers> suspendedusersList = suspendedusersRepository.findAll();
        assertThat(suspendedusersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSuspendedusers() throws Exception {
        // Initialize the database
        suspendedusersRepository.saveAndFlush(suspendedusers);

        // Get all the suspendedusersList
        restSuspendedusersMockMvc.perform(get("/api/suspendedusers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suspendedusers.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.intValue())))
            .andExpect(jsonPath("$.[*].centralbarcode").value(hasItem(DEFAULT_CENTRALBARCODE.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(sameInstant(DEFAULT_USERDATE))));
    }

    @Test
    @Transactional
    public void getSuspendedusers() throws Exception {
        // Initialize the database
        suspendedusersRepository.saveAndFlush(suspendedusers);

        // Get the suspendedusers
        restSuspendedusersMockMvc.perform(get("/api/suspendedusers/{id}", suspendedusers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(suspendedusers.getId().intValue()))
            .andExpect(jsonPath("$.mobilenumber").value(DEFAULT_MOBILENUMBER.intValue()))
            .andExpect(jsonPath("$.centralbarcode").value(DEFAULT_CENTRALBARCODE.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.userdate").value(sameInstant(DEFAULT_USERDATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSuspendedusers() throws Exception {
        // Get the suspendedusers
        restSuspendedusersMockMvc.perform(get("/api/suspendedusers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuspendedusers() throws Exception {
        // Initialize the database
        suspendedusersRepository.saveAndFlush(suspendedusers);
        suspendedusersSearchRepository.save(suspendedusers);
        int databaseSizeBeforeUpdate = suspendedusersRepository.findAll().size();

        // Update the suspendedusers
        Suspendedusers updatedSuspendedusers = suspendedusersRepository.findOne(suspendedusers.getId());
        updatedSuspendedusers
            .mobilenumber(UPDATED_MOBILENUMBER)
            .centralbarcode(UPDATED_CENTRALBARCODE)
            .count(UPDATED_COUNT)
            .user(UPDATED_USER)
            .userdate(UPDATED_USERDATE);

        restSuspendedusersMockMvc.perform(put("/api/suspendedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSuspendedusers)))
            .andExpect(status().isOk());

        // Validate the Suspendedusers in the database
        List<Suspendedusers> suspendedusersList = suspendedusersRepository.findAll();
        assertThat(suspendedusersList).hasSize(databaseSizeBeforeUpdate);
        Suspendedusers testSuspendedusers = suspendedusersList.get(suspendedusersList.size() - 1);
        assertThat(testSuspendedusers.getMobilenumber()).isEqualTo(UPDATED_MOBILENUMBER);
        assertThat(testSuspendedusers.getCentralbarcode()).isEqualTo(UPDATED_CENTRALBARCODE);
        assertThat(testSuspendedusers.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testSuspendedusers.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testSuspendedusers.getUserdate()).isEqualTo(UPDATED_USERDATE);

        // Validate the Suspendedusers in Elasticsearch
        Suspendedusers suspendedusersEs = suspendedusersSearchRepository.findOne(testSuspendedusers.getId());
        assertThat(suspendedusersEs).isEqualToComparingFieldByField(testSuspendedusers);
    }

    @Test
    @Transactional
    public void updateNonExistingSuspendedusers() throws Exception {
        int databaseSizeBeforeUpdate = suspendedusersRepository.findAll().size();

        // Create the Suspendedusers

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSuspendedusersMockMvc.perform(put("/api/suspendedusers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(suspendedusers)))
            .andExpect(status().isCreated());

        // Validate the Suspendedusers in the database
        List<Suspendedusers> suspendedusersList = suspendedusersRepository.findAll();
        assertThat(suspendedusersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSuspendedusers() throws Exception {
        // Initialize the database
        suspendedusersRepository.saveAndFlush(suspendedusers);
        suspendedusersSearchRepository.save(suspendedusers);
        int databaseSizeBeforeDelete = suspendedusersRepository.findAll().size();

        // Get the suspendedusers
        restSuspendedusersMockMvc.perform(delete("/api/suspendedusers/{id}", suspendedusers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean suspendedusersExistsInEs = suspendedusersSearchRepository.exists(suspendedusers.getId());
        assertThat(suspendedusersExistsInEs).isFalse();

        // Validate the database is empty
        List<Suspendedusers> suspendedusersList = suspendedusersRepository.findAll();
        assertThat(suspendedusersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSuspendedusers() throws Exception {
        // Initialize the database
        suspendedusersRepository.saveAndFlush(suspendedusers);
        suspendedusersSearchRepository.save(suspendedusers);

        // Search the suspendedusers
        restSuspendedusersMockMvc.perform(get("/api/_search/suspendedusers?query=id:" + suspendedusers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suspendedusers.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.intValue())))
            .andExpect(jsonPath("$.[*].centralbarcode").value(hasItem(DEFAULT_CENTRALBARCODE.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(sameInstant(DEFAULT_USERDATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Suspendedusers.class);
        Suspendedusers suspendedusers1 = new Suspendedusers();
        suspendedusers1.setId(1L);
        Suspendedusers suspendedusers2 = new Suspendedusers();
        suspendedusers2.setId(suspendedusers1.getId());
        assertThat(suspendedusers1).isEqualTo(suspendedusers2);
        suspendedusers2.setId(2L);
        assertThat(suspendedusers1).isNotEqualTo(suspendedusers2);
        suspendedusers1.setId(null);
        assertThat(suspendedusers1).isNotEqualTo(suspendedusers2);
    }
}
