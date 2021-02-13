package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.Street;
import com.access.myapp.domain.Number;
import com.access.myapp.domain.Town;
import com.access.myapp.repository.StreetRepository;
import com.access.myapp.service.StreetService;
import com.access.myapp.service.dto.StreetDTO;
import com.access.myapp.service.mapper.StreetMapper;
import com.access.myapp.service.dto.StreetCriteria;
import com.access.myapp.service.StreetQueryService;

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
 * Integration tests for the {@link StreetResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StreetResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private StreetRepository streetRepository;

    @Autowired
    private StreetMapper streetMapper;

    @Autowired
    private StreetService streetService;

    @Autowired
    private StreetQueryService streetQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStreetMockMvc;

    private Street street;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Street createEntity(EntityManager em) {
        Street street = new Street()
            .value(DEFAULT_VALUE);
        return street;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Street createUpdatedEntity(EntityManager em) {
        Street street = new Street()
            .value(UPDATED_VALUE);
        return street;
    }

    @BeforeEach
    public void initTest() {
        street = createEntity(em);
    }

    @Test
    @Transactional
    public void createStreet() throws Exception {
        int databaseSizeBeforeCreate = streetRepository.findAll().size();
        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);
        restStreetMockMvc.perform(post("/api/streets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isCreated());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeCreate + 1);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createStreetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = streetRepository.findAll().size();

        // Create the Street with an existing ID
        street.setId(1L);
        StreetDTO streetDTO = streetMapper.toDto(street);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStreetMockMvc.perform(post("/api/streets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStreets() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList
        restStreetMockMvc.perform(get("/api/streets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(street.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get the street
        restStreetMockMvc.perform(get("/api/streets/{id}", street.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(street.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getStreetsByIdFiltering() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        Long id = street.getId();

        defaultStreetShouldBeFound("id.equals=" + id);
        defaultStreetShouldNotBeFound("id.notEquals=" + id);

        defaultStreetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStreetShouldNotBeFound("id.greaterThan=" + id);

        defaultStreetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStreetShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStreetsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where value equals to DEFAULT_VALUE
        defaultStreetShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the streetList where value equals to UPDATED_VALUE
        defaultStreetShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllStreetsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where value not equals to DEFAULT_VALUE
        defaultStreetShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the streetList where value not equals to UPDATED_VALUE
        defaultStreetShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllStreetsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultStreetShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the streetList where value equals to UPDATED_VALUE
        defaultStreetShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllStreetsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where value is not null
        defaultStreetShouldBeFound("value.specified=true");

        // Get all the streetList where value is null
        defaultStreetShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllStreetsByValueContainsSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where value contains DEFAULT_VALUE
        defaultStreetShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the streetList where value contains UPDATED_VALUE
        defaultStreetShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllStreetsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        // Get all the streetList where value does not contain DEFAULT_VALUE
        defaultStreetShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the streetList where value does not contain UPDATED_VALUE
        defaultStreetShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllStreetsByNumbersIsEqualToSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);
        Number numbers = NumberResourceIT.createEntity(em);
        em.persist(numbers);
        em.flush();
        street.addNumbers(numbers);
        streetRepository.saveAndFlush(street);
        Long numbersId = numbers.getId();

        // Get all the streetList where numbers equals to numbersId
        defaultStreetShouldBeFound("numbersId.equals=" + numbersId);

        // Get all the streetList where numbers equals to numbersId + 1
        defaultStreetShouldNotBeFound("numbersId.equals=" + (numbersId + 1));
    }


    @Test
    @Transactional
    public void getAllStreetsByTownIsEqualToSomething() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);
        Town town = TownResourceIT.createEntity(em);
        em.persist(town);
        em.flush();
        street.setTown(town);
        streetRepository.saveAndFlush(street);
        Long townId = town.getId();

        // Get all the streetList where town equals to townId
        defaultStreetShouldBeFound("townId.equals=" + townId);

        // Get all the streetList where town equals to townId + 1
        defaultStreetShouldNotBeFound("townId.equals=" + (townId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStreetShouldBeFound(String filter) throws Exception {
        restStreetMockMvc.perform(get("/api/streets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(street.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restStreetMockMvc.perform(get("/api/streets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStreetShouldNotBeFound(String filter) throws Exception {
        restStreetMockMvc.perform(get("/api/streets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStreetMockMvc.perform(get("/api/streets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStreet() throws Exception {
        // Get the street
        restStreetMockMvc.perform(get("/api/streets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Update the street
        Street updatedStreet = streetRepository.findById(street.getId()).get();
        // Disconnect from session so that the updates on updatedStreet are not directly saved in db
        em.detach(updatedStreet);
        updatedStreet
            .value(UPDATED_VALUE);
        StreetDTO streetDTO = streetMapper.toDto(updatedStreet);

        restStreetMockMvc.perform(put("/api/streets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isOk());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
        Street testStreet = streetList.get(streetList.size() - 1);
        assertThat(testStreet.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingStreet() throws Exception {
        int databaseSizeBeforeUpdate = streetRepository.findAll().size();

        // Create the Street
        StreetDTO streetDTO = streetMapper.toDto(street);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStreetMockMvc.perform(put("/api/streets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(streetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Street in the database
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStreet() throws Exception {
        // Initialize the database
        streetRepository.saveAndFlush(street);

        int databaseSizeBeforeDelete = streetRepository.findAll().size();

        // Delete the street
        restStreetMockMvc.perform(delete("/api/streets/{id}", street.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Street> streetList = streetRepository.findAll();
        assertThat(streetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
