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
import jpacontroller.CategoriasJpaController;
import jpacontroller.ProveedoresJpaController;
import jpacontroller.exceptions.NonexistentEntityException;
import modelos.Categorias;
import modelos.Proveedores;

/**
 *
 * @author Kevin Duran
 */
@WebServlet(name = "proveedoresCategoriasServ", urlPatterns = {"/proveedoresCategoriasServ"})
public class proveedoresCategoriasServ extends HttpServlet {

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
            out.println("<title>Servlet proveedoresCategoriasServ</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet proveedoresCategoriasServ at " + request.getContextPath() + "</h1>");
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
        //Eliminar proveedor
        if (request.getParameter("idepro") != null) {
            int id = Integer.parseInt(request.getParameter("idepro"));
            try {
                pro.destroy(id);
                request.setAttribute("mensajeProEliminado", "Proveedor eliminado");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(proveedoresCategoriasServ.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Eliminar categoria
        if (request.getParameter("idecat") != null) {
            int id = Integer.parseInt(request.getParameter("idecat"));
            try {
                cat.destroy(id);
                request.setAttribute("mensajeCatEliminado", "Categoria eliminado");
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(proveedoresCategoriasServ.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Consulta general categorias y proveddor
        List<Proveedores> consult = pro.findProveedoresEntities();
        List<Categorias> consultC = cat.findCategoriasEntities();
        request.setAttribute("consulta", consult);
        request.setAttribute("consultac", consultC);
        RequestDispatcher dispatcher = request.getRequestDispatcher("proYcatAdmin.jsp");
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
        String nombreCategoria = request.getParameter("nombreCategoria");
        if (nombreCategoria == null) {
            //AGREGAR PROVEEDOR
            String nombreProveedor = request.getParameter("nombreProveedor");
            String telefonoProveedor = request.getParameter("telefonoProveedor");

            Proveedores proveedorExistente = pro.findProveedoresByNombre(nombreProveedor);
            if (proveedorExistente != null) {
                request.setAttribute("mensajeP", "No se agrego, el proveedor ya existe");
                doGet(request, response);

            } else {
                // El proveedor no existe, crea uno nuevo
                Proveedores proveedor = new Proveedores(nombreProveedor, telefonoProveedor);
                pro.create(proveedor);
                request.setAttribute("mensajeProAgregado", "Proveedor agregado");
            }

        } else {
            //AGRAGAR CATEGORIA
            Categorias categoriaExistente = cat.findCategoriasByNombre(nombreCategoria);
            if (categoriaExistente != null) {
                request.setAttribute("mensajeC", "No se agrego, la categoria ya existe");
                doGet(request, response);

            } else {
                int idVacio = 0;
                Categorias categoria = new Categorias(idVacio, nombreCategoria);
                cat.create(categoria);
                request.setAttribute("mensajeCatAgregado", "Categoria agregada");
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
