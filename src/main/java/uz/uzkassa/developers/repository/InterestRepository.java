package uz.uzkassa.developers.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.uzkassa.developers.domain.Interest;

/**
 * Spring Data JPA repository for the Interest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {}
