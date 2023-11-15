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
        <title>ProveedoresCategorias</title>
    </head>


    <body id="body-pd">
        <%
            HttpSession sessionn = request.getSession();
            Admin admin = (Admin) sessionn.getAttribute("admin");

            if (admin == null) {
                response.sendRedirect("index.jsp");
            }
        %>
        <div>
            <jsp:include page="menuAdmin.jsp"></jsp:include>
            </div>

            <main>

                <div class="container">

                    <br>
                <% if (request.getAttribute("mensajeP") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeP")%></h5>
                <% }%>
                <% if (request.getAttribute("mensajeProEliminado") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeProEliminado")%></h5>
                <% }%>
                <% if (request.getAttribute("mensajeProAgregado") != null) {%>
                <h5 class="text-success"><%= request.getAttribute("mensajeProAgregado")%></h5>
                <% }%>
                <div class="row">
                    <div class="col-md-6 p-2">
                        <div class="bg-light border dark-border-subtle rounded p-3"> 
                            <form method="post" action="proveedoresCategoriasServ">
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">NOMBRE PROVEEDOR:</label>
                                    <input type="text" name="nombreProveedor" class="form-control" required>                   
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">TELEFONO:</label>
                                    <input type="number" name="telefonoProveedor" class="form-control" required>                   
                                </div>
                                <button type="submit" class="btn btn-primary">AGREGAR</button>
                            </form>
                            <br>
                            <table class="table table-dark table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">NOMBRE PORVEEDOR</th>
                                        <th scope="col">TELEFONO</th>
                                        <th scope="col">Acciones</th>                          
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${consulta}" var="proveedor">
                                        <tr class="">
                                            <td scope="row">${proveedor.idproveedor}</td>
                                            <td>${proveedor.nombre}</td>
                                            <td>${proveedor.telefono}</td>                                      
                                            <td>
                                                <a href="proveedoresCategoriasServ?idepro=${proveedor.idproveedor}" class="btn btn-danger">
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


                    <div class="col-md-6 p-2">
                         <% if (request.getAttribute("mensajeC") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeC")%></h5>
                <% }%>
                 <% if (request.getAttribute("mensajeCatEliminado") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeCatEliminado")%></h5>
                <% }%>
                 <% if (request.getAttribute("mensajeCatAgregado") != null) {%>
                <h5 class="text-success"><%= request.getAttribute("mensajeCatAgregado")%></h5>
                <% }%>
                        <div class="bg-light border dark-border-subtle rounded p-3">
                            <form method="post" action="proveedoresCategoriasServ">
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">NOMBRE CATEGORIA:</label>
                                    <input type="text" name="nombreCategoria" class="form-control" required>                   
                                </div>                                   
                                <button type="submit" class="btn btn-primary">AGREGAR</button>
                            </form>
                            <br>
                            <table class="table table-dark table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">NOMBRE PORVEEDOR</th>
                                        <th scope="col">Acciones</th>                          
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${consultac}" var="categoria">
                                        <tr class="">
                                            <td scope="row">${categoria.idcategoria}</td>
                                            <td>${categoria.nombre}</td>

                                            <td>
                                                <a href="proveedoresCategoriasServ?idecat=${categoria.idcategoria}" class="btn btn-danger">
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
            </div>

        </main>

    </body>

</html>