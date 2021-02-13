package com.access.myapp.service;

import com.access.myapp.domain.JobComment;
import com.access.myapp.repository.JobCommentRepository;
import com.access.myapp.service.dto.JobCommentDTO;
import com.access.myapp.service.mapper.JobCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobComment}.
 */
@Service
@Transactional
public class JobCommentService {

    private final Logger log = LoggerFactory.getLogger(JobCommentService.class);

    private final JobCommentRepository jobCommentRepository;

    private final JobCommentMapper jobCommentMapper;

    public JobCommentService(JobCommentRepository jobCommentRepository, JobCommentMapper jobCommentMapper) {
        this.jobCommentRepository = jobCommentRepository;
        this.jobCommentMapper = jobCommentMapper;
    }

    /**
     * Save a jobComment.
     *
     * @param jobCommentDTO the entity to save.
     * @return the persisted entity.
     */
    public JobCommentDTO save(JobCommentDTO jobCommentDTO) {
        log.debug("Request to save JobComment : {}", jobCommentDTO);
        JobComment jobComment = jobCommentMapper.toEntity(jobCommentDTO);
        jobComment = jobCommentRepository.save(jobComment);
        return jobCommentMapper.toDto(jobComment);
    }

    /**
     * Get all the jobComments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobComments");
        return jobCommentRepository.findAll(pageable)
            .map(jobCommentMapper::toDto);
    }


    /**
     * Get one jobComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobCommentDTO> findOne(Long id) {
        log.debug("Request to get JobComment : {}", id);
        return jobCommentRepository.findById(id)
            .map(jobCommentMapper::toDto);
    }

    /**
     * Delete the jobComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobComment : {}", id);
        jobCommentRepository.deleteById(id);
    }
}
