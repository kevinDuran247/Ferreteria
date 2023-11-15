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
import modelos.Ventas;
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
import modelos.Vendedor;

/**
 *
 * @author Kevin Duran
 */
public class VendedorJpaController implements Serializable {
    
     public VendedorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public VendedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendedor vendedor) throws PreexistingEntityException, Exception {
        if (vendedor.getVentasCollection() == null) {
            vendedor.setVentasCollection(new ArrayList<Ventas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vendedor);          
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVendedor(vendedor.getIdvendedor()) != null) {
                throw new PreexistingEntityException("Vendedor " + vendedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendedor vendedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();           
            vendedor = em.merge(vendedor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendedor.getIdvendedor();
                if (findVendedor(id) == null) {
                    throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.");
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
        Vendedor vendedor;
        try {
            vendedor = em.find(Vendedor.class, id);
        } catch (EntityNotFoundException enfe) {
            throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.", enfe);
        }

        // Configura la relación con ventas para que el campo idvendedor sea nulo
        vendedor.setVentasCollection(null);

        em.remove(vendedor);
        em.getTransaction().commit();
    } finally {
        if (em != null) {
            em.close();
        }
    }
}


    public List<Vendedor> findVendedorEntities() {
        return findVendedorEntities(true, -1, -1);
    }

    public List<Vendedor> findVendedorEntities(int maxResults, int firstResult) {
        return findVendedorEntities(false, maxResults, firstResult);
    }

    private List<Vendedor> findVendedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendedor.class));
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

    public Vendedor findVendedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendedor.class, id);
        } finally {
            em.close();
        }
    }
    
    public Vendedor findAdminByVendedor(String usuario) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Vendedor> query = em.createNamedQuery("Vendedor.findByUsuario", Vendedor.class);
            query.setParameter("usuario", usuario);

            List<Vendedor> resultados = query.getResultList();

            if (!resultados.isEmpty()) {
                // Si hay resultados, devuelve el primer elemento
                return resultados.get(0);
            } else {
                // Si no hay resultados, devuelve null o realiza alguna otra acción según tu lógica
                return null;
            }
        } finally {
            em.close();
        }
    }

    public int getVendedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendedor> rt = cq.from(Vendedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
