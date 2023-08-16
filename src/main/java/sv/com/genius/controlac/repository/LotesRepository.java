package sv.com.genius.controlac.repository;

import sv.com.genius.controlac.domain.Lotes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lotes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LotesRepository extends JpaRepository<Lotes, Long> {}
