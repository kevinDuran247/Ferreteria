/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Admin;
import modelos.Vendedor;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "loginServ", urlPatterns = {"/loginServ"})
public class loginServ extends HttpServlet {

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
            out.println("<title>Servlet loginServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginServ at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
    String usuario = request.getParameter("usuario");
    String claveIngresada = request.getParameter("clave");

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
    EntityManager em = emf.createEntityManager();

    // Consulta al usuario Admin de la base de datos
    TypedQuery<Admin> adminQuery = em.createQuery("SELECT a FROM Admin a WHERE a.usuario = :usuario", Admin.class);
    adminQuery.setParameter("usuario", usuario);
    Admin admin = null;

    try {
        admin = adminQuery.getSingleResult();
    } catch (Exception e) {
        
    }

    // Consulta al usuario Vendedor de la base de datos
    TypedQuery<Vendedor> vendedorQuery = em.createQuery("SELECT v FROM Vendedor v WHERE v.usuario = :usuario", Vendedor.class);
    vendedorQuery.setParameter("usuario", usuario);
    Vendedor vendedor = null;

    try {
        vendedor = vendedorQuery.getSingleResult();
    } catch (Exception e) {
        
    }

    em.close();
    emf.close();

    if (admin != null && BCrypt.checkpw(claveIngresada, admin.getContraseña())) {
        // Usuario autenticado como Admin.
        HttpSession session = request.getSession();
        session.setAttribute("admin", admin);
        response.sendRedirect("inicioAdmin.jsp");
    } else if (vendedor != null && BCrypt.checkpw(claveIngresada, vendedor.getContraseña())) {
        // Usuario autenticado como Vendedor.
        HttpSession session = request.getSession();
        session.setAttribute("vendedor", vendedor);
        response.sendRedirect("vendedorServ");
    } else {
        response.sendRedirect("index.jsp");
    }
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
