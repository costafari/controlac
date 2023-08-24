package sv.com.genius.controlac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Facturas.
 */
@Entity
@Table(name = "facturas")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Facturas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numero_factura", nullable = false)
    private Long numeroFactura;

    @NotNull
    @Column(name = "fecha_factura", nullable = false)
    private Instant fechaFactura;

    @NotNull
    @Column(name = "condicion_pago", nullable = false)
    private Boolean condicionPago;

    @NotNull
    @Column(name = "estado_factura", nullable = false)
    private Boolean estadoFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "facturas", "productos" }, allowSetters = true)
    private Detalles detalles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "facturas" }, allowSetters = true)
    private Clientes clientes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facturas")
    @JsonIgnoreProperties(value = { "facturas" }, allowSetters = true)
    private Set<Abonos> abonos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Facturas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroFactura() {
        return this.numeroFactura;
    }

    public Facturas numeroFactura(Long numeroFactura) {
        this.setNumeroFactura(numeroFactura);
        return this;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Instant getFechaFactura() {
        return this.fechaFactura;
    }

    public Facturas fechaFactura(Instant fechaFactura) {
        this.setFechaFactura(fechaFactura);
        return this;
    }

    public void setFechaFactura(Instant fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Boolean getCondicionPago() {
        return this.condicionPago;
    }

    public Facturas condicionPago(Boolean condicionPago) {
        this.setCondicionPago(condicionPago);
        return this;
    }

    public void setCondicionPago(Boolean condicionPago) {
        this.condicionPago = condicionPago;
    }

    public Boolean getEstadoFactura() {
        return this.estadoFactura;
    }

    public Facturas estadoFactura(Boolean estadoFactura) {
        this.setEstadoFactura(estadoFactura);
        return this;
    }

    public void setEstadoFactura(Boolean estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public Detalles getDetalles() {
        return this.detalles;
    }

    public void setDetalles(Detalles detalles) {
        this.detalles = detalles;
    }

    public Facturas detalles(Detalles detalles) {
        this.setDetalles(detalles);
        return this;
    }

    public Clientes getClientes() {
        return this.clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Facturas clientes(Clientes clientes) {
        this.setClientes(clientes);
        return this;
    }

    public Set<Abonos> getAbonos() {
        return this.abonos;
    }

    public void setAbonos(Set<Abonos> abonos) {
        if (this.abonos != null) {
            this.abonos.forEach(i -> i.setFacturas(null));
        }
        if (abonos != null) {
            abonos.forEach(i -> i.setFacturas(this));
        }
        this.abonos = abonos;
    }

    public Facturas abonos(Set<Abonos> abonos) {
        this.setAbonos(abonos);
        return this;
    }

    public Facturas addAbonos(Abonos abonos) {
        this.abonos.add(abonos);
        abonos.setFacturas(this);
        return this;
    }

    public Facturas removeAbonos(Abonos abonos) {
        this.abonos.remove(abonos);
        abonos.setFacturas(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facturas)) {
            return false;
        }
        return id != null && id.equals(((Facturas) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facturas{" +
            "id=" + getId() +
            ", numeroFactura=" + getNumeroFactura() +
            ", fechaFactura='" + getFechaFactura() + "'" +
            ", condicionPago='" + getCondicionPago() + "'" +
            ", estadoFactura='" + getEstadoFactura() + "'" +
            "}";
    }
}
