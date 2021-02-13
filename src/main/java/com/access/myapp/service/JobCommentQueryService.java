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

import com.access.myapp.domain.JobComment;
import com.access.myapp.domain.*; // for static metamodels
import com.access.myapp.repository.JobCommentRepository;
import com.access.myapp.service.dto.JobCommentCriteria;
import com.access.myapp.service.dto.JobCommentDTO;
import com.access.myapp.service.mapper.JobCommentMapper;

/**
 * Service for executing complex queries for {@link JobComment} entities in the database.
 * The main input is a {@link JobCommentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link JobCommentDTO} or a {@link Page} of {@link JobCommentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JobCommentQueryService extends QueryService<JobComment> {

    private final Logger log = LoggerFactory.getLogger(JobCommentQueryService.class);

    private final JobCommentRepository jobCommentRepository;

    private final JobCommentMapper jobCommentMapper;

    public JobCommentQueryService(JobCommentRepository jobCommentRepository, JobCommentMapper jobCommentMapper) {
        this.jobCommentRepository = jobCommentRepository;
        this.jobCommentMapper = jobCommentMapper;
    }

    /**
     * Return a {@link List} of {@link JobCommentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<JobCommentDTO> findByCriteria(JobCommentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<JobComment> specification = createSpecification(criteria);
        return jobCommentMapper.toDto(jobCommentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link JobCommentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JobCommentDTO> findByCriteria(JobCommentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JobComment> specification = createSpecification(criteria);
        return jobCommentRepository.findAll(specification, page)
            .map(jobCommentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JobCommentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<JobComment> specification = createSpecification(criteria);
        return jobCommentRepository.count(specification);
    }

    /**
     * Function to convert {@link JobCommentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JobComment> createSpecification(JobCommentCriteria criteria) {
        Specification<JobComment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), JobComment_.id));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), JobComment_.comment));
            }
            if (criteria.getJobId() != null) {
                specification = specification.and(buildSpecification(criteria.getJobId(),
                    root -> root.join(JobComment_.job, JoinType.LEFT).get(Job_.id)));
            }
        }
        return specification;
    }
}
