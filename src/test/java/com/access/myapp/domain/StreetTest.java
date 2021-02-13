package com.access.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class StreetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Street.class);
        Street street1 = new Street();
        street1.setId(1L);
        Street street2 = new Street();
        street2.setId(street1.getId());
        assertThat(street1).isEqualTo(street2);
        street2.setId(2L);
        assertThat(street1).isNotEqualTo(street2);
        street1.setId(null);
        assertThat(street1).isNotEqualTo(street2);
    }
}
