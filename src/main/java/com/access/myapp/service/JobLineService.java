package com.access.myapp.service;

import com.access.myapp.domain.JobLine;
import com.access.myapp.repository.JobLineRepository;
import com.access.myapp.service.dto.JobLineDTO;
import com.access.myapp.service.mapper.JobLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JobLine}.
 */
@Service
@Transactional
public class JobLineService {

    private final Logger log = LoggerFactory.getLogger(JobLineService.class);

    private final JobLineRepository jobLineRepository;

    private final JobLineMapper jobLineMapper;

    public JobLineService(JobLineRepository jobLineRepository, JobLineMapper jobLineMapper) {
        this.jobLineRepository = jobLineRepository;
        this.jobLineMapper = jobLineMapper;
    }

    /**
     * Save a jobLine.
     *
     * @param jobLineDTO the entity to save.
     * @return the persisted entity.
     */
    public JobLineDTO save(JobLineDTO jobLineDTO) {
        log.debug("Request to save JobLine : {}", jobLineDTO);
        JobLine jobLine = jobLineMapper.toEntity(jobLineDTO);
        jobLine = jobLineRepository.save(jobLine);
        return jobLineMapper.toDto(jobLine);
    }

    /**
     * Get all the jobLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobLines");
        return jobLineRepository.findAll(pageable)
            .map(jobLineMapper::toDto);
    }


    /**
     * Get one jobLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobLineDTO> findOne(Long id) {
        log.debug("Request to get JobLine : {}", id);
        return jobLineRepository.findById(id)
            .map(jobLineMapper::toDto);
    }

    /**
     * Delete the jobLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobLine : {}", id);
        jobLineRepository.deleteById(id);
    }
}
