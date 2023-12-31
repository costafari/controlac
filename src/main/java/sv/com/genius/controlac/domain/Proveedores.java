package sv.com.genius.controlac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Proveedores.
 */
@Entity
@Table(name = "proveedores")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Proveedores implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "nombre_contacto", nullable = false)
    private String nombreContacto;

    @NotNull
    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proveedores")
    @JsonIgnoreProperties(value = { "proveedores", "detalles" }, allowSetters = true)
    private Set<Productos> productos = new HashSet<>();

    @JsonIgnoreProperties(value = { "proveedores" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "proveedores")
    private Lotes lotes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Proveedores id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Proveedores direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreContacto() {
        return this.nombreContacto;
    }

    public Proveedores nombreContacto(String nombreContacto) {
        this.setNombreContacto(nombreContacto);
        return this;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getNombreEmpresa() {
        return this.nombreEmpresa;
    }

    public Proveedores nombreEmpresa(String nombreEmpresa) {
        this.setNombreEmpresa(nombreEmpresa);
        return this;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNotas() {
        return this.notas;
    }

    public Proveedores notas(String notas) {
        this.setNotas(notas);
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getSitioWeb() {
        return this.sitioWeb;
    }

    public Proveedores sitioWeb(String sitioWeb) {
        this.setSitioWeb(sitioWeb);
        return this;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public Integer getTelefonoFijo() {
        return this.telefonoFijo;
    }

    public Proveedores telefonoFijo(Integer telefonoFijo) {
        this.setTelefonoFijo(telefonoFijo);
        return this;
    }

    public void setTelefonoFijo(Integer telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public Integer getTelefonoFijo2() {
        return this.telefonoFijo2;
    }

    public Proveedores telefonoFijo2(Integer telefonoFijo2) {
        this.setTelefonoFijo2(telefonoFijo2);
        return this;
    }

    public void setTelefonoFijo2(Integer telefonoFijo2) {
        this.telefonoFijo2 = telefonoFijo2;
    }

    public Integer getTelefonoMovil() {
        return this.telefonoMovil;
    }

    public Proveedores telefonoMovil(Integer telefonoMovil) {
        this.setTelefonoMovil(telefonoMovil);
        return this;
    }

    public void setTelefonoMovil(Integer telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Integer getTelefonoMovil2() {
        return this.telefonoMovil2;
    }

    public Proveedores telefonoMovil2(Integer telefonoMovil2) {
        this.setTelefonoMovil2(telefonoMovil2);
        return this;
    }

    public void setTelefonoMovil2(Integer telefonoMovil2) {
        this.telefonoMovil2 = telefonoMovil2;
    }

    public Set<Productos> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Productos> productos) {
        if (this.productos != null) {
            this.productos.forEach(i -> i.setProveedores(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setProveedores(this));
        }
        this.productos = productos;
    }

    public Proveedores productos(Set<Productos> productos) {
        this.setProductos(productos);
        return this;
    }

    public Proveedores addProductos(Productos productos) {
        this.productos.add(productos);
        productos.setProveedores(this);
        return this;
    }

    public Proveedores removeProductos(Productos productos) {
        this.productos.remove(productos);
        productos.setProveedores(null);
        return this;
    }

    public Lotes getLotes() {
        return this.lotes;
    }

    public void setLotes(Lotes lotes) {
        if (this.lotes != null) {
            this.lotes.setProveedores(null);
        }
        if (lotes != null) {
            lotes.setProveedores(this);
        }
        this.lotes = lotes;
    }

    public Proveedores lotes(Lotes lotes) {
        this.setLotes(lotes);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proveedores)) {
            return false;
        }
        return id != null && id.equals(((Proveedores) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proveedores{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", nombreContacto='" + getNombreContacto() + "'" +
            ", nombreEmpresa='" + getNombreEmpresa() + "'" +
            ", notas='" + getNotas() + "'" +
            ", sitioWeb='" + getSitioWeb() + "'" +
            ", telefonoFijo=" + getTelefonoFijo() +
            ", telefonoFijo2=" + getTelefonoFijo2() +
            ", telefonoMovil=" + getTelefonoMovil() +
            ", telefonoMovil2=" + getTelefonoMovil2() +
            "}";
    }
}
