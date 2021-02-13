package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NumberMapperTest {

    private NumberMapper numberMapper;

    @BeforeEach
    public void setUp() {
        numberMapper = new NumberMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(numberMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(numberMapper.fromId(null)).isNull();
    }
}
