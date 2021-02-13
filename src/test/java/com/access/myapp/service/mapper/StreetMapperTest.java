package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StreetMapperTest {

    private StreetMapper streetMapper;

    @BeforeEach
    public void setUp() {
        streetMapper = new StreetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(streetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(streetMapper.fromId(null)).isNull();
    }
}
