package sv.com.genius.controlac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Lotes.
 */
@Entity
@Table(name = "lotes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lotes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @NotNull
    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @NotNull
    @Column(name = "lote", nullable = false)
    private String lote;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "notas")
    private String notas;

    @JsonIgnoreProperties(value = { "productos", "lotes" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Proveedores proveedores;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lotes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public Lotes cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaEntrada() {
        return this.fechaEntrada;
    }

    public Lotes fechaEntrada(LocalDate fechaEntrada) {
        this.setFechaEntrada(fechaEntrada);
        return this;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getLote() {
        return this.lote;
    }

    public Lotes lote(String lote) {
        this.setLote(lote);
        return this;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getEstado() {
        return this.estado;
    }

    public Lotes estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return this.notas;
    }

    public Lotes notas(String notas) {
        this.setNotas(notas);
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Proveedores getProveedores() {
        return this.proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public Lotes proveedores(Proveedores proveedores) {
        this.setProveedores(proveedores);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lotes)) {
            return false;
        }
        return id != null && id.equals(((Lotes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lotes{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", fechaEntrada='" + getFechaEntrada() + "'" +
            ", lote='" + getLote() + "'" +
            ", estado='" + getEstado() + "'" +
            ", notas='" + getNotas() + "'" +
            "}";
    }
}
