package uz.uzkassa.developers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.uzkassa.developers.web.rest.TestUtil;

class InterestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interest.class);
        Interest interest1 = new Interest();
        interest1.setId(1L);
        Interest interest2 = new Interest();
        interest2.setId(interest1.getId());
        assertThat(interest1).isEqualTo(interest2);
        interest2.setId(2L);
        assertThat(interest1).isNotEqualTo(interest2);
        interest1.setId(null);
        assertThat(interest1).isNotEqualTo(interest2);
    }
}
