package sv.com.genius.controlac.repository;

import sv.com.genius.controlac.domain.Detalles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Detalles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetallesRepository extends JpaRepository<Detalles, Long> {}
