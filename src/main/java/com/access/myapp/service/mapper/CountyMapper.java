package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.CountyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link County} and its DTO {@link CountyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface CountyMapper extends EntityMapper<CountyDTO, County> {

    @Mapping(source = "client.id", target = "clientId")
    CountyDTO toDto(County county);

    @Mapping(target = "towns", ignore = true)
    @Mapping(target = "removeTown", ignore = true)
    @Mapping(source = "clientId", target = "client")
    County toEntity(CountyDTO countyDTO);

    default County fromId(Long id) {
        if (id == null) {
            return null;
        }
        County county = new County();
        county.setId(id);
        return county;
    }
}
