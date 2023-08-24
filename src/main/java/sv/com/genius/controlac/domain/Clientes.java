package sv.com.genius.controlac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Clientes.
 */
@Entity
@Table(name = "clientes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "estado_cliente", nullable = false)
    private Boolean estadoCliente;

    @NotNull
    @Column(name = "nombres_contacto", nullable = false)
    private String nombresContacto;

    @NotNull
    @Column(name = "apellido_contacto", nullable = false)
    private String apellidoContacto;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "reg_fiscal")
    private String regFiscal;

    @Column(name = "giro")
    private String giro;

    @Column(name = "notas")
    private String notas;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @NotNull
    @Column(name = "telefono_fijo", nullable = false)
    private Integer telefonoFijo;

    @Column(name = "telefono_fijo_2")
    private Integer telefonoFijo2;

    @NotNull
    @Column(name = "telefono_movil", nullable = false)
    private Integer telefonoMovil;

    @Column(name = "telefono_movil_2")
    private Integer telefonoMovil2;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private String fechaRegistro;

    @NotNull
    @Column(name = "fecha_ultima_c", nullable = false)
    private String fechaUltimaC;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clientes")
    @JsonIgnoreProperties(value = { "detalles", "clientes", "abonos" }, allowSetters = true)
    private Set<Facturas> facturas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Clientes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstadoCliente() {
        return this.estadoCliente;
    }

    public Clientes estadoCliente(Boolean estadoCliente) {
        this.setEstadoCliente(estadoCliente);
        return this;
    }

    public void setEstadoCliente(Boolean estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public String getNombresContacto() {
        return this.nombresContacto;
    }

    public Clientes nombresContacto(String nombresContacto) {
        this.setNombresContacto(nombresContacto);
        return this;
    }

    public void setNombresContacto(String nombresContacto) {
        this.nombresContacto = nombresContacto;
    }

    public String getApellidoContacto() {
        return this.apellidoContacto;
    }

    public Clientes apellidoContacto(String apellidoContacto) {
        this.setApellidoContacto(apellidoContacto);
        return this;
    }

    public void setApellidoContacto(String apellidoContacto) {
        this.apellidoContacto = apellidoContacto;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Clientes direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return this.email;
    }

    public Clientes email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreEmpresa() {
        return this.nombreEmpresa;
    }

    public Clientes nombreEmpresa(String nombreEmpresa) {
        this.setNombreEmpresa(nombreEmpresa);
        return this;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRegFiscal() {
        return this.regFiscal;
    }

    public Clientes regFiscal(String regFiscal) {
        this.setRegFiscal(regFiscal);
        return this;
    }

    public void setRegFiscal(String regFiscal) {
        this.regFiscal = regFiscal;
    }

    public String getGiro() {
        return this.giro;
    }

    public Clientes giro(String giro) {
        this.setGiro(giro);
        return this;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getNotas() {
        return this.notas;
    }

    public Clientes notas(String notas) {
        this.setNotas(notas);
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getSitioWeb() {
        return this.sitioWeb;
    }

    public Clientes sitioWeb(String sitioWeb) {
        this.setSitioWeb(sitioWeb);
        return this;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public Integer getTelefonoFijo() {
        return this.telefonoFijo;
    }

    public Clientes telefonoFijo(Integer telefonoFijo) {
        this.setTelefonoFijo(telefonoFijo);
        return this;
    }

    public void setTelefonoFijo(Integer telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public Integer getTelefonoFijo2() {
        return this.telefonoFijo2;
    }

    public Clientes telefonoFijo2(Integer telefonoFijo2) {
        this.setTelefonoFijo2(telefonoFijo2);
        return this;
    }

    public void setTelefonoFijo2(Integer telefonoFijo2) {
        this.telefonoFijo2 = telefonoFijo2;
    }

    public Integer getTelefonoMovil() {
        return this.telefonoMovil;
    }

    public Clientes telefonoMovil(Integer telefonoMovil) {
        this.setTelefonoMovil(telefonoMovil);
        return this;
    }

    public void setTelefonoMovil(Integer telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Integer getTelefonoMovil2() {
        return this.telefonoMovil2;
    }

    public Clientes telefonoMovil2(Integer telefonoMovil2) {
        this.setTelefonoMovil2(telefonoMovil2);
        return this;
    }

    public void setTelefonoMovil2(Integer telefonoMovil2) {
        this.telefonoMovil2 = telefonoMovil2;
    }

    public String getFechaRegistro() {
        return this.fechaRegistro;
    }

    public Clientes fechaRegistro(String fechaRegistro) {
        this.setFechaRegistro(fechaRegistro);
        return this;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaUltimaC() {
        return this.fechaUltimaC;
    }

    public Clientes fechaUltimaC(String fechaUltimaC) {
        this.setFechaUltimaC(fechaUltimaC);
        return this;
    }

    public void setFechaUltimaC(String fechaUltimaC) {
        this.fechaUltimaC = fechaUltimaC;
    }

    public Set<Facturas> getFacturas() {
        return this.facturas;
    }

    public void setFacturas(Set<Facturas> facturas) {
        if (this.facturas != null) {
            this.facturas.forEach(i -> i.setClientes(null));
        }
        if (facturas != null) {
            facturas.forEach(i -> i.setClientes(this));
        }
        this.facturas = facturas;
    }

    public Clientes facturas(Set<Facturas> facturas) {
        this.setFacturas(facturas);
        return this;
    }

    public Clientes addFacturas(Facturas facturas) {
        this.facturas.add(facturas);
        facturas.setClientes(this);
        return this;
    }

    public Clientes removeFacturas(Facturas facturas) {
        this.facturas.remove(facturas);
        facturas.setClientes(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clientes)) {
            return false;
        }
        return id != null && id.equals(((Clientes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clientes{" +
            "id=" + getId() +
            ", estadoCliente='" + getEstadoCliente() + "'" +
            ", nombresContacto='" + getNombresContacto() + "'" +
            ", apellidoContacto='" + getApellidoContacto() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", email='" + getEmail() + "'" +
            ", nombreEmpresa='" + getNombreEmpresa() + "'" +
            ", regFiscal='" + getRegFiscal() + "'" +
            ", giro='" + getGiro() + "'" +
            ", notas='" + getNotas() + "'" +
            ", sitioWeb='" + getSitioWeb() + "'" +
            ", telefonoFijo=" + getTelefonoFijo() +
            ", telefonoFijo2=" + getTelefonoFijo2() +
            ", telefonoMovil=" + getTelefonoMovil() +
            ", telefonoMovil2=" + getTelefonoMovil2() +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", fechaUltimaC='" + getFechaUltimaC() + "'" +
            "}";
    }
}
