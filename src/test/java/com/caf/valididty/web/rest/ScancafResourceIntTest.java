package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.repository.BoxassignRepository;
import com.caf.valididty.repository.ScancafRepository;
import com.caf.valididty.repository.search.ScancafSearchRepository;
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
 * Test class for the ScancafResource REST controller.
 *
 * @see ScancafResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class ScancafResourceIntTest {

    private static final String DEFAULT_SOURCEBOX = "AAAAAAAAAA";
    private static final String UPDATED_SOURCEBOX = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_1 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_2 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_3 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_4 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_5 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_5 = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT_CATEGORY_1 = 1;
    private static final Integer UPDATED_COUNT_CATEGORY_1 = 2;

    private static final Integer DEFAULT_COUNT_CATEGORY_2 = 1;
    private static final Integer UPDATED_COUNT_CATEGORY_2 = 2;

    private static final Integer DEFAULT_COUNT_CATEGORY_3 = 1;
    private static final Integer UPDATED_COUNT_CATEGORY_3 = 2;

    private static final Integer DEFAULT_COUNT_CATEGORY_4 = 1;
    private static final Integer UPDATED_COUNT_CATEGORY_4 = 2;

    private static final Integer DEFAULT_COUNT_CATEGORY_5 = 1;
    private static final Integer UPDATED_COUNT_CATEGORY_5 = 2;

    private static final String DEFAULT_CAFBARCODE = "AAAAAAAAAA";
    private static final String UPDATED_CAFBARCODE = "BBBBBBBBBB";

    private static final String DEFAULT_COLORCODE = "AAAAAAAAAA";
    private static final String UPDATED_COLORCODE = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_USERDATE = Instant.now();
    private static final Instant UPDATED_USERDATE = Instant.now();

    private static final String DEFAULT_BOXSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_BOXSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCEBOXSTAUS = "AAAAAAAAAA";
    private static final String UPDATED_SOURCEBOXSTAUS = "BBBBBBBBBB";

    private static final Long DEFAULT_MOBILENUMBER = 1L;
    private static final Long UPDATED_MOBILENUMBER = 2L;

    private static final String DEFAULT_CENTRALBARCODE = "AAAAAAAAAA";
    private static final String UPDATED_CENTRALBARCODE = "BBBBBBBBBB";

    private static final String DEFAULT_CAFTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CAFTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_RV = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_RV = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT_CATEGORY_RV = 1;
    private static final Integer UPDATED_COUNT_CATEGORY_RV = 2;

    private static final String DEFAULT_CATEGORY_NA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NA = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT_CATEGORY_NA = 1;
    private static final Integer UPDATED_COUNT_CATEGORY_NA = 2;

    @Autowired
    private ScancafRepository scancafRepository;

    @Autowired
    private ScancafSearchRepository scancafSearchRepository;
    
    @Autowired
    private BoxassignRepository boxassignRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScancafMockMvc;

    private Scancaf scancaf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScancafResource scancafResource = new ScancafResource(scancafRepository, scancafSearchRepository,boxassignRepository);
        this.restScancafMockMvc = MockMvcBuilders.standaloneSetup(scancafResource)
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
    public static Scancaf createEntity(EntityManager em) {
        Scancaf scancaf = new Scancaf()
            .sourcebox(DEFAULT_SOURCEBOX)
            .category(DEFAULT_CATEGORY)
            .category1(DEFAULT_CATEGORY_1)
            .category2(DEFAULT_CATEGORY_2)
            .category3(DEFAULT_CATEGORY_3)
            .category4(DEFAULT_CATEGORY_4)
            .category5(DEFAULT_CATEGORY_5)
            .countCategory1(DEFAULT_COUNT_CATEGORY_1)
            .countCategory2(DEFAULT_COUNT_CATEGORY_2)
            .countCategory3(DEFAULT_COUNT_CATEGORY_3)
            .countCategory4(DEFAULT_COUNT_CATEGORY_4)
            .countCategory5(DEFAULT_COUNT_CATEGORY_5)
            .cafbarcode(DEFAULT_CAFBARCODE)
            .colorcode(DEFAULT_COLORCODE)
            .user(DEFAULT_USER)
            .userdate(DEFAULT_USERDATE)
            .boxstatus(DEFAULT_BOXSTATUS)
            .sourceboxstaus(DEFAULT_SOURCEBOXSTAUS)
            .mobilenumber(DEFAULT_MOBILENUMBER)
            .centralbarcode(DEFAULT_CENTRALBARCODE)
            .caftype(DEFAULT_CAFTYPE)
            .categoryRv(DEFAULT_CATEGORY_RV)
            .countCategoryRv(DEFAULT_COUNT_CATEGORY_RV)
            .categoryNA(DEFAULT_CATEGORY_NA)
            .countCategoryNA(DEFAULT_COUNT_CATEGORY_NA);
        return scancaf;
    }

    @Before
    public void initTest() {
        scancafSearchRepository.deleteAll();
        scancaf = createEntity(em);
    }

    @Test
    @Transactional
    public void createScancaf() throws Exception {
        int databaseSizeBeforeCreate = scancafRepository.findAll().size();

        // Create the Scancaf
        restScancafMockMvc.perform(post("/api/scancafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scancaf)))
            .andExpect(status().isCreated());

        // Validate the Scancaf in the database
        List<Scancaf> scancafList = scancafRepository.findAll();
        assertThat(scancafList).hasSize(databaseSizeBeforeCreate + 1);
        Scancaf testScancaf = scancafList.get(scancafList.size() - 1);
        assertThat(testScancaf.getSourcebox()).isEqualTo(DEFAULT_SOURCEBOX);
        assertThat(testScancaf.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testScancaf.getCategory1()).isEqualTo(DEFAULT_CATEGORY_1);
        assertThat(testScancaf.getCategory2()).isEqualTo(DEFAULT_CATEGORY_2);
        assertThat(testScancaf.getCategory3()).isEqualTo(DEFAULT_CATEGORY_3);
        assertThat(testScancaf.getCategory4()).isEqualTo(DEFAULT_CATEGORY_4);
        assertThat(testScancaf.getCategory5()).isEqualTo(DEFAULT_CATEGORY_5);
        assertThat(testScancaf.getCountCategory1()).isEqualTo(DEFAULT_COUNT_CATEGORY_1);
        assertThat(testScancaf.getCountCategory2()).isEqualTo(DEFAULT_COUNT_CATEGORY_2);
        assertThat(testScancaf.getCountCategory3()).isEqualTo(DEFAULT_COUNT_CATEGORY_3);
        assertThat(testScancaf.getCountCategory4()).isEqualTo(DEFAULT_COUNT_CATEGORY_4);
        assertThat(testScancaf.getCountCategory5()).isEqualTo(DEFAULT_COUNT_CATEGORY_5);
        assertThat(testScancaf.getCafbarcode()).isEqualTo(DEFAULT_CAFBARCODE);
        assertThat(testScancaf.getColorcode()).isEqualTo(DEFAULT_COLORCODE);
        assertThat(testScancaf.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testScancaf.getUserdate()).isEqualTo(DEFAULT_USERDATE);
        assertThat(testScancaf.getBoxstatus()).isEqualTo(DEFAULT_BOXSTATUS);
        assertThat(testScancaf.getSourceboxstaus()).isEqualTo(DEFAULT_SOURCEBOXSTAUS);
        assertThat(testScancaf.getMobilenumber()).isEqualTo(DEFAULT_MOBILENUMBER);
        assertThat(testScancaf.getCentralbarcode()).isEqualTo(DEFAULT_CENTRALBARCODE);
        assertThat(testScancaf.getCaftype()).isEqualTo(DEFAULT_CAFTYPE);
        assertThat(testScancaf.getCategoryRv()).isEqualTo(DEFAULT_CATEGORY_RV);
        assertThat(testScancaf.getCountCategoryRv()).isEqualTo(DEFAULT_COUNT_CATEGORY_RV);
        assertThat(testScancaf.getCategoryNA()).isEqualTo(DEFAULT_CATEGORY_NA);
        assertThat(testScancaf.getCountCategoryNA()).isEqualTo(DEFAULT_COUNT_CATEGORY_NA);

        // Validate the Scancaf in Elasticsearch
        Scancaf scancafEs = scancafSearchRepository.findOne(testScancaf.getId());
        assertThat(scancafEs).isEqualToComparingFieldByField(testScancaf);
    }

    @Test
    @Transactional
    public void createScancafWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scancafRepository.findAll().size();

        // Create the Scancaf with an existing ID
        scancaf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScancafMockMvc.perform(post("/api/scancafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scancaf)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Scancaf> scancafList = scancafRepository.findAll();
        assertThat(scancafList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllScancafs() throws Exception {
        // Initialize the database
        scancafRepository.saveAndFlush(scancaf);

        // Get all the scancafList
        restScancafMockMvc.perform(get("/api/scancafs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scancaf.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourcebox").value(hasItem(DEFAULT_SOURCEBOX.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].category1").value(hasItem(DEFAULT_CATEGORY_1.toString())))
            .andExpect(jsonPath("$.[*].category2").value(hasItem(DEFAULT_CATEGORY_2.toString())))
            .andExpect(jsonPath("$.[*].category3").value(hasItem(DEFAULT_CATEGORY_3.toString())))
            .andExpect(jsonPath("$.[*].category4").value(hasItem(DEFAULT_CATEGORY_4.toString())))
            .andExpect(jsonPath("$.[*].category5").value(hasItem(DEFAULT_CATEGORY_5.toString())))
            .andExpect(jsonPath("$.[*].countCategory1").value(hasItem(DEFAULT_COUNT_CATEGORY_1)))
            .andExpect(jsonPath("$.[*].countCategory2").value(hasItem(DEFAULT_COUNT_CATEGORY_2)))
            .andExpect(jsonPath("$.[*].countCategory3").value(hasItem(DEFAULT_COUNT_CATEGORY_3)))
            .andExpect(jsonPath("$.[*].countCategory4").value(hasItem(DEFAULT_COUNT_CATEGORY_4)))
            .andExpect(jsonPath("$.[*].countCategory5").value(hasItem(DEFAULT_COUNT_CATEGORY_5)))
            .andExpect(jsonPath("$.[*].cafbarcode").value(hasItem(DEFAULT_CAFBARCODE.toString())))
            .andExpect(jsonPath("$.[*].colorcode").value(hasItem(DEFAULT_COLORCODE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(DEFAULT_USERDATE.toString())))
            .andExpect(jsonPath("$.[*].boxstatus").value(hasItem(DEFAULT_BOXSTATUS.toString())))
            .andExpect(jsonPath("$.[*].sourceboxstaus").value(hasItem(DEFAULT_SOURCEBOXSTAUS.toString())))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.intValue())))
            .andExpect(jsonPath("$.[*].centralbarcode").value(hasItem(DEFAULT_CENTRALBARCODE.toString())))
            .andExpect(jsonPath("$.[*].caftype").value(hasItem(DEFAULT_CAFTYPE.toString())))
            .andExpect(jsonPath("$.[*].categoryRv").value(hasItem(DEFAULT_CATEGORY_RV.toString())))
            .andExpect(jsonPath("$.[*].countCategoryRv").value(hasItem(DEFAULT_COUNT_CATEGORY_RV)))
            .andExpect(jsonPath("$.[*].categoryNA").value(hasItem(DEFAULT_CATEGORY_NA.toString())))
            .andExpect(jsonPath("$.[*].countCategoryNA").value(hasItem(DEFAULT_COUNT_CATEGORY_NA)));
    }

    @Test
    @Transactional
    public void getScancaf() throws Exception {
        // Initialize the database
        scancafRepository.saveAndFlush(scancaf);

        // Get the scancaf
        restScancafMockMvc.perform(get("/api/scancafs/{id}", scancaf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scancaf.getId().intValue()))
            .andExpect(jsonPath("$.sourcebox").value(DEFAULT_SOURCEBOX.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.category1").value(DEFAULT_CATEGORY_1.toString()))
            .andExpect(jsonPath("$.category2").value(DEFAULT_CATEGORY_2.toString()))
            .andExpect(jsonPath("$.category3").value(DEFAULT_CATEGORY_3.toString()))
            .andExpect(jsonPath("$.category4").value(DEFAULT_CATEGORY_4.toString()))
            .andExpect(jsonPath("$.category5").value(DEFAULT_CATEGORY_5.toString()))
            .andExpect(jsonPath("$.countCategory1").value(DEFAULT_COUNT_CATEGORY_1))
            .andExpect(jsonPath("$.countCategory2").value(DEFAULT_COUNT_CATEGORY_2))
            .andExpect(jsonPath("$.countCategory3").value(DEFAULT_COUNT_CATEGORY_3))
            .andExpect(jsonPath("$.countCategory4").value(DEFAULT_COUNT_CATEGORY_4))
            .andExpect(jsonPath("$.countCategory5").value(DEFAULT_COUNT_CATEGORY_5))
            .andExpect(jsonPath("$.cafbarcode").value(DEFAULT_CAFBARCODE.toString()))
            .andExpect(jsonPath("$.colorcode").value(DEFAULT_COLORCODE.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.userdate").value(DEFAULT_USERDATE.toString()))
            .andExpect(jsonPath("$.boxstatus").value(DEFAULT_BOXSTATUS.toString()))
            .andExpect(jsonPath("$.sourceboxstaus").value(DEFAULT_SOURCEBOXSTAUS.toString()))
            .andExpect(jsonPath("$.mobilenumber").value(DEFAULT_MOBILENUMBER.intValue()))
            .andExpect(jsonPath("$.centralbarcode").value(DEFAULT_CENTRALBARCODE.toString()))
            .andExpect(jsonPath("$.caftype").value(DEFAULT_CAFTYPE.toString()))
            .andExpect(jsonPath("$.categoryRv").value(DEFAULT_CATEGORY_RV.toString()))
            .andExpect(jsonPath("$.countCategoryRv").value(DEFAULT_COUNT_CATEGORY_RV))
            .andExpect(jsonPath("$.categoryNA").value(DEFAULT_CATEGORY_NA.toString()))
            .andExpect(jsonPath("$.countCategoryNA").value(DEFAULT_COUNT_CATEGORY_NA));
    }

    @Test
    @Transactional
    public void getNonExistingScancaf() throws Exception {
        // Get the scancaf
        restScancafMockMvc.perform(get("/api/scancafs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScancaf() throws Exception {
        // Initialize the database
        scancafRepository.saveAndFlush(scancaf);
        scancafSearchRepository.save(scancaf);
        int databaseSizeBeforeUpdate = scancafRepository.findAll().size();

        // Update the scancaf
        Scancaf updatedScancaf = scancafRepository.findOne(scancaf.getId());
        updatedScancaf
            .sourcebox(UPDATED_SOURCEBOX)
            .category(UPDATED_CATEGORY)
            .category1(UPDATED_CATEGORY_1)
            .category2(UPDATED_CATEGORY_2)
            .category3(UPDATED_CATEGORY_3)
            .category4(UPDATED_CATEGORY_4)
            .category5(UPDATED_CATEGORY_5)
            .countCategory1(UPDATED_COUNT_CATEGORY_1)
            .countCategory2(UPDATED_COUNT_CATEGORY_2)
            .countCategory3(UPDATED_COUNT_CATEGORY_3)
            .countCategory4(UPDATED_COUNT_CATEGORY_4)
            .countCategory5(UPDATED_COUNT_CATEGORY_5)
            .cafbarcode(UPDATED_CAFBARCODE)
            .colorcode(UPDATED_COLORCODE)
            .user(UPDATED_USER)
            .userdate(UPDATED_USERDATE)
            .boxstatus(UPDATED_BOXSTATUS)
            .sourceboxstaus(UPDATED_SOURCEBOXSTAUS)
            .mobilenumber(UPDATED_MOBILENUMBER)
            .centralbarcode(UPDATED_CENTRALBARCODE)
            .caftype(UPDATED_CAFTYPE)
            .categoryRv(UPDATED_CATEGORY_RV)
            .countCategoryRv(UPDATED_COUNT_CATEGORY_RV)
            .categoryNA(UPDATED_CATEGORY_NA)
            .countCategoryNA(UPDATED_COUNT_CATEGORY_NA);

        restScancafMockMvc.perform(put("/api/scancafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedScancaf)))
            .andExpect(status().isOk());

        // Validate the Scancaf in the database
        List<Scancaf> scancafList = scancafRepository.findAll();
        assertThat(scancafList).hasSize(databaseSizeBeforeUpdate);
        Scancaf testScancaf = scancafList.get(scancafList.size() - 1);
        assertThat(testScancaf.getSourcebox()).isEqualTo(UPDATED_SOURCEBOX);
        assertThat(testScancaf.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testScancaf.getCategory1()).isEqualTo(UPDATED_CATEGORY_1);
        assertThat(testScancaf.getCategory2()).isEqualTo(UPDATED_CATEGORY_2);
        assertThat(testScancaf.getCategory3()).isEqualTo(UPDATED_CATEGORY_3);
        assertThat(testScancaf.getCategory4()).isEqualTo(UPDATED_CATEGORY_4);
        assertThat(testScancaf.getCategory5()).isEqualTo(UPDATED_CATEGORY_5);
        assertThat(testScancaf.getCountCategory1()).isEqualTo(UPDATED_COUNT_CATEGORY_1);
        assertThat(testScancaf.getCountCategory2()).isEqualTo(UPDATED_COUNT_CATEGORY_2);
        assertThat(testScancaf.getCountCategory3()).isEqualTo(UPDATED_COUNT_CATEGORY_3);
        assertThat(testScancaf.getCountCategory4()).isEqualTo(UPDATED_COUNT_CATEGORY_4);
        assertThat(testScancaf.getCountCategory5()).isEqualTo(UPDATED_COUNT_CATEGORY_5);
        assertThat(testScancaf.getCafbarcode()).isEqualTo(UPDATED_CAFBARCODE);
        assertThat(testScancaf.getColorcode()).isEqualTo(UPDATED_COLORCODE);
        assertThat(testScancaf.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testScancaf.getUserdate()).isEqualTo(UPDATED_USERDATE);
        assertThat(testScancaf.getBoxstatus()).isEqualTo(UPDATED_BOXSTATUS);
        assertThat(testScancaf.getSourceboxstaus()).isEqualTo(UPDATED_SOURCEBOXSTAUS);
        assertThat(testScancaf.getMobilenumber()).isEqualTo(UPDATED_MOBILENUMBER);
        assertThat(testScancaf.getCentralbarcode()).isEqualTo(UPDATED_CENTRALBARCODE);
        assertThat(testScancaf.getCaftype()).isEqualTo(UPDATED_CAFTYPE);
        assertThat(testScancaf.getCategoryRv()).isEqualTo(UPDATED_CATEGORY_RV);
        assertThat(testScancaf.getCountCategoryRv()).isEqualTo(UPDATED_COUNT_CATEGORY_RV);
        assertThat(testScancaf.getCategoryNA()).isEqualTo(UPDATED_CATEGORY_NA);
        assertThat(testScancaf.getCountCategoryNA()).isEqualTo(UPDATED_COUNT_CATEGORY_NA);

        // Validate the Scancaf in Elasticsearch
        Scancaf scancafEs = scancafSearchRepository.findOne(testScancaf.getId());
        assertThat(scancafEs).isEqualToComparingFieldByField(testScancaf);
    }

    @Test
    @Transactional
    public void updateNonExistingScancaf() throws Exception {
        int databaseSizeBeforeUpdate = scancafRepository.findAll().size();

        // Create the Scancaf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScancafMockMvc.perform(put("/api/scancafs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scancaf)))
            .andExpect(status().isCreated());

        // Validate the Scancaf in the database
        List<Scancaf> scancafList = scancafRepository.findAll();
        assertThat(scancafList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScancaf() throws Exception {
        // Initialize the database
        scancafRepository.saveAndFlush(scancaf);
        scancafSearchRepository.save(scancaf);
        int databaseSizeBeforeDelete = scancafRepository.findAll().size();

        // Get the scancaf
        restScancafMockMvc.perform(delete("/api/scancafs/{id}", scancaf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean scancafExistsInEs = scancafSearchRepository.exists(scancaf.getId());
        assertThat(scancafExistsInEs).isFalse();

        // Validate the database is empty
        List<Scancaf> scancafList = scancafRepository.findAll();
        assertThat(scancafList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchScancaf() throws Exception {
        // Initialize the database
        scancafRepository.saveAndFlush(scancaf);
        scancafSearchRepository.save(scancaf);

        // Search the scancaf
        restScancafMockMvc.perform(get("/api/_search/scancafs?query=id:" + scancaf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scancaf.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourcebox").value(hasItem(DEFAULT_SOURCEBOX.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].category1").value(hasItem(DEFAULT_CATEGORY_1.toString())))
            .andExpect(jsonPath("$.[*].category2").value(hasItem(DEFAULT_CATEGORY_2.toString())))
            .andExpect(jsonPath("$.[*].category3").value(hasItem(DEFAULT_CATEGORY_3.toString())))
            .andExpect(jsonPath("$.[*].category4").value(hasItem(DEFAULT_CATEGORY_4.toString())))
            .andExpect(jsonPath("$.[*].category5").value(hasItem(DEFAULT_CATEGORY_5.toString())))
            .andExpect(jsonPath("$.[*].countCategory1").value(hasItem(DEFAULT_COUNT_CATEGORY_1)))
            .andExpect(jsonPath("$.[*].countCategory2").value(hasItem(DEFAULT_COUNT_CATEGORY_2)))
            .andExpect(jsonPath("$.[*].countCategory3").value(hasItem(DEFAULT_COUNT_CATEGORY_3)))
            .andExpect(jsonPath("$.[*].countCategory4").value(hasItem(DEFAULT_COUNT_CATEGORY_4)))
            .andExpect(jsonPath("$.[*].countCategory5").value(hasItem(DEFAULT_COUNT_CATEGORY_5)))
            .andExpect(jsonPath("$.[*].cafbarcode").value(hasItem(DEFAULT_CAFBARCODE.toString())))
            .andExpect(jsonPath("$.[*].colorcode").value(hasItem(DEFAULT_COLORCODE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userdate").value(hasItem(DEFAULT_USERDATE.toString())))
            .andExpect(jsonPath("$.[*].boxstatus").value(hasItem(DEFAULT_BOXSTATUS.toString())))
            .andExpect(jsonPath("$.[*].sourceboxstaus").value(hasItem(DEFAULT_SOURCEBOXSTAUS.toString())))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.intValue())))
            .andExpect(jsonPath("$.[*].centralbarcode").value(hasItem(DEFAULT_CENTRALBARCODE.toString())))
            .andExpect(jsonPath("$.[*].caftype").value(hasItem(DEFAULT_CAFTYPE.toString())))
            .andExpect(jsonPath("$.[*].categoryRv").value(hasItem(DEFAULT_CATEGORY_RV.toString())))
            .andExpect(jsonPath("$.[*].countCategoryRv").value(hasItem(DEFAULT_COUNT_CATEGORY_RV)))
            .andExpect(jsonPath("$.[*].categoryNA").value(hasItem(DEFAULT_CATEGORY_NA.toString())))
            .andExpect(jsonPath("$.[*].countCategoryNA").value(hasItem(DEFAULT_COUNT_CATEGORY_NA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scancaf.class);
        Scancaf scancaf1 = new Scancaf();
        scancaf1.setId(1L);
        Scancaf scancaf2 = new Scancaf();
        scancaf2.setId(scancaf1.getId());
        assertThat(scancaf1).isEqualTo(scancaf2);
        scancaf2.setId(2L);
        assertThat(scancaf1).isNotEqualTo(scancaf2);
        scancaf1.setId(null);
        assertThat(scancaf1).isNotEqualTo(scancaf2);
    }
}
