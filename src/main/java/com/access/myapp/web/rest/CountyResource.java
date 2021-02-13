package com.access.myapp.web.rest;

import com.access.myapp.service.CountyService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.CountyDTO;
import com.access.myapp.service.dto.CountyCriteria;
import com.access.myapp.service.CountyQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.County}.
 */
@RestController
@RequestMapping("/api")
public class CountyResource {

    private final Logger log = LoggerFactory.getLogger(CountyResource.class);

    private static final String ENTITY_NAME = "county";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountyService countyService;

    private final CountyQueryService countyQueryService;

    public CountyResource(CountyService countyService, CountyQueryService countyQueryService) {
        this.countyService = countyService;
        this.countyQueryService = countyQueryService;
    }

    /**
     * {@code POST  /counties} : Create a new county.
     *
     * @param countyDTO the countyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countyDTO, or with status {@code 400 (Bad Request)} if the county has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/counties")
    public ResponseEntity<CountyDTO> createCounty(@RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to save County : {}", countyDTO);
        if (countyDTO.getId() != null) {
            throw new BadRequestAlertException("A new county cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountyDTO result = countyService.save(countyDTO);
        return ResponseEntity.created(new URI("/api/counties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /counties} : Updates an existing county.
     *
     * @param countyDTO the countyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countyDTO,
     * or with status {@code 400 (Bad Request)} if the countyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/counties")
    public ResponseEntity<CountyDTO> updateCounty(@RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to update County : {}", countyDTO);
        if (countyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CountyDTO result = countyService.save(countyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, countyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /counties} : get all the counties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of counties in body.
     */
    @GetMapping("/counties")
    public ResponseEntity<List<CountyDTO>> getAllCounties(CountyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Counties by criteria: {}", criteria);
        Page<CountyDTO> page = countyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /counties/count} : count all the counties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/counties/count")
    public ResponseEntity<Long> countCounties(CountyCriteria criteria) {
        log.debug("REST request to count Counties by criteria: {}", criteria);
        return ResponseEntity.ok().body(countyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /counties/:id} : get the "id" county.
     *
     * @param id the id of the countyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/counties/{id}")
    public ResponseEntity<CountyDTO> getCounty(@PathVariable Long id) {
        log.debug("REST request to get County : {}", id);
        Optional<CountyDTO> countyDTO = countyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countyDTO);
    }

    /**
     * {@code DELETE  /counties/:id} : delete the "id" county.
     *
     * @param id the id of the countyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/counties/{id}")
    public ResponseEntity<Void> deleteCounty(@PathVariable Long id) {
        log.debug("REST request to delete County : {}", id);
        countyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
