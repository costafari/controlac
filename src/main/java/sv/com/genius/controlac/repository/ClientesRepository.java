package sv.com.genius.controlac.repository;

import sv.com.genius.controlac.domain.Clientes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Clientes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {}
