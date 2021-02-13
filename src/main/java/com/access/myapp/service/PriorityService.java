package com.access.myapp.service;

import com.access.myapp.domain.Priority;
import com.access.myapp.repository.PriorityRepository;
import com.access.myapp.service.dto.PriorityDTO;
import com.access.myapp.service.mapper.PriorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Priority}.
 */
@Service
@Transactional
public class PriorityService {

    private final Logger log = LoggerFactory.getLogger(PriorityService.class);

    private final PriorityRepository priorityRepository;

    private final PriorityMapper priorityMapper;

    public PriorityService(PriorityRepository priorityRepository, PriorityMapper priorityMapper) {
        this.priorityRepository = priorityRepository;
        this.priorityMapper = priorityMapper;
    }

    /**
     * Save a priority.
     *
     * @param priorityDTO the entity to save.
     * @return the persisted entity.
     */
    public PriorityDTO save(PriorityDTO priorityDTO) {
        log.debug("Request to save Priority : {}", priorityDTO);
        Priority priority = priorityMapper.toEntity(priorityDTO);
        priority = priorityRepository.save(priority);
        return priorityMapper.toDto(priority);
    }

    /**
     * Get all the priorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PriorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Priorities");
        return priorityRepository.findAll(pageable)
            .map(priorityMapper::toDto);
    }


    /**
     * Get one priority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PriorityDTO> findOne(Long id) {
        log.debug("Request to get Priority : {}", id);
        return priorityRepository.findById(id)
            .map(priorityMapper::toDto);
    }

    /**
     * Delete the priority by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Priority : {}", id);
        priorityRepository.deleteById(id);
    }
}
