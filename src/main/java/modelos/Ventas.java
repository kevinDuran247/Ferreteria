/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin Duran
 */
@Entity
@Table(name = "ventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v"),
    @NamedQuery(name = "Ventas.findByIdventa", query = "SELECT v FROM Ventas v WHERE v.idventa = :idventa"),
    @NamedQuery(name = "Ventas.findByTotal", query = "SELECT v FROM Ventas v WHERE v.total = :total"),
    @NamedQuery(name = "Ventas.findByFecha", query = "SELECT v FROM Ventas v WHERE v.fecha = :fecha"),
    @NamedQuery(name = "Ventas.findByVendedor", query = "SELECT v FROM Ventas v WHERE v.idvendedor = :vendedor"),
    @NamedQuery(name = "Ventas.findByCodigo", query = "SELECT v FROM Ventas v WHERE v.codigo = :codigo")})
public class Ventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idventa")
    private Integer idventa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total")
    private Double total;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idventa")
    private Collection<Detalleventas> detalleventasCollection;
    @JoinColumn(name = "idvendedor", referencedColumnName = "Idvendedor")
    @ManyToOne(optional = false)
    private Vendedor idvendedor;
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    
    @Transient
    private String nombreVendedor;

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public Ventas() {
    }

    public Ventas(Integer idventa) {
        this.idventa = idventa;
    }

    public Ventas(Integer idventa, Vendedor idvendedor, Double total, Date fecha, String codigo) {
        this.idventa = idventa;
        this.idvendedor = idvendedor;
        this.total = total;
        this.fecha = fecha;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIdventa() {
        return idventa;
    }

    public void setIdventa(Integer idventa) {
        this.idventa = idventa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public Collection<Detalleventas> getDetalleventasCollection() {
        return detalleventasCollection;
    }

    public void setDetalleventasCollection(Collection<Detalleventas> detalleventasCollection) {
        this.detalleventasCollection = detalleventasCollection;
    }

    public Vendedor getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Vendedor idvendedor) {
        this.idvendedor = idvendedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idventa != null ? idventa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventas)) {
            return false;
        }
        Ventas other = (Ventas) object;
        if ((this.idventa == null && other.idventa != null) || (this.idventa != null && !this.idventa.equals(other.idventa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Ventas[ idventa=" + idventa + " ]";
    }
    
}
