package sv.com.genius.controlac.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import sv.com.genius.controlac.domain.Detalles;

public interface DetallesRepositoryWithBagRelationships {
    Optional<Detalles> fetchBagRelationships(Optional<Detalles> detalles);

    List<Detalles> fetchBagRelationships(List<Detalles> detalles);

    Page<Detalles> fetchBagRelationships(Page<Detalles> detalles);
}
