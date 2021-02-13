package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.Number;
import com.access.myapp.domain.Position;
import com.access.myapp.domain.Street;
import com.access.myapp.repository.NumberRepository;
import com.access.myapp.service.NumberService;
import com.access.myapp.service.dto.NumberDTO;
import com.access.myapp.service.mapper.NumberMapper;
import com.access.myapp.service.dto.NumberCriteria;
import com.access.myapp.service.NumberQueryService;

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
 * Integration tests for the {@link NumberResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NumberResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    @Autowired
    private NumberRepository numberRepository;

    @Autowired
    private NumberMapper numberMapper;

    @Autowired
    private NumberService numberService;

    @Autowired
    private NumberQueryService numberQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNumberMockMvc;

    private Number number;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Number createEntity(EntityManager em) {
        Number number = new Number()
            .value(DEFAULT_VALUE)
            .building(DEFAULT_BUILDING)
            .postcode(DEFAULT_POSTCODE);
        return number;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Number createUpdatedEntity(EntityManager em) {
        Number number = new Number()
            .value(UPDATED_VALUE)
            .building(UPDATED_BUILDING)
            .postcode(UPDATED_POSTCODE);
        return number;
    }

    @BeforeEach
    public void initTest() {
        number = createEntity(em);
    }

    @Test
    @Transactional
    public void createNumber() throws Exception {
        int databaseSizeBeforeCreate = numberRepository.findAll().size();
        // Create the Number
        NumberDTO numberDTO = numberMapper.toDto(number);
        restNumberMockMvc.perform(post("/api/numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numberDTO)))
            .andExpect(status().isCreated());

        // Validate the Number in the database
        List<Number> numberList = numberRepository.findAll();
        assertThat(numberList).hasSize(databaseSizeBeforeCreate + 1);
        Number testNumber = numberList.get(numberList.size() - 1);
        assertThat(testNumber.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testNumber.getBuilding()).isEqualTo(DEFAULT_BUILDING);
        assertThat(testNumber.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
    }

    @Test
    @Transactional
    public void createNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numberRepository.findAll().size();

        // Create the Number with an existing ID
        number.setId(1L);
        NumberDTO numberDTO = numberMapper.toDto(number);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumberMockMvc.perform(post("/api/numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Number in the database
        List<Number> numberList = numberRepository.findAll();
        assertThat(numberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNumbers() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList
        restNumberMockMvc.perform(get("/api/numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(number.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)));
    }
    
    @Test
    @Transactional
    public void getNumber() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get the number
        restNumberMockMvc.perform(get("/api/numbers/{id}", number.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(number.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE));
    }


    @Test
    @Transactional
    public void getNumbersByIdFiltering() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        Long id = number.getId();

        defaultNumberShouldBeFound("id.equals=" + id);
        defaultNumberShouldNotBeFound("id.notEquals=" + id);

        defaultNumberShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNumberShouldNotBeFound("id.greaterThan=" + id);

        defaultNumberShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNumberShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNumbersByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where value equals to DEFAULT_VALUE
        defaultNumberShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the numberList where value equals to UPDATED_VALUE
        defaultNumberShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNumbersByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where value not equals to DEFAULT_VALUE
        defaultNumberShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the numberList where value not equals to UPDATED_VALUE
        defaultNumberShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNumbersByValueIsInShouldWork() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultNumberShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the numberList where value equals to UPDATED_VALUE
        defaultNumberShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNumbersByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where value is not null
        defaultNumberShouldBeFound("value.specified=true");

        // Get all the numberList where value is null
        defaultNumberShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllNumbersByValueContainsSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where value contains DEFAULT_VALUE
        defaultNumberShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the numberList where value contains UPDATED_VALUE
        defaultNumberShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNumbersByValueNotContainsSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where value does not contain DEFAULT_VALUE
        defaultNumberShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the numberList where value does not contain UPDATED_VALUE
        defaultNumberShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllNumbersByBuildingIsEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where building equals to DEFAULT_BUILDING
        defaultNumberShouldBeFound("building.equals=" + DEFAULT_BUILDING);

        // Get all the numberList where building equals to UPDATED_BUILDING
        defaultNumberShouldNotBeFound("building.equals=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllNumbersByBuildingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where building not equals to DEFAULT_BUILDING
        defaultNumberShouldNotBeFound("building.notEquals=" + DEFAULT_BUILDING);

        // Get all the numberList where building not equals to UPDATED_BUILDING
        defaultNumberShouldBeFound("building.notEquals=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllNumbersByBuildingIsInShouldWork() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where building in DEFAULT_BUILDING or UPDATED_BUILDING
        defaultNumberShouldBeFound("building.in=" + DEFAULT_BUILDING + "," + UPDATED_BUILDING);

        // Get all the numberList where building equals to UPDATED_BUILDING
        defaultNumberShouldNotBeFound("building.in=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllNumbersByBuildingIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where building is not null
        defaultNumberShouldBeFound("building.specified=true");

        // Get all the numberList where building is null
        defaultNumberShouldNotBeFound("building.specified=false");
    }
                @Test
    @Transactional
    public void getAllNumbersByBuildingContainsSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where building contains DEFAULT_BUILDING
        defaultNumberShouldBeFound("building.contains=" + DEFAULT_BUILDING);

        // Get all the numberList where building contains UPDATED_BUILDING
        defaultNumberShouldNotBeFound("building.contains=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllNumbersByBuildingNotContainsSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where building does not contain DEFAULT_BUILDING
        defaultNumberShouldNotBeFound("building.doesNotContain=" + DEFAULT_BUILDING);

        // Get all the numberList where building does not contain UPDATED_BUILDING
        defaultNumberShouldBeFound("building.doesNotContain=" + UPDATED_BUILDING);
    }


    @Test
    @Transactional
    public void getAllNumbersByPostcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where postcode equals to DEFAULT_POSTCODE
        defaultNumberShouldBeFound("postcode.equals=" + DEFAULT_POSTCODE);

        // Get all the numberList where postcode equals to UPDATED_POSTCODE
        defaultNumberShouldNotBeFound("postcode.equals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllNumbersByPostcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where postcode not equals to DEFAULT_POSTCODE
        defaultNumberShouldNotBeFound("postcode.notEquals=" + DEFAULT_POSTCODE);

        // Get all the numberList where postcode not equals to UPDATED_POSTCODE
        defaultNumberShouldBeFound("postcode.notEquals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllNumbersByPostcodeIsInShouldWork() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where postcode in DEFAULT_POSTCODE or UPDATED_POSTCODE
        defaultNumberShouldBeFound("postcode.in=" + DEFAULT_POSTCODE + "," + UPDATED_POSTCODE);

        // Get all the numberList where postcode equals to UPDATED_POSTCODE
        defaultNumberShouldNotBeFound("postcode.in=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllNumbersByPostcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where postcode is not null
        defaultNumberShouldBeFound("postcode.specified=true");

        // Get all the numberList where postcode is null
        defaultNumberShouldNotBeFound("postcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllNumbersByPostcodeContainsSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where postcode contains DEFAULT_POSTCODE
        defaultNumberShouldBeFound("postcode.contains=" + DEFAULT_POSTCODE);

        // Get all the numberList where postcode contains UPDATED_POSTCODE
        defaultNumberShouldNotBeFound("postcode.contains=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllNumbersByPostcodeNotContainsSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        // Get all the numberList where postcode does not contain DEFAULT_POSTCODE
        defaultNumberShouldNotBeFound("postcode.doesNotContain=" + DEFAULT_POSTCODE);

        // Get all the numberList where postcode does not contain UPDATED_POSTCODE
        defaultNumberShouldBeFound("postcode.doesNotContain=" + UPDATED_POSTCODE);
    }


    @Test
    @Transactional
    public void getAllNumbersByPositionsIsEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);
        Position positions = PositionResourceIT.createEntity(em);
        em.persist(positions);
        em.flush();
        number.addPositions(positions);
        numberRepository.saveAndFlush(number);
        Long positionsId = positions.getId();

        // Get all the numberList where positions equals to positionsId
        defaultNumberShouldBeFound("positionsId.equals=" + positionsId);

        // Get all the numberList where positions equals to positionsId + 1
        defaultNumberShouldNotBeFound("positionsId.equals=" + (positionsId + 1));
    }


    @Test
    @Transactional
    public void getAllNumbersByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);
        Street street = StreetResourceIT.createEntity(em);
        em.persist(street);
        em.flush();
        number.setStreet(street);
        numberRepository.saveAndFlush(number);
        Long streetId = street.getId();

        // Get all the numberList where street equals to streetId
        defaultNumberShouldBeFound("streetId.equals=" + streetId);

        // Get all the numberList where street equals to streetId + 1
        defaultNumberShouldNotBeFound("streetId.equals=" + (streetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNumberShouldBeFound(String filter) throws Exception {
        restNumberMockMvc.perform(get("/api/numbers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(number.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)));

        // Check, that the count call also returns 1
        restNumberMockMvc.perform(get("/api/numbers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNumberShouldNotBeFound(String filter) throws Exception {
        restNumberMockMvc.perform(get("/api/numbers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNumberMockMvc.perform(get("/api/numbers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNumber() throws Exception {
        // Get the number
        restNumberMockMvc.perform(get("/api/numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNumber() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        int databaseSizeBeforeUpdate = numberRepository.findAll().size();

        // Update the number
        Number updatedNumber = numberRepository.findById(number.getId()).get();
        // Disconnect from session so that the updates on updatedNumber are not directly saved in db
        em.detach(updatedNumber);
        updatedNumber
            .value(UPDATED_VALUE)
            .building(UPDATED_BUILDING)
            .postcode(UPDATED_POSTCODE);
        NumberDTO numberDTO = numberMapper.toDto(updatedNumber);

        restNumberMockMvc.perform(put("/api/numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numberDTO)))
            .andExpect(status().isOk());

        // Validate the Number in the database
        List<Number> numberList = numberRepository.findAll();
        assertThat(numberList).hasSize(databaseSizeBeforeUpdate);
        Number testNumber = numberList.get(numberList.size() - 1);
        assertThat(testNumber.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testNumber.getBuilding()).isEqualTo(UPDATED_BUILDING);
        assertThat(testNumber.getPostcode()).isEqualTo(UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void updateNonExistingNumber() throws Exception {
        int databaseSizeBeforeUpdate = numberRepository.findAll().size();

        // Create the Number
        NumberDTO numberDTO = numberMapper.toDto(number);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumberMockMvc.perform(put("/api/numbers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Number in the database
        List<Number> numberList = numberRepository.findAll();
        assertThat(numberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNumber() throws Exception {
        // Initialize the database
        numberRepository.saveAndFlush(number);

        int databaseSizeBeforeDelete = numberRepository.findAll().size();

        // Delete the number
        restNumberMockMvc.perform(delete("/api/numbers/{id}", number.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Number> numberList = numberRepository.findAll();
        assertThat(numberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
