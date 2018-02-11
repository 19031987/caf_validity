package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.Boxassign;
import com.caf.valididty.repository.BoxassignRepository;
import com.caf.valididty.repository.search.BoxassignSearchRepository;
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
 * Test class for the BoxassignResource REST controller.
 *
 * @see BoxassignResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class BoxassignResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_BOXASSIGN = "AAAAAAAAAA";
    private static final String UPDATED_BOXASSIGN = "BBBBBBBBBB";

    private static final String DEFAULT_CHURNTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CHURNTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    @Autowired
    private BoxassignRepository boxassignRepository;

    @Autowired
    private BoxassignSearchRepository boxassignSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBoxassignMockMvc;

    private Boxassign boxassign;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoxassignResource boxassignResource = new BoxassignResource(boxassignRepository, boxassignSearchRepository);
        this.restBoxassignMockMvc = MockMvcBuilders.standaloneSetup(boxassignResource)
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
    public static Boxassign createEntity(EntityManager em) {
        Boxassign boxassign = new Boxassign()
            .user(DEFAULT_USER)
            .boxassign(DEFAULT_BOXASSIGN)
            .churntype(DEFAULT_CHURNTYPE)
            .system(DEFAULT_SYSTEM);
        return boxassign;
    }

    @Before
    public void initTest() {
        boxassignSearchRepository.deleteAll();
        boxassign = createEntity(em);
    }

    @Test
    @Transactional
    public void createBoxassign() throws Exception {
        int databaseSizeBeforeCreate = boxassignRepository.findAll().size();

        // Create the Boxassign
        restBoxassignMockMvc.perform(post("/api/boxassigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxassign)))
            .andExpect(status().isCreated());

        // Validate the Boxassign in the database
        List<Boxassign> boxassignList = boxassignRepository.findAll();
        assertThat(boxassignList).hasSize(databaseSizeBeforeCreate + 1);
        Boxassign testBoxassign = boxassignList.get(boxassignList.size() - 1);
        assertThat(testBoxassign.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testBoxassign.getBoxassign()).isEqualTo(DEFAULT_BOXASSIGN);
        assertThat(testBoxassign.getChurntype()).isEqualTo(DEFAULT_CHURNTYPE);
        assertThat(testBoxassign.getSystem()).isEqualTo(DEFAULT_SYSTEM);

        // Validate the Boxassign in Elasticsearch
        Boxassign boxassignEs = boxassignSearchRepository.findOne(testBoxassign.getId());
        assertThat(boxassignEs).isEqualToComparingFieldByField(testBoxassign);
    }

    @Test
    @Transactional
    public void createBoxassignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxassignRepository.findAll().size();

        // Create the Boxassign with an existing ID
        boxassign.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxassignMockMvc.perform(post("/api/boxassigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxassign)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Boxassign> boxassignList = boxassignRepository.findAll();
        assertThat(boxassignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBoxassigns() throws Exception {
        // Initialize the database
        boxassignRepository.saveAndFlush(boxassign);

        // Get all the boxassignList
        restBoxassignMockMvc.perform(get("/api/boxassigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boxassign.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].boxassign").value(hasItem(DEFAULT_BOXASSIGN.toString())))
            .andExpect(jsonPath("$.[*].churntype").value(hasItem(DEFAULT_CHURNTYPE.toString())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.toString())));
    }

    @Test
    @Transactional
    public void getBoxassign() throws Exception {
        // Initialize the database
        boxassignRepository.saveAndFlush(boxassign);

        // Get the boxassign
        restBoxassignMockMvc.perform(get("/api/boxassigns/{id}", boxassign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(boxassign.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.boxassign").value(DEFAULT_BOXASSIGN.toString()))
            .andExpect(jsonPath("$.churntype").value(DEFAULT_CHURNTYPE.toString()))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBoxassign() throws Exception {
        // Get the boxassign
        restBoxassignMockMvc.perform(get("/api/boxassigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBoxassign() throws Exception {
        // Initialize the database
        boxassignRepository.saveAndFlush(boxassign);
        boxassignSearchRepository.save(boxassign);
        int databaseSizeBeforeUpdate = boxassignRepository.findAll().size();

        // Update the boxassign
        Boxassign updatedBoxassign = boxassignRepository.findOne(boxassign.getId());
        updatedBoxassign
            .user(UPDATED_USER)
            .boxassign(UPDATED_BOXASSIGN)
            .churntype(UPDATED_CHURNTYPE)
            .system(UPDATED_SYSTEM);

        restBoxassignMockMvc.perform(put("/api/boxassigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBoxassign)))
            .andExpect(status().isOk());

        // Validate the Boxassign in the database
        List<Boxassign> boxassignList = boxassignRepository.findAll();
        assertThat(boxassignList).hasSize(databaseSizeBeforeUpdate);
        Boxassign testBoxassign = boxassignList.get(boxassignList.size() - 1);
        assertThat(testBoxassign.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testBoxassign.getBoxassign()).isEqualTo(UPDATED_BOXASSIGN);
        assertThat(testBoxassign.getChurntype()).isEqualTo(UPDATED_CHURNTYPE);
        assertThat(testBoxassign.getSystem()).isEqualTo(UPDATED_SYSTEM);

        // Validate the Boxassign in Elasticsearch
        Boxassign boxassignEs = boxassignSearchRepository.findOne(testBoxassign.getId());
        assertThat(boxassignEs).isEqualToComparingFieldByField(testBoxassign);
    }

    @Test
    @Transactional
    public void updateNonExistingBoxassign() throws Exception {
        int databaseSizeBeforeUpdate = boxassignRepository.findAll().size();

        // Create the Boxassign

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBoxassignMockMvc.perform(put("/api/boxassigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxassign)))
            .andExpect(status().isCreated());

        // Validate the Boxassign in the database
        List<Boxassign> boxassignList = boxassignRepository.findAll();
        assertThat(boxassignList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBoxassign() throws Exception {
        // Initialize the database
        boxassignRepository.saveAndFlush(boxassign);
        boxassignSearchRepository.save(boxassign);
        int databaseSizeBeforeDelete = boxassignRepository.findAll().size();

        // Get the boxassign
        restBoxassignMockMvc.perform(delete("/api/boxassigns/{id}", boxassign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean boxassignExistsInEs = boxassignSearchRepository.exists(boxassign.getId());
        assertThat(boxassignExistsInEs).isFalse();

        // Validate the database is empty
        List<Boxassign> boxassignList = boxassignRepository.findAll();
        assertThat(boxassignList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBoxassign() throws Exception {
        // Initialize the database
        boxassignRepository.saveAndFlush(boxassign);
        boxassignSearchRepository.save(boxassign);

        // Search the boxassign
        restBoxassignMockMvc.perform(get("/api/_search/boxassigns?query=id:" + boxassign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boxassign.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].boxassign").value(hasItem(DEFAULT_BOXASSIGN.toString())))
            .andExpect(jsonPath("$.[*].churntype").value(hasItem(DEFAULT_CHURNTYPE.toString())))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boxassign.class);
        Boxassign boxassign1 = new Boxassign();
        boxassign1.setId(1L);
        Boxassign boxassign2 = new Boxassign();
        boxassign2.setId(boxassign1.getId());
        assertThat(boxassign1).isEqualTo(boxassign2);
        boxassign2.setId(2L);
        assertThat(boxassign1).isNotEqualTo(boxassign2);
        boxassign1.setId(null);
        assertThat(boxassign1).isNotEqualTo(boxassign2);
    }
}
