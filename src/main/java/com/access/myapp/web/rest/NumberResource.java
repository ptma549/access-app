package com.access.myapp.web.rest;

import com.access.myapp.service.NumberService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.NumberDTO;
import com.access.myapp.service.dto.NumberCriteria;
import com.access.myapp.service.NumberQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.Number}.
 */
@RestController
@RequestMapping("/api")
public class NumberResource {

    private final Logger log = LoggerFactory.getLogger(NumberResource.class);

    private static final String ENTITY_NAME = "number";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NumberService numberService;

    private final NumberQueryService numberQueryService;

    public NumberResource(NumberService numberService, NumberQueryService numberQueryService) {
        this.numberService = numberService;
        this.numberQueryService = numberQueryService;
    }

    /**
     * {@code POST  /numbers} : Create a new number.
     *
     * @param numberDTO the numberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new numberDTO, or with status {@code 400 (Bad Request)} if the number has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/numbers")
    public ResponseEntity<NumberDTO> createNumber(@RequestBody NumberDTO numberDTO) throws URISyntaxException {
        log.debug("REST request to save Number : {}", numberDTO);
        if (numberDTO.getId() != null) {
            throw new BadRequestAlertException("A new number cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumberDTO result = numberService.save(numberDTO);
        return ResponseEntity.created(new URI("/api/numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /numbers} : Updates an existing number.
     *
     * @param numberDTO the numberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numberDTO,
     * or with status {@code 400 (Bad Request)} if the numberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the numberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/numbers")
    public ResponseEntity<NumberDTO> updateNumber(@RequestBody NumberDTO numberDTO) throws URISyntaxException {
        log.debug("REST request to update Number : {}", numberDTO);
        if (numberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NumberDTO result = numberService.save(numberDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, numberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /numbers} : get all the numbers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of numbers in body.
     */
    @GetMapping("/numbers")
    public ResponseEntity<List<NumberDTO>> getAllNumbers(NumberCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Numbers by criteria: {}", criteria);
        Page<NumberDTO> page = numberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /numbers/count} : count all the numbers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/numbers/count")
    public ResponseEntity<Long> countNumbers(NumberCriteria criteria) {
        log.debug("REST request to count Numbers by criteria: {}", criteria);
        return ResponseEntity.ok().body(numberQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /numbers/:id} : get the "id" number.
     *
     * @param id the id of the numberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the numberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/numbers/{id}")
    public ResponseEntity<NumberDTO> getNumber(@PathVariable Long id) {
        log.debug("REST request to get Number : {}", id);
        Optional<NumberDTO> numberDTO = numberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(numberDTO);
    }

    /**
     * {@code DELETE  /numbers/:id} : delete the "id" number.
     *
     * @param id the id of the numberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/numbers/{id}")
    public ResponseEntity<Void> deleteNumber(@PathVariable Long id) {
        log.debug("REST request to delete Number : {}", id);
        numberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
