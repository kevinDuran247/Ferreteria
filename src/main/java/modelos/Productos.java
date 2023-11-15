/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin Duran
 */
@Entity
@Table(name = "productos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p"),
    @NamedQuery(name = "Productos.findByIdproducto", query = "SELECT p FROM Productos p WHERE p.idproducto = :idproducto"),
    @NamedQuery(name = "Productos.findByNombre", query = "SELECT p FROM Productos p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Productos.findByDescripcion", query = "SELECT p FROM Productos p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Productos.findByUrlimagen", query = "SELECT p FROM Productos p WHERE p.urlimagen = :urlimagen"),
    @NamedQuery(name = "Productos.findByStock", query = "SELECT p FROM Productos p WHERE p.stock = :stock"),
    @NamedQuery(name = "Productos.findBySminimo", query = "SELECT p FROM Productos p WHERE p.sminimo = :sminimo"),
    @NamedQuery(name = "Productos.findByNombreAndCategoria", query = "SELECT p FROM Productos p WHERE p.nombre = :nombre AND p.categoriaId.idcategoria = :idcategoria"),
    @NamedQuery(name = "Productos.findByPrecio", query = "SELECT p FROM Productos p WHERE p.precio = :precio")
})
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducto")
    private Integer idproducto;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "urlimagen")
    private String urlimagen;
    @Basic(optional = false)
    @Column(name = "stock")
    private int stock;
    @Basic(optional = false)
    @Column(name = "sminimo")
    private int sminimo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "precio")
    private Double precio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idproducto")
    private Collection<Detalleventas> detalleventasCollection;
    @JoinColumn(name = "proveedor_id", referencedColumnName = "idproveedor")
    @ManyToOne
    private Proveedores proveedorId;
    @JoinColumn(name = "categoria_id", referencedColumnName = "idcategoria")
    @ManyToOne
    private Categorias categoriaId;

    @Transient
    private String nombreProveedor;

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Transient
    private String nombreCategoria;

    public Productos() {
    }

    public Productos(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public Productos(Integer idproducto, String nombre, String descripcion, String urlimagen, int stock, int sminimo, double precio, Proveedores proveedorId, Categorias categoriaId) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlimagen = urlimagen;
        this.stock = stock;
        this.sminimo = sminimo;
        this.precio = precio;
        this.proveedorId = proveedorId;
        this.categoriaId = categoriaId;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSminimo() {
        return sminimo;
    }

    public void setSminimo(int sminimo) {
        this.sminimo = sminimo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public Collection<Detalleventas> getDetalleventasCollection() {
        return detalleventasCollection;
    }

    public void setDetalleventasCollection(Collection<Detalleventas> detalleventasCollection) {
        this.detalleventasCollection = detalleventasCollection;
    }

    public Proveedores getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Proveedores proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Categorias getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Categorias categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducto != null ? idproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.idproducto == null && other.idproducto != null) || (this.idproducto != null && !this.idproducto.equals(other.idproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Productos[ idproducto=" + idproducto + " ]";
    }

}
