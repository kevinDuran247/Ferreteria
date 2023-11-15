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
        <title>VENTAS VENDEDORES</title>

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
                                        <a href="ventasdetallesServ?idventa=${venta.idventa}" class="btn btn-success">
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
    </main>
</body>
</html>