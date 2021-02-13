package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JobLineMapperTest {

    private JobLineMapper jobLineMapper;

    @BeforeEach
    public void setUp() {
        jobLineMapper = new JobLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jobLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobLineMapper.fromId(null)).isNull();
    }
}
