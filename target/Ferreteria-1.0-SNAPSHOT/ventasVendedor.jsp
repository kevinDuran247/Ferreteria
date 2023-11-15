<%@page import="modelos.Vendedor"%>
<%@page import="modelos.Carrito"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VENTAS</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <style>
            .navbar-nav .nav-item:hover .nav-link {
                color: black !important;
            }

            /* Puedes mantener el estilo original de fondo en hover si lo deseas */
            .navbar-nav .nav-item:hover {
                background-color: white;
            }
        </style>
    </head>
    <body>
        <%
            // Acceder a la sesión y obtener los datos del usuario
            HttpSession sessionn = request.getSession();
            Vendedor vendedor = (Vendedor) sessionn.getAttribute("vendedor");

            if (vendedor != null) {
        %>
        <nav class="navbar navbar-expand bg-dark">
            <div class="container-fluid">             
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="vendedorServ"  style="color: white;"><h5>CAJA</h5></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#"  style="color: white;"><h5>VENTAS</h5></a>
                        </li> 
                        <li class="nav-item">
                            <h6 class="nav-link" style="color: white;"><%= vendedor.getNombresApellidos()%></h6>
                        </li> 
                    </ul>                 
                </div>
                <%
                    } else {
                        // La sesión no contiene un objeto Admin, redirigir al usuario a index.jsp
                        response.sendRedirect("index.jsp");
                    }
                %>       
                <a href="cerrarSesion?tipo=vendedor" style="color: white;">Cerrar Sesion </a>
            </div>
        </nav>

        <div class="container">
            <br>
            <br>
            <div class="table-responsive">
                <table class="table">
                    <thead class="table-dark">
                        <tr>
                            <th scope="row">ID</th>
                            <th>TOTAL</th>
                            <th>FECHA</th>  
                            <th>ID VENDEDOR</th>                          
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="venta" items="${ventas}">
                            <tr>                                   
                                <td>${venta.idventa}</td>
                                <td>$${venta.total}</td>
                                <td>${venta.fecha}</td>
                                <td>${venta.idvendedor.idvendedor}</td>                                

                                <td>                                    
                                    <a href="misdetallesServ?idventa=${venta.idventa}" class="btn btn-success">
                                        Ver detalles
                                    </a>   
                                </td> 
                            </tr>
                        </c:forEach>        
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
            integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
            integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz" crossorigin="anonymous">
    </script>
</body>
</html>
