package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.County;
import com.access.myapp.domain.Town;
import com.access.myapp.domain.Client;
import com.access.myapp.repository.CountyRepository;
import com.access.myapp.service.CountyService;
import com.access.myapp.service.dto.CountyDTO;
import com.access.myapp.service.mapper.CountyMapper;
import com.access.myapp.service.dto.CountyCriteria;
import com.access.myapp.service.CountyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CountyResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CountyRepository countyRepository;

    @Autowired
    private CountyMapper countyMapper;

    @Autowired
    private CountyService countyService;

    @Autowired
    private CountyQueryService countyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountyMockMvc;

    private County county;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static County createEntity(EntityManager em) {
        County county = new County()
            .name(DEFAULT_NAME);
        return county;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static County createUpdatedEntity(EntityManager em) {
        County county = new County()
            .name(UPDATED_NAME);
        return county;
    }

    @BeforeEach
    public void initTest() {
        county = createEntity(em);
    }

    @Test
    @Transactional
    public void createCounty() throws Exception {
        int databaseSizeBeforeCreate = countyRepository.findAll().size();
        // Create the County
        CountyDTO countyDTO = countyMapper.toDto(county);
        restCountyMockMvc.perform(post("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countyDTO)))
            .andExpect(status().isCreated());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeCreate + 1);
        County testCounty = countyList.get(countyList.size() - 1);
        assertThat(testCounty.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCountyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countyRepository.findAll().size();

        // Create the County with an existing ID
        county.setId(1L);
        CountyDTO countyDTO = countyMapper.toDto(county);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountyMockMvc.perform(post("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCounties() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList
        restCountyMockMvc.perform(get("/api/counties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(county.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get the county
        restCountyMockMvc.perform(get("/api/counties/{id}", county.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(county.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getCountiesByIdFiltering() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        Long id = county.getId();

        defaultCountyShouldBeFound("id.equals=" + id);
        defaultCountyShouldNotBeFound("id.notEquals=" + id);

        defaultCountyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCountyShouldNotBeFound("id.greaterThan=" + id);

        defaultCountyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCountyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCountiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList where name equals to DEFAULT_NAME
        defaultCountyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the countyList where name equals to UPDATED_NAME
        defaultCountyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList where name not equals to DEFAULT_NAME
        defaultCountyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the countyList where name not equals to UPDATED_NAME
        defaultCountyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCountyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the countyList where name equals to UPDATED_NAME
        defaultCountyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList where name is not null
        defaultCountyShouldBeFound("name.specified=true");

        // Get all the countyList where name is null
        defaultCountyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCountiesByNameContainsSomething() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList where name contains DEFAULT_NAME
        defaultCountyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the countyList where name contains UPDATED_NAME
        defaultCountyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCountiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the countyList where name does not contain DEFAULT_NAME
        defaultCountyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the countyList where name does not contain UPDATED_NAME
        defaultCountyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCountiesByTownIsEqualToSomething() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);
        Town town = TownResourceIT.createEntity(em);
        em.persist(town);
        em.flush();
        county.addTown(town);
        countyRepository.saveAndFlush(county);
        Long townId = town.getId();

        // Get all the countyList where town equals to townId
        defaultCountyShouldBeFound("townId.equals=" + townId);

        // Get all the countyList where town equals to townId + 1
        defaultCountyShouldNotBeFound("townId.equals=" + (townId + 1));
    }


    @Test
    @Transactional
    public void getAllCountiesByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);
        Client client = ClientResourceIT.createEntity(em);
        em.persist(client);
        em.flush();
        county.setClient(client);
        countyRepository.saveAndFlush(county);
        Long clientId = client.getId();

        // Get all the countyList where client equals to clientId
        defaultCountyShouldBeFound("clientId.equals=" + clientId);

        // Get all the countyList where client equals to clientId + 1
        defaultCountyShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCountyShouldBeFound(String filter) throws Exception {
        restCountyMockMvc.perform(get("/api/counties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(county.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCountyMockMvc.perform(get("/api/counties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCountyShouldNotBeFound(String filter) throws Exception {
        restCountyMockMvc.perform(get("/api/counties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCountyMockMvc.perform(get("/api/counties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCounty() throws Exception {
        // Get the county
        restCountyMockMvc.perform(get("/api/counties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        int databaseSizeBeforeUpdate = countyRepository.findAll().size();

        // Update the county
        County updatedCounty = countyRepository.findById(county.getId()).get();
        // Disconnect from session so that the updates on updatedCounty are not directly saved in db
        em.detach(updatedCounty);
        updatedCounty
            .name(UPDATED_NAME);
        CountyDTO countyDTO = countyMapper.toDto(updatedCounty);

        restCountyMockMvc.perform(put("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countyDTO)))
            .andExpect(status().isOk());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeUpdate);
        County testCounty = countyList.get(countyList.size() - 1);
        assertThat(testCounty.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCounty() throws Exception {
        int databaseSizeBeforeUpdate = countyRepository.findAll().size();

        // Create the County
        CountyDTO countyDTO = countyMapper.toDto(county);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountyMockMvc.perform(put("/api/counties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(countyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the County in the database
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        int databaseSizeBeforeDelete = countyRepository.findAll().size();

        // Delete the county
        restCountyMockMvc.perform(delete("/api/counties/{id}", county.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<County> countyList = countyRepository.findAll();
        assertThat(countyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
