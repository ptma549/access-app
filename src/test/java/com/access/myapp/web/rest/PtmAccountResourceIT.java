package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.PtmAccount;
import com.access.myapp.domain.Client;
import com.access.myapp.repository.PtmAccountRepository;
import com.access.myapp.service.PtmAccountService;
import com.access.myapp.service.dto.PtmAccountDTO;
import com.access.myapp.service.mapper.PtmAccountMapper;
import com.access.myapp.service.dto.PtmAccountCriteria;
import com.access.myapp.service.PtmAccountQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PtmAccountResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PtmAccountResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOUR = "AAAAAAAAAA";
    private static final String UPDATED_COLOUR = "BBBBBBBBBB";

    @Autowired
    private PtmAccountRepository ptmAccountRepository;

    @Autowired
    private PtmAccountMapper ptmAccountMapper;

    @Autowired
    private PtmAccountService ptmAccountService;

    @Autowired
    private PtmAccountQueryService ptmAccountQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPtmAccountMockMvc;

    private PtmAccount ptmAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PtmAccount createEntity(EntityManager em) {
        PtmAccount ptmAccount = new PtmAccount()
            .name(DEFAULT_NAME)
            .colour(DEFAULT_COLOUR);
        return ptmAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PtmAccount createUpdatedEntity(EntityManager em) {
        PtmAccount ptmAccount = new PtmAccount()
            .name(UPDATED_NAME)
            .colour(UPDATED_COLOUR);
        return ptmAccount;
    }

    @BeforeEach
    public void initTest() {
        ptmAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPtmAccount() throws Exception {
        int databaseSizeBeforeCreate = ptmAccountRepository.findAll().size();
        // Create the PtmAccount
        PtmAccountDTO ptmAccountDTO = ptmAccountMapper.toDto(ptmAccount);
        restPtmAccountMockMvc.perform(post("/api/ptm-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ptmAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the PtmAccount in the database
        List<PtmAccount> ptmAccountList = ptmAccountRepository.findAll();
        assertThat(ptmAccountList).hasSize(databaseSizeBeforeCreate + 1);
        PtmAccount testPtmAccount = ptmAccountList.get(ptmAccountList.size() - 1);
        assertThat(testPtmAccount.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPtmAccount.getColour()).isEqualTo(DEFAULT_COLOUR);
    }

    @Test
    @Transactional
    public void createPtmAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ptmAccountRepository.findAll().size();

        // Create the PtmAccount with an existing ID
        ptmAccount.setId(1L);
        PtmAccountDTO ptmAccountDTO = ptmAccountMapper.toDto(ptmAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPtmAccountMockMvc.perform(post("/api/ptm-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ptmAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PtmAccount in the database
        List<PtmAccount> ptmAccountList = ptmAccountRepository.findAll();
        assertThat(ptmAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPtmAccounts() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList
        restPtmAccountMockMvc.perform(get("/api/ptm-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ptmAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)));
    }
    
    @Test
    @Transactional
    public void getPtmAccount() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get the ptmAccount
        restPtmAccountMockMvc.perform(get("/api/ptm-accounts/{id}", ptmAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ptmAccount.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR));
    }


    @Test
    @Transactional
    public void getPtmAccountsByIdFiltering() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        Long id = ptmAccount.getId();

        defaultPtmAccountShouldBeFound("id.equals=" + id);
        defaultPtmAccountShouldNotBeFound("id.notEquals=" + id);

        defaultPtmAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPtmAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultPtmAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPtmAccountShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPtmAccountsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where name equals to DEFAULT_NAME
        defaultPtmAccountShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the ptmAccountList where name equals to UPDATED_NAME
        defaultPtmAccountShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where name not equals to DEFAULT_NAME
        defaultPtmAccountShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the ptmAccountList where name not equals to UPDATED_NAME
        defaultPtmAccountShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPtmAccountShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the ptmAccountList where name equals to UPDATED_NAME
        defaultPtmAccountShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where name is not null
        defaultPtmAccountShouldBeFound("name.specified=true");

        // Get all the ptmAccountList where name is null
        defaultPtmAccountShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPtmAccountsByNameContainsSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where name contains DEFAULT_NAME
        defaultPtmAccountShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the ptmAccountList where name contains UPDATED_NAME
        defaultPtmAccountShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where name does not contain DEFAULT_NAME
        defaultPtmAccountShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the ptmAccountList where name does not contain UPDATED_NAME
        defaultPtmAccountShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPtmAccountsByColourIsEqualToSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where colour equals to DEFAULT_COLOUR
        defaultPtmAccountShouldBeFound("colour.equals=" + DEFAULT_COLOUR);

        // Get all the ptmAccountList where colour equals to UPDATED_COLOUR
        defaultPtmAccountShouldNotBeFound("colour.equals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByColourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where colour not equals to DEFAULT_COLOUR
        defaultPtmAccountShouldNotBeFound("colour.notEquals=" + DEFAULT_COLOUR);

        // Get all the ptmAccountList where colour not equals to UPDATED_COLOUR
        defaultPtmAccountShouldBeFound("colour.notEquals=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByColourIsInShouldWork() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where colour in DEFAULT_COLOUR or UPDATED_COLOUR
        defaultPtmAccountShouldBeFound("colour.in=" + DEFAULT_COLOUR + "," + UPDATED_COLOUR);

        // Get all the ptmAccountList where colour equals to UPDATED_COLOUR
        defaultPtmAccountShouldNotBeFound("colour.in=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByColourIsNullOrNotNull() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where colour is not null
        defaultPtmAccountShouldBeFound("colour.specified=true");

        // Get all the ptmAccountList where colour is null
        defaultPtmAccountShouldNotBeFound("colour.specified=false");
    }
                @Test
    @Transactional
    public void getAllPtmAccountsByColourContainsSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where colour contains DEFAULT_COLOUR
        defaultPtmAccountShouldBeFound("colour.contains=" + DEFAULT_COLOUR);

        // Get all the ptmAccountList where colour contains UPDATED_COLOUR
        defaultPtmAccountShouldNotBeFound("colour.contains=" + UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void getAllPtmAccountsByColourNotContainsSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        // Get all the ptmAccountList where colour does not contain DEFAULT_COLOUR
        defaultPtmAccountShouldNotBeFound("colour.doesNotContain=" + DEFAULT_COLOUR);

        // Get all the ptmAccountList where colour does not contain UPDATED_COLOUR
        defaultPtmAccountShouldBeFound("colour.doesNotContain=" + UPDATED_COLOUR);
    }


    @Test
    @Transactional
    public void getAllPtmAccountsByClientsIsEqualToSomething() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);
        Client clients = ClientResourceIT.createEntity(em);
        em.persist(clients);
        em.flush();
        ptmAccount.addClients(clients);
        ptmAccountRepository.saveAndFlush(ptmAccount);
        Long clientsId = clients.getId();

        // Get all the ptmAccountList where clients equals to clientsId
        defaultPtmAccountShouldBeFound("clientsId.equals=" + clientsId);

        // Get all the ptmAccountList where clients equals to clientsId + 1
        defaultPtmAccountShouldNotBeFound("clientsId.equals=" + (clientsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPtmAccountShouldBeFound(String filter) throws Exception {
        restPtmAccountMockMvc.perform(get("/api/ptm-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ptmAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)));

        // Check, that the count call also returns 1
        restPtmAccountMockMvc.perform(get("/api/ptm-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPtmAccountShouldNotBeFound(String filter) throws Exception {
        restPtmAccountMockMvc.perform(get("/api/ptm-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPtmAccountMockMvc.perform(get("/api/ptm-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPtmAccount() throws Exception {
        // Get the ptmAccount
        restPtmAccountMockMvc.perform(get("/api/ptm-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePtmAccount() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        int databaseSizeBeforeUpdate = ptmAccountRepository.findAll().size();

        // Update the ptmAccount
        PtmAccount updatedPtmAccount = ptmAccountRepository.findById(ptmAccount.getId()).get();
        // Disconnect from session so that the updates on updatedPtmAccount are not directly saved in db
        em.detach(updatedPtmAccount);
        updatedPtmAccount
            .name(UPDATED_NAME)
            .colour(UPDATED_COLOUR);
        PtmAccountDTO ptmAccountDTO = ptmAccountMapper.toDto(updatedPtmAccount);

        restPtmAccountMockMvc.perform(put("/api/ptm-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ptmAccountDTO)))
            .andExpect(status().isOk());

        // Validate the PtmAccount in the database
        List<PtmAccount> ptmAccountList = ptmAccountRepository.findAll();
        assertThat(ptmAccountList).hasSize(databaseSizeBeforeUpdate);
        PtmAccount testPtmAccount = ptmAccountList.get(ptmAccountList.size() - 1);
        assertThat(testPtmAccount.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPtmAccount.getColour()).isEqualTo(UPDATED_COLOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingPtmAccount() throws Exception {
        int databaseSizeBeforeUpdate = ptmAccountRepository.findAll().size();

        // Create the PtmAccount
        PtmAccountDTO ptmAccountDTO = ptmAccountMapper.toDto(ptmAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPtmAccountMockMvc.perform(put("/api/ptm-accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ptmAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PtmAccount in the database
        List<PtmAccount> ptmAccountList = ptmAccountRepository.findAll();
        assertThat(ptmAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePtmAccount() throws Exception {
        // Initialize the database
        ptmAccountRepository.saveAndFlush(ptmAccount);

        int databaseSizeBeforeDelete = ptmAccountRepository.findAll().size();

        // Delete the ptmAccount
        restPtmAccountMockMvc.perform(delete("/api/ptm-accounts/{id}", ptmAccount.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PtmAccount> ptmAccountList = ptmAccountRepository.findAll();
        assertThat(ptmAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
