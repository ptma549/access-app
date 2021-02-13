package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.Town;
import com.access.myapp.domain.Street;
import com.access.myapp.domain.County;
import com.access.myapp.repository.TownRepository;
import com.access.myapp.service.TownService;
import com.access.myapp.service.dto.TownDTO;
import com.access.myapp.service.mapper.TownMapper;
import com.access.myapp.service.dto.TownCriteria;
import com.access.myapp.service.TownQueryService;

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
 * Integration tests for the {@link TownResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TownResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private TownRepository townRepository;

    @Autowired
    private TownMapper townMapper;

    @Autowired
    private TownService townService;

    @Autowired
    private TownQueryService townQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownMockMvc;

    private Town town;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Town createEntity(EntityManager em) {
        Town town = new Town()
            .value(DEFAULT_VALUE);
        return town;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Town createUpdatedEntity(EntityManager em) {
        Town town = new Town()
            .value(UPDATED_VALUE);
        return town;
    }

    @BeforeEach
    public void initTest() {
        town = createEntity(em);
    }

    @Test
    @Transactional
    public void createTown() throws Exception {
        int databaseSizeBeforeCreate = townRepository.findAll().size();
        // Create the Town
        TownDTO townDTO = townMapper.toDto(town);
        restTownMockMvc.perform(post("/api/towns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townDTO)))
            .andExpect(status().isCreated());

        // Validate the Town in the database
        List<Town> townList = townRepository.findAll();
        assertThat(townList).hasSize(databaseSizeBeforeCreate + 1);
        Town testTown = townList.get(townList.size() - 1);
        assertThat(testTown.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createTownWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = townRepository.findAll().size();

        // Create the Town with an existing ID
        town.setId(1L);
        TownDTO townDTO = townMapper.toDto(town);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownMockMvc.perform(post("/api/towns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Town in the database
        List<Town> townList = townRepository.findAll();
        assertThat(townList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTowns() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get all the townList
        restTownMockMvc.perform(get("/api/towns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(town.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getTown() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get the town
        restTownMockMvc.perform(get("/api/towns/{id}", town.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(town.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getTownsByIdFiltering() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        Long id = town.getId();

        defaultTownShouldBeFound("id.equals=" + id);
        defaultTownShouldNotBeFound("id.notEquals=" + id);

        defaultTownShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTownShouldNotBeFound("id.greaterThan=" + id);

        defaultTownShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTownShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTownsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get all the townList where value equals to DEFAULT_VALUE
        defaultTownShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the townList where value equals to UPDATED_VALUE
        defaultTownShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTownsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get all the townList where value not equals to DEFAULT_VALUE
        defaultTownShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the townList where value not equals to UPDATED_VALUE
        defaultTownShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTownsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get all the townList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultTownShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the townList where value equals to UPDATED_VALUE
        defaultTownShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTownsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get all the townList where value is not null
        defaultTownShouldBeFound("value.specified=true");

        // Get all the townList where value is null
        defaultTownShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllTownsByValueContainsSomething() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get all the townList where value contains DEFAULT_VALUE
        defaultTownShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the townList where value contains UPDATED_VALUE
        defaultTownShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllTownsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        // Get all the townList where value does not contain DEFAULT_VALUE
        defaultTownShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the townList where value does not contain UPDATED_VALUE
        defaultTownShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllTownsByStreetsIsEqualToSomething() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);
        Street streets = StreetResourceIT.createEntity(em);
        em.persist(streets);
        em.flush();
        town.addStreets(streets);
        townRepository.saveAndFlush(town);
        Long streetsId = streets.getId();

        // Get all the townList where streets equals to streetsId
        defaultTownShouldBeFound("streetsId.equals=" + streetsId);

        // Get all the townList where streets equals to streetsId + 1
        defaultTownShouldNotBeFound("streetsId.equals=" + (streetsId + 1));
    }


    @Test
    @Transactional
    public void getAllTownsByCountyIsEqualToSomething() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);
        County county = CountyResourceIT.createEntity(em);
        em.persist(county);
        em.flush();
        town.setCounty(county);
        townRepository.saveAndFlush(town);
        Long countyId = county.getId();

        // Get all the townList where county equals to countyId
        defaultTownShouldBeFound("countyId.equals=" + countyId);

        // Get all the townList where county equals to countyId + 1
        defaultTownShouldNotBeFound("countyId.equals=" + (countyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTownShouldBeFound(String filter) throws Exception {
        restTownMockMvc.perform(get("/api/towns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(town.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restTownMockMvc.perform(get("/api/towns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTownShouldNotBeFound(String filter) throws Exception {
        restTownMockMvc.perform(get("/api/towns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTownMockMvc.perform(get("/api/towns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTown() throws Exception {
        // Get the town
        restTownMockMvc.perform(get("/api/towns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTown() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        int databaseSizeBeforeUpdate = townRepository.findAll().size();

        // Update the town
        Town updatedTown = townRepository.findById(town.getId()).get();
        // Disconnect from session so that the updates on updatedTown are not directly saved in db
        em.detach(updatedTown);
        updatedTown
            .value(UPDATED_VALUE);
        TownDTO townDTO = townMapper.toDto(updatedTown);

        restTownMockMvc.perform(put("/api/towns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townDTO)))
            .andExpect(status().isOk());

        // Validate the Town in the database
        List<Town> townList = townRepository.findAll();
        assertThat(townList).hasSize(databaseSizeBeforeUpdate);
        Town testTown = townList.get(townList.size() - 1);
        assertThat(testTown.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTown() throws Exception {
        int databaseSizeBeforeUpdate = townRepository.findAll().size();

        // Create the Town
        TownDTO townDTO = townMapper.toDto(town);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownMockMvc.perform(put("/api/towns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(townDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Town in the database
        List<Town> townList = townRepository.findAll();
        assertThat(townList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTown() throws Exception {
        // Initialize the database
        townRepository.saveAndFlush(town);

        int databaseSizeBeforeDelete = townRepository.findAll().size();

        // Delete the town
        restTownMockMvc.perform(delete("/api/towns/{id}", town.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Town> townList = townRepository.findAll();
        assertThat(townList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
