package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PtmAccountMapperTest {

    private PtmAccountMapper ptmAccountMapper;

    @BeforeEach
    public void setUp() {
        ptmAccountMapper = new PtmAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ptmAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ptmAccountMapper.fromId(null)).isNull();
    }
}
