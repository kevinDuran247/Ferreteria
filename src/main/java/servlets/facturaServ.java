/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpacontroller.DetalleventasJpaController;
import jpacontroller.ProductosJpaController;
import jpacontroller.VendedorJpaController;
import jpacontroller.VentasJpaController;
import modelos.Detalleventas;
import modelos.Mail;
import modelos.Productos;
import modelos.Vendedor;
import modelos.Ventas;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "facturaServ", urlPatterns = {"/facturaServ"})
public class facturaServ extends HttpServlet {

    VendedorJpaController ven = new VendedorJpaController();
    VentasJpaController ventas = new VentasJpaController();
    DetalleventasJpaController detallep = new DetalleventasJpaController();

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
            out.println("<title>Servlet facturaServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet facturaServ at " + request.getContextPath() + "</h1>");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("facturaVendedor.jsp");
        dispatcher.forward(request, response);
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
        ProductosJpaController p = new ProductosJpaController();
        // Obtén los parámetros del formulario
        int idVacio = 0;
        int idVendedor = Integer.parseInt(request.getParameter("idvendedor"));
        Vendedor v = ven.findVendedor(idVendedor);
        UUID uniqueKey = UUID.randomUUID();
        String codigo = uniqueKey.toString();
        double total = Double.parseDouble(request.getParameter("total"));
        Date fechaActual = Calendar.getInstance().getTime();

        //INSERTAR VENTA
        Ventas venta = new Ventas(idVacio, v, total, fechaActual, codigo);
        try {
            ventas.create(venta);
        } catch (Exception ex) {
            Logger.getLogger(facturaServ.class.getName()).log(Level.SEVERE, null, ex);
        }

        //INSERTAR DEATALLEVENTAS
        int ventaInt = detallep.obtenerIdPorCodigo(codigo);
        Ventas detalle = ventas.findVentas(ventaInt);

        String[] idproductos = request.getParameterValues("ids");
        String[] cantidades = request.getParameterValues("cantidades");
        String[] subtotales = request.getParameterValues("subtotales");

        for (int i = 0; i < idproductos.length; i++) {
            int iddetalle = 0; // Aquí deberías generar un nuevo ID para cada detalle
            int cantidad = Integer.valueOf(cantidades[i]);
            double subtotal = Double.valueOf(subtotales[i]);
            Productos producto = p.findProductos(Integer.valueOf(idproductos[i]));

            Detalleventas detalles = new Detalleventas(iddetalle, producto, detalle, cantidad, subtotal, fechaActual);
            detallep.create(detalles);
        }

        //ACTUALIZAR EXISTENCIAS DE LOS PRODUCTOS
        String[] existencias = request.getParameterValues("existencias");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
        EntityManager em = emf.createEntityManager();

        for (int i = 0; i < idproductos.length; i++) {
            int idproducto = Integer.parseInt(idproductos[i]);
            int cantidad = Integer.parseInt(cantidades[i]);
            int existencia = Integer.parseInt(existencias[i]);

            int nuevaExistencia = existencia - cantidad;

            // Aquí puedes actualizar la existencia en la base de datos
            Productos producto = em.find(Productos.class, idproducto);
            if (producto != null) {
                em.getTransaction().begin();
                producto.setStock(nuevaExistencia);
                em.getTransaction().commit();
            }
        }

        //ENVIAR MAIL
        try {

            List<Object[]> resultados = new ArrayList<>();

            // Supongamos que "idproductos" es una lista de enteros
            List<Integer> idproductosList = new ArrayList<>();
            for (String id : idproductos) {
                idproductosList.add(Integer.parseInt(id));
            }

            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT p.idproducto, p.nombre, p.stock, p.sminimo FROM Productos p WHERE p.idproducto IN :idproductosList AND p.stock <= p.sminimo",
                    Object[].class
            );
            query.setParameter("idproductosList", idproductosList);

            resultados.addAll(query.getResultList());

            if (!resultados.isEmpty()) {
                Properties mailProperties = new Properties();
                mailProperties.put("mail.smtp.host", "smtp-mail.outlook.com");
                mailProperties.put("mail.smtp.port", "587");
                mailProperties.put("mail.smtp.user", "correo@outlook.com");
                mailProperties.put("mail.smtp.password", "Contraseña");
                mailProperties.put("mail.smtp.starttls.enable", "true");
                mailProperties.put("mail.smtp.auth", "true");
                Mail m = new Mail(mailProperties);

                for (Object[] result : resultados) {
                    int idproducto = (int) result[0];
                    String nombre = (String) result[1];
                    int stock = (int) result[2];
                    int sminimo = (int) result[3];

                    String subject = "NOTIFICACIÓN DE STOCK";
                    String message = "El producto con ID " + idproducto + " y nombre " + nombre + " ha bajado su stock a " + stock+" cuando su stock minimo es "+sminimo;

                    m.enviarEmail(subject, message, "correo@gmail.com");
                }
            }
        } catch (MessagingException ex) {
            Logger.getLogger(facturaServ.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
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
