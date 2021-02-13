package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JobVisitMapperTest {

    private JobVisitMapper jobVisitMapper;

    @BeforeEach
    public void setUp() {
        jobVisitMapper = new JobVisitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jobVisitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobVisitMapper.fromId(null)).isNull();
    }
}
