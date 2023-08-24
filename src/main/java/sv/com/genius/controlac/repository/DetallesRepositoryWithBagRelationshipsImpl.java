package sv.com.genius.controlac.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import sv.com.genius.controlac.domain.Detalles;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DetallesRepositoryWithBagRelationshipsImpl implements DetallesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Detalles> fetchBagRelationships(Optional<Detalles> detalles) {
        return detalles.map(this::fetchProductos);
    }

    @Override
    public Page<Detalles> fetchBagRelationships(Page<Detalles> detalles) {
        return new PageImpl<>(fetchBagRelationships(detalles.getContent()), detalles.getPageable(), detalles.getTotalElements());
    }

    @Override
    public List<Detalles> fetchBagRelationships(List<Detalles> detalles) {
        return Optional.of(detalles).map(this::fetchProductos).orElse(Collections.emptyList());
    }

    Detalles fetchProductos(Detalles result) {
        return entityManager
            .createQuery(
                "select detalles from Detalles detalles left join fetch detalles.productos where detalles.id = :id",
                Detalles.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Detalles> fetchProductos(List<Detalles> detalles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, detalles.size()).forEach(index -> order.put(detalles.get(index).getId(), index));
        List<Detalles> result = entityManager
            .createQuery(
                "select detalles from Detalles detalles left join fetch detalles.productos where detalles in :detalles",
                Detalles.class
            )
            .setParameter("detalles", detalles)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
