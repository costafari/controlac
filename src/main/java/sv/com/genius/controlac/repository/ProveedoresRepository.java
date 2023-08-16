package sv.com.genius.controlac.repository;

import sv.com.genius.controlac.domain.Proveedores;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Proveedores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedores, Long> {}
