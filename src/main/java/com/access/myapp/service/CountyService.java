package com.access.myapp.service;

import com.access.myapp.domain.County;
import com.access.myapp.repository.CountyRepository;
import com.access.myapp.service.dto.CountyDTO;
import com.access.myapp.service.mapper.CountyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link County}.
 */
@Service
@Transactional
public class CountyService {

    private final Logger log = LoggerFactory.getLogger(CountyService.class);

    private final CountyRepository countyRepository;

    private final CountyMapper countyMapper;

    public CountyService(CountyRepository countyRepository, CountyMapper countyMapper) {
        this.countyRepository = countyRepository;
        this.countyMapper = countyMapper;
    }

    /**
     * Save a county.
     *
     * @param countyDTO the entity to save.
     * @return the persisted entity.
     */
    public CountyDTO save(CountyDTO countyDTO) {
        log.debug("Request to save County : {}", countyDTO);
        County county = countyMapper.toEntity(countyDTO);
        county = countyRepository.save(county);
        return countyMapper.toDto(county);
    }

    /**
     * Get all the counties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CountyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Counties");
        return countyRepository.findAll(pageable)
            .map(countyMapper::toDto);
    }


    /**
     * Get one county by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CountyDTO> findOne(Long id) {
        log.debug("Request to get County : {}", id);
        return countyRepository.findById(id)
            .map(countyMapper::toDto);
    }

    /**
     * Delete the county by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete County : {}", id);
        countyRepository.deleteById(id);
    }
}
