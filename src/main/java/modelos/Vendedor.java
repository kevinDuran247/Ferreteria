/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "vendedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v"),
    @NamedQuery(name = "Vendedor.findByIdvendedor", query = "SELECT v FROM Vendedor v WHERE v.idvendedor = :idvendedor"),
    @NamedQuery(name = "Vendedor.findByNombresApellidos", query = "SELECT v FROM Vendedor v WHERE v.nombresApellidos = :nombresApellidos"),
    @NamedQuery(name = "Vendedor.findByUsuario", query = "SELECT v FROM Vendedor v WHERE v.usuario = :usuario"),
    @NamedQuery(name = "Vendedor.findByContrase\u00f1a", query = "SELECT v FROM Vendedor v WHERE v.contrase\u00f1a = :contrase\u00f1a")})
public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Idvendedor")
    private Integer idvendedor;
    @Basic(optional = false)
    @Column(name = "nombresApellidos")
    private String nombresApellidos;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idvendedor")
    private Collection<Ventas> ventasCollection;

    public Vendedor() {
    }

    public Vendedor(Integer idvendedor) {
        this.idvendedor = idvendedor;
    }

    public Vendedor(Integer idvendedor, String nombresApellidos, String usuario, String contraseña) {
        this.idvendedor = idvendedor;
        this.nombresApellidos = nombresApellidos;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public Integer getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Integer idvendedor) {
        this.idvendedor = idvendedor;
    }

    public String getNombresApellidos() {
        return nombresApellidos;
    }

    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @XmlTransient
    public Collection<Ventas> getVentasCollection() {
        return ventasCollection;
    }

    public void setVentasCollection(Collection<Ventas> ventasCollection) {
        this.ventasCollection = ventasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvendedor != null ? idvendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.idvendedor == null && other.idvendedor != null) || (this.idvendedor != null && !this.idvendedor.equals(other.idvendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Vendedor[ idvendedor=" + idvendedor + " ]";
    }
    
}
