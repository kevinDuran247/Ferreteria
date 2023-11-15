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
                    <% if (request.getAttribute("mensajeVendedorEliminado") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeVendedorEliminado")%></h5>
                <% }%>
                 <% if (request.getAttribute("mensajeVendedorAgregado") != null) {%>
                <h5 class="text-success"><%= request.getAttribute("mensajeVendedorAgregado")%></h5>
                <% }%>
                <% if (request.getAttribute("mensajeVendedor") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeVendedor")%></h5>
                <% }%>
                <% if (request.getAttribute("mensajeVendedorModificado") != null) {%>
                <h5 class="text-success"><%= request.getAttribute("mensajeVendedorModificado")%></h5>
                <% }%>
                <div class="row bg-light border dark-border-subtle rounded p-2">   
                    
                    <form method="post" action="vendedoresServ">
                        <div class="mb-3">
                            <label for="exampleInputEmail1" class="form-label">NOMBRES Y APELLIDOS:</label>
                            <input type="text" name="nombres" placeholder="Dos nombres y dos apellidos!" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="descripcion" class="form-label">USUARIO:</label>
                            <input type="text" name="usuario" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="urlimagen" class="form-label">CONTRASEÑA:</label>
                            <input type="password" name="contrasena" class="form-control"  required>
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
                                    <th>Nombre</th>
                                    <th>Usuario</th>
                                    <th>Contraseña</th>     
                                    <th>Acciones</th>     
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="vendedor" items="${consulta}">
                                    <tr>                                   
                                        <td>${vendedor.idvendedor}</td>
                                        <td>${vendedor.nombresApellidos}</td>
                                        <td>${vendedor.usuario}</td>
                                        <td>${vendedor.contraseña}</td>                                  

                                        <td>
                                            <a href="vendedoresServ?idmv=${vendedor.idvendedor}" class="btn btn-warning"> <svg
                                                    xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                    viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                                    stroke-linecap="round" stroke-linejoin="round"
                                                    class="lucide lucide-pen-square">
                                                <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7" />
                                                <path d="M18.5 2.5a2.12 2.12 0 0 1 3 3L12 15l-4 1 1-4Z" />
                                                </svg>
                                            </a>
                                            <a href="vendedoresServ?idev=${vendedor.idvendedor}" class="btn btn-danger">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                     viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                                     stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-trash">
                                                <path d="M3 6h18" />
                                                <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
                                                <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" />
                                                </svg>
                                            </a> 
                                            <a href="ventasvendedoresServ?id=${vendedor.idvendedor}" class="btn btn-success">
                                                Ver ventas
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