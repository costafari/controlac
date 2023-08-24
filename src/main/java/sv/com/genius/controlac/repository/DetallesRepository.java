package sv.com.genius.controlac.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sv.com.genius.controlac.domain.Detalles;

/**
 * Spring Data JPA repository for the Detalles entity.
 *
 * When extending this class, extend DetallesRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DetallesRepository extends DetallesRepositoryWithBagRelationships, JpaRepository<Detalles, Long> {
    default Optional<Detalles> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Detalles> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Detalles> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
