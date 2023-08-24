package sv.com.genius.controlac.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Productos.
 */
@Entity
@Table(name = "productos")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descipcion", nullable = false)
    private String descipcion;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "precio_u", nullable = false)
    private Long precioU;

    @NotNull
    @Column(name = "precio_c", nullable = false)
    private Long precioC;

    @Column(name = "notas")
    private String notas;

    @NotNull
    @Column(name = "estado_producto", nullable = false)
    private String estadoProducto;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private String fechaRegistro;

    @NotNull
    @Column(name = "fecha_caducidad", nullable = false)
    private String fechaCaducidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productos", "lotes" }, allowSetters = true)
    private Proveedores proveedores;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "productos")
    @JsonIgnoreProperties(value = { "facturas", "productos" }, allowSetters = true)
    private Set<Detalles> detalles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Productos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescipcion() {
        return this.descipcion;
    }

    public Productos descipcion(String descipcion) {
        this.setDescipcion(descipcion);
        return this;
    }

    public void setDescipcion(String descipcion) {
        this.descipcion = descipcion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Productos nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecioU() {
        return this.precioU;
    }

    public Productos precioU(Long precioU) {
        this.setPrecioU(precioU);
        return this;
    }

    public void setPrecioU(Long precioU) {
        this.precioU = precioU;
    }

    public Long getPrecioC() {
        return this.precioC;
    }

    public Productos precioC(Long precioC) {
        this.setPrecioC(precioC);
        return this;
    }

    public void setPrecioC(Long precioC) {
        this.precioC = precioC;
    }

    public String getNotas() {
        return this.notas;
    }

    public Productos notas(String notas) {
        this.setNotas(notas);
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEstadoProducto() {
        return this.estadoProducto;
    }

    public Productos estadoProducto(String estadoProducto) {
        this.setEstadoProducto(estadoProducto);
        return this;
    }

    public void setEstadoProducto(String estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public String getFechaRegistro() {
        return this.fechaRegistro;
    }

    public Productos fechaRegistro(String fechaRegistro) {
        this.setFechaRegistro(fechaRegistro);
        return this;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaCaducidad() {
        return this.fechaCaducidad;
    }

    public Productos fechaCaducidad(String fechaCaducidad) {
        this.setFechaCaducidad(fechaCaducidad);
        return this;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Proveedores getProveedores() {
        return this.proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public Productos proveedores(Proveedores proveedores) {
        this.setProveedores(proveedores);
        return this;
    }

    public Set<Detalles> getDetalles() {
        return this.detalles;
    }

    public void setDetalles(Set<Detalles> detalles) {
        if (this.detalles != null) {
            this.detalles.forEach(i -> i.removeProductos(this));
        }
        if (detalles != null) {
            detalles.forEach(i -> i.addProductos(this));
        }
        this.detalles = detalles;
    }

    public Productos detalles(Set<Detalles> detalles) {
        this.setDetalles(detalles);
        return this;
    }

    public Productos addDetalles(Detalles detalles) {
        this.detalles.add(detalles);
        detalles.getProductos().add(this);
        return this;
    }

    public Productos removeDetalles(Detalles detalles) {
        this.detalles.remove(detalles);
        detalles.getProductos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Productos)) {
            return false;
        }
        return id != null && id.equals(((Productos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Productos{" +
            "id=" + getId() +
            ", descipcion='" + getDescipcion() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", precioU=" + getPrecioU() +
            ", precioC=" + getPrecioC() +
            ", notas='" + getNotas() + "'" +
            ", estadoProducto='" + getEstadoProducto() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", fechaCaducidad='" + getFechaCaducidad() + "'" +
            "}";
    }
}
