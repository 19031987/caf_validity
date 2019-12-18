package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.Secondsegregation;
import com.caf.valididty.repository.SecondsegregationRepository;
import com.caf.valididty.repository.search.SecondsegregationSearchRepository;
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
 * Test class for the SecondsegregationResource REST controller.
 *
 * @see SecondsegregationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class SecondsegregationResourceIntTest {

    private static final String DEFAULT_BOXNAME = "AAAAAAAAAA";
    private static final String UPDATED_BOXNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAFCODE = "AAAAAAAAAA";
    private static final String UPDATED_CAFCODE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTLEVELUSER = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTLEVELUSER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEGREGATEDCOUNT = 1;
    private static final Integer UPDATED_SEGREGATEDCOUNT = 2;

    private static final Integer DEFAULT_WRONGSEGREGATEDCOUNT = 1;
    private static final Integer UPDATED_WRONGSEGREGATEDCOUNT = 2;

    private static final Integer DEFAULT_NOTSEGREGATEDCOUNT = 1;
    private static final Integer UPDATED_NOTSEGREGATEDCOUNT = 2;

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_USERDATE = Instant.now();
    private static final Instant UPDATED_USERDATE = Instant.now();

    @Autowired
    private SecondsegregationRepository secondsegregationRepository;

    @Autowired
    private SecondsegregationSearchRepository secondsegregationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSecondsegregationMockMvc;

    private Secondsegregation secondsegregation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SecondsegregationResource secondsegregationResource = new SecondsegregationResource(secondsegregationRepository, secondsegregationSearchRepository);
        this.restSecondsegregationMockMvc = MockMvcBuilders.standaloneSetup(secondsegregationResource)
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
    public static Secondsegregation createEntity(EntityManager em) {
        Secondsegregation secondsegregation = new Secondsegregation()
            .boxname(DEFAULT_BOXNAME)
            .cafcode(DEFAULT_CAFCODE)
            .firstleveluser(DEFAULT_FIRSTLEVELUSER)
            .segregatedcount(DEFAULT_SEGREGATEDCOUNT)
            .wrongsegregatedcount(DEFAULT_WRONGSEGREGATEDCOUNT)
            .notsegregatedcount(DEFAULT_NOTSEGREGATEDCOUNT)
            .user(DEFAULT_USER)
            .userdate(DEFAULT_USERDATE);
        return secondsegregation;
    }

    @Before
    public void initTest() {
        secondsegregationSearchRepository.deleteAll();
        secondsegregation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecondsegregation() throws Exception {
        int databaseSizeBeforeCreate = secondsegregationRepository.findAll().size();

        // Create the Secondsegregation
        restSecondsegregationMockMvc.perform(post("/api/secondsegregations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondsegregation)))
            .andExpect(status().isCreated());

        // Validate the Secondsegregation in the database
        List<Secondsegregation> secondsegregationList = secondsegregationRepository.findAll();
        assertThat(secondsegregationList).hasSize(databaseSizeBeforeCreate + 1);
        Secondsegregation testSecondsegregation = secondsegregationList.get(secondsegregationList.size() - 1);
        assertThat(testSecondsegregation.getBoxname()).isEqualTo(DEFAULT_BOXNAME);
        assertThat(testSecondsegregation.getCafcode()).isEqualTo(DEFAULT_CAFCODE);
        assertThat(testSecondsegregation.getFirstleveluser()).isEqualTo(DEFAULT_FIRSTLEVELUSER);
        assertThat(testSecondsegregation.getSegregatedcount()).isEqualTo(DEFAULT_SEGREGATEDCOUNT);
        assertThat(testSecondsegregation.getWrongsegregatedcount()).isEqualTo(DEFAULT_WRONGSEGREGATEDCOUNT);
        assertThat(testSecondsegregation.getNotsegregatedcount()).isEqualTo(DEFAULT_NOTSEGREGATEDCOUNT);
        assertThat(testSecondsegregation.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testSecondsegregation.getUserdate()).isEqualTo(DEFAULT_USERDATE);

        // Validate the Secondsegregation in Elasticsearch
        Secondsegregation secondsegregationEs = secondsegregationSearchRepository.findOne(testSecondsegregation.getId());
        assertThat(secondsegregationEs).isEqualToComparingFieldByField(testSecondsegregation);
    }

    @Test
    @Transactional
    public void createSecondsegregationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secondsegregationRepository.findAll().size();

        // Create the Secondsegregation with an existing ID
        secondsegregation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecondsegregationMockMvc.perform(post("/api/secondsegregations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondsegregation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Secondsegregation> secondsegregationList = secondsegregationRepository.findAll();
        assertThat(secondsegregationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSecondsegregations() throws Exception {
        // Initialize the database
        secondsegregationRepository.saveAndFlush(secondsegregation);

        // Get all the secondsegregationList
        restSecondsegregationMockMvc.perform(get("/api/secondsegregations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secondsegregation.getId().intValue())))
            .andExpect(jsonPath("$.[*].boxname").value(hasItem(DEFAULT_BOXNAME.toString())))
            .andExpect(jsonPath("$.[*].cafcode").value(hasItem(DEFAULT_CAFCODE.toString())))
            .andExpect(jsonPath("$.[*].firstleveluser").value(hasItem(DEFAULT_FIRSTLEVELUSER.toString())))
            .andExpect(jsonPath("$.[*].segregatedcount").value(hasItem(DEFAULT_SEGREGATEDCOUNT)))
            .andExpect(jsonPath("$.[*].wrongsegregatedcount").value(hasItem(DEFAULT_WRONGSEGREGATEDCOUNT)))
            .andExpect(jsonPath("$.[*].notsegregatedcount").value(hasItem(DEFAULT_NOTSEGREGATEDCOUNT)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(DEFAULT_USERDATE.toString())));
    }

    @Test
    @Transactional
    public void getSecondsegregation() throws Exception {
        // Initialize the database
        secondsegregationRepository.saveAndFlush(secondsegregation);

        // Get the secondsegregation
        restSecondsegregationMockMvc.perform(get("/api/secondsegregations/{id}", secondsegregation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(secondsegregation.getId().intValue()))
            .andExpect(jsonPath("$.boxname").value(DEFAULT_BOXNAME.toString()))
            .andExpect(jsonPath("$.cafcode").value(DEFAULT_CAFCODE.toString()))
            .andExpect(jsonPath("$.firstleveluser").value(DEFAULT_FIRSTLEVELUSER.toString()))
            .andExpect(jsonPath("$.segregatedcount").value(DEFAULT_SEGREGATEDCOUNT))
            .andExpect(jsonPath("$.wrongsegregatedcount").value(DEFAULT_WRONGSEGREGATEDCOUNT))
            .andExpect(jsonPath("$.notsegregatedcount").value(DEFAULT_NOTSEGREGATEDCOUNT))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.userdate").value(DEFAULT_USERDATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecondsegregation() throws Exception {
        // Get the secondsegregation
        restSecondsegregationMockMvc.perform(get("/api/secondsegregations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecondsegregation() throws Exception {
        // Initialize the database
        secondsegregationRepository.saveAndFlush(secondsegregation);
        secondsegregationSearchRepository.save(secondsegregation);
        int databaseSizeBeforeUpdate = secondsegregationRepository.findAll().size();

        // Update the secondsegregation
        Secondsegregation updatedSecondsegregation = secondsegregationRepository.findOne(secondsegregation.getId());
        updatedSecondsegregation
            .boxname(UPDATED_BOXNAME)
            .cafcode(UPDATED_CAFCODE)
            .firstleveluser(UPDATED_FIRSTLEVELUSER)
            .segregatedcount(UPDATED_SEGREGATEDCOUNT)
            .wrongsegregatedcount(UPDATED_WRONGSEGREGATEDCOUNT)
            .notsegregatedcount(UPDATED_NOTSEGREGATEDCOUNT)
            .user(UPDATED_USER)
            .userdate(UPDATED_USERDATE);

        restSecondsegregationMockMvc.perform(put("/api/secondsegregations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSecondsegregation)))
            .andExpect(status().isOk());

        // Validate the Secondsegregation in the database
        List<Secondsegregation> secondsegregationList = secondsegregationRepository.findAll();
        assertThat(secondsegregationList).hasSize(databaseSizeBeforeUpdate);
        Secondsegregation testSecondsegregation = secondsegregationList.get(secondsegregationList.size() - 1);
        assertThat(testSecondsegregation.getBoxname()).isEqualTo(UPDATED_BOXNAME);
        assertThat(testSecondsegregation.getCafcode()).isEqualTo(UPDATED_CAFCODE);
        assertThat(testSecondsegregation.getFirstleveluser()).isEqualTo(UPDATED_FIRSTLEVELUSER);
        assertThat(testSecondsegregation.getSegregatedcount()).isEqualTo(UPDATED_SEGREGATEDCOUNT);
        assertThat(testSecondsegregation.getWrongsegregatedcount()).isEqualTo(UPDATED_WRONGSEGREGATEDCOUNT);
        assertThat(testSecondsegregation.getNotsegregatedcount()).isEqualTo(UPDATED_NOTSEGREGATEDCOUNT);
        assertThat(testSecondsegregation.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testSecondsegregation.getUserdate()).isEqualTo(UPDATED_USERDATE);

        // Validate the Secondsegregation in Elasticsearch
        Secondsegregation secondsegregationEs = secondsegregationSearchRepository.findOne(testSecondsegregation.getId());
        assertThat(secondsegregationEs).isEqualToComparingFieldByField(testSecondsegregation);
    }

    @Test
    @Transactional
    public void updateNonExistingSecondsegregation() throws Exception {
        int databaseSizeBeforeUpdate = secondsegregationRepository.findAll().size();

        // Create the Secondsegregation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSecondsegregationMockMvc.perform(put("/api/secondsegregations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secondsegregation)))
            .andExpect(status().isCreated());

        // Validate the Secondsegregation in the database
        List<Secondsegregation> secondsegregationList = secondsegregationRepository.findAll();
        assertThat(secondsegregationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSecondsegregation() throws Exception {
        // Initialize the database
        secondsegregationRepository.saveAndFlush(secondsegregation);
        secondsegregationSearchRepository.save(secondsegregation);
        int databaseSizeBeforeDelete = secondsegregationRepository.findAll().size();

        // Get the secondsegregation
        restSecondsegregationMockMvc.perform(delete("/api/secondsegregations/{id}", secondsegregation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean secondsegregationExistsInEs = secondsegregationSearchRepository.exists(secondsegregation.getId());
        assertThat(secondsegregationExistsInEs).isFalse();

        // Validate the database is empty
        List<Secondsegregation> secondsegregationList = secondsegregationRepository.findAll();
        assertThat(secondsegregationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSecondsegregation() throws Exception {
        // Initialize the database
        secondsegregationRepository.saveAndFlush(secondsegregation);
        secondsegregationSearchRepository.save(secondsegregation);

        // Search the secondsegregation
        restSecondsegregationMockMvc.perform(get("/api/_search/secondsegregations?query=id:" + secondsegregation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secondsegregation.getId().intValue())))
            .andExpect(jsonPath("$.[*].boxname").value(hasItem(DEFAULT_BOXNAME.toString())))
            .andExpect(jsonPath("$.[*].cafcode").value(hasItem(DEFAULT_CAFCODE.toString())))
            .andExpect(jsonPath("$.[*].firstleveluser").value(hasItem(DEFAULT_FIRSTLEVELUSER.toString())))
            .andExpect(jsonPath("$.[*].segregatedcount").value(hasItem(DEFAULT_SEGREGATEDCOUNT)))
            .andExpect(jsonPath("$.[*].wrongsegregatedcount").value(hasItem(DEFAULT_WRONGSEGREGATEDCOUNT)))
            .andExpect(jsonPath("$.[*].notsegregatedcount").value(hasItem(DEFAULT_NOTSEGREGATEDCOUNT)))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(DEFAULT_USERDATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Secondsegregation.class);
        Secondsegregation secondsegregation1 = new Secondsegregation();
        secondsegregation1.setId(1L);
        Secondsegregation secondsegregation2 = new Secondsegregation();
        secondsegregation2.setId(secondsegregation1.getId());
        assertThat(secondsegregation1).isEqualTo(secondsegregation2);
        secondsegregation2.setId(2L);
        assertThat(secondsegregation1).isNotEqualTo(secondsegregation2);
        secondsegregation1.setId(null);
        assertThat(secondsegregation1).isNotEqualTo(secondsegregation2);
    }
}
