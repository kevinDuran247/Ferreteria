/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jpacontroller.CategoriasJpaController;
import jpacontroller.ProductosJpaController;
import jpacontroller.ProveedoresJpaController;
import modelos.Carrito;
import modelos.Categorias;
import modelos.Productos;
import modelos.Proveedores;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "vendedorServ", urlPatterns = {"/vendedorServ"})
public class vendedorServ extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet vendedorServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet vendedorServ at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoriasJpaController cat = new CategoriasJpaController();
        ProveedoresJpaController pro = new ProveedoresJpaController();

        String accion = request.getParameter("accion");
        if (accion != null) {
            int indice = Integer.parseInt(request.getParameter("indice"));

            HttpSession ses = request.getSession();
            ArrayList<Carrito> lista = (ArrayList<Carrito>) ses.getAttribute("lista");

            if (accion.equals("disminuir")) {
                // Disminuir la cantidad del carrito, que no sea menor que 1
                Carrito carrito = lista.get(indice);
                if (carrito.getCantidad() > 1) {
                    carrito.setCantidad(carrito.getCantidad() - 1);
                    carrito.setSubtotal(carrito.getPrecio() * carrito.getCantidad());
                }
            } else if (accion.equals("eliminar")) {
                if (lista != null && indice >= 0 && indice < lista.size()) {
                    Carrito carritoEliminado = lista.remove(indice);
                }
            } else if (accion.equals("vaciar")) {
                // Vaciar el carrito
                lista.clear();
            }

            double nuevoTotal = 0.0;
            for (Carrito carrito : lista) {
                nuevoTotal += carrito.getSubtotal();
            }

            // Establece el nuevo total en el ámbito de sesión
            ses.setAttribute("total", nuevoTotal);
        }

        String idCatParam = request.getParameter("idCategoria");

        if (idCatParam != null) {
            if ("vacio".equals(idCatParam)) {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
                EntityManager em = emf.createEntityManager();
                int pagina = 1;
                int registrosPorPagina = 5;

                if (request.getParameter("pagina") != null) {
                    pagina = Integer.parseInt(request.getParameter("pagina"));
                }

                List<Productos> registros = em.createQuery("SELECT p FROM Productos p", Productos.class)
                        .setFirstResult((pagina - 1) * registrosPorPagina)
                        .setMaxResults(registrosPorPagina)
                        .getResultList();
                // Recorre la lista de productos y obtén el nombre del proveedor para cada producto
                for (Productos producto : registros) {
                    int idProveedor = producto.getProveedorId() != null ? producto.getProveedorId().getIdproveedor() : -1;
                    int idCategoria = producto.getCategoriaId() != null ? producto.getCategoriaId().getIdcategoria() : -1;

                    Proveedores proveedor = idProveedor != -1 ? pro.findProveedores(idProveedor) : null;
                    Categorias categoria = idCategoria != -1 ? cat.findCategorias(idCategoria) : null;

                    producto.setNombreProveedor(proveedor != null ? proveedor.getNombre() : "Proveedor eliminado");
                    producto.setNombreCategoria(categoria != null ? categoria.getNombre() : "Categoría eliminado");
                }

                request.setAttribute("registros", registros);

                // Calcula el número total de páginas
                Long totalRegistros = (Long) em.createQuery("SELECT COUNT(p) FROM Productos p")
                        .getSingleResult();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
                request.setAttribute("totalPaginas", totalPaginas);

                request.setAttribute("paginaActual", pagina);

                em.close();
                emf.close();

                List<Categorias> categorias = cat.findCategoriasEntities();
                request.setAttribute("categorias", categorias);
                RequestDispatcher dispatcher = request.getRequestDispatcher("inicioVendedor.jsp");
                dispatcher.forward(request, response);
            } else {
                int idCat = Integer.parseInt(idCatParam);
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
                EntityManager em = emf.createEntityManager();
                int pagina = 1;
                int registrosPorPagina = 20;

                if (request.getParameter("pagina") != null) {
                    pagina = Integer.parseInt(request.getParameter("pagina"));
                }

                TypedQuery<Productos> query = em.createQuery("SELECT p FROM Productos p WHERE p.categoriaId.idcategoria = :idCat", Productos.class)
                        .setParameter("idCat", idCat)
                        .setFirstResult((pagina - 1) * registrosPorPagina)
                        .setMaxResults(registrosPorPagina);

                List<Productos> registros = query.getResultList();

                // Resto del código para obtener nombres de proveedores y categorías
                // Recorre la lista de productos y obtén el nombre del proveedor para cada producto
                for (Productos producto : registros) {
                    int idProveedor = producto.getProveedorId() != null ? producto.getProveedorId().getIdproveedor() : -1;
                    int idCategoria = producto.getCategoriaId() != null ? producto.getCategoriaId().getIdcategoria() : -1;

                    Proveedores proveedor = idProveedor != -1 ? pro.findProveedores(idProveedor) : null;
                    Categorias categoria = idCategoria != -1 ? cat.findCategorias(idCategoria) : null;

                    producto.setNombreProveedor(proveedor != null ? proveedor.getNombre() : "Proveedor eliminado");
                    producto.setNombreCategoria(categoria != null ? categoria.getNombre() : "Categoría eliminado");
                }
                request.setAttribute("registros", registros);

                // Calcula el número total de páginas
                Long totalRegistros = (Long) em.createQuery("SELECT COUNT(p) FROM Productos p WHERE p.categoriaId.idcategoria = :idCat")
                        .setParameter("idCat", idCat)
                        .getSingleResult();

                int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
                request.setAttribute("totalPaginas", totalPaginas);

                request.setAttribute("paginaActual", pagina);

                em.close();
                emf.close();

                List<Categorias> categorías = cat.findCategoriasEntities();
                request.setAttribute("categorias", categorías);
                RequestDispatcher dispatcher = request.getRequestDispatcher("inicioVendedor.jsp");
                dispatcher.forward(request, response);
            };

        } else {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
            EntityManager em = emf.createEntityManager();
            int pagina = 1;
            int registrosPorPagina = 5;

            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }

            List<Productos> registros = em.createQuery("SELECT p FROM Productos p", Productos.class)
                    .setFirstResult((pagina - 1) * registrosPorPagina)
                    .setMaxResults(registrosPorPagina)
                    .getResultList();
            // Recorre la lista de productos y obtén el nombre del proveedor para cada producto
            for (Productos producto : registros) {
                int idProveedor = producto.getProveedorId() != null ? producto.getProveedorId().getIdproveedor() : -1;
                int idCategoria = producto.getCategoriaId() != null ? producto.getCategoriaId().getIdcategoria() : -1;

                Proveedores proveedor = idProveedor != -1 ? pro.findProveedores(idProveedor) : null;
                Categorias categoria = idCategoria != -1 ? cat.findCategorias(idCategoria) : null;

                producto.setNombreProveedor(proveedor != null ? proveedor.getNombre() : "Proveedor eliminado");
                producto.setNombreCategoria(categoria != null ? categoria.getNombre() : "Categoría eliminado");
            }

            request.setAttribute("registros", registros);

            // Calcula el número total de páginas
            Long totalRegistros = (Long) em.createQuery("SELECT COUNT(p) FROM Productos p")
                    .getSingleResult();
            int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
            request.setAttribute("totalPaginas", totalPaginas);

            request.setAttribute("paginaActual", pagina);

            em.close();
            emf.close();

            List<Categorias> categorias = cat.findCategoriasEntities();
            request.setAttribute("categorias", categorias);
            RequestDispatcher dispatcher = request.getRequestDispatcher("inicioVendedor.jsp");
            dispatcher.forward(request, response);

        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double total = 0.0;
        int idproducto = Integer.parseInt(request.getParameter("idproducto"));
        String nombreProducto = request.getParameter("nombreProducto");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int existencias = Integer.parseInt(request.getParameter("existencias"));

        HttpSession ses = request.getSession();

        ArrayList<Carrito> lista = (ArrayList<Carrito>) ses.getAttribute("lista");

        if (lista == null) {
            lista = new ArrayList<>();
            ses.setAttribute("lista", lista);
        }

        // Verificar si el producto ya existe en la lista antes de agregarlo
        Carrito productoExistente = null;
        for (Carrito carrito : lista) {
            if (carrito.getNombreProducto().equals(nombreProducto)) {
                productoExistente = carrito;
                break;
            }
        }

        if (productoExistente != null) {
            // Actualizar la cantidad y subtotal del producto existente
            int nuevaCantidad = productoExistente.getCantidad() + cantidad;

            if (nuevaCantidad <= existencias) {
                productoExistente.setCantidad(nuevaCantidad);
                double nuevoSubtotal = productoExistente.getPrecio() * nuevaCantidad;
                productoExistente.setSubtotal(nuevoSubtotal);
            } else {

            }

        } else {
            // Calcular el subtotal y agregar una instancia de Producto a la lista
            double subtotal = precio * cantidad;
            Carrito nuevoProducto = new Carrito(idproducto, nombreProducto, precio, cantidad, existencias, subtotal);
            lista.add(nuevoProducto);
        }

        // Calcular el total sumando todos los subtotales
        for (Carrito carrito : lista) {
            total += carrito.getSubtotal();
        }

        ses.setAttribute("total", total);
        response.sendRedirect("vendedorServ");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
