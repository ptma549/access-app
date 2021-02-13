package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CountyMapperTest {

    private CountyMapper countyMapper;

    @BeforeEach
    public void setUp() {
        countyMapper = new CountyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(countyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(countyMapper.fromId(null)).isNull();
    }
}
