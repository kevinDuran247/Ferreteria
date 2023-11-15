/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin Duran
 */
@Entity
@Table(name = "proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p"),
    @NamedQuery(name = "Proveedores.findByIdproveedor", query = "SELECT p FROM Proveedores p WHERE p.idproveedor = :idproveedor"),
    @NamedQuery(name = "Proveedores.findByNombre", query = "SELECT p FROM Proveedores p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proveedores.findByTelefono", query = "SELECT p FROM Proveedores p WHERE p.telefono = :telefono")})
public class Proveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproveedor")
    private Integer idproveedor;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(mappedBy = "proveedorId")
    private Collection<Productos> productosCollection;

    public Proveedores() {
    }

    public Proveedores(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }

    public Proveedores(Integer idproveedor, String nombre) {
        this.idproveedor = idproveedor;
        this.nombre = nombre;
    }
    
    public Proveedores(String nombre, String telefono) {     
        this.nombre = nombre;
        this.telefono = telefono;
       
    }

    public Integer getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public Collection<Productos> getProductosCollection() {
        return productosCollection;
    }

    public void setProductosCollection(Collection<Productos> productosCollection) {
        this.productosCollection = productosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproveedor != null ? idproveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.idproveedor == null && other.idproveedor != null) || (this.idproveedor != null && !this.idproveedor.equals(other.idproveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Proveedores[ idproveedor=" + idproveedor + " ]";
    }
    
}
