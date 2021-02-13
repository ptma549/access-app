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

import com.access.myapp.domain.Priority;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.PriorityRepository;
import com.access.myapp.service.dto.PriorityCriteria;
import com.access.myapp.service.dto.PriorityDTO;
import com.access.myapp.service.mapper.PriorityMapper;

/**
 * Service for executing complex queries for {@link Priority} entities in the database.
 * The main input is a {@link PriorityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PriorityDTO} or a {@link Page} of {@link PriorityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PriorityQueryService extends QueryService<Priority> {

    private final Logger log = LoggerFactory.getLogger(PriorityQueryService.class);

    private final PriorityRepository priorityRepository;

    private final PriorityMapper priorityMapper;

    public PriorityQueryService(PriorityRepository priorityRepository, PriorityMapper priorityMapper) {
        this.priorityRepository = priorityRepository;
        this.priorityMapper = priorityMapper;
    }

    /**
     * Return a {@link List} of {@link PriorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PriorityDTO> findByCriteria(PriorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Priority> specification = createSpecification(criteria);
        return priorityMapper.toDto(priorityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PriorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PriorityDTO> findByCriteria(PriorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Priority> specification = createSpecification(criteria);
        return priorityRepository.findAll(specification, page)
            .map(priorityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PriorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Priority> specification = createSpecification(criteria);
        return priorityRepository.count(specification);
    }

    /**
     * Function to convert {@link PriorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Priority> createSpecification(PriorityCriteria criteria) {
        Specification<Priority> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Priority_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Priority_.value));
            }
        }
        return specification;
    }
}
