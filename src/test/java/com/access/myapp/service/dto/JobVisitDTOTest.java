package com.access.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class JobVisitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobVisitDTO.class);
        JobVisitDTO jobVisitDTO1 = new JobVisitDTO();
        jobVisitDTO1.setId(1L);
        JobVisitDTO jobVisitDTO2 = new JobVisitDTO();
        assertThat(jobVisitDTO1).isNotEqualTo(jobVisitDTO2);
        jobVisitDTO2.setId(jobVisitDTO1.getId());
        assertThat(jobVisitDTO1).isEqualTo(jobVisitDTO2);
        jobVisitDTO2.setId(2L);
        assertThat(jobVisitDTO1).isNotEqualTo(jobVisitDTO2);
        jobVisitDTO1.setId(null);
        assertThat(jobVisitDTO1).isNotEqualTo(jobVisitDTO2);
    }
}
