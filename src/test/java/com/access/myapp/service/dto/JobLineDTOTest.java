package com.access.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class JobLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobLineDTO.class);
        JobLineDTO jobLineDTO1 = new JobLineDTO();
        jobLineDTO1.setId(1L);
        JobLineDTO jobLineDTO2 = new JobLineDTO();
        assertThat(jobLineDTO1).isNotEqualTo(jobLineDTO2);
        jobLineDTO2.setId(jobLineDTO1.getId());
        assertThat(jobLineDTO1).isEqualTo(jobLineDTO2);
        jobLineDTO2.setId(2L);
        assertThat(jobLineDTO1).isNotEqualTo(jobLineDTO2);
        jobLineDTO1.setId(null);
        assertThat(jobLineDTO1).isNotEqualTo(jobLineDTO2);
    }
}
