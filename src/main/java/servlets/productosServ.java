/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpacontroller.CategoriasJpaController;
import jpacontroller.ProductosJpaController;
import jpacontroller.ProveedoresJpaController;
import jpacontroller.exceptions.IllegalOrphanException;
import jpacontroller.exceptions.NonexistentEntityException;
import modelos.Categorias;
import modelos.Mail;
import modelos.Productos;
import modelos.Proveedores;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "productosServ", urlPatterns = {"/productosServ"})
public class productosServ extends HttpServlet {

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
            out.println("<title>Servlet productosServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet productosServ at " + request.getContextPath() + "</h1>");
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
        ProductosJpaController productoObj = new ProductosJpaController();
        String nProducto = request.getParameter("nProducto");

        //Eliminar producto
        if (request.getParameter("idp") != null) {
            int id = Integer.parseInt(request.getParameter("idp"));
            try {
                productoObj.destroy(id);
                request.setAttribute("mensajeProEliminado", "Producto eliminado");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(productosServ.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (request.getParameter("idpm") != null) {
            int idpm = Integer.parseInt(request.getParameter("idpm"));
            Productos productos = productoObj.findProductos(idpm);

            // Obtener el nombre del proveedor y la categoría
            int idProveedor = productos.getProveedorId() != null ? productos.getProveedorId().getIdproveedor() : -1;
            int idCategoria = productos.getCategoriaId() != null ? productos.getCategoriaId().getIdcategoria() : -1;

            Proveedores proveedor = idProveedor != -1 ? pro.findProveedores(idProveedor) : null;
            Categorias categoria = idCategoria != -1 ? cat.findCategorias(idCategoria) : null;

            productos.setNombreProveedor(proveedor != null ? proveedor.getNombre() : "Proveedor eliminado");
            productos.setNombreCategoria(categoria != null ? categoria.getNombre() : "Categoría eliminado");

            List<Proveedores> listaProveedores = pro.findProveedoresEntities();
            List<Categorias> listaCategorias = cat.findCategoriasEntities();

            request.setAttribute("productos", productos);
            request.setAttribute("proveedores", listaProveedores);
            request.setAttribute("categorias", listaCategorias);

            RequestDispatcher dispatcher2 = request.getRequestDispatcher("modificarProductoAdmin.jsp");
            dispatcher2.forward(request, response);
        }

        if (nProducto == null || nProducto.isEmpty()) {
            //Consultar productos y paginarlos
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
            EntityManager em = emf.createEntityManager();
            int pagina = 1;
            int registrosPorPagina = 4;

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

            RequestDispatcher dispatcher = request.getRequestDispatcher("productosAdmin.jsp");
            dispatcher.forward(request, response);
        } else {
            //Consultar productos y paginarlos
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
            EntityManager em = emf.createEntityManager();
            int pagina = 1;
            int registrosPorPagina = 52;

            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }

            // Modificar la consulta para buscar productos por nombre
            String queryString = "SELECT p FROM Productos p WHERE p.nombre LIKE :nombre";
            List<Productos> registros = em.createQuery(queryString, Productos.class)
                    .setParameter("nombre", "%" + nProducto + "%") // Utilizar LIKE para buscar coincidencias parciales
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

            RequestDispatcher dispatcher = request.getRequestDispatcher("productosAdmin.jsp");
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

        try {

            ProveedoresJpaController pro = new ProveedoresJpaController();
            CategoriasJpaController cat = new CategoriasJpaController();
            ProductosJpaController productoObj = new ProductosJpaController();

            int idM = Integer.parseInt(request.getParameter("idM"));
            String nombreProductoM = request.getParameter("nombreM");
            String descripcionM = request.getParameter("descripcionM");
            String urlimagenM = request.getParameter("urlimagenM");
            int stockM = Integer.parseInt(request.getParameter("stockM"));
            int sminimoM = Integer.parseInt(request.getParameter("sminimoM"));
            double precioM = Double.parseDouble(request.getParameter("precioM"));
            int proveedorIdM = Integer.parseInt(request.getParameter("nombreProveedorM"));
            int categoriaIdM = Integer.parseInt(request.getParameter("nombreCategoriaM"));

            // Obtener el objeto Proveedores y Categorias basados en los IDs
            Proveedores proveedor = pro.findProveedores(proveedorIdM);
            Categorias categoria = cat.findCategorias(categoriaIdM);

                if (sminimoM >= stockM) {
                    Productos productoM = new Productos(idM, nombreProductoM, descripcionM, urlimagenM, stockM, sminimoM, precioM, proveedor, categoria);
                    productoObj.edit(productoM);

                    Properties mailProperties = new Properties();
                    mailProperties.put("mail.smtp.host", "smtp-mail.outlook.com");
                    mailProperties.put("mail.smtp.port", "587");
                    mailProperties.put("mail.smtp.user", "correo@outlook.com");
                    mailProperties.put("mail.smtp.password", "Contraseña");
                    mailProperties.put("mail.smtp.starttls.enable", "true");
                    mailProperties.put("mail.smtp.auth", "true");
                    Mail m = new Mail(mailProperties);
                    String subject = "NOTIFICACIÓN DE BAJO STOCK MODIFICADO";
                    String message = "El producto de nombre " + nombreProductoM + " fue ingresado con un stock de " + stockM + " cuando su stock minimo es de" + sminimoM;

                    m.enviarEmail(subject, message, "correo@gmail.com");

                } else {

                    Productos productoM = new Productos(idM, nombreProductoM, descripcionM, urlimagenM, stockM, sminimoM, precioM, proveedor, categoria);
                    productoObj.edit(productoM);
                    request.setAttribute("mensajeProductoMEditado", "El producto " + nombreProductoM + " se edito");
                }
            

        } catch (Exception ex) {
            Logger.getLogger(productosServ.class.getName()).log(Level.SEVERE, null, ex);
        }

        doGet(request, response);
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
