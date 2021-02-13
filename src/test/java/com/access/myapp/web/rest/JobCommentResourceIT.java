package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.JobComment;
import com.access.myapp.domain.Job;
import com.access.myapp.repository.JobCommentRepository;
import com.access.myapp.service.JobCommentService;
import com.access.myapp.service.dto.JobCommentDTO;
import com.access.myapp.service.mapper.JobCommentMapper;
import com.access.myapp.service.dto.JobCommentCriteria;
import com.access.myapp.service.JobCommentQueryService;

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
 * Integration tests for the {@link JobCommentResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobCommentResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private JobCommentRepository jobCommentRepository;

    @Autowired
    private JobCommentMapper jobCommentMapper;

    @Autowired
    private JobCommentService jobCommentService;

    @Autowired
    private JobCommentQueryService jobCommentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobCommentMockMvc;

    private JobComment jobComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobComment createEntity(EntityManager em) {
        JobComment jobComment = new JobComment()
            .comment(DEFAULT_COMMENT);
        return jobComment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobComment createUpdatedEntity(EntityManager em) {
        JobComment jobComment = new JobComment()
            .comment(UPDATED_COMMENT);
        return jobComment;
    }

    @BeforeEach
    public void initTest() {
        jobComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobComment() throws Exception {
        int databaseSizeBeforeCreate = jobCommentRepository.findAll().size();
        // Create the JobComment
        JobCommentDTO jobCommentDTO = jobCommentMapper.toDto(jobComment);
        restJobCommentMockMvc.perform(post("/api/job-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobCommentDTO)))
            .andExpect(status().isCreated());

        // Validate the JobComment in the database
        List<JobComment> jobCommentList = jobCommentRepository.findAll();
        assertThat(jobCommentList).hasSize(databaseSizeBeforeCreate + 1);
        JobComment testJobComment = jobCommentList.get(jobCommentList.size() - 1);
        assertThat(testJobComment.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createJobCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobCommentRepository.findAll().size();

        // Create the JobComment with an existing ID
        jobComment.setId(1L);
        JobCommentDTO jobCommentDTO = jobCommentMapper.toDto(jobComment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobCommentMockMvc.perform(post("/api/job-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobComment in the database
        List<JobComment> jobCommentList = jobCommentRepository.findAll();
        assertThat(jobCommentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobComments() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get all the jobCommentList
        restJobCommentMockMvc.perform(get("/api/job-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getJobComment() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get the jobComment
        restJobCommentMockMvc.perform(get("/api/job-comments/{id}", jobComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }


    @Test
    @Transactional
    public void getJobCommentsByIdFiltering() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        Long id = jobComment.getId();

        defaultJobCommentShouldBeFound("id.equals=" + id);
        defaultJobCommentShouldNotBeFound("id.notEquals=" + id);

        defaultJobCommentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultJobCommentShouldNotBeFound("id.greaterThan=" + id);

        defaultJobCommentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultJobCommentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllJobCommentsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get all the jobCommentList where comment equals to DEFAULT_COMMENT
        defaultJobCommentShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the jobCommentList where comment equals to UPDATED_COMMENT
        defaultJobCommentShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllJobCommentsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get all the jobCommentList where comment not equals to DEFAULT_COMMENT
        defaultJobCommentShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the jobCommentList where comment not equals to UPDATED_COMMENT
        defaultJobCommentShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllJobCommentsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get all the jobCommentList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultJobCommentShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the jobCommentList where comment equals to UPDATED_COMMENT
        defaultJobCommentShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllJobCommentsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get all the jobCommentList where comment is not null
        defaultJobCommentShouldBeFound("comment.specified=true");

        // Get all the jobCommentList where comment is null
        defaultJobCommentShouldNotBeFound("comment.specified=false");
    }
                @Test
    @Transactional
    public void getAllJobCommentsByCommentContainsSomething() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get all the jobCommentList where comment contains DEFAULT_COMMENT
        defaultJobCommentShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the jobCommentList where comment contains UPDATED_COMMENT
        defaultJobCommentShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllJobCommentsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        // Get all the jobCommentList where comment does not contain DEFAULT_COMMENT
        defaultJobCommentShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the jobCommentList where comment does not contain UPDATED_COMMENT
        defaultJobCommentShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }


    @Test
    @Transactional
    public void getAllJobCommentsByJobIsEqualToSomething() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);
        Job job = JobResourceIT.createEntity(em);
        em.persist(job);
        em.flush();
        jobComment.setJob(job);
        jobCommentRepository.saveAndFlush(jobComment);
        Long jobId = job.getId();

        // Get all the jobCommentList where job equals to jobId
        defaultJobCommentShouldBeFound("jobId.equals=" + jobId);

        // Get all the jobCommentList where job equals to jobId + 1
        defaultJobCommentShouldNotBeFound("jobId.equals=" + (jobId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJobCommentShouldBeFound(String filter) throws Exception {
        restJobCommentMockMvc.perform(get("/api/job-comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));

        // Check, that the count call also returns 1
        restJobCommentMockMvc.perform(get("/api/job-comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJobCommentShouldNotBeFound(String filter) throws Exception {
        restJobCommentMockMvc.perform(get("/api/job-comments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJobCommentMockMvc.perform(get("/api/job-comments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingJobComment() throws Exception {
        // Get the jobComment
        restJobCommentMockMvc.perform(get("/api/job-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobComment() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        int databaseSizeBeforeUpdate = jobCommentRepository.findAll().size();

        // Update the jobComment
        JobComment updatedJobComment = jobCommentRepository.findById(jobComment.getId()).get();
        // Disconnect from session so that the updates on updatedJobComment are not directly saved in db
        em.detach(updatedJobComment);
        updatedJobComment
            .comment(UPDATED_COMMENT);
        JobCommentDTO jobCommentDTO = jobCommentMapper.toDto(updatedJobComment);

        restJobCommentMockMvc.perform(put("/api/job-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobCommentDTO)))
            .andExpect(status().isOk());

        // Validate the JobComment in the database
        List<JobComment> jobCommentList = jobCommentRepository.findAll();
        assertThat(jobCommentList).hasSize(databaseSizeBeforeUpdate);
        JobComment testJobComment = jobCommentList.get(jobCommentList.size() - 1);
        assertThat(testJobComment.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingJobComment() throws Exception {
        int databaseSizeBeforeUpdate = jobCommentRepository.findAll().size();

        // Create the JobComment
        JobCommentDTO jobCommentDTO = jobCommentMapper.toDto(jobComment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobCommentMockMvc.perform(put("/api/job-comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobCommentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobComment in the database
        List<JobComment> jobCommentList = jobCommentRepository.findAll();
        assertThat(jobCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobComment() throws Exception {
        // Initialize the database
        jobCommentRepository.saveAndFlush(jobComment);

        int databaseSizeBeforeDelete = jobCommentRepository.findAll().size();

        // Delete the jobComment
        restJobCommentMockMvc.perform(delete("/api/job-comments/{id}", jobComment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobComment> jobCommentList = jobCommentRepository.findAll();
        assertThat(jobCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
