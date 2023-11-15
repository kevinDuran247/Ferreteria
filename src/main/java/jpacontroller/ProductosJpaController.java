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
import modelos.Proveedores;
import modelos.Categorias;
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
import modelos.Productos;

/**
 *
 * @author Kevin Duran
 */
public class ProductosJpaController implements Serializable {
    
     public ProductosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ferreteriaPU");
    }

    public ProductosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productos productos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(productos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productos productos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            productos = em.merge(productos);            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productos.getIdproducto();
                if (findProductos(id) == null) {
                    throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
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
        Productos productos = em.find(Productos.class, id);
        
        if (productos == null) {
            throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
        }

        // Configura la relación con detalleventas para que el campo idproducto sea nulo
        productos.setDetalleventasCollection(null);

        Proveedores proveedorId = productos.getProveedorId();
        if (proveedorId != null) {
            proveedorId.getProductosCollection().remove(productos);
        }

        Categorias categoriaId = productos.getCategoriaId();
        if (categoriaId != null) {
            categoriaId.getProductosCollection().remove(productos);
        }

        em.remove(productos);
        em.getTransaction().commit();
    } finally {
        if (em != null) {
            em.close();
        }
    }
}


    public List<Productos> findProductosEntities() {
        return findProductosEntities(true, -1, -1);
    }

    public List<Productos> findProductosEntities(int maxResults, int firstResult) {
        return findProductosEntities(false, maxResults, firstResult);
    }

    private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productos.class));
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
    
    public Productos findProductosByNombreAndCategoria(String nombre, int idCategoria) {
         EntityManager em = getEntityManager();
        try {
            TypedQuery<Productos> query = em.createNamedQuery("Productos.findByNombreAndCategoria", Productos.class);
            query.setParameter("nombre", nombre);
            query.setParameter("idcategoria", idCategoria);

            List<Productos> resultados = query.getResultList();

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

    public Productos findProductos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productos> rt = cq.from(Productos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
