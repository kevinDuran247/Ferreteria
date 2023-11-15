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
import jpacontroller.VendedorJpaController;
import jpacontroller.VentasJpaController;
import modelos.Vendedor;
import modelos.Ventas;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "comprasServ", urlPatterns = {"/comprasServ"})
public class comprasServ extends HttpServlet {
     VendedorJpaController vendedorr = new VendedorJpaController();
    VentasJpaController ventass = new VentasJpaController();

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
            out.println("<title>Servlet comprasServ</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet comprasServ at " + request.getContextPath() + "</h1>");
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
        List<Ventas> ventas = ventass.findVentasEntities();
        
         for (Ventas venta : ventas) {
            int idVendedor = venta.getIdvendedor()!= null ? venta.getIdvendedor().getIdvendedor() : -1;

            Vendedor vendedor = idVendedor != -1 ? vendedorr.findVendedor(idVendedor) : null;

            venta.setNombreVendedor(vendedor != null ? vendedor.getNombresApellidos(): "Vendedor eliminado");
        }
        request.setAttribute("ventas", ventas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("comprasAdmin.jsp");
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