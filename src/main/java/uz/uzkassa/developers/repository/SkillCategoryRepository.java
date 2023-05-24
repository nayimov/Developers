package uz.uzkassa.developers.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.uzkassa.developers.domain.SkillCategory;

/**
 * Spring Data JPA repository for the SkillCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillCategoryRepository extends JpaRepository<SkillCategory, Long> {}
