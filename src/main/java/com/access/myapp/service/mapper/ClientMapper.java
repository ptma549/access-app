package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = {PtmAccountMapper.class})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {

    @Mapping(source = "account.id", target = "accountId")
    ClientDTO toDto(Client client);

    @Mapping(target = "counties", ignore = true)
    @Mapping(target = "removeCounties", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "removeJobs", ignore = true)
    @Mapping(source = "accountId", target = "account")
    Client toEntity(ClientDTO clientDTO);

    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
