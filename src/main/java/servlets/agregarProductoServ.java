/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpacontroller.CategoriasJpaController;
import jpacontroller.ProductosJpaController;
import jpacontroller.ProveedoresJpaController;
import modelos.Categorias;
import modelos.Mail;
import modelos.Productos;
import modelos.Proveedores;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "agregarProductoServ", urlPatterns = {"/agregarProductoServ"})
public class agregarProductoServ extends HttpServlet {

    ProveedoresJpaController pro = new ProveedoresJpaController();
    CategoriasJpaController cat = new CategoriasJpaController();

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
            out.println("<title>Servlet agregarProductoServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet agregarProductoServ at " + request.getContextPath() + "</h1>");
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

        // Consultar selects
        List<Proveedores> consultp = pro.findProveedoresEntities();
        request.setAttribute("consultap", consultp);
        List<Categorias> consultc = cat.findCategoriasEntities();
        request.setAttribute("consultac", consultc);

        RequestDispatcher dispatcher = request.getRequestDispatcher("agregarProductoAdmin.jsp");
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
        ProductosJpaController productoObj = new ProductosJpaController();
        Calendar calendar = Calendar.getInstance();

        int idVacio = 0;
        String nombreProducto = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String urlimagen = request.getParameter("urlimagen");
        int stock = Integer.parseInt(request.getParameter("stock"));
        int sminimo = Integer.parseInt(request.getParameter("sminimo"));
        double precio = Double.parseDouble(request.getParameter("precio"));
        int proveedorId = Integer.parseInt(request.getParameter("nombreProveedor"));
        int categoriaId = Integer.parseInt(request.getParameter("nombreCategoria"));

        // Obtener el objeto Proveedores y Categorias basados en los IDs
        Proveedores proveedor = pro.findProveedores(proveedorId);
        Categorias categoria = cat.findCategorias(categoriaId);

        Productos productoExistente = productoObj.findProductosByNombreAndCategoria(nombreProducto, categoriaId);
        if (productoExistente != null) {
            request.setAttribute("mensajeProductoExiste", "No se agrego, el producto " + nombreProducto + " con una misma categoria ya existe");
            doGet(request, response);

        } else {
            if (sminimo >= stock) {
                //inserta producto
                Productos producto = new Productos(idVacio, nombreProducto, descripcion, urlimagen, stock, sminimo, precio, proveedor, categoria);
                productoObj.create(producto);
                request.setAttribute("mensajeProAgregado", "Producto agregado");
                //manda correo de por que el sminimo es mayor a stock
                Properties mailProperties = new Properties();
                mailProperties.put("mail.smtp.host", "smtp-mail.outlook.com");
                mailProperties.put("mail.smtp.port", "587");
                mailProperties.put("mail.smtp.user", "correo@outlook.com");
                mailProperties.put("mail.smtp.password", "Contraseña");
                mailProperties.put("mail.smtp.starttls.enable", "true");
                mailProperties.put("mail.smtp.auth", "true");
                Mail m = new Mail(mailProperties);
                String subject = "NOTIFICACIÓN DE BAJO STOCK AGREGADO";
                String message = "El producto de nombre " + nombreProducto + " fue ingresado con un stock de " + stock + " cuando su stock minimo es de" + sminimo;

                try {
                    m.enviarEmail(subject, message, "correo@gmail.com");
                } catch (MessagingException ex) {
                    Logger.getLogger(agregarProductoServ.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                Productos producto = new Productos(idVacio, nombreProducto, descripcion, urlimagen, stock, sminimo, precio, proveedor, categoria);
                productoObj.create(producto);
                request.setAttribute("mensajeProAgregado", "Producto agregado");
               
            }
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
