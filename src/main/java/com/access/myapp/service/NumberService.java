package com.access.myapp.service;

import com.access.myapp.domain.Number;
import com.access.myapp.repository.NumberRepository;
import com.access.myapp.service.dto.NumberDTO;
import com.access.myapp.service.mapper.NumberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Number}.
 */
@Service
@Transactional
public class NumberService {

    private final Logger log = LoggerFactory.getLogger(NumberService.class);

    private final NumberRepository numberRepository;

    private final NumberMapper numberMapper;

    public NumberService(NumberRepository numberRepository, NumberMapper numberMapper) {
        this.numberRepository = numberRepository;
        this.numberMapper = numberMapper;
    }

    /**
     * Save a number.
     *
     * @param numberDTO the entity to save.
     * @return the persisted entity.
     */
    public NumberDTO save(NumberDTO numberDTO) {
        log.debug("Request to save Number : {}", numberDTO);
        Number number = numberMapper.toEntity(numberDTO);
        number = numberRepository.save(number);
        return numberMapper.toDto(number);
    }

    /**
     * Get all the numbers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NumberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Numbers");
        return numberRepository.findAll(pageable)
            .map(numberMapper::toDto);
    }


    /**
     * Get one number by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NumberDTO> findOne(Long id) {
        log.debug("Request to get Number : {}", id);
        return numberRepository.findById(id)
            .map(numberMapper::toDto);
    }

    /**
     * Delete the number by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Number : {}", id);
        numberRepository.deleteById(id);
    }
}
