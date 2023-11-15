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
import jpacontroller.AdminJpaController;
import jpacontroller.exceptions.IllegalOrphanException;
import jpacontroller.exceptions.NonexistentEntityException;
import modelos.Admin;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "administradoreServ", urlPatterns = {"/administradoreServ"})
public class administradoreServ extends HttpServlet {
    AdminJpaController adminn = new AdminJpaController();

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
            out.println("<title>Servlet administradoreServ</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet administradoreServ at " + request.getContextPath() + "</h1>");
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
        if (request.getParameter("idev") != null) {
            int id = Integer.parseInt(request.getParameter("idev"));

            try {
                adminn.destroy(id);
                request.setAttribute("mensajeAdminEliminado", "Adiministrador eliminado");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(administradoreServ.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        //CONSULTA GENERAL
        List<Admin> consulta = adminn.findAdminEntities();
        request.setAttribute("consulta", consulta);
        RequestDispatcher dispatcher = request.getRequestDispatcher("administradoresAdmin.jsp");
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
            //AGREGAR ADMIN
            int idVacio = 0;
            String correo = request.getParameter("correo");
            String usuario = request.getParameter("usuario");
            String contrasenaPlana = request.getParameter("contrasena");
            String telefono = request.getParameter("telefono");
            String contrasena = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());

            Admin administradorExistente = adminn.findAdminByUsuario(usuario);
            if (administradorExistente != null) {
                request.setAttribute("mensajeAdmin", "No se agrego, el usuario ya existe");
                doGet(request, response);

            } else {
                Admin admin = new Admin(idVacio, usuario, contrasena, correo, telefono);
                adminn.create(admin);
                request.setAttribute("mensajeAdminAgregado", "Administrador agregado");
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
