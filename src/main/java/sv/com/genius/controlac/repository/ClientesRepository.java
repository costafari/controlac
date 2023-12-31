package sv.com.genius.controlac.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sv.com.genius.controlac.domain.Clientes;

/**
 * Spring Data JPA repository for the Clientes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {}
