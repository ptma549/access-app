package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TownMapperTest {

    private TownMapper townMapper;

    @BeforeEach
    public void setUp() {
        townMapper = new TownMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(townMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(townMapper.fromId(null)).isNull();
    }
}
