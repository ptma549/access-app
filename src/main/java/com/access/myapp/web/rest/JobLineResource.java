package com.access.myapp.web.rest;

import com.access.myapp.service.JobLineService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.JobLineDTO;
import com.access.myapp.service.dto.JobLineCriteria;
import com.access.myapp.service.JobLineQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.JobLine}.
 */
@RestController
@RequestMapping("/api")
public class JobLineResource {

    private final Logger log = LoggerFactory.getLogger(JobLineResource.class);

    private static final String ENTITY_NAME = "jobLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobLineService jobLineService;

    private final JobLineQueryService jobLineQueryService;

    public JobLineResource(JobLineService jobLineService, JobLineQueryService jobLineQueryService) {
        this.jobLineService = jobLineService;
        this.jobLineQueryService = jobLineQueryService;
    }

    /**
     * {@code POST  /job-lines} : Create a new jobLine.
     *
     * @param jobLineDTO the jobLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobLineDTO, or with status {@code 400 (Bad Request)} if the jobLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-lines")
    public ResponseEntity<JobLineDTO> createJobLine(@RequestBody JobLineDTO jobLineDTO) throws URISyntaxException {
        log.debug("REST request to save JobLine : {}", jobLineDTO);
        if (jobLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobLineDTO result = jobLineService.save(jobLineDTO);
        return ResponseEntity.created(new URI("/api/job-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-lines} : Updates an existing jobLine.
     *
     * @param jobLineDTO the jobLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobLineDTO,
     * or with status {@code 400 (Bad Request)} if the jobLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-lines")
    public ResponseEntity<JobLineDTO> updateJobLine(@RequestBody JobLineDTO jobLineDTO) throws URISyntaxException {
        log.debug("REST request to update JobLine : {}", jobLineDTO);
        if (jobLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobLineDTO result = jobLineService.save(jobLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-lines} : get all the jobLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobLines in body.
     */
    @GetMapping("/job-lines")
    public ResponseEntity<List<JobLineDTO>> getAllJobLines(JobLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get JobLines by criteria: {}", criteria);
        Page<JobLineDTO> page = jobLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-lines/count} : count all the jobLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/job-lines/count")
    public ResponseEntity<Long> countJobLines(JobLineCriteria criteria) {
        log.debug("REST request to count JobLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(jobLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /job-lines/:id} : get the "id" jobLine.
     *
     * @param id the id of the jobLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-lines/{id}")
    public ResponseEntity<JobLineDTO> getJobLine(@PathVariable Long id) {
        log.debug("REST request to get JobLine : {}", id);
        Optional<JobLineDTO> jobLineDTO = jobLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobLineDTO);
    }

    /**
     * {@code DELETE  /job-lines/:id} : delete the "id" jobLine.
     *
     * @param id the id of the jobLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-lines/{id}")
    public ResponseEntity<Void> deleteJobLine(@PathVariable Long id) {
        log.debug("REST request to delete JobLine : {}", id);
        jobLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
