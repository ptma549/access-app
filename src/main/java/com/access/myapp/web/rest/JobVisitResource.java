package com.access.myapp.web.rest;

import com.access.myapp.service.JobVisitService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.JobVisitDTO;
import com.access.myapp.service.dto.JobVisitCriteria;
import com.access.myapp.service.JobVisitQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.JobVisit}.
 */
@RestController
@RequestMapping("/api")
public class JobVisitResource {

    private final Logger log = LoggerFactory.getLogger(JobVisitResource.class);

    private static final String ENTITY_NAME = "jobVisit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobVisitService jobVisitService;

    private final JobVisitQueryService jobVisitQueryService;

    public JobVisitResource(JobVisitService jobVisitService, JobVisitQueryService jobVisitQueryService) {
        this.jobVisitService = jobVisitService;
        this.jobVisitQueryService = jobVisitQueryService;
    }

    /**
     * {@code POST  /job-visits} : Create a new jobVisit.
     *
     * @param jobVisitDTO the jobVisitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobVisitDTO, or with status {@code 400 (Bad Request)} if the jobVisit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-visits")
    public ResponseEntity<JobVisitDTO> createJobVisit(@RequestBody JobVisitDTO jobVisitDTO) throws URISyntaxException {
        log.debug("REST request to save JobVisit : {}", jobVisitDTO);
        if (jobVisitDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobVisitDTO result = jobVisitService.save(jobVisitDTO);
        return ResponseEntity.created(new URI("/api/job-visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-visits} : Updates an existing jobVisit.
     *
     * @param jobVisitDTO the jobVisitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobVisitDTO,
     * or with status {@code 400 (Bad Request)} if the jobVisitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobVisitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-visits")
    public ResponseEntity<JobVisitDTO> updateJobVisit(@RequestBody JobVisitDTO jobVisitDTO) throws URISyntaxException {
        log.debug("REST request to update JobVisit : {}", jobVisitDTO);
        if (jobVisitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobVisitDTO result = jobVisitService.save(jobVisitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobVisitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-visits} : get all the jobVisits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobVisits in body.
     */
    @GetMapping("/job-visits")
    public ResponseEntity<List<JobVisitDTO>> getAllJobVisits(JobVisitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JobVisits by criteria: {}", criteria);
        Page<JobVisitDTO> page = jobVisitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-visits/count} : count all the jobVisits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/job-visits/count")
    public ResponseEntity<Long> countJobVisits(JobVisitCriteria criteria) {
        log.debug("REST request to count JobVisits by criteria: {}", criteria);
        return ResponseEntity.ok().body(jobVisitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /job-visits/:id} : get the "id" jobVisit.
     *
     * @param id the id of the jobVisitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobVisitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-visits/{id}")
    public ResponseEntity<JobVisitDTO> getJobVisit(@PathVariable Long id) {
        log.debug("REST request to get JobVisit : {}", id);
        Optional<JobVisitDTO> jobVisitDTO = jobVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobVisitDTO);
    }

    /**
     * {@code DELETE  /job-visits/:id} : delete the "id" jobVisit.
     *
     * @param id the id of the jobVisitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-visits/{id}")
    public ResponseEntity<Void> deleteJobVisit(@PathVariable Long id) {
        log.debug("REST request to delete JobVisit : {}", id);
        jobVisitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
