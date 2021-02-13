package com.access.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.access.myapp.domain.PtmAccount;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.PtmAccountRepository;
import com.access.myapp.service.dto.PtmAccountCriteria;
import com.access.myapp.service.dto.PtmAccountDTO;
import com.access.myapp.service.mapper.PtmAccountMapper;

/**
 * Service for executing complex queries for {@link PtmAccount} entities in the database.
 * The main input is a {@link PtmAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PtmAccountDTO} or a {@link Page} of {@link PtmAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PtmAccountQueryService extends QueryService<PtmAccount> {

    private final Logger log = LoggerFactory.getLogger(PtmAccountQueryService.class);

    private final PtmAccountRepository ptmAccountRepository;

    private final PtmAccountMapper ptmAccountMapper;

    public PtmAccountQueryService(PtmAccountRepository ptmAccountRepository, PtmAccountMapper ptmAccountMapper) {
        this.ptmAccountRepository = ptmAccountRepository;
        this.ptmAccountMapper = ptmAccountMapper;
    }

    /**
     * Return a {@link List} of {@link PtmAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PtmAccountDTO> findByCriteria(PtmAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PtmAccount> specification = createSpecification(criteria);
        return ptmAccountMapper.toDto(ptmAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PtmAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PtmAccountDTO> findByCriteria(PtmAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PtmAccount> specification = createSpecification(criteria);
        return ptmAccountRepository.findAll(specification, page)
            .map(ptmAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PtmAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PtmAccount> specification = createSpecification(criteria);
        return ptmAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link PtmAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PtmAccount> createSpecification(PtmAccountCriteria criteria) {
        Specification<PtmAccount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PtmAccount_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PtmAccount_.name));
            }
            if (criteria.getColour() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColour(), PtmAccount_.colour));
            }
            if (criteria.getClientsId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientsId(),
                    root -> root.join(PtmAccount_.clients, JoinType.LEFT).get(Client_.id)));
            }
        }
        return specification;
    }
}
