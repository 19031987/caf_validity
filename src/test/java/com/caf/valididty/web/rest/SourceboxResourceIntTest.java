package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.Sourcebox;
import com.caf.valididty.repository.SourceboxRepository;
import com.caf.valididty.repository.search.SourceboxSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SourceboxResource REST controller.
 *
 * @see SourceboxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class SourceboxResourceIntTest {

    private static final String DEFAULT_SOURCEBOXNAME = "AAAAAAAAAA";
    private static final String UPDATED_SOURCEBOXNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREATEDUSER = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDUSER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATEDDATE = Instant.now();
    private static final Instant UPDATED_CREATEDDATE = Instant.now();

    @Autowired
    private SourceboxRepository sourceboxRepository;

    @Autowired
    private SourceboxSearchRepository sourceboxSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSourceboxMockMvc;

    private Sourcebox sourcebox;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceboxResource sourceboxResource = new SourceboxResource(sourceboxRepository, sourceboxSearchRepository);
        this.restSourceboxMockMvc = MockMvcBuilders.standaloneSetup(sourceboxResource)
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
    public static Sourcebox createEntity(EntityManager em) {
        Sourcebox sourcebox = new Sourcebox()
            .sourceboxname(DEFAULT_SOURCEBOXNAME)
            .createduser(DEFAULT_CREATEDUSER)
            .createddate(DEFAULT_CREATEDDATE);
        return sourcebox;
    }

    @Before
    public void initTest() {
        sourceboxSearchRepository.deleteAll();
        sourcebox = createEntity(em);
    }

    @Test
    @Transactional
    public void createSourcebox() throws Exception {
        int databaseSizeBeforeCreate = sourceboxRepository.findAll().size();

        // Create the Sourcebox
        restSourceboxMockMvc.perform(post("/api/sourceboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourcebox)))
            .andExpect(status().isCreated());

        // Validate the Sourcebox in the database
        List<Sourcebox> sourceboxList = sourceboxRepository.findAll();
        assertThat(sourceboxList).hasSize(databaseSizeBeforeCreate + 1);
        Sourcebox testSourcebox = sourceboxList.get(sourceboxList.size() - 1);
        assertThat(testSourcebox.getSourceboxname()).isEqualTo(DEFAULT_SOURCEBOXNAME);
        assertThat(testSourcebox.getCreateduser()).isEqualTo(DEFAULT_CREATEDUSER);
        assertThat(testSourcebox.getCreateddate()).isEqualTo(DEFAULT_CREATEDDATE);

        // Validate the Sourcebox in Elasticsearch
        Sourcebox sourceboxEs = sourceboxSearchRepository.findOne(testSourcebox.getId());
        assertThat(sourceboxEs).isEqualToComparingFieldByField(testSourcebox);
    }

    @Test
    @Transactional
    public void createSourceboxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceboxRepository.findAll().size();

        // Create the Sourcebox with an existing ID
        sourcebox.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceboxMockMvc.perform(post("/api/sourceboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourcebox)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sourcebox> sourceboxList = sourceboxRepository.findAll();
        assertThat(sourceboxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSourceboxes() throws Exception {
        // Initialize the database
        sourceboxRepository.saveAndFlush(sourcebox);

        // Get all the sourceboxList
        restSourceboxMockMvc.perform(get("/api/sourceboxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourcebox.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceboxname").value(hasItem(DEFAULT_SOURCEBOXNAME.toString())))
            .andExpect(jsonPath("$.[*].createduser").value(hasItem(DEFAULT_CREATEDUSER.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())));
    }

    @Test
    @Transactional
    public void getSourcebox() throws Exception {
        // Initialize the database
        sourceboxRepository.saveAndFlush(sourcebox);

        // Get the sourcebox
        restSourceboxMockMvc.perform(get("/api/sourceboxes/{id}", sourcebox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sourcebox.getId().intValue()))
            .andExpect(jsonPath("$.sourceboxname").value(DEFAULT_SOURCEBOXNAME.toString()))
            .andExpect(jsonPath("$.createduser").value(DEFAULT_CREATEDUSER.toString()))
            .andExpect(jsonPath("$.createddate").value(DEFAULT_CREATEDDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSourcebox() throws Exception {
        // Get the sourcebox
        restSourceboxMockMvc.perform(get("/api/sourceboxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSourcebox() throws Exception {
        // Initialize the database
        sourceboxRepository.saveAndFlush(sourcebox);
        sourceboxSearchRepository.save(sourcebox);
        int databaseSizeBeforeUpdate = sourceboxRepository.findAll().size();

        // Update the sourcebox
        Sourcebox updatedSourcebox = sourceboxRepository.findOne(sourcebox.getId());
        updatedSourcebox
            .sourceboxname(UPDATED_SOURCEBOXNAME)
            .createduser(UPDATED_CREATEDUSER)
            .createddate(UPDATED_CREATEDDATE);

        restSourceboxMockMvc.perform(put("/api/sourceboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSourcebox)))
            .andExpect(status().isOk());

        // Validate the Sourcebox in the database
        List<Sourcebox> sourceboxList = sourceboxRepository.findAll();
        assertThat(sourceboxList).hasSize(databaseSizeBeforeUpdate);
        Sourcebox testSourcebox = sourceboxList.get(sourceboxList.size() - 1);
        assertThat(testSourcebox.getSourceboxname()).isEqualTo(UPDATED_SOURCEBOXNAME);
        assertThat(testSourcebox.getCreateduser()).isEqualTo(UPDATED_CREATEDUSER);
        assertThat(testSourcebox.getCreateddate()).isEqualTo(UPDATED_CREATEDDATE);

        // Validate the Sourcebox in Elasticsearch
        Sourcebox sourceboxEs = sourceboxSearchRepository.findOne(testSourcebox.getId());
        assertThat(sourceboxEs).isEqualToComparingFieldByField(testSourcebox);
    }

    @Test
    @Transactional
    public void updateNonExistingSourcebox() throws Exception {
        int databaseSizeBeforeUpdate = sourceboxRepository.findAll().size();

        // Create the Sourcebox

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSourceboxMockMvc.perform(put("/api/sourceboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sourcebox)))
            .andExpect(status().isCreated());

        // Validate the Sourcebox in the database
        List<Sourcebox> sourceboxList = sourceboxRepository.findAll();
        assertThat(sourceboxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSourcebox() throws Exception {
        // Initialize the database
        sourceboxRepository.saveAndFlush(sourcebox);
        sourceboxSearchRepository.save(sourcebox);
        int databaseSizeBeforeDelete = sourceboxRepository.findAll().size();

        // Get the sourcebox
        restSourceboxMockMvc.perform(delete("/api/sourceboxes/{id}", sourcebox.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean sourceboxExistsInEs = sourceboxSearchRepository.exists(sourcebox.getId());
        assertThat(sourceboxExistsInEs).isFalse();

        // Validate the database is empty
        List<Sourcebox> sourceboxList = sourceboxRepository.findAll();
        assertThat(sourceboxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSourcebox() throws Exception {
        // Initialize the database
        sourceboxRepository.saveAndFlush(sourcebox);
        sourceboxSearchRepository.save(sourcebox);

        // Search the sourcebox
        restSourceboxMockMvc.perform(get("/api/_search/sourceboxes?query=id:" + sourcebox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sourcebox.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceboxname").value(hasItem(DEFAULT_SOURCEBOXNAME.toString())))
            .andExpect(jsonPath("$.[*].createduser").value(hasItem(DEFAULT_CREATEDUSER.toString())))
            .andExpect(jsonPath("$.[*].createddate").value(hasItem(DEFAULT_CREATEDDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sourcebox.class);
        Sourcebox sourcebox1 = new Sourcebox();
        sourcebox1.setId(1L);
        Sourcebox sourcebox2 = new Sourcebox();
        sourcebox2.setId(sourcebox1.getId());
        assertThat(sourcebox1).isEqualTo(sourcebox2);
        sourcebox2.setId(2L);
        assertThat(sourcebox1).isNotEqualTo(sourcebox2);
        sourcebox1.setId(null);
        assertThat(sourcebox1).isNotEqualTo(sourcebox2);
    }
}
