package uz.uzkassa.developers.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.uzkassa.developers.domain.Career;

/**
 * Spring Data JPA repository for the Career entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {}
