package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.JobVisit;
import com.access.myapp.domain.Job;
import com.access.myapp.repository.JobVisitRepository;
import com.access.myapp.service.JobVisitService;
import com.access.myapp.service.dto.JobVisitDTO;
import com.access.myapp.service.mapper.JobVisitMapper;
import com.access.myapp.service.dto.JobVisitCriteria;
import com.access.myapp.service.JobVisitQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.access.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobVisitResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobVisitResourceIT {

    private static final ZonedDateTime DEFAULT_ARRIVED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ARRIVED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ARRIVED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DEPARTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEPARTED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DEPARTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHARGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CHARGE = new BigDecimal(1 - 1);

    private static final String DEFAULT_WORK_CARRIED_OUT = "AAAAAAAAAA";
    private static final String UPDATED_WORK_CARRIED_OUT = "BBBBBBBBBB";

    @Autowired
    private JobVisitRepository jobVisitRepository;

    @Autowired
    private JobVisitMapper jobVisitMapper;

    @Autowired
    private JobVisitService jobVisitService;

    @Autowired
    private JobVisitQueryService jobVisitQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobVisitMockMvc;

    private JobVisit jobVisit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobVisit createEntity(EntityManager em) {
        JobVisit jobVisit = new JobVisit()
            .arrived(DEFAULT_ARRIVED)
            .departed(DEFAULT_DEPARTED)
            .charge(DEFAULT_CHARGE)
            .workCarriedOut(DEFAULT_WORK_CARRIED_OUT);
        return jobVisit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobVisit createUpdatedEntity(EntityManager em) {
        JobVisit jobVisit = new JobVisit()
            .arrived(UPDATED_ARRIVED)
            .departed(UPDATED_DEPARTED)
            .charge(UPDATED_CHARGE)
            .workCarriedOut(UPDATED_WORK_CARRIED_OUT);
        return jobVisit;
    }

    @BeforeEach
    public void initTest() {
        jobVisit = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobVisit() throws Exception {
        int databaseSizeBeforeCreate = jobVisitRepository.findAll().size();
        // Create the JobVisit
        JobVisitDTO jobVisitDTO = jobVisitMapper.toDto(jobVisit);
        restJobVisitMockMvc.perform(post("/api/job-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the JobVisit in the database
        List<JobVisit> jobVisitList = jobVisitRepository.findAll();
        assertThat(jobVisitList).hasSize(databaseSizeBeforeCreate + 1);
        JobVisit testJobVisit = jobVisitList.get(jobVisitList.size() - 1);
        assertThat(testJobVisit.getArrived()).isEqualTo(DEFAULT_ARRIVED);
        assertThat(testJobVisit.getDeparted()).isEqualTo(DEFAULT_DEPARTED);
        assertThat(testJobVisit.getCharge()).isEqualTo(DEFAULT_CHARGE);
        assertThat(testJobVisit.getWorkCarriedOut()).isEqualTo(DEFAULT_WORK_CARRIED_OUT);
    }

    @Test
    @Transactional
    public void createJobVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobVisitRepository.findAll().size();

        // Create the JobVisit with an existing ID
        jobVisit.setId(1L);
        JobVisitDTO jobVisitDTO = jobVisitMapper.toDto(jobVisit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobVisitMockMvc.perform(post("/api/job-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobVisit in the database
        List<JobVisit> jobVisitList = jobVisitRepository.findAll();
        assertThat(jobVisitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobVisits() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList
        restJobVisitMockMvc.perform(get("/api/job-visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].arrived").value(hasItem(sameInstant(DEFAULT_ARRIVED))))
            .andExpect(jsonPath("$.[*].departed").value(hasItem(sameInstant(DEFAULT_DEPARTED))))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE.intValue())))
            .andExpect(jsonPath("$.[*].workCarriedOut").value(hasItem(DEFAULT_WORK_CARRIED_OUT)));
    }
    
    @Test
    @Transactional
    public void getJobVisit() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get the jobVisit
        restJobVisitMockMvc.perform(get("/api/job-visits/{id}", jobVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobVisit.getId().intValue()))
            .andExpect(jsonPath("$.arrived").value(sameInstant(DEFAULT_ARRIVED)))
            .andExpect(jsonPath("$.departed").value(sameInstant(DEFAULT_DEPARTED)))
            .andExpect(jsonPath("$.charge").value(DEFAULT_CHARGE.intValue()))
            .andExpect(jsonPath("$.workCarriedOut").value(DEFAULT_WORK_CARRIED_OUT));
    }


    @Test
    @Transactional
    public void getJobVisitsByIdFiltering() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        Long id = jobVisit.getId();

        defaultJobVisitShouldBeFound("id.equals=" + id);
        defaultJobVisitShouldNotBeFound("id.notEquals=" + id);

        defaultJobVisitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobVisitShouldNotBeFound("id.greaterThan=" + id);

        defaultJobVisitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobVisitShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived equals to DEFAULT_ARRIVED
        defaultJobVisitShouldBeFound("arrived.equals=" + DEFAULT_ARRIVED);

        // Get all the jobVisitList where arrived equals to UPDATED_ARRIVED
        defaultJobVisitShouldNotBeFound("arrived.equals=" + UPDATED_ARRIVED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived not equals to DEFAULT_ARRIVED
        defaultJobVisitShouldNotBeFound("arrived.notEquals=" + DEFAULT_ARRIVED);

        // Get all the jobVisitList where arrived not equals to UPDATED_ARRIVED
        defaultJobVisitShouldBeFound("arrived.notEquals=" + UPDATED_ARRIVED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsInShouldWork() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived in DEFAULT_ARRIVED or UPDATED_ARRIVED
        defaultJobVisitShouldBeFound("arrived.in=" + DEFAULT_ARRIVED + "," + UPDATED_ARRIVED);

        // Get all the jobVisitList where arrived equals to UPDATED_ARRIVED
        defaultJobVisitShouldNotBeFound("arrived.in=" + UPDATED_ARRIVED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived is not null
        defaultJobVisitShouldBeFound("arrived.specified=true");

        // Get all the jobVisitList where arrived is null
        defaultJobVisitShouldNotBeFound("arrived.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived is greater than or equal to DEFAULT_ARRIVED
        defaultJobVisitShouldBeFound("arrived.greaterThanOrEqual=" + DEFAULT_ARRIVED);

        // Get all the jobVisitList where arrived is greater than or equal to UPDATED_ARRIVED
        defaultJobVisitShouldNotBeFound("arrived.greaterThanOrEqual=" + UPDATED_ARRIVED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived is less than or equal to DEFAULT_ARRIVED
        defaultJobVisitShouldBeFound("arrived.lessThanOrEqual=" + DEFAULT_ARRIVED);

        // Get all the jobVisitList where arrived is less than or equal to SMALLER_ARRIVED
        defaultJobVisitShouldNotBeFound("arrived.lessThanOrEqual=" + SMALLER_ARRIVED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsLessThanSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived is less than DEFAULT_ARRIVED
        defaultJobVisitShouldNotBeFound("arrived.lessThan=" + DEFAULT_ARRIVED);

        // Get all the jobVisitList where arrived is less than UPDATED_ARRIVED
        defaultJobVisitShouldBeFound("arrived.lessThan=" + UPDATED_ARRIVED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByArrivedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where arrived is greater than DEFAULT_ARRIVED
        defaultJobVisitShouldNotBeFound("arrived.greaterThan=" + DEFAULT_ARRIVED);

        // Get all the jobVisitList where arrived is greater than SMALLER_ARRIVED
        defaultJobVisitShouldBeFound("arrived.greaterThan=" + SMALLER_ARRIVED);
    }


    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed equals to DEFAULT_DEPARTED
        defaultJobVisitShouldBeFound("departed.equals=" + DEFAULT_DEPARTED);

        // Get all the jobVisitList where departed equals to UPDATED_DEPARTED
        defaultJobVisitShouldNotBeFound("departed.equals=" + UPDATED_DEPARTED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed not equals to DEFAULT_DEPARTED
        defaultJobVisitShouldNotBeFound("departed.notEquals=" + DEFAULT_DEPARTED);

        // Get all the jobVisitList where departed not equals to UPDATED_DEPARTED
        defaultJobVisitShouldBeFound("departed.notEquals=" + UPDATED_DEPARTED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsInShouldWork() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed in DEFAULT_DEPARTED or UPDATED_DEPARTED
        defaultJobVisitShouldBeFound("departed.in=" + DEFAULT_DEPARTED + "," + UPDATED_DEPARTED);

        // Get all the jobVisitList where departed equals to UPDATED_DEPARTED
        defaultJobVisitShouldNotBeFound("departed.in=" + UPDATED_DEPARTED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed is not null
        defaultJobVisitShouldBeFound("departed.specified=true");

        // Get all the jobVisitList where departed is null
        defaultJobVisitShouldNotBeFound("departed.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed is greater than or equal to DEFAULT_DEPARTED
        defaultJobVisitShouldBeFound("departed.greaterThanOrEqual=" + DEFAULT_DEPARTED);

        // Get all the jobVisitList where departed is greater than or equal to UPDATED_DEPARTED
        defaultJobVisitShouldNotBeFound("departed.greaterThanOrEqual=" + UPDATED_DEPARTED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed is less than or equal to DEFAULT_DEPARTED
        defaultJobVisitShouldBeFound("departed.lessThanOrEqual=" + DEFAULT_DEPARTED);

        // Get all the jobVisitList where departed is less than or equal to SMALLER_DEPARTED
        defaultJobVisitShouldNotBeFound("departed.lessThanOrEqual=" + SMALLER_DEPARTED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsLessThanSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed is less than DEFAULT_DEPARTED
        defaultJobVisitShouldNotBeFound("departed.lessThan=" + DEFAULT_DEPARTED);

        // Get all the jobVisitList where departed is less than UPDATED_DEPARTED
        defaultJobVisitShouldBeFound("departed.lessThan=" + UPDATED_DEPARTED);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByDepartedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where departed is greater than DEFAULT_DEPARTED
        defaultJobVisitShouldNotBeFound("departed.greaterThan=" + DEFAULT_DEPARTED);

        // Get all the jobVisitList where departed is greater than SMALLER_DEPARTED
        defaultJobVisitShouldBeFound("departed.greaterThan=" + SMALLER_DEPARTED);
    }


    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge equals to DEFAULT_CHARGE
        defaultJobVisitShouldBeFound("charge.equals=" + DEFAULT_CHARGE);

        // Get all the jobVisitList where charge equals to UPDATED_CHARGE
        defaultJobVisitShouldNotBeFound("charge.equals=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge not equals to DEFAULT_CHARGE
        defaultJobVisitShouldNotBeFound("charge.notEquals=" + DEFAULT_CHARGE);

        // Get all the jobVisitList where charge not equals to UPDATED_CHARGE
        defaultJobVisitShouldBeFound("charge.notEquals=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsInShouldWork() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge in DEFAULT_CHARGE or UPDATED_CHARGE
        defaultJobVisitShouldBeFound("charge.in=" + DEFAULT_CHARGE + "," + UPDATED_CHARGE);

        // Get all the jobVisitList where charge equals to UPDATED_CHARGE
        defaultJobVisitShouldNotBeFound("charge.in=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge is not null
        defaultJobVisitShouldBeFound("charge.specified=true");

        // Get all the jobVisitList where charge is null
        defaultJobVisitShouldNotBeFound("charge.specified=false");
    }

    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge is greater than or equal to DEFAULT_CHARGE
        defaultJobVisitShouldBeFound("charge.greaterThanOrEqual=" + DEFAULT_CHARGE);

        // Get all the jobVisitList where charge is greater than or equal to UPDATED_CHARGE
        defaultJobVisitShouldNotBeFound("charge.greaterThanOrEqual=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge is less than or equal to DEFAULT_CHARGE
        defaultJobVisitShouldBeFound("charge.lessThanOrEqual=" + DEFAULT_CHARGE);

        // Get all the jobVisitList where charge is less than or equal to SMALLER_CHARGE
        defaultJobVisitShouldNotBeFound("charge.lessThanOrEqual=" + SMALLER_CHARGE);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsLessThanSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge is less than DEFAULT_CHARGE
        defaultJobVisitShouldNotBeFound("charge.lessThan=" + DEFAULT_CHARGE);

        // Get all the jobVisitList where charge is less than UPDATED_CHARGE
        defaultJobVisitShouldBeFound("charge.lessThan=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByChargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where charge is greater than DEFAULT_CHARGE
        defaultJobVisitShouldNotBeFound("charge.greaterThan=" + DEFAULT_CHARGE);

        // Get all the jobVisitList where charge is greater than SMALLER_CHARGE
        defaultJobVisitShouldBeFound("charge.greaterThan=" + SMALLER_CHARGE);
    }


    @Test
    @Transactional
    public void getAllJobVisitsByWorkCarriedOutIsEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where workCarriedOut equals to DEFAULT_WORK_CARRIED_OUT
        defaultJobVisitShouldBeFound("workCarriedOut.equals=" + DEFAULT_WORK_CARRIED_OUT);

        // Get all the jobVisitList where workCarriedOut equals to UPDATED_WORK_CARRIED_OUT
        defaultJobVisitShouldNotBeFound("workCarriedOut.equals=" + UPDATED_WORK_CARRIED_OUT);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByWorkCarriedOutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where workCarriedOut not equals to DEFAULT_WORK_CARRIED_OUT
        defaultJobVisitShouldNotBeFound("workCarriedOut.notEquals=" + DEFAULT_WORK_CARRIED_OUT);

        // Get all the jobVisitList where workCarriedOut not equals to UPDATED_WORK_CARRIED_OUT
        defaultJobVisitShouldBeFound("workCarriedOut.notEquals=" + UPDATED_WORK_CARRIED_OUT);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByWorkCarriedOutIsInShouldWork() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where workCarriedOut in DEFAULT_WORK_CARRIED_OUT or UPDATED_WORK_CARRIED_OUT
        defaultJobVisitShouldBeFound("workCarriedOut.in=" + DEFAULT_WORK_CARRIED_OUT + "," + UPDATED_WORK_CARRIED_OUT);

        // Get all the jobVisitList where workCarriedOut equals to UPDATED_WORK_CARRIED_OUT
        defaultJobVisitShouldNotBeFound("workCarriedOut.in=" + UPDATED_WORK_CARRIED_OUT);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByWorkCarriedOutIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where workCarriedOut is not null
        defaultJobVisitShouldBeFound("workCarriedOut.specified=true");

        // Get all the jobVisitList where workCarriedOut is null
        defaultJobVisitShouldNotBeFound("workCarriedOut.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobVisitsByWorkCarriedOutContainsSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where workCarriedOut contains DEFAULT_WORK_CARRIED_OUT
        defaultJobVisitShouldBeFound("workCarriedOut.contains=" + DEFAULT_WORK_CARRIED_OUT);

        // Get all the jobVisitList where workCarriedOut contains UPDATED_WORK_CARRIED_OUT
        defaultJobVisitShouldNotBeFound("workCarriedOut.contains=" + UPDATED_WORK_CARRIED_OUT);
    }

    @Test
    @Transactional
    public void getAllJobVisitsByWorkCarriedOutNotContainsSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        // Get all the jobVisitList where workCarriedOut does not contain DEFAULT_WORK_CARRIED_OUT
        defaultJobVisitShouldNotBeFound("workCarriedOut.doesNotContain=" + DEFAULT_WORK_CARRIED_OUT);

        // Get all the jobVisitList where workCarriedOut does not contain UPDATED_WORK_CARRIED_OUT
        defaultJobVisitShouldBeFound("workCarriedOut.doesNotContain=" + UPDATED_WORK_CARRIED_OUT);
    }


    @Test
    @Transactional
    public void getAllJobVisitsByJobIsEqualToSomething() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);
        Job job = JobResourceIT.createEntity(em);
        em.persist(job);
        em.flush();
        jobVisit.setJob(job);
        jobVisitRepository.saveAndFlush(jobVisit);
        Long jobId = job.getId();

        // Get all the jobVisitList where job equals to jobId
        defaultJobVisitShouldBeFound("jobId.equals=" + jobId);

        // Get all the jobVisitList where job equals to jobId + 1
        defaultJobVisitShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobVisitShouldBeFound(String filter) throws Exception {
        restJobVisitMockMvc.perform(get("/api/job-visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].arrived").value(hasItem(sameInstant(DEFAULT_ARRIVED))))
            .andExpect(jsonPath("$.[*].departed").value(hasItem(sameInstant(DEFAULT_DEPARTED))))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE.intValue())))
            .andExpect(jsonPath("$.[*].workCarriedOut").value(hasItem(DEFAULT_WORK_CARRIED_OUT)));

        // Check, that the count call also returns 1
        restJobVisitMockMvc.perform(get("/api/job-visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobVisitShouldNotBeFound(String filter) throws Exception {
        restJobVisitMockMvc.perform(get("/api/job-visits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobVisitMockMvc.perform(get("/api/job-visits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJobVisit() throws Exception {
        // Get the jobVisit
        restJobVisitMockMvc.perform(get("/api/job-visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobVisit() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        int databaseSizeBeforeUpdate = jobVisitRepository.findAll().size();

        // Update the jobVisit
        JobVisit updatedJobVisit = jobVisitRepository.findById(jobVisit.getId()).get();
        // Disconnect from session so that the updates on updatedJobVisit are not directly saved in db
        em.detach(updatedJobVisit);
        updatedJobVisit
            .arrived(UPDATED_ARRIVED)
            .departed(UPDATED_DEPARTED)
            .charge(UPDATED_CHARGE)
            .workCarriedOut(UPDATED_WORK_CARRIED_OUT);
        JobVisitDTO jobVisitDTO = jobVisitMapper.toDto(updatedJobVisit);

        restJobVisitMockMvc.perform(put("/api/job-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobVisitDTO)))
            .andExpect(status().isOk());

        // Validate the JobVisit in the database
        List<JobVisit> jobVisitList = jobVisitRepository.findAll();
        assertThat(jobVisitList).hasSize(databaseSizeBeforeUpdate);
        JobVisit testJobVisit = jobVisitList.get(jobVisitList.size() - 1);
        assertThat(testJobVisit.getArrived()).isEqualTo(UPDATED_ARRIVED);
        assertThat(testJobVisit.getDeparted()).isEqualTo(UPDATED_DEPARTED);
        assertThat(testJobVisit.getCharge()).isEqualTo(UPDATED_CHARGE);
        assertThat(testJobVisit.getWorkCarriedOut()).isEqualTo(UPDATED_WORK_CARRIED_OUT);
    }

    @Test
    @Transactional
    public void updateNonExistingJobVisit() throws Exception {
        int databaseSizeBeforeUpdate = jobVisitRepository.findAll().size();

        // Create the JobVisit
        JobVisitDTO jobVisitDTO = jobVisitMapper.toDto(jobVisit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobVisitMockMvc.perform(put("/api/job-visits")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobVisit in the database
        List<JobVisit> jobVisitList = jobVisitRepository.findAll();
        assertThat(jobVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobVisit() throws Exception {
        // Initialize the database
        jobVisitRepository.saveAndFlush(jobVisit);

        int databaseSizeBeforeDelete = jobVisitRepository.findAll().size();

        // Delete the jobVisit
        restJobVisitMockMvc.perform(delete("/api/job-visits/{id}", jobVisit.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobVisit> jobVisitList = jobVisitRepository.findAll();
        assertThat(jobVisitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
