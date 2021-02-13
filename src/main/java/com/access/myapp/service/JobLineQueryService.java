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

import com.access.myapp.domain.JobLine;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.JobLineRepository;
import com.access.myapp.service.dto.JobLineCriteria;
import com.access.myapp.service.dto.JobLineDTO;
import com.access.myapp.service.mapper.JobLineMapper;

/**
 * Service for executing complex queries for {@link JobLine} entities in the database.
 * The main input is a {@link JobLineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobLineDTO} or a {@link Page} of {@link JobLineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobLineQueryService extends QueryService<JobLine> {

    private final Logger log = LoggerFactory.getLogger(JobLineQueryService.class);

    private final JobLineRepository jobLineRepository;

    private final JobLineMapper jobLineMapper;

    public JobLineQueryService(JobLineRepository jobLineRepository, JobLineMapper jobLineMapper) {
        this.jobLineRepository = jobLineRepository;
        this.jobLineMapper = jobLineMapper;
    }

    /**
     * Return a {@link List} of {@link JobLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobLineDTO> findByCriteria(JobLineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobLine> specification = createSpecification(criteria);
        return jobLineMapper.toDto(jobLineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JobLineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobLineDTO> findByCriteria(JobLineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobLine> specification = createSpecification(criteria);
        return jobLineRepository.findAll(specification, page)
            .map(jobLineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobLineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobLine> specification = createSpecification(criteria);
        return jobLineRepository.count(specification);
    }

    /**
     * Function to convert {@link JobLineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JobLine> createSpecification(JobLineCriteria criteria) {
        Specification<JobLine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JobLine_.id));
            }
            if (criteria.getMaterial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterial(), JobLine_.material));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), JobLine_.quantity));
            }
            if (criteria.getUnitCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitCost(), JobLine_.unitCost));
            }
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(),
                    root -> root.join(JobLine_.job, JoinType.LEFT).get(Job_.id)));
            }
        }
        return specification;
    }
}
