package com.access.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class JobCommentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobCommentDTO.class);
        JobCommentDTO jobCommentDTO1 = new JobCommentDTO();
        jobCommentDTO1.setId(1L);
        JobCommentDTO jobCommentDTO2 = new JobCommentDTO();
        assertThat(jobCommentDTO1).isNotEqualTo(jobCommentDTO2);
        jobCommentDTO2.setId(jobCommentDTO1.getId());
        assertThat(jobCommentDTO1).isEqualTo(jobCommentDTO2);
        jobCommentDTO2.setId(2L);
        assertThat(jobCommentDTO1).isNotEqualTo(jobCommentDTO2);
        jobCommentDTO1.setId(null);
        assertThat(jobCommentDTO1).isNotEqualTo(jobCommentDTO2);
    }
}
