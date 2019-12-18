package com.caf.valididty.web.rest;

import com.caf.valididty.CafvalidityV2App;

import com.caf.valididty.domain.Userchange;
import com.caf.valididty.repository.UserchangeRepository;
import com.caf.valididty.repository.search.UserchangeSearchRepository;
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
 * Test class for the UserchangeResource REST controller.
 *
 * @see UserchangeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CafvalidityV2App.class)
public class UserchangeResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_CHANGEUSER = "AAAAAAAAAA";
    private static final String UPDATED_CHANGEUSER = "BBBBBBBBBB";

    @Autowired
    private UserchangeRepository userchangeRepository;

    @Autowired
    private UserchangeSearchRepository userchangeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserchangeMockMvc;

    private Userchange userchange;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
       // final UserchangeResource userchangeResource = new UserchangeResource(userchangeRepository, userchangeSearchRepository,null,null);
       /* this.restUserchangeMockMvc = MockMvcBuilders.standaloneSetup(userchangeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();*/
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userchange createEntity(EntityManager em) {
        Userchange userchange = new Userchange()
            .user(DEFAULT_USER)
            .changeuser(DEFAULT_CHANGEUSER);
        return userchange;
    }

    @Before
    public void initTest() {
        userchangeSearchRepository.deleteAll();
        userchange = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserchange() throws Exception {
        int databaseSizeBeforeCreate = userchangeRepository.findAll().size();

        // Create the Userchange
        restUserchangeMockMvc.perform(post("/api/userchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userchange)))
            .andExpect(status().isCreated());

        // Validate the Userchange in the database
        List<Userchange> userchangeList = userchangeRepository.findAll();
        assertThat(userchangeList).hasSize(databaseSizeBeforeCreate + 1);
        Userchange testUserchange = userchangeList.get(userchangeList.size() - 1);
        assertThat(testUserchange.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testUserchange.getChangeuser()).isEqualTo(DEFAULT_CHANGEUSER);

        // Validate the Userchange in Elasticsearch
        Userchange userchangeEs = userchangeSearchRepository.findOne(testUserchange.getId());
        assertThat(userchangeEs).isEqualToComparingFieldByField(testUserchange);
    }

    @Test
    @Transactional
    public void createUserchangeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userchangeRepository.findAll().size();

        // Create the Userchange with an existing ID
        userchange.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserchangeMockMvc.perform(post("/api/userchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userchange)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Userchange> userchangeList = userchangeRepository.findAll();
        assertThat(userchangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserchanges() throws Exception {
        // Initialize the database
        userchangeRepository.saveAndFlush(userchange);

        // Get all the userchangeList
        restUserchangeMockMvc.perform(get("/api/userchanges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userchange.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].changeuser").value(hasItem(DEFAULT_CHANGEUSER.toString())));
    }

    @Test
    @Transactional
    public void getUserchange() throws Exception {
        // Initialize the database
        userchangeRepository.saveAndFlush(userchange);

        // Get the userchange
        restUserchangeMockMvc.perform(get("/api/userchanges/{id}", userchange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userchange.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.changeuser").value(DEFAULT_CHANGEUSER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserchange() throws Exception {
        // Get the userchange
        restUserchangeMockMvc.perform(get("/api/userchanges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserchange() throws Exception {
        // Initialize the database
        userchangeRepository.saveAndFlush(userchange);
        userchangeSearchRepository.save(userchange);
        int databaseSizeBeforeUpdate = userchangeRepository.findAll().size();

        // Update the userchange
        Userchange updatedUserchange = userchangeRepository.findOne(userchange.getId());
        updatedUserchange
            .user(UPDATED_USER)
            .changeuser(UPDATED_CHANGEUSER);

        restUserchangeMockMvc.perform(put("/api/userchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserchange)))
            .andExpect(status().isOk());

        // Validate the Userchange in the database
        List<Userchange> userchangeList = userchangeRepository.findAll();
        assertThat(userchangeList).hasSize(databaseSizeBeforeUpdate);
        Userchange testUserchange = userchangeList.get(userchangeList.size() - 1);
        assertThat(testUserchange.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testUserchange.getChangeuser()).isEqualTo(UPDATED_CHANGEUSER);

        // Validate the Userchange in Elasticsearch
        Userchange userchangeEs = userchangeSearchRepository.findOne(testUserchange.getId());
        assertThat(userchangeEs).isEqualToComparingFieldByField(testUserchange);
    }

    @Test
    @Transactional
    public void updateNonExistingUserchange() throws Exception {
        int databaseSizeBeforeUpdate = userchangeRepository.findAll().size();

        // Create the Userchange

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserchangeMockMvc.perform(put("/api/userchanges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userchange)))
            .andExpect(status().isCreated());

        // Validate the Userchange in the database
        List<Userchange> userchangeList = userchangeRepository.findAll();
        assertThat(userchangeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserchange() throws Exception {
        // Initialize the database
        userchangeRepository.saveAndFlush(userchange);
        userchangeSearchRepository.save(userchange);
        int databaseSizeBeforeDelete = userchangeRepository.findAll().size();

        // Get the userchange
        restUserchangeMockMvc.perform(delete("/api/userchanges/{id}", userchange.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userchangeExistsInEs = userchangeSearchRepository.exists(userchange.getId());
        assertThat(userchangeExistsInEs).isFalse();

        // Validate the database is empty
        List<Userchange> userchangeList = userchangeRepository.findAll();
        assertThat(userchangeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserchange() throws Exception {
        // Initialize the database
        userchangeRepository.saveAndFlush(userchange);
        userchangeSearchRepository.save(userchange);

        // Search the userchange
        restUserchangeMockMvc.perform(get("/api/_search/userchanges?query=id:" + userchange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userchange.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].changeuser").value(hasItem(DEFAULT_CHANGEUSER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userchange.class);
        Userchange userchange1 = new Userchange();
        userchange1.setId(1L);
        Userchange userchange2 = new Userchange();
        userchange2.setId(userchange1.getId());
        assertThat(userchange1).isEqualTo(userchange2);
        userchange2.setId(2L);
        assertThat(userchange1).isNotEqualTo(userchange2);
        userchange1.setId(null);
        assertThat(userchange1).isNotEqualTo(userchange2);
    }
}
