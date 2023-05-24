package uz.uzkassa.developers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.uzkassa.developers.web.rest.TestUtil;

class CareerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Career.class);
        Career career1 = new Career();
        career1.setId(1L);
        Career career2 = new Career();
        career2.setId(career1.getId());
        assertThat(career1).isEqualTo(career2);
        career2.setId(2L);
        assertThat(career1).isNotEqualTo(career2);
        career1.setId(null);
        assertThat(career1).isNotEqualTo(career2);
    }
}
