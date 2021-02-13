package com.access.myapp.service;

import com.access.myapp.domain.PtmAccount;
import com.access.myapp.repository.PtmAccountRepository;
import com.access.myapp.service.dto.PtmAccountDTO;
import com.access.myapp.service.mapper.PtmAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PtmAccount}.
 */
@Service
@Transactional
public class PtmAccountService {

    private final Logger log = LoggerFactory.getLogger(PtmAccountService.class);

    private final PtmAccountRepository ptmAccountRepository;

    private final PtmAccountMapper ptmAccountMapper;

    public PtmAccountService(PtmAccountRepository ptmAccountRepository, PtmAccountMapper ptmAccountMapper) {
        this.ptmAccountRepository = ptmAccountRepository;
        this.ptmAccountMapper = ptmAccountMapper;
    }

    /**
     * Save a ptmAccount.
     *
     * @param ptmAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public PtmAccountDTO save(PtmAccountDTO ptmAccountDTO) {
        log.debug("Request to save PtmAccount : {}", ptmAccountDTO);
        PtmAccount ptmAccount = ptmAccountMapper.toEntity(ptmAccountDTO);
        ptmAccount = ptmAccountRepository.save(ptmAccount);
        return ptmAccountMapper.toDto(ptmAccount);
    }

    /**
     * Get all the ptmAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PtmAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PtmAccounts");
        return ptmAccountRepository.findAll(pageable)
            .map(ptmAccountMapper::toDto);
    }


    /**
     * Get one ptmAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PtmAccountDTO> findOne(Long id) {
        log.debug("Request to get PtmAccount : {}", id);
        return ptmAccountRepository.findById(id)
            .map(ptmAccountMapper::toDto);
    }

    /**
     * Delete the ptmAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PtmAccount : {}", id);
        ptmAccountRepository.deleteById(id);
    }
}
