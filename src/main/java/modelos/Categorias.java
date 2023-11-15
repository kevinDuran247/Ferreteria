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
@Table(name = "categorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorias.findAll", query = "SELECT c FROM Categorias c"),
    @NamedQuery(name = "Categorias.findByIdcategoria", query = "SELECT c FROM Categorias c WHERE c.idcategoria = :idcategoria"),
    @NamedQuery(name = "Categorias.findByNombre", query = "SELECT c FROM Categorias c WHERE c.nombre = :nombre")})
public class Categorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcategoria")
    private Integer idcategoria;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "categoriaId")
    private Collection<Productos> productosCollection;

    public Categorias() {
    }

    public Categorias(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Categorias(Integer idcategoria, String nombre) {
        this.idcategoria = idcategoria;
        this.nombre = nombre;
    }

    public Integer getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (idcategoria != null ? idcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorias)) {
            return false;
        }
        Categorias other = (Categorias) object;
        if ((this.idcategoria == null && other.idcategoria != null) || (this.idcategoria != null && !this.idcategoria.equals(other.idcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Categorias[ idcategoria=" + idcategoria + " ]";
    }
    
}
