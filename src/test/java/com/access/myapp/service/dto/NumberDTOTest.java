package com.access.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class NumberDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumberDTO.class);
        NumberDTO numberDTO1 = new NumberDTO();
        numberDTO1.setId(1L);
        NumberDTO numberDTO2 = new NumberDTO();
        assertThat(numberDTO1).isNotEqualTo(numberDTO2);
        numberDTO2.setId(numberDTO1.getId());
        assertThat(numberDTO1).isEqualTo(numberDTO2);
        numberDTO2.setId(2L);
        assertThat(numberDTO1).isNotEqualTo(numberDTO2);
        numberDTO1.setId(null);
        assertThat(numberDTO1).isNotEqualTo(numberDTO2);
    }
}
