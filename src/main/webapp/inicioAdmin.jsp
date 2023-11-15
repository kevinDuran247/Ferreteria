
<%@page import="modelos.Admin"%>
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
        <title>ADMINISTRADOR</title>

    </head>


    <body id="body-pd">
        <div>
            <jsp:include page="menuAdmin.jsp"></jsp:include>
            </div>

            <main>
            <%
                // Acceder a la sesión y obtener los datos del usuario
                HttpSession sessionn = request.getSession();
                Admin admin = (Admin) sessionn.getAttribute("admin");

                if (admin != null) {
            %>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <center>
                <h1>Bienvenido al inventario!</h1>
                <p>Usuario: <%= admin.getUsuario()%></p>
                <p>Correo electrónico: <%= admin.getEmail()%></p>
            </center>
            <%
                } else {
                    // La sesión no contiene un objeto Admin, redirigir al usuario a index.jsp
                    response.sendRedirect("index.jsp");
                }
            %>
        </main>


    </body>

</html>