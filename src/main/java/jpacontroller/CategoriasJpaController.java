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
import modelos.Categorias;

/**
 *
 * @author Kevin Duran
 */
public class CategoriasJpaController implements Serializable {
    
     public CategoriasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public CategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categorias categorias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(categorias);         
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categorias categorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias persistentCategorias = em.find(Categorias.class, categorias.getIdcategoria());
            Collection<Productos> productosCollectionOld = persistentCategorias.getProductosCollection();
            Collection<Productos> productosCollectionNew = categorias.getProductosCollection();
            Collection<Productos> attachedProductosCollectionNew = new ArrayList<Productos>();
            for (Productos productosCollectionNewProductosToAttach : productosCollectionNew) {
                productosCollectionNewProductosToAttach = em.getReference(productosCollectionNewProductosToAttach.getClass(), productosCollectionNewProductosToAttach.getIdproducto());
                attachedProductosCollectionNew.add(productosCollectionNewProductosToAttach);
            }
            productosCollectionNew = attachedProductosCollectionNew;
            categorias.setProductosCollection(productosCollectionNew);
            categorias = em.merge(categorias);
            for (Productos productosCollectionOldProductos : productosCollectionOld) {
                if (!productosCollectionNew.contains(productosCollectionOldProductos)) {
                    productosCollectionOldProductos.setCategoriaId(null);
                    productosCollectionOldProductos = em.merge(productosCollectionOldProductos);
                }
            }
            for (Productos productosCollectionNewProductos : productosCollectionNew) {
                if (!productosCollectionOld.contains(productosCollectionNewProductos)) {
                    Categorias oldCategoriaIdOfProductosCollectionNewProductos = productosCollectionNewProductos.getCategoriaId();
                    productosCollectionNewProductos.setCategoriaId(categorias);
                    productosCollectionNewProductos = em.merge(productosCollectionNewProductos);
                    if (oldCategoriaIdOfProductosCollectionNewProductos != null && !oldCategoriaIdOfProductosCollectionNewProductos.equals(categorias)) {
                        oldCategoriaIdOfProductosCollectionNewProductos.getProductosCollection().remove(productosCollectionNewProductos);
                        oldCategoriaIdOfProductosCollectionNewProductos = em.merge(oldCategoriaIdOfProductosCollectionNewProductos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorias.getIdcategoria();
                if (findCategorias(id) == null) {
                    throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.");
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
            Categorias categorias;
            try {
                categorias = em.getReference(Categorias.class, id);
                categorias.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.", enfe);
            }
            Collection<Productos> productosCollection = categorias.getProductosCollection();
            for (Productos productosCollectionProductos : productosCollection) {
                productosCollectionProductos.setCategoriaId(null);
                productosCollectionProductos = em.merge(productosCollectionProductos);
            }
            em.remove(categorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categorias> findCategoriasEntities() {
        return findCategoriasEntities(true, -1, -1);
    }

    public List<Categorias> findCategoriasEntities(int maxResults, int firstResult) {
        return findCategoriasEntities(false, maxResults, firstResult);
    }

    private List<Categorias> findCategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorias.class));
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

    public Categorias findCategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorias.class, id);
        } finally {
            em.close();
        }
    }

    public Categorias findCategoriasByNombre(String nombre) {
    EntityManager em = getEntityManager();
    try {
        TypedQuery<Categorias> query = em.createNamedQuery("Categorias.findByNombre", Categorias.class);
        query.setParameter("nombre", nombre);
        
        List<Categorias> resultados = query.getResultList();
        
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
    
    public int getCategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorias> rt = cq.from(Categorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
