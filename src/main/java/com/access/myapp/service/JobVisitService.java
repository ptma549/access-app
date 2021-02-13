package com.access.myapp.service;

import com.access.myapp.domain.JobVisit;
import com.access.myapp.repository.JobVisitRepository;
import com.access.myapp.service.dto.JobVisitDTO;
import com.access.myapp.service.mapper.JobVisitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobVisit}.
 */
@Service
@Transactional
public class JobVisitService {

    private final Logger log = LoggerFactory.getLogger(JobVisitService.class);

    private final JobVisitRepository jobVisitRepository;

    private final JobVisitMapper jobVisitMapper;

    public JobVisitService(JobVisitRepository jobVisitRepository, JobVisitMapper jobVisitMapper) {
        this.jobVisitRepository = jobVisitRepository;
        this.jobVisitMapper = jobVisitMapper;
    }

    /**
     * Save a jobVisit.
     *
     * @param jobVisitDTO the entity to save.
     * @return the persisted entity.
     */
    public JobVisitDTO save(JobVisitDTO jobVisitDTO) {
        log.debug("Request to save JobVisit : {}", jobVisitDTO);
        JobVisit jobVisit = jobVisitMapper.toEntity(jobVisitDTO);
        jobVisit = jobVisitRepository.save(jobVisit);
        return jobVisitMapper.toDto(jobVisit);
    }

    /**
     * Get all the jobVisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobVisitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobVisits");
        return jobVisitRepository.findAll(pageable)
            .map(jobVisitMapper::toDto);
    }


    /**
     * Get one jobVisit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobVisitDTO> findOne(Long id) {
        log.debug("Request to get JobVisit : {}", id);
        return jobVisitRepository.findById(id)
            .map(jobVisitMapper::toDto);
    }

    /**
     * Delete the jobVisit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobVisit : {}", id);
        jobVisitRepository.deleteById(id);
    }
}
