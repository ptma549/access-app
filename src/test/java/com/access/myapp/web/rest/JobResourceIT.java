package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.Job;
import com.access.myapp.domain.JobComment;
import com.access.myapp.domain.JobLine;
import com.access.myapp.domain.JobVisit;
import com.access.myapp.domain.Client;
import com.access.myapp.repository.JobRepository;
import com.access.myapp.service.JobService;
import com.access.myapp.service.dto.JobDTO;
import com.access.myapp.service.mapper.JobMapper;
import com.access.myapp.service.dto.JobCriteria;
import com.access.myapp.service.JobQueryService;

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
 * Integration tests for the {@link JobResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobResourceIT {

    private static final String DEFAULT_REPORTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REPORTED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ORDER_REF = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ORDER_REF = "BBBBBBBBBB";

    private static final String DEFAULT_RAISE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RAISE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITY = "BBBBBBBBBB";

    private static final String DEFAULT_FAULT = "AAAAAAAAAA";
    private static final String UPDATED_FAULT = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPIER = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPIER = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_TEL = "AAAAAAAAAA";
    private static final String UPDATED_HOME_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_TEL = "AAAAAAAAAA";
    private static final String UPDATED_WORK_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_TEL = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLETE = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_DETAILS = "BBBBBBBBBB";

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobQueryService jobQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobMockMvc;

    private Job job;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Job createEntity(EntityManager em) {
        Job job = new Job()
            .reportedBy(DEFAULT_REPORTED_BY)
            .clientOrderRef(DEFAULT_CLIENT_ORDER_REF)
            .raiseDate(DEFAULT_RAISE_DATE)
            .priority(DEFAULT_PRIORITY)
            .fault(DEFAULT_FAULT)
            .instructions(DEFAULT_INSTRUCTIONS)
            .occupier(DEFAULT_OCCUPIER)
            .homeTel(DEFAULT_HOME_TEL)
            .workTel(DEFAULT_WORK_TEL)
            .mobileTel(DEFAULT_MOBILE_TEL)
            .tenantName(DEFAULT_TENANT_NAME)
            .complete(DEFAULT_COMPLETE)
            .position(DEFAULT_POSITION)
            .invoice(DEFAULT_INVOICE)
            .invoiceDetails(DEFAULT_INVOICE_DETAILS);
        return job;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Job createUpdatedEntity(EntityManager em) {
        Job job = new Job()
            .reportedBy(UPDATED_REPORTED_BY)
            .clientOrderRef(UPDATED_CLIENT_ORDER_REF)
            .raiseDate(UPDATED_RAISE_DATE)
            .priority(UPDATED_PRIORITY)
            .fault(UPDATED_FAULT)
            .instructions(UPDATED_INSTRUCTIONS)
            .occupier(UPDATED_OCCUPIER)
            .homeTel(UPDATED_HOME_TEL)
            .workTel(UPDATED_WORK_TEL)
            .mobileTel(UPDATED_MOBILE_TEL)
            .tenantName(UPDATED_TENANT_NAME)
            .complete(UPDATED_COMPLETE)
            .position(UPDATED_POSITION)
            .invoice(UPDATED_INVOICE)
            .invoiceDetails(UPDATED_INVOICE_DETAILS);
        return job;
    }

    @BeforeEach
    public void initTest() {
        job = createEntity(em);
    }

    @Test
    @Transactional
    public void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();
        // Create the Job
        JobDTO jobDTO = jobMapper.toDto(job);
        restJobMockMvc.perform(post("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobDTO)))
            .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getReportedBy()).isEqualTo(DEFAULT_REPORTED_BY);
        assertThat(testJob.getClientOrderRef()).isEqualTo(DEFAULT_CLIENT_ORDER_REF);
        assertThat(testJob.getRaiseDate()).isEqualTo(DEFAULT_RAISE_DATE);
        assertThat(testJob.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testJob.getFault()).isEqualTo(DEFAULT_FAULT);
        assertThat(testJob.getInstructions()).isEqualTo(DEFAULT_INSTRUCTIONS);
        assertThat(testJob.getOccupier()).isEqualTo(DEFAULT_OCCUPIER);
        assertThat(testJob.getHomeTel()).isEqualTo(DEFAULT_HOME_TEL);
        assertThat(testJob.getWorkTel()).isEqualTo(DEFAULT_WORK_TEL);
        assertThat(testJob.getMobileTel()).isEqualTo(DEFAULT_MOBILE_TEL);
        assertThat(testJob.getTenantName()).isEqualTo(DEFAULT_TENANT_NAME);
        assertThat(testJob.getComplete()).isEqualTo(DEFAULT_COMPLETE);
        assertThat(testJob.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testJob.getInvoice()).isEqualTo(DEFAULT_INVOICE);
        assertThat(testJob.getInvoiceDetails()).isEqualTo(DEFAULT_INVOICE_DETAILS);
    }

    @Test
    @Transactional
    public void createJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job with an existing ID
        job.setId(1L);
        JobDTO jobDTO = jobMapper.toDto(job);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobMockMvc.perform(post("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList
        restJobMockMvc.perform(get("/api/jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportedBy").value(hasItem(DEFAULT_REPORTED_BY)))
            .andExpect(jsonPath("$.[*].clientOrderRef").value(hasItem(DEFAULT_CLIENT_ORDER_REF)))
            .andExpect(jsonPath("$.[*].raiseDate").value(hasItem(DEFAULT_RAISE_DATE)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].fault").value(hasItem(DEFAULT_FAULT)))
            .andExpect(jsonPath("$.[*].instructions").value(hasItem(DEFAULT_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].occupier").value(hasItem(DEFAULT_OCCUPIER)))
            .andExpect(jsonPath("$.[*].homeTel").value(hasItem(DEFAULT_HOME_TEL)))
            .andExpect(jsonPath("$.[*].workTel").value(hasItem(DEFAULT_WORK_TEL)))
            .andExpect(jsonPath("$.[*].mobileTel").value(hasItem(DEFAULT_MOBILE_TEL)))
            .andExpect(jsonPath("$.[*].tenantName").value(hasItem(DEFAULT_TENANT_NAME)))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].invoice").value(hasItem(DEFAULT_INVOICE)))
            .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(job.getId().intValue()))
            .andExpect(jsonPath("$.reportedBy").value(DEFAULT_REPORTED_BY))
            .andExpect(jsonPath("$.clientOrderRef").value(DEFAULT_CLIENT_ORDER_REF))
            .andExpect(jsonPath("$.raiseDate").value(DEFAULT_RAISE_DATE))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.fault").value(DEFAULT_FAULT))
            .andExpect(jsonPath("$.instructions").value(DEFAULT_INSTRUCTIONS))
            .andExpect(jsonPath("$.occupier").value(DEFAULT_OCCUPIER))
            .andExpect(jsonPath("$.homeTel").value(DEFAULT_HOME_TEL))
            .andExpect(jsonPath("$.workTel").value(DEFAULT_WORK_TEL))
            .andExpect(jsonPath("$.mobileTel").value(DEFAULT_MOBILE_TEL))
            .andExpect(jsonPath("$.tenantName").value(DEFAULT_TENANT_NAME))
            .andExpect(jsonPath("$.complete").value(DEFAULT_COMPLETE))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.invoice").value(DEFAULT_INVOICE))
            .andExpect(jsonPath("$.invoiceDetails").value(DEFAULT_INVOICE_DETAILS));
    }


    @Test
    @Transactional
    public void getJobsByIdFiltering() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        Long id = job.getId();

        defaultJobShouldBeFound("id.equals=" + id);
        defaultJobShouldNotBeFound("id.notEquals=" + id);

        defaultJobShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobShouldNotBeFound("id.greaterThan=" + id);

        defaultJobShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJobsByReportedByIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where reportedBy equals to DEFAULT_REPORTED_BY
        defaultJobShouldBeFound("reportedBy.equals=" + DEFAULT_REPORTED_BY);

        // Get all the jobList where reportedBy equals to UPDATED_REPORTED_BY
        defaultJobShouldNotBeFound("reportedBy.equals=" + UPDATED_REPORTED_BY);
    }

    @Test
    @Transactional
    public void getAllJobsByReportedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where reportedBy not equals to DEFAULT_REPORTED_BY
        defaultJobShouldNotBeFound("reportedBy.notEquals=" + DEFAULT_REPORTED_BY);

        // Get all the jobList where reportedBy not equals to UPDATED_REPORTED_BY
        defaultJobShouldBeFound("reportedBy.notEquals=" + UPDATED_REPORTED_BY);
    }

    @Test
    @Transactional
    public void getAllJobsByReportedByIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where reportedBy in DEFAULT_REPORTED_BY or UPDATED_REPORTED_BY
        defaultJobShouldBeFound("reportedBy.in=" + DEFAULT_REPORTED_BY + "," + UPDATED_REPORTED_BY);

        // Get all the jobList where reportedBy equals to UPDATED_REPORTED_BY
        defaultJobShouldNotBeFound("reportedBy.in=" + UPDATED_REPORTED_BY);
    }

    @Test
    @Transactional
    public void getAllJobsByReportedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where reportedBy is not null
        defaultJobShouldBeFound("reportedBy.specified=true");

        // Get all the jobList where reportedBy is null
        defaultJobShouldNotBeFound("reportedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByReportedByContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where reportedBy contains DEFAULT_REPORTED_BY
        defaultJobShouldBeFound("reportedBy.contains=" + DEFAULT_REPORTED_BY);

        // Get all the jobList where reportedBy contains UPDATED_REPORTED_BY
        defaultJobShouldNotBeFound("reportedBy.contains=" + UPDATED_REPORTED_BY);
    }

    @Test
    @Transactional
    public void getAllJobsByReportedByNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where reportedBy does not contain DEFAULT_REPORTED_BY
        defaultJobShouldNotBeFound("reportedBy.doesNotContain=" + DEFAULT_REPORTED_BY);

        // Get all the jobList where reportedBy does not contain UPDATED_REPORTED_BY
        defaultJobShouldBeFound("reportedBy.doesNotContain=" + UPDATED_REPORTED_BY);
    }


    @Test
    @Transactional
    public void getAllJobsByClientOrderRefIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where clientOrderRef equals to DEFAULT_CLIENT_ORDER_REF
        defaultJobShouldBeFound("clientOrderRef.equals=" + DEFAULT_CLIENT_ORDER_REF);

        // Get all the jobList where clientOrderRef equals to UPDATED_CLIENT_ORDER_REF
        defaultJobShouldNotBeFound("clientOrderRef.equals=" + UPDATED_CLIENT_ORDER_REF);
    }

    @Test
    @Transactional
    public void getAllJobsByClientOrderRefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where clientOrderRef not equals to DEFAULT_CLIENT_ORDER_REF
        defaultJobShouldNotBeFound("clientOrderRef.notEquals=" + DEFAULT_CLIENT_ORDER_REF);

        // Get all the jobList where clientOrderRef not equals to UPDATED_CLIENT_ORDER_REF
        defaultJobShouldBeFound("clientOrderRef.notEquals=" + UPDATED_CLIENT_ORDER_REF);
    }

    @Test
    @Transactional
    public void getAllJobsByClientOrderRefIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where clientOrderRef in DEFAULT_CLIENT_ORDER_REF or UPDATED_CLIENT_ORDER_REF
        defaultJobShouldBeFound("clientOrderRef.in=" + DEFAULT_CLIENT_ORDER_REF + "," + UPDATED_CLIENT_ORDER_REF);

        // Get all the jobList where clientOrderRef equals to UPDATED_CLIENT_ORDER_REF
        defaultJobShouldNotBeFound("clientOrderRef.in=" + UPDATED_CLIENT_ORDER_REF);
    }

    @Test
    @Transactional
    public void getAllJobsByClientOrderRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where clientOrderRef is not null
        defaultJobShouldBeFound("clientOrderRef.specified=true");

        // Get all the jobList where clientOrderRef is null
        defaultJobShouldNotBeFound("clientOrderRef.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByClientOrderRefContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where clientOrderRef contains DEFAULT_CLIENT_ORDER_REF
        defaultJobShouldBeFound("clientOrderRef.contains=" + DEFAULT_CLIENT_ORDER_REF);

        // Get all the jobList where clientOrderRef contains UPDATED_CLIENT_ORDER_REF
        defaultJobShouldNotBeFound("clientOrderRef.contains=" + UPDATED_CLIENT_ORDER_REF);
    }

    @Test
    @Transactional
    public void getAllJobsByClientOrderRefNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where clientOrderRef does not contain DEFAULT_CLIENT_ORDER_REF
        defaultJobShouldNotBeFound("clientOrderRef.doesNotContain=" + DEFAULT_CLIENT_ORDER_REF);

        // Get all the jobList where clientOrderRef does not contain UPDATED_CLIENT_ORDER_REF
        defaultJobShouldBeFound("clientOrderRef.doesNotContain=" + UPDATED_CLIENT_ORDER_REF);
    }


    @Test
    @Transactional
    public void getAllJobsByRaiseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where raiseDate equals to DEFAULT_RAISE_DATE
        defaultJobShouldBeFound("raiseDate.equals=" + DEFAULT_RAISE_DATE);

        // Get all the jobList where raiseDate equals to UPDATED_RAISE_DATE
        defaultJobShouldNotBeFound("raiseDate.equals=" + UPDATED_RAISE_DATE);
    }

    @Test
    @Transactional
    public void getAllJobsByRaiseDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where raiseDate not equals to DEFAULT_RAISE_DATE
        defaultJobShouldNotBeFound("raiseDate.notEquals=" + DEFAULT_RAISE_DATE);

        // Get all the jobList where raiseDate not equals to UPDATED_RAISE_DATE
        defaultJobShouldBeFound("raiseDate.notEquals=" + UPDATED_RAISE_DATE);
    }

    @Test
    @Transactional
    public void getAllJobsByRaiseDateIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where raiseDate in DEFAULT_RAISE_DATE or UPDATED_RAISE_DATE
        defaultJobShouldBeFound("raiseDate.in=" + DEFAULT_RAISE_DATE + "," + UPDATED_RAISE_DATE);

        // Get all the jobList where raiseDate equals to UPDATED_RAISE_DATE
        defaultJobShouldNotBeFound("raiseDate.in=" + UPDATED_RAISE_DATE);
    }

    @Test
    @Transactional
    public void getAllJobsByRaiseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where raiseDate is not null
        defaultJobShouldBeFound("raiseDate.specified=true");

        // Get all the jobList where raiseDate is null
        defaultJobShouldNotBeFound("raiseDate.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByRaiseDateContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where raiseDate contains DEFAULT_RAISE_DATE
        defaultJobShouldBeFound("raiseDate.contains=" + DEFAULT_RAISE_DATE);

        // Get all the jobList where raiseDate contains UPDATED_RAISE_DATE
        defaultJobShouldNotBeFound("raiseDate.contains=" + UPDATED_RAISE_DATE);
    }

    @Test
    @Transactional
    public void getAllJobsByRaiseDateNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where raiseDate does not contain DEFAULT_RAISE_DATE
        defaultJobShouldNotBeFound("raiseDate.doesNotContain=" + DEFAULT_RAISE_DATE);

        // Get all the jobList where raiseDate does not contain UPDATED_RAISE_DATE
        defaultJobShouldBeFound("raiseDate.doesNotContain=" + UPDATED_RAISE_DATE);
    }


    @Test
    @Transactional
    public void getAllJobsByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where priority equals to DEFAULT_PRIORITY
        defaultJobShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the jobList where priority equals to UPDATED_PRIORITY
        defaultJobShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllJobsByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where priority not equals to DEFAULT_PRIORITY
        defaultJobShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the jobList where priority not equals to UPDATED_PRIORITY
        defaultJobShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllJobsByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultJobShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the jobList where priority equals to UPDATED_PRIORITY
        defaultJobShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllJobsByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where priority is not null
        defaultJobShouldBeFound("priority.specified=true");

        // Get all the jobList where priority is null
        defaultJobShouldNotBeFound("priority.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByPriorityContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where priority contains DEFAULT_PRIORITY
        defaultJobShouldBeFound("priority.contains=" + DEFAULT_PRIORITY);

        // Get all the jobList where priority contains UPDATED_PRIORITY
        defaultJobShouldNotBeFound("priority.contains=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllJobsByPriorityNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where priority does not contain DEFAULT_PRIORITY
        defaultJobShouldNotBeFound("priority.doesNotContain=" + DEFAULT_PRIORITY);

        // Get all the jobList where priority does not contain UPDATED_PRIORITY
        defaultJobShouldBeFound("priority.doesNotContain=" + UPDATED_PRIORITY);
    }


    @Test
    @Transactional
    public void getAllJobsByFaultIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where fault equals to DEFAULT_FAULT
        defaultJobShouldBeFound("fault.equals=" + DEFAULT_FAULT);

        // Get all the jobList where fault equals to UPDATED_FAULT
        defaultJobShouldNotBeFound("fault.equals=" + UPDATED_FAULT);
    }

    @Test
    @Transactional
    public void getAllJobsByFaultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where fault not equals to DEFAULT_FAULT
        defaultJobShouldNotBeFound("fault.notEquals=" + DEFAULT_FAULT);

        // Get all the jobList where fault not equals to UPDATED_FAULT
        defaultJobShouldBeFound("fault.notEquals=" + UPDATED_FAULT);
    }

    @Test
    @Transactional
    public void getAllJobsByFaultIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where fault in DEFAULT_FAULT or UPDATED_FAULT
        defaultJobShouldBeFound("fault.in=" + DEFAULT_FAULT + "," + UPDATED_FAULT);

        // Get all the jobList where fault equals to UPDATED_FAULT
        defaultJobShouldNotBeFound("fault.in=" + UPDATED_FAULT);
    }

    @Test
    @Transactional
    public void getAllJobsByFaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where fault is not null
        defaultJobShouldBeFound("fault.specified=true");

        // Get all the jobList where fault is null
        defaultJobShouldNotBeFound("fault.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByFaultContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where fault contains DEFAULT_FAULT
        defaultJobShouldBeFound("fault.contains=" + DEFAULT_FAULT);

        // Get all the jobList where fault contains UPDATED_FAULT
        defaultJobShouldNotBeFound("fault.contains=" + UPDATED_FAULT);
    }

    @Test
    @Transactional
    public void getAllJobsByFaultNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where fault does not contain DEFAULT_FAULT
        defaultJobShouldNotBeFound("fault.doesNotContain=" + DEFAULT_FAULT);

        // Get all the jobList where fault does not contain UPDATED_FAULT
        defaultJobShouldBeFound("fault.doesNotContain=" + UPDATED_FAULT);
    }


    @Test
    @Transactional
    public void getAllJobsByInstructionsIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where instructions equals to DEFAULT_INSTRUCTIONS
        defaultJobShouldBeFound("instructions.equals=" + DEFAULT_INSTRUCTIONS);

        // Get all the jobList where instructions equals to UPDATED_INSTRUCTIONS
        defaultJobShouldNotBeFound("instructions.equals=" + UPDATED_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllJobsByInstructionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where instructions not equals to DEFAULT_INSTRUCTIONS
        defaultJobShouldNotBeFound("instructions.notEquals=" + DEFAULT_INSTRUCTIONS);

        // Get all the jobList where instructions not equals to UPDATED_INSTRUCTIONS
        defaultJobShouldBeFound("instructions.notEquals=" + UPDATED_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllJobsByInstructionsIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where instructions in DEFAULT_INSTRUCTIONS or UPDATED_INSTRUCTIONS
        defaultJobShouldBeFound("instructions.in=" + DEFAULT_INSTRUCTIONS + "," + UPDATED_INSTRUCTIONS);

        // Get all the jobList where instructions equals to UPDATED_INSTRUCTIONS
        defaultJobShouldNotBeFound("instructions.in=" + UPDATED_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllJobsByInstructionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where instructions is not null
        defaultJobShouldBeFound("instructions.specified=true");

        // Get all the jobList where instructions is null
        defaultJobShouldNotBeFound("instructions.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByInstructionsContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where instructions contains DEFAULT_INSTRUCTIONS
        defaultJobShouldBeFound("instructions.contains=" + DEFAULT_INSTRUCTIONS);

        // Get all the jobList where instructions contains UPDATED_INSTRUCTIONS
        defaultJobShouldNotBeFound("instructions.contains=" + UPDATED_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllJobsByInstructionsNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where instructions does not contain DEFAULT_INSTRUCTIONS
        defaultJobShouldNotBeFound("instructions.doesNotContain=" + DEFAULT_INSTRUCTIONS);

        // Get all the jobList where instructions does not contain UPDATED_INSTRUCTIONS
        defaultJobShouldBeFound("instructions.doesNotContain=" + UPDATED_INSTRUCTIONS);
    }


    @Test
    @Transactional
    public void getAllJobsByOccupierIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where occupier equals to DEFAULT_OCCUPIER
        defaultJobShouldBeFound("occupier.equals=" + DEFAULT_OCCUPIER);

        // Get all the jobList where occupier equals to UPDATED_OCCUPIER
        defaultJobShouldNotBeFound("occupier.equals=" + UPDATED_OCCUPIER);
    }

    @Test
    @Transactional
    public void getAllJobsByOccupierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where occupier not equals to DEFAULT_OCCUPIER
        defaultJobShouldNotBeFound("occupier.notEquals=" + DEFAULT_OCCUPIER);

        // Get all the jobList where occupier not equals to UPDATED_OCCUPIER
        defaultJobShouldBeFound("occupier.notEquals=" + UPDATED_OCCUPIER);
    }

    @Test
    @Transactional
    public void getAllJobsByOccupierIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where occupier in DEFAULT_OCCUPIER or UPDATED_OCCUPIER
        defaultJobShouldBeFound("occupier.in=" + DEFAULT_OCCUPIER + "," + UPDATED_OCCUPIER);

        // Get all the jobList where occupier equals to UPDATED_OCCUPIER
        defaultJobShouldNotBeFound("occupier.in=" + UPDATED_OCCUPIER);
    }

    @Test
    @Transactional
    public void getAllJobsByOccupierIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where occupier is not null
        defaultJobShouldBeFound("occupier.specified=true");

        // Get all the jobList where occupier is null
        defaultJobShouldNotBeFound("occupier.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByOccupierContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where occupier contains DEFAULT_OCCUPIER
        defaultJobShouldBeFound("occupier.contains=" + DEFAULT_OCCUPIER);

        // Get all the jobList where occupier contains UPDATED_OCCUPIER
        defaultJobShouldNotBeFound("occupier.contains=" + UPDATED_OCCUPIER);
    }

    @Test
    @Transactional
    public void getAllJobsByOccupierNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where occupier does not contain DEFAULT_OCCUPIER
        defaultJobShouldNotBeFound("occupier.doesNotContain=" + DEFAULT_OCCUPIER);

        // Get all the jobList where occupier does not contain UPDATED_OCCUPIER
        defaultJobShouldBeFound("occupier.doesNotContain=" + UPDATED_OCCUPIER);
    }


    @Test
    @Transactional
    public void getAllJobsByHomeTelIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where homeTel equals to DEFAULT_HOME_TEL
        defaultJobShouldBeFound("homeTel.equals=" + DEFAULT_HOME_TEL);

        // Get all the jobList where homeTel equals to UPDATED_HOME_TEL
        defaultJobShouldNotBeFound("homeTel.equals=" + UPDATED_HOME_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByHomeTelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where homeTel not equals to DEFAULT_HOME_TEL
        defaultJobShouldNotBeFound("homeTel.notEquals=" + DEFAULT_HOME_TEL);

        // Get all the jobList where homeTel not equals to UPDATED_HOME_TEL
        defaultJobShouldBeFound("homeTel.notEquals=" + UPDATED_HOME_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByHomeTelIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where homeTel in DEFAULT_HOME_TEL or UPDATED_HOME_TEL
        defaultJobShouldBeFound("homeTel.in=" + DEFAULT_HOME_TEL + "," + UPDATED_HOME_TEL);

        // Get all the jobList where homeTel equals to UPDATED_HOME_TEL
        defaultJobShouldNotBeFound("homeTel.in=" + UPDATED_HOME_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByHomeTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where homeTel is not null
        defaultJobShouldBeFound("homeTel.specified=true");

        // Get all the jobList where homeTel is null
        defaultJobShouldNotBeFound("homeTel.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByHomeTelContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where homeTel contains DEFAULT_HOME_TEL
        defaultJobShouldBeFound("homeTel.contains=" + DEFAULT_HOME_TEL);

        // Get all the jobList where homeTel contains UPDATED_HOME_TEL
        defaultJobShouldNotBeFound("homeTel.contains=" + UPDATED_HOME_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByHomeTelNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where homeTel does not contain DEFAULT_HOME_TEL
        defaultJobShouldNotBeFound("homeTel.doesNotContain=" + DEFAULT_HOME_TEL);

        // Get all the jobList where homeTel does not contain UPDATED_HOME_TEL
        defaultJobShouldBeFound("homeTel.doesNotContain=" + UPDATED_HOME_TEL);
    }


    @Test
    @Transactional
    public void getAllJobsByWorkTelIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where workTel equals to DEFAULT_WORK_TEL
        defaultJobShouldBeFound("workTel.equals=" + DEFAULT_WORK_TEL);

        // Get all the jobList where workTel equals to UPDATED_WORK_TEL
        defaultJobShouldNotBeFound("workTel.equals=" + UPDATED_WORK_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByWorkTelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where workTel not equals to DEFAULT_WORK_TEL
        defaultJobShouldNotBeFound("workTel.notEquals=" + DEFAULT_WORK_TEL);

        // Get all the jobList where workTel not equals to UPDATED_WORK_TEL
        defaultJobShouldBeFound("workTel.notEquals=" + UPDATED_WORK_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByWorkTelIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where workTel in DEFAULT_WORK_TEL or UPDATED_WORK_TEL
        defaultJobShouldBeFound("workTel.in=" + DEFAULT_WORK_TEL + "," + UPDATED_WORK_TEL);

        // Get all the jobList where workTel equals to UPDATED_WORK_TEL
        defaultJobShouldNotBeFound("workTel.in=" + UPDATED_WORK_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByWorkTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where workTel is not null
        defaultJobShouldBeFound("workTel.specified=true");

        // Get all the jobList where workTel is null
        defaultJobShouldNotBeFound("workTel.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByWorkTelContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where workTel contains DEFAULT_WORK_TEL
        defaultJobShouldBeFound("workTel.contains=" + DEFAULT_WORK_TEL);

        // Get all the jobList where workTel contains UPDATED_WORK_TEL
        defaultJobShouldNotBeFound("workTel.contains=" + UPDATED_WORK_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByWorkTelNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where workTel does not contain DEFAULT_WORK_TEL
        defaultJobShouldNotBeFound("workTel.doesNotContain=" + DEFAULT_WORK_TEL);

        // Get all the jobList where workTel does not contain UPDATED_WORK_TEL
        defaultJobShouldBeFound("workTel.doesNotContain=" + UPDATED_WORK_TEL);
    }


    @Test
    @Transactional
    public void getAllJobsByMobileTelIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where mobileTel equals to DEFAULT_MOBILE_TEL
        defaultJobShouldBeFound("mobileTel.equals=" + DEFAULT_MOBILE_TEL);

        // Get all the jobList where mobileTel equals to UPDATED_MOBILE_TEL
        defaultJobShouldNotBeFound("mobileTel.equals=" + UPDATED_MOBILE_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByMobileTelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where mobileTel not equals to DEFAULT_MOBILE_TEL
        defaultJobShouldNotBeFound("mobileTel.notEquals=" + DEFAULT_MOBILE_TEL);

        // Get all the jobList where mobileTel not equals to UPDATED_MOBILE_TEL
        defaultJobShouldBeFound("mobileTel.notEquals=" + UPDATED_MOBILE_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByMobileTelIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where mobileTel in DEFAULT_MOBILE_TEL or UPDATED_MOBILE_TEL
        defaultJobShouldBeFound("mobileTel.in=" + DEFAULT_MOBILE_TEL + "," + UPDATED_MOBILE_TEL);

        // Get all the jobList where mobileTel equals to UPDATED_MOBILE_TEL
        defaultJobShouldNotBeFound("mobileTel.in=" + UPDATED_MOBILE_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByMobileTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where mobileTel is not null
        defaultJobShouldBeFound("mobileTel.specified=true");

        // Get all the jobList where mobileTel is null
        defaultJobShouldNotBeFound("mobileTel.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByMobileTelContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where mobileTel contains DEFAULT_MOBILE_TEL
        defaultJobShouldBeFound("mobileTel.contains=" + DEFAULT_MOBILE_TEL);

        // Get all the jobList where mobileTel contains UPDATED_MOBILE_TEL
        defaultJobShouldNotBeFound("mobileTel.contains=" + UPDATED_MOBILE_TEL);
    }

    @Test
    @Transactional
    public void getAllJobsByMobileTelNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where mobileTel does not contain DEFAULT_MOBILE_TEL
        defaultJobShouldNotBeFound("mobileTel.doesNotContain=" + DEFAULT_MOBILE_TEL);

        // Get all the jobList where mobileTel does not contain UPDATED_MOBILE_TEL
        defaultJobShouldBeFound("mobileTel.doesNotContain=" + UPDATED_MOBILE_TEL);
    }


    @Test
    @Transactional
    public void getAllJobsByTenantNameIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where tenantName equals to DEFAULT_TENANT_NAME
        defaultJobShouldBeFound("tenantName.equals=" + DEFAULT_TENANT_NAME);

        // Get all the jobList where tenantName equals to UPDATED_TENANT_NAME
        defaultJobShouldNotBeFound("tenantName.equals=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllJobsByTenantNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where tenantName not equals to DEFAULT_TENANT_NAME
        defaultJobShouldNotBeFound("tenantName.notEquals=" + DEFAULT_TENANT_NAME);

        // Get all the jobList where tenantName not equals to UPDATED_TENANT_NAME
        defaultJobShouldBeFound("tenantName.notEquals=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllJobsByTenantNameIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where tenantName in DEFAULT_TENANT_NAME or UPDATED_TENANT_NAME
        defaultJobShouldBeFound("tenantName.in=" + DEFAULT_TENANT_NAME + "," + UPDATED_TENANT_NAME);

        // Get all the jobList where tenantName equals to UPDATED_TENANT_NAME
        defaultJobShouldNotBeFound("tenantName.in=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllJobsByTenantNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where tenantName is not null
        defaultJobShouldBeFound("tenantName.specified=true");

        // Get all the jobList where tenantName is null
        defaultJobShouldNotBeFound("tenantName.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByTenantNameContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where tenantName contains DEFAULT_TENANT_NAME
        defaultJobShouldBeFound("tenantName.contains=" + DEFAULT_TENANT_NAME);

        // Get all the jobList where tenantName contains UPDATED_TENANT_NAME
        defaultJobShouldNotBeFound("tenantName.contains=" + UPDATED_TENANT_NAME);
    }

    @Test
    @Transactional
    public void getAllJobsByTenantNameNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where tenantName does not contain DEFAULT_TENANT_NAME
        defaultJobShouldNotBeFound("tenantName.doesNotContain=" + DEFAULT_TENANT_NAME);

        // Get all the jobList where tenantName does not contain UPDATED_TENANT_NAME
        defaultJobShouldBeFound("tenantName.doesNotContain=" + UPDATED_TENANT_NAME);
    }


    @Test
    @Transactional
    public void getAllJobsByCompleteIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where complete equals to DEFAULT_COMPLETE
        defaultJobShouldBeFound("complete.equals=" + DEFAULT_COMPLETE);

        // Get all the jobList where complete equals to UPDATED_COMPLETE
        defaultJobShouldNotBeFound("complete.equals=" + UPDATED_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllJobsByCompleteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where complete not equals to DEFAULT_COMPLETE
        defaultJobShouldNotBeFound("complete.notEquals=" + DEFAULT_COMPLETE);

        // Get all the jobList where complete not equals to UPDATED_COMPLETE
        defaultJobShouldBeFound("complete.notEquals=" + UPDATED_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllJobsByCompleteIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where complete in DEFAULT_COMPLETE or UPDATED_COMPLETE
        defaultJobShouldBeFound("complete.in=" + DEFAULT_COMPLETE + "," + UPDATED_COMPLETE);

        // Get all the jobList where complete equals to UPDATED_COMPLETE
        defaultJobShouldNotBeFound("complete.in=" + UPDATED_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllJobsByCompleteIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where complete is not null
        defaultJobShouldBeFound("complete.specified=true");

        // Get all the jobList where complete is null
        defaultJobShouldNotBeFound("complete.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByCompleteContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where complete contains DEFAULT_COMPLETE
        defaultJobShouldBeFound("complete.contains=" + DEFAULT_COMPLETE);

        // Get all the jobList where complete contains UPDATED_COMPLETE
        defaultJobShouldNotBeFound("complete.contains=" + UPDATED_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllJobsByCompleteNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where complete does not contain DEFAULT_COMPLETE
        defaultJobShouldNotBeFound("complete.doesNotContain=" + DEFAULT_COMPLETE);

        // Get all the jobList where complete does not contain UPDATED_COMPLETE
        defaultJobShouldBeFound("complete.doesNotContain=" + UPDATED_COMPLETE);
    }


    @Test
    @Transactional
    public void getAllJobsByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where position equals to DEFAULT_POSITION
        defaultJobShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the jobList where position equals to UPDATED_POSITION
        defaultJobShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllJobsByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where position not equals to DEFAULT_POSITION
        defaultJobShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the jobList where position not equals to UPDATED_POSITION
        defaultJobShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllJobsByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultJobShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the jobList where position equals to UPDATED_POSITION
        defaultJobShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllJobsByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where position is not null
        defaultJobShouldBeFound("position.specified=true");

        // Get all the jobList where position is null
        defaultJobShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByPositionContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where position contains DEFAULT_POSITION
        defaultJobShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the jobList where position contains UPDATED_POSITION
        defaultJobShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllJobsByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where position does not contain DEFAULT_POSITION
        defaultJobShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the jobList where position does not contain UPDATED_POSITION
        defaultJobShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllJobsByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoice equals to DEFAULT_INVOICE
        defaultJobShouldBeFound("invoice.equals=" + DEFAULT_INVOICE);

        // Get all the jobList where invoice equals to UPDATED_INVOICE
        defaultJobShouldNotBeFound("invoice.equals=" + UPDATED_INVOICE);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoice not equals to DEFAULT_INVOICE
        defaultJobShouldNotBeFound("invoice.notEquals=" + DEFAULT_INVOICE);

        // Get all the jobList where invoice not equals to UPDATED_INVOICE
        defaultJobShouldBeFound("invoice.notEquals=" + UPDATED_INVOICE);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoice in DEFAULT_INVOICE or UPDATED_INVOICE
        defaultJobShouldBeFound("invoice.in=" + DEFAULT_INVOICE + "," + UPDATED_INVOICE);

        // Get all the jobList where invoice equals to UPDATED_INVOICE
        defaultJobShouldNotBeFound("invoice.in=" + UPDATED_INVOICE);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoice is not null
        defaultJobShouldBeFound("invoice.specified=true");

        // Get all the jobList where invoice is null
        defaultJobShouldNotBeFound("invoice.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByInvoiceContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoice contains DEFAULT_INVOICE
        defaultJobShouldBeFound("invoice.contains=" + DEFAULT_INVOICE);

        // Get all the jobList where invoice contains UPDATED_INVOICE
        defaultJobShouldNotBeFound("invoice.contains=" + UPDATED_INVOICE);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoice does not contain DEFAULT_INVOICE
        defaultJobShouldNotBeFound("invoice.doesNotContain=" + DEFAULT_INVOICE);

        // Get all the jobList where invoice does not contain UPDATED_INVOICE
        defaultJobShouldBeFound("invoice.doesNotContain=" + UPDATED_INVOICE);
    }


    @Test
    @Transactional
    public void getAllJobsByInvoiceDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoiceDetails equals to DEFAULT_INVOICE_DETAILS
        defaultJobShouldBeFound("invoiceDetails.equals=" + DEFAULT_INVOICE_DETAILS);

        // Get all the jobList where invoiceDetails equals to UPDATED_INVOICE_DETAILS
        defaultJobShouldNotBeFound("invoiceDetails.equals=" + UPDATED_INVOICE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoiceDetails not equals to DEFAULT_INVOICE_DETAILS
        defaultJobShouldNotBeFound("invoiceDetails.notEquals=" + DEFAULT_INVOICE_DETAILS);

        // Get all the jobList where invoiceDetails not equals to UPDATED_INVOICE_DETAILS
        defaultJobShouldBeFound("invoiceDetails.notEquals=" + UPDATED_INVOICE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoiceDetails in DEFAULT_INVOICE_DETAILS or UPDATED_INVOICE_DETAILS
        defaultJobShouldBeFound("invoiceDetails.in=" + DEFAULT_INVOICE_DETAILS + "," + UPDATED_INVOICE_DETAILS);

        // Get all the jobList where invoiceDetails equals to UPDATED_INVOICE_DETAILS
        defaultJobShouldNotBeFound("invoiceDetails.in=" + UPDATED_INVOICE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoiceDetails is not null
        defaultJobShouldBeFound("invoiceDetails.specified=true");

        // Get all the jobList where invoiceDetails is null
        defaultJobShouldNotBeFound("invoiceDetails.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobsByInvoiceDetailsContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoiceDetails contains DEFAULT_INVOICE_DETAILS
        defaultJobShouldBeFound("invoiceDetails.contains=" + DEFAULT_INVOICE_DETAILS);

        // Get all the jobList where invoiceDetails contains UPDATED_INVOICE_DETAILS
        defaultJobShouldNotBeFound("invoiceDetails.contains=" + UPDATED_INVOICE_DETAILS);
    }

    @Test
    @Transactional
    public void getAllJobsByInvoiceDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList where invoiceDetails does not contain DEFAULT_INVOICE_DETAILS
        defaultJobShouldNotBeFound("invoiceDetails.doesNotContain=" + DEFAULT_INVOICE_DETAILS);

        // Get all the jobList where invoiceDetails does not contain UPDATED_INVOICE_DETAILS
        defaultJobShouldBeFound("invoiceDetails.doesNotContain=" + UPDATED_INVOICE_DETAILS);
    }


    @Test
    @Transactional
    public void getAllJobsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);
        JobComment comments = JobCommentResourceIT.createEntity(em);
        em.persist(comments);
        em.flush();
        job.addComments(comments);
        jobRepository.saveAndFlush(job);
        Long commentsId = comments.getId();

        // Get all the jobList where comments equals to commentsId
        defaultJobShouldBeFound("commentsId.equals=" + commentsId);

        // Get all the jobList where comments equals to commentsId + 1
        defaultJobShouldNotBeFound("commentsId.equals=" + (commentsId + 1));
    }


    @Test
    @Transactional
    public void getAllJobsByLinesIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);
        JobLine lines = JobLineResourceIT.createEntity(em);
        em.persist(lines);
        em.flush();
        job.addLines(lines);
        jobRepository.saveAndFlush(job);
        Long linesId = lines.getId();

        // Get all the jobList where lines equals to linesId
        defaultJobShouldBeFound("linesId.equals=" + linesId);

        // Get all the jobList where lines equals to linesId + 1
        defaultJobShouldNotBeFound("linesId.equals=" + (linesId + 1));
    }


    @Test
    @Transactional
    public void getAllJobsByVisitsIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);
        JobVisit visits = JobVisitResourceIT.createEntity(em);
        em.persist(visits);
        em.flush();
        job.addVisits(visits);
        jobRepository.saveAndFlush(job);
        Long visitsId = visits.getId();

        // Get all the jobList where visits equals to visitsId
        defaultJobShouldBeFound("visitsId.equals=" + visitsId);

        // Get all the jobList where visits equals to visitsId + 1
        defaultJobShouldNotBeFound("visitsId.equals=" + (visitsId + 1));
    }


    @Test
    @Transactional
    public void getAllJobsByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);
        Client client = ClientResourceIT.createEntity(em);
        em.persist(client);
        em.flush();
        job.setClient(client);
        jobRepository.saveAndFlush(job);
        Long clientId = client.getId();

        // Get all the jobList where client equals to clientId
        defaultJobShouldBeFound("clientId.equals=" + clientId);

        // Get all the jobList where client equals to clientId + 1
        defaultJobShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobShouldBeFound(String filter) throws Exception {
        restJobMockMvc.perform(get("/api/jobs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportedBy").value(hasItem(DEFAULT_REPORTED_BY)))
            .andExpect(jsonPath("$.[*].clientOrderRef").value(hasItem(DEFAULT_CLIENT_ORDER_REF)))
            .andExpect(jsonPath("$.[*].raiseDate").value(hasItem(DEFAULT_RAISE_DATE)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].fault").value(hasItem(DEFAULT_FAULT)))
            .andExpect(jsonPath("$.[*].instructions").value(hasItem(DEFAULT_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].occupier").value(hasItem(DEFAULT_OCCUPIER)))
            .andExpect(jsonPath("$.[*].homeTel").value(hasItem(DEFAULT_HOME_TEL)))
            .andExpect(jsonPath("$.[*].workTel").value(hasItem(DEFAULT_WORK_TEL)))
            .andExpect(jsonPath("$.[*].mobileTel").value(hasItem(DEFAULT_MOBILE_TEL)))
            .andExpect(jsonPath("$.[*].tenantName").value(hasItem(DEFAULT_TENANT_NAME)))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].invoice").value(hasItem(DEFAULT_INVOICE)))
            .andExpect(jsonPath("$.[*].invoiceDetails").value(hasItem(DEFAULT_INVOICE_DETAILS)));

        // Check, that the count call also returns 1
        restJobMockMvc.perform(get("/api/jobs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobShouldNotBeFound(String filter) throws Exception {
        restJobMockMvc.perform(get("/api/jobs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobMockMvc.perform(get("/api/jobs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        Job updatedJob = jobRepository.findById(job.getId()).get();
        // Disconnect from session so that the updates on updatedJob are not directly saved in db
        em.detach(updatedJob);
        updatedJob
            .reportedBy(UPDATED_REPORTED_BY)
            .clientOrderRef(UPDATED_CLIENT_ORDER_REF)
            .raiseDate(UPDATED_RAISE_DATE)
            .priority(UPDATED_PRIORITY)
            .fault(UPDATED_FAULT)
            .instructions(UPDATED_INSTRUCTIONS)
            .occupier(UPDATED_OCCUPIER)
            .homeTel(UPDATED_HOME_TEL)
            .workTel(UPDATED_WORK_TEL)
            .mobileTel(UPDATED_MOBILE_TEL)
            .tenantName(UPDATED_TENANT_NAME)
            .complete(UPDATED_COMPLETE)
            .position(UPDATED_POSITION)
            .invoice(UPDATED_INVOICE)
            .invoiceDetails(UPDATED_INVOICE_DETAILS);
        JobDTO jobDTO = jobMapper.toDto(updatedJob);

        restJobMockMvc.perform(put("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobDTO)))
            .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getReportedBy()).isEqualTo(UPDATED_REPORTED_BY);
        assertThat(testJob.getClientOrderRef()).isEqualTo(UPDATED_CLIENT_ORDER_REF);
        assertThat(testJob.getRaiseDate()).isEqualTo(UPDATED_RAISE_DATE);
        assertThat(testJob.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testJob.getFault()).isEqualTo(UPDATED_FAULT);
        assertThat(testJob.getInstructions()).isEqualTo(UPDATED_INSTRUCTIONS);
        assertThat(testJob.getOccupier()).isEqualTo(UPDATED_OCCUPIER);
        assertThat(testJob.getHomeTel()).isEqualTo(UPDATED_HOME_TEL);
        assertThat(testJob.getWorkTel()).isEqualTo(UPDATED_WORK_TEL);
        assertThat(testJob.getMobileTel()).isEqualTo(UPDATED_MOBILE_TEL);
        assertThat(testJob.getTenantName()).isEqualTo(UPDATED_TENANT_NAME);
        assertThat(testJob.getComplete()).isEqualTo(UPDATED_COMPLETE);
        assertThat(testJob.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testJob.getInvoice()).isEqualTo(UPDATED_INVOICE);
        assertThat(testJob.getInvoiceDetails()).isEqualTo(UPDATED_INVOICE_DETAILS);
    }

    @Test
    @Transactional
    public void updateNonExistingJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Create the Job
        JobDTO jobDTO = jobMapper.toDto(job);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobMockMvc.perform(put("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Delete the job
        restJobMockMvc.perform(delete("/api/jobs/{id}", job.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
