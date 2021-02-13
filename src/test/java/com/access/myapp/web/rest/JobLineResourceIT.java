package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.JobLine;
import com.access.myapp.domain.Job;
import com.access.myapp.repository.JobLineRepository;
import com.access.myapp.service.JobLineService;
import com.access.myapp.service.dto.JobLineDTO;
import com.access.myapp.service.mapper.JobLineMapper;
import com.access.myapp.service.dto.JobLineCriteria;
import com.access.myapp.service.JobLineQueryService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobLineResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobLineResourceIT {

    private static final String DEFAULT_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final Integer SMALLER_QUANTITY = 1 - 1;

    private static final BigDecimal DEFAULT_UNIT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_COST = new BigDecimal(1 - 1);

    @Autowired
    private JobLineRepository jobLineRepository;

    @Autowired
    private JobLineMapper jobLineMapper;

    @Autowired
    private JobLineService jobLineService;

    @Autowired
    private JobLineQueryService jobLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobLineMockMvc;

    private JobLine jobLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobLine createEntity(EntityManager em) {
        JobLine jobLine = new JobLine()
            .material(DEFAULT_MATERIAL)
            .quantity(DEFAULT_QUANTITY)
            .unitCost(DEFAULT_UNIT_COST);
        return jobLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobLine createUpdatedEntity(EntityManager em) {
        JobLine jobLine = new JobLine()
            .material(UPDATED_MATERIAL)
            .quantity(UPDATED_QUANTITY)
            .unitCost(UPDATED_UNIT_COST);
        return jobLine;
    }

    @BeforeEach
    public void initTest() {
        jobLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobLine() throws Exception {
        int databaseSizeBeforeCreate = jobLineRepository.findAll().size();
        // Create the JobLine
        JobLineDTO jobLineDTO = jobLineMapper.toDto(jobLine);
        restJobLineMockMvc.perform(post("/api/job-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobLineDTO)))
            .andExpect(status().isCreated());

        // Validate the JobLine in the database
        List<JobLine> jobLineList = jobLineRepository.findAll();
        assertThat(jobLineList).hasSize(databaseSizeBeforeCreate + 1);
        JobLine testJobLine = jobLineList.get(jobLineList.size() - 1);
        assertThat(testJobLine.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testJobLine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testJobLine.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
    }

    @Test
    @Transactional
    public void createJobLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobLineRepository.findAll().size();

        // Create the JobLine with an existing ID
        jobLine.setId(1L);
        JobLineDTO jobLineDTO = jobLineMapper.toDto(jobLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobLineMockMvc.perform(post("/api/job-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobLine in the database
        List<JobLine> jobLineList = jobLineRepository.findAll();
        assertThat(jobLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobLines() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList
        restJobLineMockMvc.perform(get("/api/job-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.intValue())));
    }
    
    @Test
    @Transactional
    public void getJobLine() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get the jobLine
        restJobLineMockMvc.perform(get("/api/job-lines/{id}", jobLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobLine.getId().intValue()))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitCost").value(DEFAULT_UNIT_COST.intValue()));
    }


    @Test
    @Transactional
    public void getJobLinesByIdFiltering() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        Long id = jobLine.getId();

        defaultJobLineShouldBeFound("id.equals=" + id);
        defaultJobLineShouldNotBeFound("id.notEquals=" + id);

        defaultJobLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobLineShouldNotBeFound("id.greaterThan=" + id);

        defaultJobLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJobLinesByMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where material equals to DEFAULT_MATERIAL
        defaultJobLineShouldBeFound("material.equals=" + DEFAULT_MATERIAL);

        // Get all the jobLineList where material equals to UPDATED_MATERIAL
        defaultJobLineShouldNotBeFound("material.equals=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    public void getAllJobLinesByMaterialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where material not equals to DEFAULT_MATERIAL
        defaultJobLineShouldNotBeFound("material.notEquals=" + DEFAULT_MATERIAL);

        // Get all the jobLineList where material not equals to UPDATED_MATERIAL
        defaultJobLineShouldBeFound("material.notEquals=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    public void getAllJobLinesByMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where material in DEFAULT_MATERIAL or UPDATED_MATERIAL
        defaultJobLineShouldBeFound("material.in=" + DEFAULT_MATERIAL + "," + UPDATED_MATERIAL);

        // Get all the jobLineList where material equals to UPDATED_MATERIAL
        defaultJobLineShouldNotBeFound("material.in=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    public void getAllJobLinesByMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where material is not null
        defaultJobLineShouldBeFound("material.specified=true");

        // Get all the jobLineList where material is null
        defaultJobLineShouldNotBeFound("material.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobLinesByMaterialContainsSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where material contains DEFAULT_MATERIAL
        defaultJobLineShouldBeFound("material.contains=" + DEFAULT_MATERIAL);

        // Get all the jobLineList where material contains UPDATED_MATERIAL
        defaultJobLineShouldNotBeFound("material.contains=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    public void getAllJobLinesByMaterialNotContainsSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where material does not contain DEFAULT_MATERIAL
        defaultJobLineShouldNotBeFound("material.doesNotContain=" + DEFAULT_MATERIAL);

        // Get all the jobLineList where material does not contain UPDATED_MATERIAL
        defaultJobLineShouldBeFound("material.doesNotContain=" + UPDATED_MATERIAL);
    }


    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity equals to DEFAULT_QUANTITY
        defaultJobLineShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the jobLineList where quantity equals to UPDATED_QUANTITY
        defaultJobLineShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity not equals to DEFAULT_QUANTITY
        defaultJobLineShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the jobLineList where quantity not equals to UPDATED_QUANTITY
        defaultJobLineShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultJobLineShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the jobLineList where quantity equals to UPDATED_QUANTITY
        defaultJobLineShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity is not null
        defaultJobLineShouldBeFound("quantity.specified=true");

        // Get all the jobLineList where quantity is null
        defaultJobLineShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultJobLineShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the jobLineList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultJobLineShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultJobLineShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the jobLineList where quantity is less than or equal to SMALLER_QUANTITY
        defaultJobLineShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity is less than DEFAULT_QUANTITY
        defaultJobLineShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the jobLineList where quantity is less than UPDATED_QUANTITY
        defaultJobLineShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllJobLinesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where quantity is greater than DEFAULT_QUANTITY
        defaultJobLineShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the jobLineList where quantity is greater than SMALLER_QUANTITY
        defaultJobLineShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost equals to DEFAULT_UNIT_COST
        defaultJobLineShouldBeFound("unitCost.equals=" + DEFAULT_UNIT_COST);

        // Get all the jobLineList where unitCost equals to UPDATED_UNIT_COST
        defaultJobLineShouldNotBeFound("unitCost.equals=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost not equals to DEFAULT_UNIT_COST
        defaultJobLineShouldNotBeFound("unitCost.notEquals=" + DEFAULT_UNIT_COST);

        // Get all the jobLineList where unitCost not equals to UPDATED_UNIT_COST
        defaultJobLineShouldBeFound("unitCost.notEquals=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsInShouldWork() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost in DEFAULT_UNIT_COST or UPDATED_UNIT_COST
        defaultJobLineShouldBeFound("unitCost.in=" + DEFAULT_UNIT_COST + "," + UPDATED_UNIT_COST);

        // Get all the jobLineList where unitCost equals to UPDATED_UNIT_COST
        defaultJobLineShouldNotBeFound("unitCost.in=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost is not null
        defaultJobLineShouldBeFound("unitCost.specified=true");

        // Get all the jobLineList where unitCost is null
        defaultJobLineShouldNotBeFound("unitCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost is greater than or equal to DEFAULT_UNIT_COST
        defaultJobLineShouldBeFound("unitCost.greaterThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the jobLineList where unitCost is greater than or equal to UPDATED_UNIT_COST
        defaultJobLineShouldNotBeFound("unitCost.greaterThanOrEqual=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost is less than or equal to DEFAULT_UNIT_COST
        defaultJobLineShouldBeFound("unitCost.lessThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the jobLineList where unitCost is less than or equal to SMALLER_UNIT_COST
        defaultJobLineShouldNotBeFound("unitCost.lessThanOrEqual=" + SMALLER_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsLessThanSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost is less than DEFAULT_UNIT_COST
        defaultJobLineShouldNotBeFound("unitCost.lessThan=" + DEFAULT_UNIT_COST);

        // Get all the jobLineList where unitCost is less than UPDATED_UNIT_COST
        defaultJobLineShouldBeFound("unitCost.lessThan=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllJobLinesByUnitCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        // Get all the jobLineList where unitCost is greater than DEFAULT_UNIT_COST
        defaultJobLineShouldNotBeFound("unitCost.greaterThan=" + DEFAULT_UNIT_COST);

        // Get all the jobLineList where unitCost is greater than SMALLER_UNIT_COST
        defaultJobLineShouldBeFound("unitCost.greaterThan=" + SMALLER_UNIT_COST);
    }


    @Test
    @Transactional
    public void getAllJobLinesByJobIsEqualToSomething() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);
        Job job = JobResourceIT.createEntity(em);
        em.persist(job);
        em.flush();
        jobLine.setJob(job);
        jobLineRepository.saveAndFlush(jobLine);
        Long jobId = job.getId();

        // Get all the jobLineList where job equals to jobId
        defaultJobLineShouldBeFound("jobId.equals=" + jobId);

        // Get all the jobLineList where job equals to jobId + 1
        defaultJobLineShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobLineShouldBeFound(String filter) throws Exception {
        restJobLineMockMvc.perform(get("/api/job-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.intValue())));

        // Check, that the count call also returns 1
        restJobLineMockMvc.perform(get("/api/job-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobLineShouldNotBeFound(String filter) throws Exception {
        restJobLineMockMvc.perform(get("/api/job-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobLineMockMvc.perform(get("/api/job-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJobLine() throws Exception {
        // Get the jobLine
        restJobLineMockMvc.perform(get("/api/job-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobLine() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        int databaseSizeBeforeUpdate = jobLineRepository.findAll().size();

        // Update the jobLine
        JobLine updatedJobLine = jobLineRepository.findById(jobLine.getId()).get();
        // Disconnect from session so that the updates on updatedJobLine are not directly saved in db
        em.detach(updatedJobLine);
        updatedJobLine
            .material(UPDATED_MATERIAL)
            .quantity(UPDATED_QUANTITY)
            .unitCost(UPDATED_UNIT_COST);
        JobLineDTO jobLineDTO = jobLineMapper.toDto(updatedJobLine);

        restJobLineMockMvc.perform(put("/api/job-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobLineDTO)))
            .andExpect(status().isOk());

        // Validate the JobLine in the database
        List<JobLine> jobLineList = jobLineRepository.findAll();
        assertThat(jobLineList).hasSize(databaseSizeBeforeUpdate);
        JobLine testJobLine = jobLineList.get(jobLineList.size() - 1);
        assertThat(testJobLine.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testJobLine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testJobLine.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingJobLine() throws Exception {
        int databaseSizeBeforeUpdate = jobLineRepository.findAll().size();

        // Create the JobLine
        JobLineDTO jobLineDTO = jobLineMapper.toDto(jobLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobLineMockMvc.perform(put("/api/job-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobLine in the database
        List<JobLine> jobLineList = jobLineRepository.findAll();
        assertThat(jobLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobLine() throws Exception {
        // Initialize the database
        jobLineRepository.saveAndFlush(jobLine);

        int databaseSizeBeforeDelete = jobLineRepository.findAll().size();

        // Delete the jobLine
        restJobLineMockMvc.perform(delete("/api/job-lines/{id}", jobLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobLine> jobLineList = jobLineRepository.findAll();
        assertThat(jobLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
