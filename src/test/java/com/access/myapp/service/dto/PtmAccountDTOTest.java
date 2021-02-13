package com.access.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class PtmAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PtmAccountDTO.class);
        PtmAccountDTO ptmAccountDTO1 = new PtmAccountDTO();
        ptmAccountDTO1.setId(1L);
        PtmAccountDTO ptmAccountDTO2 = new PtmAccountDTO();
        assertThat(ptmAccountDTO1).isNotEqualTo(ptmAccountDTO2);
        ptmAccountDTO2.setId(ptmAccountDTO1.getId());
        assertThat(ptmAccountDTO1).isEqualTo(ptmAccountDTO2);
        ptmAccountDTO2.setId(2L);
        assertThat(ptmAccountDTO1).isNotEqualTo(ptmAccountDTO2);
        ptmAccountDTO1.setId(null);
        assertThat(ptmAccountDTO1).isNotEqualTo(ptmAccountDTO2);
    }
}
