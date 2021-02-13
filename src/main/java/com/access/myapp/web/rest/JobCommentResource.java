package com.access.myapp.web.rest;

import com.access.myapp.service.JobCommentService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.JobCommentDTO;
import com.access.myapp.service.dto.JobCommentCriteria;
import com.access.myapp.service.JobCommentQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.access.myapp.domain.JobComment}.
 */
@RestController
@RequestMapping("/api")
public class JobCommentResource {

    private final Logger log = LoggerFactory.getLogger(JobCommentResource.class);

    private static final String ENTITY_NAME = "jobComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobCommentService jobCommentService;

    private final JobCommentQueryService jobCommentQueryService;

    public JobCommentResource(JobCommentService jobCommentService, JobCommentQueryService jobCommentQueryService) {
        this.jobCommentService = jobCommentService;
        this.jobCommentQueryService = jobCommentQueryService;
    }

    /**
     * {@code POST  /job-comments} : Create a new jobComment.
     *
     * @param jobCommentDTO the jobCommentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobCommentDTO, or with status {@code 400 (Bad Request)} if the jobComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-comments")
    public ResponseEntity<JobCommentDTO> createJobComment(@RequestBody JobCommentDTO jobCommentDTO) throws URISyntaxException {
        log.debug("REST request to save JobComment : {}", jobCommentDTO);
        if (jobCommentDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobCommentDTO result = jobCommentService.save(jobCommentDTO);
        return ResponseEntity.created(new URI("/api/job-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-comments} : Updates an existing jobComment.
     *
     * @param jobCommentDTO the jobCommentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobCommentDTO,
     * or with status {@code 400 (Bad Request)} if the jobCommentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobCommentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-comments")
    public ResponseEntity<JobCommentDTO> updateJobComment(@RequestBody JobCommentDTO jobCommentDTO) throws URISyntaxException {
        log.debug("REST request to update JobComment : {}", jobCommentDTO);
        if (jobCommentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobCommentDTO result = jobCommentService.save(jobCommentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobCommentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-comments} : get all the jobComments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobComments in body.
     */
    @GetMapping("/job-comments")
    public ResponseEntity<List<JobCommentDTO>> getAllJobComments(JobCommentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JobComments by criteria: {}", criteria);
        Page<JobCommentDTO> page = jobCommentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-comments/count} : count all the jobComments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/job-comments/count")
    public ResponseEntity<Long> countJobComments(JobCommentCriteria criteria) {
        log.debug("REST request to count JobComments by criteria: {}", criteria);
        return ResponseEntity.ok().body(jobCommentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /job-comments/:id} : get the "id" jobComment.
     *
     * @param id the id of the jobCommentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobCommentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-comments/{id}")
    public ResponseEntity<JobCommentDTO> getJobComment(@PathVariable Long id) {
        log.debug("REST request to get JobComment : {}", id);
        Optional<JobCommentDTO> jobCommentDTO = jobCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobCommentDTO);
    }

    /**
     * {@code DELETE  /job-comments/:id} : delete the "id" jobComment.
     *
     * @param id the id of the jobCommentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-comments/{id}")
    public ResponseEntity<Void> deleteJobComment(@PathVariable Long id) {
        log.debug("REST request to delete JobComment : {}", id);
        jobCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
