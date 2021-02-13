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

import com.access.myapp.domain.Number;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.NumberRepository;
import com.access.myapp.service.dto.NumberCriteria;
import com.access.myapp.service.dto.NumberDTO;
import com.access.myapp.service.mapper.NumberMapper;

/**
 * Service for executing complex queries for {@link Number} entities in the database.
 * The main input is a {@link NumberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NumberDTO} or a {@link Page} of {@link NumberDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NumberQueryService extends QueryService<Number> {

    private final Logger log = LoggerFactory.getLogger(NumberQueryService.class);

    private final NumberRepository numberRepository;

    private final NumberMapper numberMapper;

    public NumberQueryService(NumberRepository numberRepository, NumberMapper numberMapper) {
        this.numberRepository = numberRepository;
        this.numberMapper = numberMapper;
    }

    /**
     * Return a {@link List} of {@link NumberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NumberDTO> findByCriteria(NumberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Number> specification = createSpecification(criteria);
        return numberMapper.toDto(numberRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NumberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NumberDTO> findByCriteria(NumberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Number> specification = createSpecification(criteria);
        return numberRepository.findAll(specification, page)
            .map(numberMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NumberCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Number> specification = createSpecification(criteria);
        return numberRepository.count(specification);
    }

    /**
     * Function to convert {@link NumberCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Number> createSpecification(NumberCriteria criteria) {
        Specification<Number> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Number_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Number_.value));
            }
            if (criteria.getBuilding() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBuilding(), Number_.building));
            }
            if (criteria.getPostcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostcode(), Number_.postcode));
            }
            if (criteria.getPositionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPositionsId(),
                    root -> root.join(Number_.positions, JoinType.LEFT).get(Position_.id)));
            }
            if (criteria.getStreetId() != null) {
                specification = specification.and(buildSpecification(criteria.getStreetId(),
                    root -> root.join(Number_.street, JoinType.LEFT).get(Street_.id)));
            }
        }
        return specification;
    }
}
