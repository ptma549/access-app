package com.access.myapp.web.rest;

import com.access.myapp.service.PriorityService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.PriorityDTO;
import com.access.myapp.service.dto.PriorityCriteria;
import com.access.myapp.service.PriorityQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.Priority}.
 */
@RestController
@RequestMapping("/api")
public class PriorityResource {

    private final Logger log = LoggerFactory.getLogger(PriorityResource.class);

    private static final String ENTITY_NAME = "priority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriorityService priorityService;

    private final PriorityQueryService priorityQueryService;

    public PriorityResource(PriorityService priorityService, PriorityQueryService priorityQueryService) {
        this.priorityService = priorityService;
        this.priorityQueryService = priorityQueryService;
    }

    /**
     * {@code POST  /priorities} : Create a new priority.
     *
     * @param priorityDTO the priorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priorityDTO, or with status {@code 400 (Bad Request)} if the priority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/priorities")
    public ResponseEntity<PriorityDTO> createPriority(@RequestBody PriorityDTO priorityDTO) throws URISyntaxException {
        log.debug("REST request to save Priority : {}", priorityDTO);
        if (priorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new priority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriorityDTO result = priorityService.save(priorityDTO);
        return ResponseEntity.created(new URI("/api/priorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /priorities} : Updates an existing priority.
     *
     * @param priorityDTO the priorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priorityDTO,
     * or with status {@code 400 (Bad Request)} if the priorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/priorities")
    public ResponseEntity<PriorityDTO> updatePriority(@RequestBody PriorityDTO priorityDTO) throws URISyntaxException {
        log.debug("REST request to update Priority : {}", priorityDTO);
        if (priorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PriorityDTO result = priorityService.save(priorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, priorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /priorities} : get all the priorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priorities in body.
     */
    @GetMapping("/priorities")
    public ResponseEntity<List<PriorityDTO>> getAllPriorities(PriorityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Priorities by criteria: {}", criteria);
        Page<PriorityDTO> page = priorityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /priorities/count} : count all the priorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/priorities/count")
    public ResponseEntity<Long> countPriorities(PriorityCriteria criteria) {
        log.debug("REST request to count Priorities by criteria: {}", criteria);
        return ResponseEntity.ok().body(priorityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /priorities/:id} : get the "id" priority.
     *
     * @param id the id of the priorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/priorities/{id}")
    public ResponseEntity<PriorityDTO> getPriority(@PathVariable Long id) {
        log.debug("REST request to get Priority : {}", id);
        Optional<PriorityDTO> priorityDTO = priorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priorityDTO);
    }

    /**
     * {@code DELETE  /priorities/:id} : delete the "id" priority.
     *
     * @param id the id of the priorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/priorities/{id}")
    public ResponseEntity<Void> deletePriority(@PathVariable Long id) {
        log.debug("REST request to delete Priority : {}", id);
        priorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
