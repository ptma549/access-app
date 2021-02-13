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

import com.access.myapp.domain.JobVisit;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.JobVisitRepository;
import com.access.myapp.service.dto.JobVisitCriteria;
import com.access.myapp.service.dto.JobVisitDTO;
import com.access.myapp.service.mapper.JobVisitMapper;

/**
 * Service for executing complex queries for {@link JobVisit} entities in the database.
 * The main input is a {@link JobVisitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobVisitDTO} or a {@link Page} of {@link JobVisitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobVisitQueryService extends QueryService<JobVisit> {

    private final Logger log = LoggerFactory.getLogger(JobVisitQueryService.class);

    private final JobVisitRepository jobVisitRepository;

    private final JobVisitMapper jobVisitMapper;

    public JobVisitQueryService(JobVisitRepository jobVisitRepository, JobVisitMapper jobVisitMapper) {
        this.jobVisitRepository = jobVisitRepository;
        this.jobVisitMapper = jobVisitMapper;
    }

    /**
     * Return a {@link List} of {@link JobVisitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobVisitDTO> findByCriteria(JobVisitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobVisit> specification = createSpecification(criteria);
        return jobVisitMapper.toDto(jobVisitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JobVisitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobVisitDTO> findByCriteria(JobVisitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobVisit> specification = createSpecification(criteria);
        return jobVisitRepository.findAll(specification, page)
            .map(jobVisitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobVisitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobVisit> specification = createSpecification(criteria);
        return jobVisitRepository.count(specification);
    }

    /**
     * Function to convert {@link JobVisitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JobVisit> createSpecification(JobVisitCriteria criteria) {
        Specification<JobVisit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JobVisit_.id));
            }
            if (criteria.getArrived() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArrived(), JobVisit_.arrived));
            }
            if (criteria.getDeparted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeparted(), JobVisit_.departed));
            }
            if (criteria.getCharge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCharge(), JobVisit_.charge));
            }
            if (criteria.getWorkCarriedOut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkCarriedOut(), JobVisit_.workCarriedOut));
            }
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(),
                    root -> root.join(JobVisit_.job, JoinType.LEFT).get(Job_.id)));
            }
        }
        return specification;
    }
}
