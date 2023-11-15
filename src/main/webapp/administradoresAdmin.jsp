<%@page import="modelos.Admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="scriptMenuAdmin.js"></script>
        <title>VENDEDORES</title>

    </head>


    <body id="body-pd">
        <%
            /* HttpSession sessionn = request.getSession();
            Admin admin = (Admin) sessionn.getAttribute("admin");

            if (admin == null) {
                response.sendRedirect("index.jsp");
            }*/
        %>
        <div>
            <jsp:include page="menuAdmin.jsp"></jsp:include>
            </div>

            <main>
                <div class="container">
                    <br>
                    <br>
                     <% if (request.getAttribute("mensajeAdminEliminado") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeAdminEliminado")%></h5>
                <% }%>
                 <% if (request.getAttribute("mensajeAdminAgregado") != null) {%>
                <h5 class="text-success"><%= request.getAttribute("mensajeAdminAgregado")%></h5>
                <% }%>
                <% if (request.getAttribute("mensajeAdmin") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeAdmin")%></h5>
                <% }%>
                <div class="row bg-light border dark-border-subtle rounded p-2">                  
                    <form method="post" action="administradoreServ">                            
                        <div class="mb-3">
                            <label for="descripcion" class="form-label">USUARIO:</label>
                            <input type="text" name="usuario" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="urlimagen" class="form-label">CONTRASEÑA:</label>
                            <input type="password" name="contrasena" class="form-control"  required>
                        </div>

                        <div class="mb-3">
                            <label for="exampleInputEmail1" class="form-label">CORREO ELECTRONICO:</label>
                            <input type="text" name="correo" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="exampleInputEmail1" class="form-label">TELEFONO:</label>
                            <input type="number" name="telefono" class="form-control" required>
                        </div>

                        <div class="d-flex justify-content-end">             
                            <button type="submit" class="btn btn-primary">AGREGAR</button>

                        </div>
                        <br>
                    </form>


                    <div class="table-responsive">
                        <table class="table">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="row">ID</th>                                       
                                    <th>Usuario</th>
                                    <th>Contraseña</th> 
                                    <th>Correo</th>
                                    <th>Telefono</th>
                                    <th>Acciones</th>     
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="vendedor" items="${consulta}">
                                    <tr>                                   
                                        <td>${vendedor.idusuario}</td>                                      
                                        <td>${vendedor.usuario}</td>
                                        <td>${vendedor.contraseña}</td>                                  
                                        <td>${vendedor.email}</td>
                                        <td>${vendedor.telefono}</td>
                                        <td>                                            
                                            <a href="administradoreServ?idev=${vendedor.idusuario}" class="btn btn-danger">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                                     stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-trash">
                                                <path d="M3 6h18" />
                                                <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
                                                <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" />
                                                </svg>
                                            </a>                                             
                                        </td> 
                                    </tr>
                                </c:forEach>        
                            </tbody>
                        </table>
                    </div>
                </div>


            </div>


        </main>

    </body>

</html>