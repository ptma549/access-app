package com.access.myapp.service.mapper;


import com.access.myapp.domain.*;
import com.access.myapp.service.dto.NumberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Number} and its DTO {@link NumberDTO}.
 */
@Mapper(componentModel = "spring", uses = {StreetMapper.class})
public interface NumberMapper extends EntityMapper<NumberDTO, Number> {

    @Mapping(source = "street.id", target = "streetId")
    NumberDTO toDto(Number number);

    @Mapping(target = "positions", ignore = true)
    @Mapping(target = "removePositions", ignore = true)
    @Mapping(source = "streetId", target = "street")
    Number toEntity(NumberDTO numberDTO);

    default Number fromId(Long id) {
        if (id == null) {
            return null;
        }
        Number number = new Number();
        number.setId(id);
        return number;
    }
}
