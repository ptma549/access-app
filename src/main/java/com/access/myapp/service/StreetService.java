package com.access.myapp.service;

import com.access.myapp.domain.Street;
import com.access.myapp.repository.StreetRepository;
import com.access.myapp.service.dto.StreetDTO;
import com.access.myapp.service.mapper.StreetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Street}.
 */
@Service
@Transactional
public class StreetService {

    private final Logger log = LoggerFactory.getLogger(StreetService.class);

    private final StreetRepository streetRepository;

    private final StreetMapper streetMapper;

    public StreetService(StreetRepository streetRepository, StreetMapper streetMapper) {
        this.streetRepository = streetRepository;
        this.streetMapper = streetMapper;
    }

    /**
     * Save a street.
     *
     * @param streetDTO the entity to save.
     * @return the persisted entity.
     */
    public StreetDTO save(StreetDTO streetDTO) {
        log.debug("Request to save Street : {}", streetDTO);
        Street street = streetMapper.toEntity(streetDTO);
        street = streetRepository.save(street);
        return streetMapper.toDto(street);
    }

    /**
     * Get all the streets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StreetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Streets");
        return streetRepository.findAll(pageable)
            .map(streetMapper::toDto);
    }


    /**
     * Get one street by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StreetDTO> findOne(Long id) {
        log.debug("Request to get Street : {}", id);
        return streetRepository.findById(id)
            .map(streetMapper::toDto);
    }

    /**
     * Delete the street by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Street : {}", id);
        streetRepository.deleteById(id);
    }
}
