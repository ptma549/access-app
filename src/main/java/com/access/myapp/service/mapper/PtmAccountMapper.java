package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.PtmAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PtmAccount} and its DTO {@link PtmAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PtmAccountMapper extends EntityMapper<PtmAccountDTO, PtmAccount> {


    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "removeClients", ignore = true)
    PtmAccount toEntity(PtmAccountDTO ptmAccountDTO);

    default PtmAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        PtmAccount ptmAccount = new PtmAccount();
        ptmAccount.setId(id);
        return ptmAccount;
    }
}
