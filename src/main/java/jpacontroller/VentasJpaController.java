/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelos.Vendedor;
import modelos.Detalleventas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import jpacontroller.exceptions.IllegalOrphanException;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.PreexistingEntityException;
import modelos.Ventas;

/**
 *
 * @author Kevin Duran
 */
public class VentasJpaController implements Serializable {

    public VentasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public VentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) throws PreexistingEntityException, Exception {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            em.persist(ventas);

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentas(ventas.getIdventa()) != null) {
                throw new PreexistingEntityException("Ventas " + ventas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getIdventa());
            Vendedor idvendedorOld = persistentVentas.getIdvendedor();
            Vendedor idvendedorNew = ventas.getIdvendedor();
            Collection<Detalleventas> detalleventasCollectionOld = persistentVentas.getDetalleventasCollection();
            Collection<Detalleventas> detalleventasCollectionNew = ventas.getDetalleventasCollection();
            List<String> illegalOrphanMessages = null;
            for (Detalleventas detalleventasCollectionOldDetalleventas : detalleventasCollectionOld) {
                if (!detalleventasCollectionNew.contains(detalleventasCollectionOldDetalleventas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detalleventas " + detalleventasCollectionOldDetalleventas + " since its idventa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idvendedorNew != null) {
                idvendedorNew = em.getReference(idvendedorNew.getClass(), idvendedorNew.getIdvendedor());
                ventas.setIdvendedor(idvendedorNew);
            }
            Collection<Detalleventas> attachedDetalleventasCollectionNew = new ArrayList<Detalleventas>();
            for (Detalleventas detalleventasCollectionNewDetalleventasToAttach : detalleventasCollectionNew) {
                detalleventasCollectionNewDetalleventasToAttach = em.getReference(detalleventasCollectionNewDetalleventasToAttach.getClass(), detalleventasCollectionNewDetalleventasToAttach.getIddetalle());
                attachedDetalleventasCollectionNew.add(detalleventasCollectionNewDetalleventasToAttach);
            }
            detalleventasCollectionNew = attachedDetalleventasCollectionNew;
            ventas.setDetalleventasCollection(detalleventasCollectionNew);
            ventas = em.merge(ventas);
            if (idvendedorOld != null && !idvendedorOld.equals(idvendedorNew)) {
                idvendedorOld.getVentasCollection().remove(ventas);
                idvendedorOld = em.merge(idvendedorOld);
            }
            if (idvendedorNew != null && !idvendedorNew.equals(idvendedorOld)) {
                idvendedorNew.getVentasCollection().add(ventas);
                idvendedorNew = em.merge(idvendedorNew);
            }
            for (Detalleventas detalleventasCollectionNewDetalleventas : detalleventasCollectionNew) {
                if (!detalleventasCollectionOld.contains(detalleventasCollectionNewDetalleventas)) {
                    Ventas oldIdventaOfDetalleventasCollectionNewDetalleventas = detalleventasCollectionNewDetalleventas.getIdventa();
                    detalleventasCollectionNewDetalleventas.setIdventa(ventas);
                    detalleventasCollectionNewDetalleventas = em.merge(detalleventasCollectionNewDetalleventas);
                    if (oldIdventaOfDetalleventasCollectionNewDetalleventas != null && !oldIdventaOfDetalleventasCollectionNewDetalleventas.equals(ventas)) {
                        oldIdventaOfDetalleventasCollectionNewDetalleventas.getDetalleventasCollection().remove(detalleventasCollectionNewDetalleventas);
                        oldIdventaOfDetalleventasCollectionNewDetalleventas = em.merge(oldIdventaOfDetalleventasCollectionNewDetalleventas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventas.getIdventa();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getIdventa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detalleventas> detalleventasCollectionOrphanCheck = ventas.getDetalleventasCollection();
            for (Detalleventas detalleventasCollectionOrphanCheckDetalleventas : detalleventasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ventas (" + ventas + ") cannot be destroyed since the Detalleventas " + detalleventasCollectionOrphanCheckDetalleventas + " in its detalleventasCollection field has a non-nullable idventa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Vendedor idvendedor = ventas.getIdvendedor();
            if (idvendedor != null) {
                idvendedor.getVentasCollection().remove(ventas);
                idvendedor = em.merge(idvendedor);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public List<Ventas> findVentasVendedor(Vendedor vendedor) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Ventas> query = em.createNamedQuery("Ventas.findByVendedor", Ventas.class);
            query.setParameter("vendedor", vendedor);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
