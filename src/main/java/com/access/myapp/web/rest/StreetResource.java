package com.access.myapp.web.rest;

import com.access.myapp.service.StreetService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.StreetDTO;
import com.access.myapp.service.dto.StreetCriteria;
import com.access.myapp.service.StreetQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.Street}.
 */
@RestController
@RequestMapping("/api")
public class StreetResource {

    private final Logger log = LoggerFactory.getLogger(StreetResource.class);

    private static final String ENTITY_NAME = "street";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StreetService streetService;

    private final StreetQueryService streetQueryService;

    public StreetResource(StreetService streetService, StreetQueryService streetQueryService) {
        this.streetService = streetService;
        this.streetQueryService = streetQueryService;
    }

    /**
     * {@code POST  /streets} : Create a new street.
     *
     * @param streetDTO the streetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new streetDTO, or with status {@code 400 (Bad Request)} if the street has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/streets")
    public ResponseEntity<StreetDTO> createStreet(@RequestBody StreetDTO streetDTO) throws URISyntaxException {
        log.debug("REST request to save Street : {}", streetDTO);
        if (streetDTO.getId() != null) {
            throw new BadRequestAlertException("A new street cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StreetDTO result = streetService.save(streetDTO);
        return ResponseEntity.created(new URI("/api/streets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /streets} : Updates an existing street.
     *
     * @param streetDTO the streetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated streetDTO,
     * or with status {@code 400 (Bad Request)} if the streetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the streetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/streets")
    public ResponseEntity<StreetDTO> updateStreet(@RequestBody StreetDTO streetDTO) throws URISyntaxException {
        log.debug("REST request to update Street : {}", streetDTO);
        if (streetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StreetDTO result = streetService.save(streetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, streetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /streets} : get all the streets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of streets in body.
     */
    @GetMapping("/streets")
    public ResponseEntity<List<StreetDTO>> getAllStreets(StreetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Streets by criteria: {}", criteria);
        Page<StreetDTO> page = streetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /streets/count} : count all the streets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/streets/count")
    public ResponseEntity<Long> countStreets(StreetCriteria criteria) {
        log.debug("REST request to count Streets by criteria: {}", criteria);
        return ResponseEntity.ok().body(streetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /streets/:id} : get the "id" street.
     *
     * @param id the id of the streetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the streetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/streets/{id}")
    public ResponseEntity<StreetDTO> getStreet(@PathVariable Long id) {
        log.debug("REST request to get Street : {}", id);
        Optional<StreetDTO> streetDTO = streetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(streetDTO);
    }

    /**
     * {@code DELETE  /streets/:id} : delete the "id" street.
     *
     * @param id the id of the streetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/streets/{id}")
    public ResponseEntity<Void> deleteStreet(@PathVariable Long id) {
        log.debug("REST request to delete Street : {}", id);
        streetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
