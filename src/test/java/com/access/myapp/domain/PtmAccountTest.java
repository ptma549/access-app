package com.access.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class PtmAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PtmAccount.class);
        PtmAccount ptmAccount1 = new PtmAccount();
        ptmAccount1.setId(1L);
        PtmAccount ptmAccount2 = new PtmAccount();
        ptmAccount2.setId(ptmAccount1.getId());
        assertThat(ptmAccount1).isEqualTo(ptmAccount2);
        ptmAccount2.setId(2L);
        assertThat(ptmAccount1).isNotEqualTo(ptmAccount2);
        ptmAccount1.setId(null);
        assertThat(ptmAccount1).isNotEqualTo(ptmAccount2);
    }
}
