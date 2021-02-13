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

import com.access.myapp.domain.Town;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.TownRepository;
import com.access.myapp.service.dto.TownCriteria;
import com.access.myapp.service.dto.TownDTO;
import com.access.myapp.service.mapper.TownMapper;

/**
 * Service for executing complex queries for {@link Town} entities in the database.
 * The main input is a {@link TownCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TownDTO} or a {@link Page} of {@link TownDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TownQueryService extends QueryService<Town> {

    private final Logger log = LoggerFactory.getLogger(TownQueryService.class);

    private final TownRepository townRepository;

    private final TownMapper townMapper;

    public TownQueryService(TownRepository townRepository, TownMapper townMapper) {
        this.townRepository = townRepository;
        this.townMapper = townMapper;
    }

    /**
     * Return a {@link List} of {@link TownDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TownDTO> findByCriteria(TownCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Town> specification = createSpecification(criteria);
        return townMapper.toDto(townRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TownDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TownDTO> findByCriteria(TownCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Town> specification = createSpecification(criteria);
        return townRepository.findAll(specification, page)
            .map(townMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TownCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Town> specification = createSpecification(criteria);
        return townRepository.count(specification);
    }

    /**
     * Function to convert {@link TownCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Town> createSpecification(TownCriteria criteria) {
        Specification<Town> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Town_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Town_.value));
            }
            if (criteria.getStreetsId() != null) {
                specification = specification.and(buildSpecification(criteria.getStreetsId(),
                    root -> root.join(Town_.streets, JoinType.LEFT).get(Street_.id)));
            }
            if (criteria.getCountyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountyId(),
                    root -> root.join(Town_.county, JoinType.LEFT).get(County_.id)));
            }
        }
        return specification;
    }
}
