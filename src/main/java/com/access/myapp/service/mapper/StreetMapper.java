package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.StreetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Street} and its DTO {@link StreetDTO}.
 */
@Mapper(componentModel = "spring", uses = {TownMapper.class})
public interface StreetMapper extends EntityMapper<StreetDTO, Street> {

    @Mapping(source = "town.id", target = "townId")
    StreetDTO toDto(Street street);

    @Mapping(target = "numbers", ignore = true)
    @Mapping(target = "removeNumbers", ignore = true)
    @Mapping(source = "townId", target = "town")
    Street toEntity(StreetDTO streetDTO);

    default Street fromId(Long id) {
        if (id == null) {
            return null;
        }
        Street street = new Street();
        street.setId(id);
        return street;
    }
}
