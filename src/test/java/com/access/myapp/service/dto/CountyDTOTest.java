package com.access.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class CountyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountyDTO.class);
        CountyDTO countyDTO1 = new CountyDTO();
        countyDTO1.setId(1L);
        CountyDTO countyDTO2 = new CountyDTO();
        assertThat(countyDTO1).isNotEqualTo(countyDTO2);
        countyDTO2.setId(countyDTO1.getId());
        assertThat(countyDTO1).isEqualTo(countyDTO2);
        countyDTO2.setId(2L);
        assertThat(countyDTO1).isNotEqualTo(countyDTO2);
        countyDTO1.setId(null);
        assertThat(countyDTO1).isNotEqualTo(countyDTO2);
    }
}
