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

import com.access.myapp.domain.Job;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.JobRepository;
import com.access.myapp.service.dto.JobCriteria;
import com.access.myapp.service.dto.JobDTO;
import com.access.myapp.service.mapper.JobMapper;

/**
 * Service for executing complex queries for {@link Job} entities in the database.
 * The main input is a {@link JobCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobDTO} or a {@link Page} of {@link JobDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobQueryService extends QueryService<Job> {

    private final Logger log = LoggerFactory.getLogger(JobQueryService.class);

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    public JobQueryService(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    /**
     * Return a {@link List} of {@link JobDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobDTO> findByCriteria(JobCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Job> specification = createSpecification(criteria);
        return jobMapper.toDto(jobRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JobDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobDTO> findByCriteria(JobCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Job> specification = createSpecification(criteria);
        return jobRepository.findAll(specification, page)
            .map(jobMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Job> specification = createSpecification(criteria);
        return jobRepository.count(specification);
    }

    /**
     * Function to convert {@link JobCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Job> createSpecification(JobCriteria criteria) {
        Specification<Job> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Job_.id));
            }
            if (criteria.getReportedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReportedBy(), Job_.reportedBy));
            }
            if (criteria.getClientOrderRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientOrderRef(), Job_.clientOrderRef));
            }
            if (criteria.getRaiseDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRaiseDate(), Job_.raiseDate));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriority(), Job_.priority));
            }
            if (criteria.getFault() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFault(), Job_.fault));
            }
            if (criteria.getInstructions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstructions(), Job_.instructions));
            }
            if (criteria.getOccupier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOccupier(), Job_.occupier));
            }
            if (criteria.getHomeTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHomeTel(), Job_.homeTel));
            }
            if (criteria.getWorkTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkTel(), Job_.workTel));
            }
            if (criteria.getMobileTel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileTel(), Job_.mobileTel));
            }
            if (criteria.getTenantName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantName(), Job_.tenantName));
            }
            if (criteria.getComplete() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComplete(), Job_.complete));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), Job_.position));
            }
            if (criteria.getInvoice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoice(), Job_.invoice));
            }
            if (criteria.getInvoiceDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceDetails(), Job_.invoiceDetails));
            }
            if (criteria.getCommentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentsId(),
                    root -> root.join(Job_.comments, JoinType.LEFT).get(JobComment_.id)));
            }
            if (criteria.getLinesId() != null) {
                specification = specification.and(buildSpecification(criteria.getLinesId(),
                    root -> root.join(Job_.lines, JoinType.LEFT).get(JobLine_.id)));
            }
            if (criteria.getVisitsId() != null) {
                specification = specification.and(buildSpecification(criteria.getVisitsId(),
                    root -> root.join(Job_.visits, JoinType.LEFT).get(JobVisit_.id)));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientId(),
                    root -> root.join(Job_.client, JoinType.LEFT).get(Client_.id)));
            }
        }
        return specification;
    }
}
