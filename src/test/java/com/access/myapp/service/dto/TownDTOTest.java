package com.access.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class TownDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownDTO.class);
        TownDTO townDTO1 = new TownDTO();
        townDTO1.setId(1L);
        TownDTO townDTO2 = new TownDTO();
        assertThat(townDTO1).isNotEqualTo(townDTO2);
        townDTO2.setId(townDTO1.getId());
        assertThat(townDTO1).isEqualTo(townDTO2);
        townDTO2.setId(2L);
        assertThat(townDTO1).isNotEqualTo(townDTO2);
        townDTO1.setId(null);
        assertThat(townDTO1).isNotEqualTo(townDTO2);
    }
}
