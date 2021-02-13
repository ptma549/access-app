package com.access.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class JobCommentMapperTest {

    private JobCommentMapper jobCommentMapper;

    @BeforeEach
    public void setUp() {
        jobCommentMapper = new JobCommentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(jobCommentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(jobCommentMapper.fromId(null)).isNull();
    }
}
