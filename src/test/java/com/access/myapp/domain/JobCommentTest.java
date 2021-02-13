package com.access.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class JobCommentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobComment.class);
        JobComment jobComment1 = new JobComment();
        jobComment1.setId(1L);
        JobComment jobComment2 = new JobComment();
        jobComment2.setId(jobComment1.getId());
        assertThat(jobComment1).isEqualTo(jobComment2);
        jobComment2.setId(2L);
        assertThat(jobComment1).isNotEqualTo(jobComment2);
        jobComment1.setId(null);
        assertThat(jobComment1).isNotEqualTo(jobComment2);
    }
}
