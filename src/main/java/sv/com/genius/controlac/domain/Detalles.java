package sv.com.genius.controlac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Detalles.
 */
@Entity
@Table(name = "detalles")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Detalles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Long cantidad;

    @NotNull
    @Column(name = "impuestos", nullable = false)
    private Long impuestos;

    @NotNull
    @Column(name = "descuento", nullable = false)
    private Long descuento;

    @Column(name = "total")
    private Long total;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "detalles")
    @JsonIgnoreProperties(value = { "detalles", "clientes", "abonos" }, allowSetters = true)
    private Set<Facturas> facturas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_detalles__productos",
        joinColumns = @JoinColumn(name = "detalles_id"),
        inverseJoinColumns = @JoinColumn(name = "productos_id")
    )
    @JsonIgnoreProperties(value = { "proveedores", "detalles" }, allowSetters = true)
    private Set<Productos> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Detalles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return this.cantidad;
    }

    public Detalles cantidad(Long cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getImpuestos() {
        return this.impuestos;
    }

    public Detalles impuestos(Long impuestos) {
        this.setImpuestos(impuestos);
        return this;
    }

    public void setImpuestos(Long impuestos) {
        this.impuestos = impuestos;
    }

    public Long getDescuento() {
        return this.descuento;
    }

    public Detalles descuento(Long descuento) {
        this.setDescuento(descuento);
        return this;
    }

    public void setDescuento(Long descuento) {
        this.descuento = descuento;
    }

    public Long getTotal() {
        return this.total;
    }

    public Detalles total(Long total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Set<Facturas> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<Facturas> facturas) {
        if (this.facturas != null) {
            this.facturas.forEach(i -> i.setDetalles(null));
        }
        if (facturas != null) {
            facturas.forEach(i -> i.setDetalles(this));
        }
        this.facturas = facturas;
    }

    public Detalles facturas(Set<Facturas> facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public Detalles addFacturas(Facturas facturas) {
        this.facturas.add(facturas);
        facturas.setDetalles(this);
        return this;
    }

    public Detalles removeFacturas(Facturas facturas) {
        this.facturas.remove(facturas);
        facturas.setDetalles(null);
        return this;
    }

    public Set<Productos> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Productos> productos) {
        this.productos = productos;
    }

    public Detalles productos(Set<Productos> productos) {
        this.setProductos(productos);
        return this;
    }

    public Detalles addProductos(Productos productos) {
        this.productos.add(productos);
        productos.getDetalles().add(this);
        return this;
    }

    public Detalles removeProductos(Productos productos) {
        this.productos.remove(productos);
        productos.getDetalles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Detalles)) {
            return false;
        }
        return id != null && id.equals(((Detalles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Detalles{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", impuestos=" + getImpuestos() +
            ", descuento=" + getDescuento() +
            ", total=" + getTotal() +
            "}";
    }
}
