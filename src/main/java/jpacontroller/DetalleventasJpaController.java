/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacontroller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpacontroller.exceptions.NonexistentEntityException;
import modelos.Detalleventas;
import modelos.Ventas;
import modelos.Productos;

/**
 *
 * @author Kevin Duran
 */
public class DetalleventasJpaController implements Serializable {
    
     public DetalleventasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public DetalleventasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Integer obtenerIdPorCodigo(String codigo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT v.idventa FROM Ventas v WHERE v.codigo = :codigo");
            query.setParameter("codigo", codigo);

            List<Integer> results = query.getResultList();
            if (results.isEmpty()) {
                // No se encontró ningún pedido con el estado especificado
                return null;
            } else {
                // Devuelve el ID del primer pedido que cumple con los criterios
                return results.get(0);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(Detalleventas detalleventas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas idventa = detalleventas.getIdventa();
            if (idventa != null) {
                idventa = em.getReference(idventa.getClass(), idventa.getIdventa());
                detalleventas.setIdventa(idventa);
            }
            Productos idproducto = detalleventas.getIdproducto();
            if (idproducto != null) {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getIdproducto());
                detalleventas.setIdproducto(idproducto);
            }
            em.persist(detalleventas);
            if (idventa != null) {
                idventa.getDetalleventasCollection().add(detalleventas);
                idventa = em.merge(idventa);
            }
            if (idproducto != null) {
                idproducto.getDetalleventasCollection().add(detalleventas);
                idproducto = em.merge(idproducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalleventas detalleventas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleventas persistentDetalleventas = em.find(Detalleventas.class, detalleventas.getIddetalle());
            Ventas idventaOld = persistentDetalleventas.getIdventa();
            Ventas idventaNew = detalleventas.getIdventa();
            Productos idproductoOld = persistentDetalleventas.getIdproducto();
            Productos idproductoNew = detalleventas.getIdproducto();
            if (idventaNew != null) {
                idventaNew = em.getReference(idventaNew.getClass(), idventaNew.getIdventa());
                detalleventas.setIdventa(idventaNew);
            }
            if (idproductoNew != null) {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getIdproducto());
                detalleventas.setIdproducto(idproductoNew);
            }
            detalleventas = em.merge(detalleventas);
            if (idventaOld != null && !idventaOld.equals(idventaNew)) {
                idventaOld.getDetalleventasCollection().remove(detalleventas);
                idventaOld = em.merge(idventaOld);
            }
            if (idventaNew != null && !idventaNew.equals(idventaOld)) {
                idventaNew.getDetalleventasCollection().add(detalleventas);
                idventaNew = em.merge(idventaNew);
            }
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
                idproductoOld.getDetalleventasCollection().remove(detalleventas);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
                idproductoNew.getDetalleventasCollection().add(detalleventas);
                idproductoNew = em.merge(idproductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleventas.getIddetalle();
                if (findDetalleventas(id) == null) {
                    throw new NonexistentEntityException("The detalleventas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleventas detalleventas;
            try {
                detalleventas = em.getReference(Detalleventas.class, id);
                detalleventas.getIddetalle();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleventas with id " + id + " no longer exists.", enfe);
            }
            Ventas idventa = detalleventas.getIdventa();
            if (idventa != null) {
                idventa.getDetalleventasCollection().remove(detalleventas);
                idventa = em.merge(idventa);
            }
            Productos idproducto = detalleventas.getIdproducto();
            if (idproducto != null) {
                idproducto.getDetalleventasCollection().remove(detalleventas);
                idproducto = em.merge(idproducto);
            }
            em.remove(detalleventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalleventas> findDetalleventasEntities() {
        return findDetalleventasEntities(true, -1, -1);
    }

    public List<Detalleventas> findDetalleventasEntities(int maxResults, int firstResult) {
        return findDetalleventasEntities(false, maxResults, firstResult);
    }

    private List<Detalleventas> findDetalleventasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalleventas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Detalleventas findDetalleventas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalleventas.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Detalleventas> findVentasDetalles(Ventas ventas) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Detalleventas> query = em.createNamedQuery("Detalleventas.findByVentas", Detalleventas.class);
            query.setParameter("ventas", ventas);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int getDetalleventasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalleventas> rt = cq.from(Detalleventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
