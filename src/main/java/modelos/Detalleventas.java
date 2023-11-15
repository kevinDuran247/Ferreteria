/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kevin Duran
 */
@Entity
@Table(name = "detalleventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detalleventas.findAll", query = "SELECT d FROM Detalleventas d"),
    @NamedQuery(name = "Detalleventas.findByIddetalle", query = "SELECT d FROM Detalleventas d WHERE d.iddetalle = :iddetalle"),
    @NamedQuery(name = "Detalleventas.findByCantidad", query = "SELECT d FROM Detalleventas d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "Detalleventas.findByTotal", query = "SELECT d FROM Detalleventas d WHERE d.total = :total"),
    @NamedQuery(name = "Detalleventas.findByVentas", query = "SELECT d FROM Detalleventas d WHERE d.idventa = :ventas"),
    @NamedQuery(name = "Detalleventas.findByFecha", query = "SELECT d FROM Detalleventas d WHERE d.fecha = :fecha")})
public class Detalleventas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetalle")
    private Integer iddetalle;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
     @Basic(optional = false)
    @Column(name = "total")
    private Double total;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "idventa", referencedColumnName = "idventa")
    @ManyToOne(optional = false)
    private Ventas idventa;
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Productos idproducto;
    @Transient
    private String nombreProducto;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Detalleventas() {
    }

    public Detalleventas(Integer iddetalle) {
        this.iddetalle = iddetalle;
    }

    public Detalleventas(Integer iddetalle, Productos idproducto, Ventas idventa, int cantidad, Double total, Date fecha) {
        this.iddetalle = iddetalle;
        this.idproducto = idproducto;
        this.idventa = idventa;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(Integer iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Ventas getIdventa() {
        return idventa;
    }

    public void setIdventa(Ventas idventa) {
        this.idventa = idventa;
    }

    public Productos getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Productos idproducto) {
        this.idproducto = idproducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetalle != null ? iddetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalleventas)) {
            return false;
        }
        Detalleventas other = (Detalleventas) object;
        if ((this.iddetalle == null && other.iddetalle != null) || (this.iddetalle != null && !this.iddetalle.equals(other.iddetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Detalleventas[ iddetalle=" + iddetalle + " ]";
    }
    
}
