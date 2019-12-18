package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.MobileValidation;
import com.caf.valididty.repository.MobileValidationRepository;
import com.caf.valididty.repository.search.MobileValidationSearchRepository;
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
 * Test class for the MobileValidationResource REST controller.
 *
 * @see MobileValidationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class MobileValidationResourceIntTest {

    private static final Long DEFAULT_MOBILENUMBER = 1L;
    private static final Long UPDATED_MOBILENUMBER = 2L;

    private static final String DEFAULT_ACTIVATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_USER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_USER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ISSELECTED = false;
    private static final Boolean UPDATED_ISSELECTED = true;

    private static final String DEFAULT_CATEGORY_1 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_2 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_3 = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CATERGORY_4 = "AAAAAAAAAA";
    private static final String UPDATED_CATERGORY_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CATERGORY_5 = "AAAAAAAAAA";
    private static final String UPDATED_CATERGORY_5 = "BBBBBBBBBB";

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

    private static final String DEFAULT_SOURCEBOX = "AAAAAAAAAA";
    private static final String UPDATED_SOURCEBOX = "BBBBBBBBBB";

    @Autowired
    private MobileValidationRepository mobileValidationRepository;

    @Autowired
    private MobileValidationSearchRepository mobileValidationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMobileValidationMockMvc;

    private MobileValidation mobileValidation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MobileValidationResource mobileValidationResource = new MobileValidationResource(mobileValidationRepository, mobileValidationSearchRepository);
        this.restMobileValidationMockMvc = MockMvcBuilders.standaloneSetup(mobileValidationResource)
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
    public static MobileValidation createEntity(EntityManager em) {
        MobileValidation mobileValidation = new MobileValidation()
            .mobilenumber(DEFAULT_MOBILENUMBER)
            .activationDate(DEFAULT_ACTIVATION_DATE)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .colorCode(DEFAULT_COLOR_CODE)
            .user(DEFAULT_USER)
          //  .userDate(DEFAULT_USER_DATE)
            .isselected(DEFAULT_ISSELECTED)
            .category1(DEFAULT_CATEGORY_1)
            .category2(DEFAULT_CATEGORY_2)
            .category3(DEFAULT_CATEGORY_3)
            .catergory4(DEFAULT_CATERGORY_4)
            .catergory5(DEFAULT_CATERGORY_5)
            .countCategory1(DEFAULT_COUNT_CATEGORY_1)
            .countCategory2(DEFAULT_COUNT_CATEGORY_2)
            .countCategory3(DEFAULT_COUNT_CATEGORY_3)
            .countCategory4(DEFAULT_COUNT_CATEGORY_4)
            .countCategory5(DEFAULT_COUNT_CATEGORY_5)
            .sourcebox(DEFAULT_SOURCEBOX);
        return mobileValidation;
    }

    @Before
    public void initTest() {
        mobileValidationSearchRepository.deleteAll();
        mobileValidation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMobileValidation() throws Exception {
        int databaseSizeBeforeCreate = mobileValidationRepository.findAll().size();

        // Create the MobileValidation
        restMobileValidationMockMvc.perform(post("/api/mobile-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mobileValidation)))
            .andExpect(status().isCreated());

        // Validate the MobileValidation in the database
        List<MobileValidation> mobileValidationList = mobileValidationRepository.findAll();
        assertThat(mobileValidationList).hasSize(databaseSizeBeforeCreate + 1);
        MobileValidation testMobileValidation = mobileValidationList.get(mobileValidationList.size() - 1);
        assertThat(testMobileValidation.getMobilenumber()).isEqualTo(DEFAULT_MOBILENUMBER);
        assertThat(testMobileValidation.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testMobileValidation.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testMobileValidation.getColorCode()).isEqualTo(DEFAULT_COLOR_CODE);
        assertThat(testMobileValidation.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testMobileValidation.getUserDate()).isEqualTo(DEFAULT_USER_DATE);
        assertThat(testMobileValidation.isIsselected()).isEqualTo(DEFAULT_ISSELECTED);
        assertThat(testMobileValidation.getCategory1()).isEqualTo(DEFAULT_CATEGORY_1);
        assertThat(testMobileValidation.getCategory2()).isEqualTo(DEFAULT_CATEGORY_2);
        assertThat(testMobileValidation.getCategory3()).isEqualTo(DEFAULT_CATEGORY_3);
        assertThat(testMobileValidation.getCatergory4()).isEqualTo(DEFAULT_CATERGORY_4);
        assertThat(testMobileValidation.getCatergory5()).isEqualTo(DEFAULT_CATERGORY_5);
        assertThat(testMobileValidation.getCountCategory1()).isEqualTo(DEFAULT_COUNT_CATEGORY_1);
        assertThat(testMobileValidation.getCountCategory2()).isEqualTo(DEFAULT_COUNT_CATEGORY_2);
        assertThat(testMobileValidation.getCountCategory3()).isEqualTo(DEFAULT_COUNT_CATEGORY_3);
        assertThat(testMobileValidation.getCountCategory4()).isEqualTo(DEFAULT_COUNT_CATEGORY_4);
        assertThat(testMobileValidation.getCountCategory5()).isEqualTo(DEFAULT_COUNT_CATEGORY_5);
        assertThat(testMobileValidation.getSourcebox()).isEqualTo(DEFAULT_SOURCEBOX);

        // Validate the MobileValidation in Elasticsearch
        MobileValidation mobileValidationEs = mobileValidationSearchRepository.findOne(testMobileValidation.getId());
        assertThat(mobileValidationEs).isEqualToComparingFieldByField(testMobileValidation);
    }

    @Test
    @Transactional
    public void createMobileValidationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mobileValidationRepository.findAll().size();

        // Create the MobileValidation with an existing ID
        mobileValidation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMobileValidationMockMvc.perform(post("/api/mobile-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mobileValidation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MobileValidation> mobileValidationList = mobileValidationRepository.findAll();
        assertThat(mobileValidationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMobileValidations() throws Exception {
        // Initialize the database
        mobileValidationRepository.saveAndFlush(mobileValidation);

        // Get all the mobileValidationList
        restMobileValidationMockMvc.perform(get("/api/mobile-validations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobileValidation.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.intValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].colorCode").value(hasItem(DEFAULT_COLOR_CODE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userDate").value(hasItem(DEFAULT_USER_DATE.toString())))
            .andExpect(jsonPath("$.[*].isselected").value(hasItem(DEFAULT_ISSELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].category1").value(hasItem(DEFAULT_CATEGORY_1.toString())))
            .andExpect(jsonPath("$.[*].category2").value(hasItem(DEFAULT_CATEGORY_2.toString())))
            .andExpect(jsonPath("$.[*].category3").value(hasItem(DEFAULT_CATEGORY_3.toString())))
            .andExpect(jsonPath("$.[*].catergory4").value(hasItem(DEFAULT_CATERGORY_4.toString())))
            .andExpect(jsonPath("$.[*].catergory5").value(hasItem(DEFAULT_CATERGORY_5.toString())))
            .andExpect(jsonPath("$.[*].countCategory1").value(hasItem(DEFAULT_COUNT_CATEGORY_1)))
            .andExpect(jsonPath("$.[*].countCategory2").value(hasItem(DEFAULT_COUNT_CATEGORY_2)))
            .andExpect(jsonPath("$.[*].countCategory3").value(hasItem(DEFAULT_COUNT_CATEGORY_3)))
            .andExpect(jsonPath("$.[*].countCategory4").value(hasItem(DEFAULT_COUNT_CATEGORY_4)))
            .andExpect(jsonPath("$.[*].countCategory5").value(hasItem(DEFAULT_COUNT_CATEGORY_5)))
            .andExpect(jsonPath("$.[*].sourcebox").value(hasItem(DEFAULT_SOURCEBOX.toString())));
    }

    @Test
    @Transactional
    public void getMobileValidation() throws Exception {
        // Initialize the database
        mobileValidationRepository.saveAndFlush(mobileValidation);

        // Get the mobileValidation
        restMobileValidationMockMvc.perform(get("/api/mobile-validations/{id}", mobileValidation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mobileValidation.getId().intValue()))
            .andExpect(jsonPath("$.mobilenumber").value(DEFAULT_MOBILENUMBER.intValue()))
            .andExpect(jsonPath("$.activationDate").value(DEFAULT_ACTIVATION_DATE.toString()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME.toString()))
            .andExpect(jsonPath("$.colorCode").value(DEFAULT_COLOR_CODE.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.userDate").value(DEFAULT_USER_DATE.toString()))
            .andExpect(jsonPath("$.isselected").value(DEFAULT_ISSELECTED.booleanValue()))
            .andExpect(jsonPath("$.category1").value(DEFAULT_CATEGORY_1.toString()))
            .andExpect(jsonPath("$.category2").value(DEFAULT_CATEGORY_2.toString()))
            .andExpect(jsonPath("$.category3").value(DEFAULT_CATEGORY_3.toString()))
            .andExpect(jsonPath("$.catergory4").value(DEFAULT_CATERGORY_4.toString()))
            .andExpect(jsonPath("$.catergory5").value(DEFAULT_CATERGORY_5.toString()))
            .andExpect(jsonPath("$.countCategory1").value(DEFAULT_COUNT_CATEGORY_1))
            .andExpect(jsonPath("$.countCategory2").value(DEFAULT_COUNT_CATEGORY_2))
            .andExpect(jsonPath("$.countCategory3").value(DEFAULT_COUNT_CATEGORY_3))
            .andExpect(jsonPath("$.countCategory4").value(DEFAULT_COUNT_CATEGORY_4))
            .andExpect(jsonPath("$.countCategory5").value(DEFAULT_COUNT_CATEGORY_5))
            .andExpect(jsonPath("$.sourcebox").value(DEFAULT_SOURCEBOX.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMobileValidation() throws Exception {
        // Get the mobileValidation
        restMobileValidationMockMvc.perform(get("/api/mobile-validations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMobileValidation() throws Exception {
        // Initialize the database
        mobileValidationRepository.saveAndFlush(mobileValidation);
        mobileValidationSearchRepository.save(mobileValidation);
        int databaseSizeBeforeUpdate = mobileValidationRepository.findAll().size();

        // Update the mobileValidation
        MobileValidation updatedMobileValidation = mobileValidationRepository.findOne(mobileValidation.getId());
        updatedMobileValidation
            .mobilenumber(UPDATED_MOBILENUMBER)
            .activationDate(UPDATED_ACTIVATION_DATE)
            .customerName(UPDATED_CUSTOMER_NAME)
            .colorCode(UPDATED_COLOR_CODE)
            .user(UPDATED_USER)
          //  .userDate(UPDATED_USER_DATE)
            .isselected(UPDATED_ISSELECTED)
            .category1(UPDATED_CATEGORY_1)
            .category2(UPDATED_CATEGORY_2)
            .category3(UPDATED_CATEGORY_3)
            .catergory4(UPDATED_CATERGORY_4)
            .catergory5(UPDATED_CATERGORY_5)
            .countCategory1(UPDATED_COUNT_CATEGORY_1)
            .countCategory2(UPDATED_COUNT_CATEGORY_2)
            .countCategory3(UPDATED_COUNT_CATEGORY_3)
            .countCategory4(UPDATED_COUNT_CATEGORY_4)
            .countCategory5(UPDATED_COUNT_CATEGORY_5)
            .sourcebox(UPDATED_SOURCEBOX);

        restMobileValidationMockMvc.perform(put("/api/mobile-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMobileValidation)))
            .andExpect(status().isOk());

        // Validate the MobileValidation in the database
        List<MobileValidation> mobileValidationList = mobileValidationRepository.findAll();
        assertThat(mobileValidationList).hasSize(databaseSizeBeforeUpdate);
        MobileValidation testMobileValidation = mobileValidationList.get(mobileValidationList.size() - 1);
        assertThat(testMobileValidation.getMobilenumber()).isEqualTo(UPDATED_MOBILENUMBER);
        assertThat(testMobileValidation.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testMobileValidation.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testMobileValidation.getColorCode()).isEqualTo(UPDATED_COLOR_CODE);
        assertThat(testMobileValidation.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testMobileValidation.getUserDate()).isEqualTo(UPDATED_USER_DATE);
        assertThat(testMobileValidation.isIsselected()).isEqualTo(UPDATED_ISSELECTED);
        assertThat(testMobileValidation.getCategory1()).isEqualTo(UPDATED_CATEGORY_1);
        assertThat(testMobileValidation.getCategory2()).isEqualTo(UPDATED_CATEGORY_2);
        assertThat(testMobileValidation.getCategory3()).isEqualTo(UPDATED_CATEGORY_3);
        assertThat(testMobileValidation.getCatergory4()).isEqualTo(UPDATED_CATERGORY_4);
        assertThat(testMobileValidation.getCatergory5()).isEqualTo(UPDATED_CATERGORY_5);
        assertThat(testMobileValidation.getCountCategory1()).isEqualTo(UPDATED_COUNT_CATEGORY_1);
        assertThat(testMobileValidation.getCountCategory2()).isEqualTo(UPDATED_COUNT_CATEGORY_2);
        assertThat(testMobileValidation.getCountCategory3()).isEqualTo(UPDATED_COUNT_CATEGORY_3);
        assertThat(testMobileValidation.getCountCategory4()).isEqualTo(UPDATED_COUNT_CATEGORY_4);
        assertThat(testMobileValidation.getCountCategory5()).isEqualTo(UPDATED_COUNT_CATEGORY_5);
        assertThat(testMobileValidation.getSourcebox()).isEqualTo(UPDATED_SOURCEBOX);

        // Validate the MobileValidation in Elasticsearch
        MobileValidation mobileValidationEs = mobileValidationSearchRepository.findOne(testMobileValidation.getId());
        assertThat(mobileValidationEs).isEqualToComparingFieldByField(testMobileValidation);
    }

    @Test
    @Transactional
    public void updateNonExistingMobileValidation() throws Exception {
        int databaseSizeBeforeUpdate = mobileValidationRepository.findAll().size();

        // Create the MobileValidation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMobileValidationMockMvc.perform(put("/api/mobile-validations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mobileValidation)))
            .andExpect(status().isCreated());

        // Validate the MobileValidation in the database
        List<MobileValidation> mobileValidationList = mobileValidationRepository.findAll();
        assertThat(mobileValidationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMobileValidation() throws Exception {
        // Initialize the database
        mobileValidationRepository.saveAndFlush(mobileValidation);
        mobileValidationSearchRepository.save(mobileValidation);
        int databaseSizeBeforeDelete = mobileValidationRepository.findAll().size();

        // Get the mobileValidation
        restMobileValidationMockMvc.perform(delete("/api/mobile-validations/{id}", mobileValidation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mobileValidationExistsInEs = mobileValidationSearchRepository.exists(mobileValidation.getId());
        assertThat(mobileValidationExistsInEs).isFalse();

        // Validate the database is empty
        List<MobileValidation> mobileValidationList = mobileValidationRepository.findAll();
        assertThat(mobileValidationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMobileValidation() throws Exception {
        // Initialize the database
        mobileValidationRepository.saveAndFlush(mobileValidation);
        mobileValidationSearchRepository.save(mobileValidation);

        // Search the mobileValidation
        restMobileValidationMockMvc.perform(get("/api/_search/mobile-validations?query=id:" + mobileValidation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobileValidation.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobilenumber").value(hasItem(DEFAULT_MOBILENUMBER.intValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME.toString())))
            .andExpect(jsonPath("$.[*].colorCode").value(hasItem(DEFAULT_COLOR_CODE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].userDate").value(hasItem(DEFAULT_USER_DATE.toString())))
            .andExpect(jsonPath("$.[*].isselected").value(hasItem(DEFAULT_ISSELECTED.booleanValue())))
            .andExpect(jsonPath("$.[*].category1").value(hasItem(DEFAULT_CATEGORY_1.toString())))
            .andExpect(jsonPath("$.[*].category2").value(hasItem(DEFAULT_CATEGORY_2.toString())))
            .andExpect(jsonPath("$.[*].category3").value(hasItem(DEFAULT_CATEGORY_3.toString())))
            .andExpect(jsonPath("$.[*].catergory4").value(hasItem(DEFAULT_CATERGORY_4.toString())))
            .andExpect(jsonPath("$.[*].catergory5").value(hasItem(DEFAULT_CATERGORY_5.toString())))
            .andExpect(jsonPath("$.[*].countCategory1").value(hasItem(DEFAULT_COUNT_CATEGORY_1)))
            .andExpect(jsonPath("$.[*].countCategory2").value(hasItem(DEFAULT_COUNT_CATEGORY_2)))
            .andExpect(jsonPath("$.[*].countCategory3").value(hasItem(DEFAULT_COUNT_CATEGORY_3)))
            .andExpect(jsonPath("$.[*].countCategory4").value(hasItem(DEFAULT_COUNT_CATEGORY_4)))
            .andExpect(jsonPath("$.[*].countCategory5").value(hasItem(DEFAULT_COUNT_CATEGORY_5)))
            .andExpect(jsonPath("$.[*].sourcebox").value(hasItem(DEFAULT_SOURCEBOX.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobileValidation.class);
        MobileValidation mobileValidation1 = new MobileValidation();
        mobileValidation1.setId(1L);
        MobileValidation mobileValidation2 = new MobileValidation();
        mobileValidation2.setId(mobileValidation1.getId());
        assertThat(mobileValidation1).isEqualTo(mobileValidation2);
        mobileValidation2.setId(2L);
        assertThat(mobileValidation1).isNotEqualTo(mobileValidation2);
        mobileValidation1.setId(null);
        assertThat(mobileValidation1).isNotEqualTo(mobileValidation2);
    }
}
