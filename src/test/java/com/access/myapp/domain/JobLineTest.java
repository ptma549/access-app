package com.access.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class JobLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobLine.class);
        JobLine jobLine1 = new JobLine();
        jobLine1.setId(1L);
        JobLine jobLine2 = new JobLine();
        jobLine2.setId(jobLine1.getId());
        assertThat(jobLine1).isEqualTo(jobLine2);
        jobLine2.setId(2L);
        assertThat(jobLine1).isNotEqualTo(jobLine2);
        jobLine1.setId(null);
        assertThat(jobLine1).isNotEqualTo(jobLine2);
    }
}
