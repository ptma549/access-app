package com.access.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class JobVisitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobVisit.class);
        JobVisit jobVisit1 = new JobVisit();
        jobVisit1.setId(1L);
        JobVisit jobVisit2 = new JobVisit();
        jobVisit2.setId(jobVisit1.getId());
        assertThat(jobVisit1).isEqualTo(jobVisit2);
        jobVisit2.setId(2L);
        assertThat(jobVisit1).isNotEqualTo(jobVisit2);
        jobVisit1.setId(null);
        assertThat(jobVisit1).isNotEqualTo(jobVisit2);
    }
}
