package sv.com.genius.controlac.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A ClientesAudit.
 */
@Entity
@Table(name = "clientes_audit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientesAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_audit", nullable = false)
    private String fechaAudit;

    @NotNull
    @Column(name = "tipo_crud", nullable = false)
    private String tipoCrud;

    @NotNull
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @NotNull
    @Column(name = "id_u_suario", nullable = false)
    private Long idUSuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientesAudit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFechaAudit() {
        return this.fechaAudit;
    }

    public ClientesAudit fechaAudit(String fechaAudit) {
        this.setFechaAudit(fechaAudit);
        return this;
    }

    public void setFechaAudit(String fechaAudit) {
        this.fechaAudit = fechaAudit;
    }

    public String getTipoCrud() {
        return this.tipoCrud;
    }

    public ClientesAudit tipoCrud(String tipoCrud) {
        this.setTipoCrud(tipoCrud);
        return this;
    }

    public void setTipoCrud(String tipoCrud) {
        this.tipoCrud = tipoCrud;
    }

    public Long getIdCliente() {
        return this.idCliente;
    }

    public ClientesAudit idCliente(Long idCliente) {
        this.setIdCliente(idCliente);
        return this;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdUSuario() {
        return this.idUSuario;
    }

    public ClientesAudit idUSuario(Long idUSuario) {
        this.setIdUSuario(idUSuario);
        return this;
    }

    public void setIdUSuario(Long idUSuario) {
        this.idUSuario = idUSuario;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientesAudit)) {
            return false;
        }
        return id != null && id.equals(((ClientesAudit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientesAudit{" +
            "id=" + getId() +
            ", fechaAudit='" + getFechaAudit() + "'" +
            ", tipoCrud='" + getTipoCrud() + "'" +
            ", idCliente=" + getIdCliente() +
            ", idUSuario=" + getIdUSuario() +
            "}";
    }
}
