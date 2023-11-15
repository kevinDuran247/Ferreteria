/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jpacontroller.VendedorJpaController;
import jpacontroller.exceptions.IllegalOrphanException;
import jpacontroller.exceptions.NonexistentEntityException;
import modelos.Vendedor;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "vendedoresServ", urlPatterns = {"/vendedoresServ"})
public class vendedoresServ extends HttpServlet {

    VendedorJpaController vendedorObj = new VendedorJpaController();

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
            out.println("<title>Servlet vendedoresServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet vendedoresServ at " + request.getContextPath() + "</h1>");
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

        //Eliminar vendedor
        if (request.getParameter("idev") != null) {
            int id = Integer.parseInt(request.getParameter("idev"));

            try {
                vendedorObj.destroy(id);
                request.setAttribute("mensajeVendedorEliminado", "Vendedor eliminado");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(vendedoresServ.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //Vista modificar
        if (request.getParameter("idmv") != null) {
            int idmv = Integer.parseInt(request.getParameter("idmv"));
            Vendedor vendedor = vendedorObj.findVendedor(idmv);

            request.setAttribute("vendedor", vendedor);

            RequestDispatcher dispatcher2 = request.getRequestDispatcher("modificarVendedorAdmin.jsp");
            dispatcher2.forward(request, response);
        }

        //CONSULTA GENERAL
        List<Vendedor> consulta = vendedorObj.findVendedorEntities();
        request.setAttribute("consulta", consulta);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vendedoresAdmin.jsp");
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

        String nombresM = request.getParameter("nombresM");
        if (nombresM == null) {
            //AGREGAR VENDEDOR
            int idVacio = 0;
            String nombres = request.getParameter("nombres");
            String usuario = request.getParameter("usuario");
            String contrasenaPlana = request.getParameter("contrasena");
            String contrasena = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());

            Vendedor vendedorExistente = vendedorObj.findAdminByVendedor(usuario);
            if (vendedorExistente != null) {
                request.setAttribute("mensajeVendedor", "No se agrego, el usuario del vendedor ya existe");
                doGet(request, response);

            } else {
                Vendedor vendedor = new Vendedor(idVacio, nombres, usuario, contrasena);         
                try {
                    vendedorObj.create(vendedor);
                    request.setAttribute("mensajeVendedorAgregado", "Vendedor agregado");
                } catch (Exception ex) {
                    Logger.getLogger(vendedoresServ.class.getName()).log(Level.SEVERE, null, ex);
                }
       
            }
            
       
        } else {
            //MODIFICAR VENDEDOR
            int idM = Integer.parseInt(request.getParameter("idM"));
            String usuarioM = request.getParameter("usuarioM");
            String contrasenaPlana = request.getParameter("contrasenaM");
            String contrasenaM = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());

            
            Vendedor vendedor = new Vendedor(idM, nombresM, usuarioM, contrasenaM);
            
            try {
                vendedorObj.edit(vendedor);
                request.setAttribute("mensajeVendedorModificado", "Vendedor modificado");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(vendedoresServ.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(vendedoresServ.class.getName()).log(Level.SEVERE, null, ex);
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
