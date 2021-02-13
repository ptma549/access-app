package com.access.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.access.myapp.web.rest.TestUtil;

public class NumberTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Number.class);
        Number number1 = new Number();
        number1.setId(1L);
        Number number2 = new Number();
        number2.setId(number1.getId());
        assertThat(number1).isEqualTo(number2);
        number2.setId(2L);
        assertThat(number1).isNotEqualTo(number2);
        number1.setId(null);
        assertThat(number1).isNotEqualTo(number2);
    }
}
