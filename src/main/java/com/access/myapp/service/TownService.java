package com.access.myapp.service;

import com.access.myapp.domain.Town;
import com.access.myapp.repository.TownRepository;
import com.access.myapp.service.dto.TownDTO;
import com.access.myapp.service.mapper.TownMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Town}.
 */
@Service
@Transactional
public class TownService {

    private final Logger log = LoggerFactory.getLogger(TownService.class);

    private final TownRepository townRepository;

    private final TownMapper townMapper;

    public TownService(TownRepository townRepository, TownMapper townMapper) {
        this.townRepository = townRepository;
        this.townMapper = townMapper;
    }

    /**
     * Save a town.
     *
     * @param townDTO the entity to save.
     * @return the persisted entity.
     */
    public TownDTO save(TownDTO townDTO) {
        log.debug("Request to save Town : {}", townDTO);
        Town town = townMapper.toEntity(townDTO);
        town = townRepository.save(town);
        return townMapper.toDto(town);
    }

    /**
     * Get all the towns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TownDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Towns");
        return townRepository.findAll(pageable)
            .map(townMapper::toDto);
    }


    /**
     * Get one town by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TownDTO> findOne(Long id) {
        log.debug("Request to get Town : {}", id);
        return townRepository.findById(id)
            .map(townMapper::toDto);
    }

    /**
     * Delete the town by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Town : {}", id);
        townRepository.deleteById(id);
    }
}
