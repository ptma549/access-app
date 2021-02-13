package com.access.myapp.web.rest;

import com.access.myapp.service.TownService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.TownDTO;
import com.access.myapp.service.dto.TownCriteria;
import com.access.myapp.service.TownQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.Town}.
 */
@RestController
@RequestMapping("/api")
public class TownResource {

    private final Logger log = LoggerFactory.getLogger(TownResource.class);

    private static final String ENTITY_NAME = "town";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownService townService;

    private final TownQueryService townQueryService;

    public TownResource(TownService townService, TownQueryService townQueryService) {
        this.townService = townService;
        this.townQueryService = townQueryService;
    }

    /**
     * {@code POST  /towns} : Create a new town.
     *
     * @param townDTO the townDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townDTO, or with status {@code 400 (Bad Request)} if the town has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/towns")
    public ResponseEntity<TownDTO> createTown(@RequestBody TownDTO townDTO) throws URISyntaxException {
        log.debug("REST request to save Town : {}", townDTO);
        if (townDTO.getId() != null) {
            throw new BadRequestAlertException("A new town cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TownDTO result = townService.save(townDTO);
        return ResponseEntity.created(new URI("/api/towns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /towns} : Updates an existing town.
     *
     * @param townDTO the townDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townDTO,
     * or with status {@code 400 (Bad Request)} if the townDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/towns")
    public ResponseEntity<TownDTO> updateTown(@RequestBody TownDTO townDTO) throws URISyntaxException {
        log.debug("REST request to update Town : {}", townDTO);
        if (townDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TownDTO result = townService.save(townDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /towns} : get all the towns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of towns in body.
     */
    @GetMapping("/towns")
    public ResponseEntity<List<TownDTO>> getAllTowns(TownCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Towns by criteria: {}", criteria);
        Page<TownDTO> page = townQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /towns/count} : count all the towns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/towns/count")
    public ResponseEntity<Long> countTowns(TownCriteria criteria) {
        log.debug("REST request to count Towns by criteria: {}", criteria);
        return ResponseEntity.ok().body(townQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /towns/:id} : get the "id" town.
     *
     * @param id the id of the townDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/towns/{id}")
    public ResponseEntity<TownDTO> getTown(@PathVariable Long id) {
        log.debug("REST request to get Town : {}", id);
        Optional<TownDTO> townDTO = townService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townDTO);
    }

    /**
     * {@code DELETE  /towns/:id} : delete the "id" town.
     *
     * @param id the id of the townDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/towns/{id}")
    public ResponseEntity<Void> deleteTown(@PathVariable Long id) {
        log.debug("REST request to delete Town : {}", id);
        townService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
