package uz.uzkassa.developers.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.uzkassa.developers.web.rest.TestUtil;

class SkillCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillCategory.class);
        SkillCategory skillCategory1 = new SkillCategory();
        skillCategory1.setId(1L);
        SkillCategory skillCategory2 = new SkillCategory();
        skillCategory2.setId(skillCategory1.getId());
        assertThat(skillCategory1).isEqualTo(skillCategory2);
        skillCategory2.setId(2L);
        assertThat(skillCategory1).isNotEqualTo(skillCategory2);
        skillCategory1.setId(null);
        assertThat(skillCategory1).isNotEqualTo(skillCategory2);
    }
}
