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
                <div class="row bg-light border dark-border-subtle rounded p-2"> 
                    <h5>Por cualquier dato que vayas a modificar debes tambien modificar la contraseña, de lo contrario la constraseña sera la contraseña encriptada</h5>
                    <form method="post" action="vendedoresServ">
                        <div class="mb-3">
                            <label for="inputName" class="col-4 col-form-label">ID:</label>
                                <input type="number" class="form-control" name="idM" id="idM" value="${vendedor.idvendedor}" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="exampleInputEmail1" class="form-label">NOMBRES Y APELLIDOS:</label>
                            <input type="text" name="nombresM" placeholder="Dos nombres y dos apellidos!" value="${vendedor.nombresApellidos}" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="descripcion" class="form-label">USUARIO:</label>
                            <input type="text" name="usuarioM" class="form-control" value="${vendedor.usuario}" required>
                        </div>

                        <div class="mb-3">
                            <label for="urlimagen" class="form-label">CONTRASEÑA:</label>
                            <input type="text" name="contrasenaM" class="form-control" value="${vendedor.contraseña}" required>
                        </div>

                        <div class="d-flex justify-content-end">             
                            <button type="submit" class="btn btn-warning">MODIFICAR</button>

                        </div>
                        <br>
                    </form>
                </div>


            </div>


        </main>

    </body>

</html>