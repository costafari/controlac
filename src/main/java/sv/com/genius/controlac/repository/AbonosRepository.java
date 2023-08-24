package sv.com.genius.controlac.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sv.com.genius.controlac.domain.Abonos;

/**
 * Spring Data JPA repository for the Abonos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbonosRepository extends JpaRepository<Abonos, Long> {}
