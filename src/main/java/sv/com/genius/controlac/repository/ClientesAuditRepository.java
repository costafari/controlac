package sv.com.genius.controlac.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sv.com.genius.controlac.domain.ClientesAudit;

/**
 * Spring Data JPA repository for the ClientesAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientesAuditRepository extends JpaRepository<ClientesAudit, Long> {}
