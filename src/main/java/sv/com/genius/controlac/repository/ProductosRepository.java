package sv.com.genius.controlac.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sv.com.genius.controlac.domain.Productos;

/**
 * Spring Data JPA repository for the Productos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {}
