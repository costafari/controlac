package sv.com.genius.controlac.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sv.com.genius.controlac.domain.ProveedorAudit;

/**
 * Spring Data JPA repository for the ProveedorAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedorAuditRepository extends JpaRepository<ProveedorAudit, Long> {}
