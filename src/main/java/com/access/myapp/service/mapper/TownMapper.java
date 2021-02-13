package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.TownDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Town} and its DTO {@link TownDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountyMapper.class})
public interface TownMapper extends EntityMapper<TownDTO, Town> {

    @Mapping(source = "county.id", target = "countyId")
    TownDTO toDto(Town town);

    @Mapping(target = "streets", ignore = true)
    @Mapping(target = "removeStreets", ignore = true)
    @Mapping(source = "countyId", target = "county")
    Town toEntity(TownDTO townDTO);

    default Town fromId(Long id) {
        if (id == null) {
            return null;
        }
        Town town = new Town();
        town.setId(id);
        return town;
    }
}
