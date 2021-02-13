package com.access.myapp.web.rest;

import com.access.myapp.service.PtmAccountService;
import com.access.myapp.web.rest.errors.BadRequestAlertException;
import com.access.myapp.service.dto.PtmAccountDTO;
import com.access.myapp.service.dto.PtmAccountCriteria;
import com.access.myapp.service.PtmAccountQueryService;

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
 * REST controller for managing {@link com.access.myapp.domain.PtmAccount}.
 */
@RestController
@RequestMapping("/api")
public class PtmAccountResource {

    private final Logger log = LoggerFactory.getLogger(PtmAccountResource.class);

    private static final String ENTITY_NAME = "ptmAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PtmAccountService ptmAccountService;

    private final PtmAccountQueryService ptmAccountQueryService;

    public PtmAccountResource(PtmAccountService ptmAccountService, PtmAccountQueryService ptmAccountQueryService) {
        this.ptmAccountService = ptmAccountService;
        this.ptmAccountQueryService = ptmAccountQueryService;
    }

    /**
     * {@code POST  /ptm-accounts} : Create a new ptmAccount.
     *
     * @param ptmAccountDTO the ptmAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ptmAccountDTO, or with status {@code 400 (Bad Request)} if the ptmAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ptm-accounts")
    public ResponseEntity<PtmAccountDTO> createPtmAccount(@RequestBody PtmAccountDTO ptmAccountDTO) throws URISyntaxException {
        log.debug("REST request to save PtmAccount : {}", ptmAccountDTO);
        if (ptmAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new ptmAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PtmAccountDTO result = ptmAccountService.save(ptmAccountDTO);
        return ResponseEntity.created(new URI("/api/ptm-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ptm-accounts} : Updates an existing ptmAccount.
     *
     * @param ptmAccountDTO the ptmAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ptmAccountDTO,
     * or with status {@code 400 (Bad Request)} if the ptmAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ptmAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ptm-accounts")
    public ResponseEntity<PtmAccountDTO> updatePtmAccount(@RequestBody PtmAccountDTO ptmAccountDTO) throws URISyntaxException {
        log.debug("REST request to update PtmAccount : {}", ptmAccountDTO);
        if (ptmAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PtmAccountDTO result = ptmAccountService.save(ptmAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ptmAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ptm-accounts} : get all the ptmAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ptmAccounts in body.
     */
    @GetMapping("/ptm-accounts")
    public ResponseEntity<List<PtmAccountDTO>> getAllPtmAccounts(PtmAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PtmAccounts by criteria: {}", criteria);
        Page<PtmAccountDTO> page = ptmAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ptm-accounts/count} : count all the ptmAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ptm-accounts/count")
    public ResponseEntity<Long> countPtmAccounts(PtmAccountCriteria criteria) {
        log.debug("REST request to count PtmAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(ptmAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ptm-accounts/:id} : get the "id" ptmAccount.
     *
     * @param id the id of the ptmAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ptmAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ptm-accounts/{id}")
    public ResponseEntity<PtmAccountDTO> getPtmAccount(@PathVariable Long id) {
        log.debug("REST request to get PtmAccount : {}", id);
        Optional<PtmAccountDTO> ptmAccountDTO = ptmAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ptmAccountDTO);
    }

    /**
     * {@code DELETE  /ptm-accounts/:id} : delete the "id" ptmAccount.
     *
     * @param id the id of the ptmAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ptm-accounts/{id}")
    public ResponseEntity<Void> deletePtmAccount(@PathVariable Long id) {
        log.debug("REST request to delete PtmAccount : {}", id);
        ptmAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
