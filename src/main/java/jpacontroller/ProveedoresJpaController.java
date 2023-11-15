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
import modelos.Productos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import jpacontroller.exceptions.NonexistentEntityException;
import modelos.Proveedores;

/**
 *
 * @author Kevin Duran
 */
public class ProveedoresJpaController implements Serializable {

    public ProveedoresJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public ProveedoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedores proveedores) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(proveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedores proveedores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedores persistentProveedores = em.find(Proveedores.class, proveedores.getIdproveedor());
            Collection<Productos> productosCollectionOld = persistentProveedores.getProductosCollection();
            Collection<Productos> productosCollectionNew = proveedores.getProductosCollection();
            Collection<Productos> attachedProductosCollectionNew = new ArrayList<Productos>();
            for (Productos productosCollectionNewProductosToAttach : productosCollectionNew) {
                productosCollectionNewProductosToAttach = em.getReference(productosCollectionNewProductosToAttach.getClass(), productosCollectionNewProductosToAttach.getIdproducto());
                attachedProductosCollectionNew.add(productosCollectionNewProductosToAttach);
            }
            productosCollectionNew = attachedProductosCollectionNew;
            proveedores.setProductosCollection(productosCollectionNew);
            proveedores = em.merge(proveedores);
            for (Productos productosCollectionOldProductos : productosCollectionOld) {
                if (!productosCollectionNew.contains(productosCollectionOldProductos)) {
                    productosCollectionOldProductos.setProveedorId(null);
                    productosCollectionOldProductos = em.merge(productosCollectionOldProductos);
                }
            }
            for (Productos productosCollectionNewProductos : productosCollectionNew) {
                if (!productosCollectionOld.contains(productosCollectionNewProductos)) {
                    Proveedores oldProveedorIdOfProductosCollectionNewProductos = productosCollectionNewProductos.getProveedorId();
                    productosCollectionNewProductos.setProveedorId(proveedores);
                    productosCollectionNewProductos = em.merge(productosCollectionNewProductos);
                    if (oldProveedorIdOfProductosCollectionNewProductos != null && !oldProveedorIdOfProductosCollectionNewProductos.equals(proveedores)) {
                        oldProveedorIdOfProductosCollectionNewProductos.getProductosCollection().remove(productosCollectionNewProductos);
                        oldProveedorIdOfProductosCollectionNewProductos = em.merge(oldProveedorIdOfProductosCollectionNewProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedores.getIdproveedor();
                if (findProveedores(id) == null) {
                    throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.");
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
            Proveedores proveedores;
            try {
                proveedores = em.getReference(Proveedores.class, id);
                proveedores.getIdproveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.", enfe);
            }
            Collection<Productos> productosCollection = proveedores.getProductosCollection();
            for (Productos productosCollectionProductos : productosCollection) {
                productosCollectionProductos.setProveedorId(null);
                productosCollectionProductos = em.merge(productosCollectionProductos);
            }
            em.remove(proveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedores> findProveedoresEntities() {
        return findProveedoresEntities(true, -1, -1);
    }

    public List<Proveedores> findProveedoresEntities(int maxResults, int firstResult) {
        return findProveedoresEntities(false, maxResults, firstResult);
    }

    private List<Proveedores> findProveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedores.class));
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

    public Proveedores findProveedores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedores.class, id);
        } finally {
            em.close();
        }
    }

    public Proveedores findProveedoresByNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Proveedores> query = em.createNamedQuery("Proveedores.findByNombre", Proveedores.class);
            query.setParameter("nombre", nombre);

            List<Proveedores> resultados = query.getResultList();

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

    public int getProveedoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedores> rt = cq.from(Proveedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
