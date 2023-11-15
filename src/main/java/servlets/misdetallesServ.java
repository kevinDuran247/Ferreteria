/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import modelos.Productos;
import modelos.Ventas;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "misdetallesServ", urlPatterns = {"/misdetallesServ"})
public class misdetallesServ extends HttpServlet {

    VendedorJpaController vendedorr = new VendedorJpaController();
    VentasJpaController ventass = new VentasJpaController();
    DetalleventasJpaController detallesss = new DetalleventasJpaController();
    ProductosJpaController pro = new ProductosJpaController();

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
            out.println("<title>Servlet misdetallesServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet misdetallesServ at " + request.getContextPath() + "</h1>");
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
        int id = Integer.parseInt(request.getParameter("idventa"));
        Ventas ventas = ventass.findVentas(id);
        List<Detalleventas> detalles = detallesss.findVentasDetalles(ventas);
        // Recorre la lista de productos y obtén el nombre del producto
        for (Detalleventas detalle : detalles) {
            int idProducto = detalle.getIdproducto() != null ? detalle.getIdproducto().getIdproducto() : -1;

            Productos producto = idProducto != -1 ? pro.findProductos(idProducto) : null;

            detalle.setNombreProducto(producto != null ? producto.getNombre() : "Producto eliminado");
        }

        request.setAttribute("detalles", detalles);
        RequestDispatcher dispatcher = request.getRequestDispatcher("misdetallesVendedor.jsp");
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
        processRequest(request, response);
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
