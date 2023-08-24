package sv.com.genius.controlac.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sv.com.genius.controlac.domain.ProductoAudit;

/**
 * Spring Data JPA repository for the ProductoAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoAuditRepository extends JpaRepository<ProductoAudit, Long> {}
