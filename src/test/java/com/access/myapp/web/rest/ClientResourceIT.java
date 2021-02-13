package com.access.myapp.web.rest;

import com.access.myapp.AccessApp;
import com.access.myapp.domain.Client;
import com.access.myapp.domain.County;
import com.access.myapp.domain.Job;
import com.access.myapp.domain.PtmAccount;
import com.access.myapp.repository.ClientRepository;
import com.access.myapp.service.ClientService;
import com.access.myapp.service.dto.ClientDTO;
import com.access.myapp.service.mapper.ClientMapper;
import com.access.myapp.service.dto.ClientCriteria;
import com.access.myapp.service.ClientQueryService;

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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = AccessApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientQueryService clientQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .postcode(DEFAULT_POSTCODE)
            .telephone(DEFAULT_TELEPHONE)
            .fax(DEFAULT_FAX);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .postcode(UPDATED_POSTCODE)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClient.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testClient.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testClient.getFax()).isEqualTo(DEFAULT_FAX);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);
        ClientDTO clientDTO = clientMapper.toDto(client);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX));
    }


    @Test
    @Transactional
    public void getClientsByIdFiltering() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        Long id = client.getId();

        defaultClientShouldBeFound("id.equals=" + id);
        defaultClientShouldNotBeFound("id.notEquals=" + id);

        defaultClientShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.greaterThan=" + id);

        defaultClientShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClientShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClientsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where name equals to DEFAULT_NAME
        defaultClientShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the clientList where name equals to UPDATED_NAME
        defaultClientShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where name not equals to DEFAULT_NAME
        defaultClientShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the clientList where name not equals to UPDATED_NAME
        defaultClientShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClientShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the clientList where name equals to UPDATED_NAME
        defaultClientShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where name is not null
        defaultClientShouldBeFound("name.specified=true");

        // Get all the clientList where name is null
        defaultClientShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByNameContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where name contains DEFAULT_NAME
        defaultClientShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the clientList where name contains UPDATED_NAME
        defaultClientShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClientsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where name does not contain DEFAULT_NAME
        defaultClientShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the clientList where name does not contain UPDATED_NAME
        defaultClientShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllClientsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where address equals to DEFAULT_ADDRESS
        defaultClientShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the clientList where address equals to UPDATED_ADDRESS
        defaultClientShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClientsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where address not equals to DEFAULT_ADDRESS
        defaultClientShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the clientList where address not equals to UPDATED_ADDRESS
        defaultClientShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClientsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultClientShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the clientList where address equals to UPDATED_ADDRESS
        defaultClientShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClientsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where address is not null
        defaultClientShouldBeFound("address.specified=true");

        // Get all the clientList where address is null
        defaultClientShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByAddressContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where address contains DEFAULT_ADDRESS
        defaultClientShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the clientList where address contains UPDATED_ADDRESS
        defaultClientShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClientsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where address does not contain DEFAULT_ADDRESS
        defaultClientShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the clientList where address does not contain UPDATED_ADDRESS
        defaultClientShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllClientsByPostcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where postcode equals to DEFAULT_POSTCODE
        defaultClientShouldBeFound("postcode.equals=" + DEFAULT_POSTCODE);

        // Get all the clientList where postcode equals to UPDATED_POSTCODE
        defaultClientShouldNotBeFound("postcode.equals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllClientsByPostcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where postcode not equals to DEFAULT_POSTCODE
        defaultClientShouldNotBeFound("postcode.notEquals=" + DEFAULT_POSTCODE);

        // Get all the clientList where postcode not equals to UPDATED_POSTCODE
        defaultClientShouldBeFound("postcode.notEquals=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllClientsByPostcodeIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where postcode in DEFAULT_POSTCODE or UPDATED_POSTCODE
        defaultClientShouldBeFound("postcode.in=" + DEFAULT_POSTCODE + "," + UPDATED_POSTCODE);

        // Get all the clientList where postcode equals to UPDATED_POSTCODE
        defaultClientShouldNotBeFound("postcode.in=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllClientsByPostcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where postcode is not null
        defaultClientShouldBeFound("postcode.specified=true");

        // Get all the clientList where postcode is null
        defaultClientShouldNotBeFound("postcode.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByPostcodeContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where postcode contains DEFAULT_POSTCODE
        defaultClientShouldBeFound("postcode.contains=" + DEFAULT_POSTCODE);

        // Get all the clientList where postcode contains UPDATED_POSTCODE
        defaultClientShouldNotBeFound("postcode.contains=" + UPDATED_POSTCODE);
    }

    @Test
    @Transactional
    public void getAllClientsByPostcodeNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where postcode does not contain DEFAULT_POSTCODE
        defaultClientShouldNotBeFound("postcode.doesNotContain=" + DEFAULT_POSTCODE);

        // Get all the clientList where postcode does not contain UPDATED_POSTCODE
        defaultClientShouldBeFound("postcode.doesNotContain=" + UPDATED_POSTCODE);
    }


    @Test
    @Transactional
    public void getAllClientsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telephone equals to DEFAULT_TELEPHONE
        defaultClientShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the clientList where telephone equals to UPDATED_TELEPHONE
        defaultClientShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telephone not equals to DEFAULT_TELEPHONE
        defaultClientShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the clientList where telephone not equals to UPDATED_TELEPHONE
        defaultClientShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultClientShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the clientList where telephone equals to UPDATED_TELEPHONE
        defaultClientShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telephone is not null
        defaultClientShouldBeFound("telephone.specified=true");

        // Get all the clientList where telephone is null
        defaultClientShouldNotBeFound("telephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telephone contains DEFAULT_TELEPHONE
        defaultClientShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the clientList where telephone contains UPDATED_TELEPHONE
        defaultClientShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllClientsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where telephone does not contain DEFAULT_TELEPHONE
        defaultClientShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the clientList where telephone does not contain UPDATED_TELEPHONE
        defaultClientShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllClientsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax equals to DEFAULT_FAX
        defaultClientShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the clientList where fax equals to UPDATED_FAX
        defaultClientShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax not equals to DEFAULT_FAX
        defaultClientShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the clientList where fax not equals to UPDATED_FAX
        defaultClientShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultClientShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the clientList where fax equals to UPDATED_FAX
        defaultClientShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax is not null
        defaultClientShouldBeFound("fax.specified=true");

        // Get all the clientList where fax is null
        defaultClientShouldNotBeFound("fax.specified=false");
    }
                @Test
    @Transactional
    public void getAllClientsByFaxContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax contains DEFAULT_FAX
        defaultClientShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the clientList where fax contains UPDATED_FAX
        defaultClientShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientsByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList where fax does not contain DEFAULT_FAX
        defaultClientShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the clientList where fax does not contain UPDATED_FAX
        defaultClientShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }


    @Test
    @Transactional
    public void getAllClientsByCountiesIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        County counties = CountyResourceIT.createEntity(em);
        em.persist(counties);
        em.flush();
        client.addCounties(counties);
        clientRepository.saveAndFlush(client);
        Long countiesId = counties.getId();

        // Get all the clientList where counties equals to countiesId
        defaultClientShouldBeFound("countiesId.equals=" + countiesId);

        // Get all the clientList where counties equals to countiesId + 1
        defaultClientShouldNotBeFound("countiesId.equals=" + (countiesId + 1));
    }


    @Test
    @Transactional
    public void getAllClientsByJobsIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        Job jobs = JobResourceIT.createEntity(em);
        em.persist(jobs);
        em.flush();
        client.addJobs(jobs);
        clientRepository.saveAndFlush(client);
        Long jobsId = jobs.getId();

        // Get all the clientList where jobs equals to jobsId
        defaultClientShouldBeFound("jobsId.equals=" + jobsId);

        // Get all the clientList where jobs equals to jobsId + 1
        defaultClientShouldNotBeFound("jobsId.equals=" + (jobsId + 1));
    }


    @Test
    @Transactional
    public void getAllClientsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);
        PtmAccount account = PtmAccountResourceIT.createEntity(em);
        em.persist(account);
        em.flush();
        client.setAccount(account);
        clientRepository.saveAndFlush(client);
        Long accountId = account.getId();

        // Get all the clientList where account equals to accountId
        defaultClientShouldBeFound("accountId.equals=" + accountId);

        // Get all the clientList where account equals to accountId + 1
        defaultClientShouldNotBeFound("accountId.equals=" + (accountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClientShouldBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)));

        // Check, that the count call also returns 1
        restClientMockMvc.perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClientShouldNotBeFound(String filter) throws Exception {
        restClientMockMvc.perform(get("/api/clients?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClientMockMvc.perform(get("/api/clients/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .postcode(UPDATED_POSTCODE)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClient.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testClient.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testClient.getFax()).isEqualTo(UPDATED_FAX);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
