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
        <title>PRODUCTOS</title>
        <style>form, a.boton-enlace {
                display: inline-block;
                margin-right: 10px; /* Espacio entre los elementos, ajusta según tu preferencia */
            }
        </style>

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

            <main id="resultados">
                <div class="container">
                    <br>
                    <% if (request.getAttribute("mensajeProEliminado") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeProEliminado")%></h5>
                <% }%>
                <% if (request.getAttribute("mensajeProductoMExiste") != null) {%>
                <h5 class="text-danger"><%= request.getAttribute("mensajeProductoMExiste")%></h5>
                <% }%>
                <% if (request.getAttribute("mensajeProductoMEditado") != null) {%>
                <h5 class="text-success"><%= request.getAttribute("mensajeProductoMEditado")%></h5>
                <% }%>
                    <div class="d-flex justify-content-end">             
                        <a href="agregarProductoServ" type="button" class="btn btn-primary align-self-end">AGREGAR NUEVO PRODUCTO</a>
                    </div>
                     
                  
                    <form id="miFormulario" method="get" action="productosServ">
                        <input type="text" name="nProducto" placeholder="Buscar por nombre">
                        <button class="btn btn-primary" type="submit">Buscar</button>
                    </form>
                    <a class="btn btn-primary boton-enlace" href="productosServ">Ver todos</a>
                    <br>

                    <br>
                    <div class="table-responsive">
                        <table class="table">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="row">ID</th>
                                    <th>Nombre</th>
                                    <th>Descripcíon</th>
                                    <th>Imagen</th>
                                    <th>Stock</th>
                                    <th>Stock minimo</th>
                                    <th>Precio</th>
                                    <th>Categoria</th>
                                    <th>Proveedor</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="registro" items="${registros}">
                                <tr>                                   
                                    <td>${registro.idproducto}</td>
                                    <td>${registro.nombre}</td>
                                    <td>${registro.descripcion}</td>
                                    <td><img src="${registro.urlimagen}" alt="" class="img-thumbnail img-fluid" style="width: 95px; height: auto;"></td>
                                    <td>${registro.stock}</td>
                                    <td>${registro.sminimo}</td>
                                    <td>$${registro.precio}</td>
                                    <td>${registro.nombreCategoria}</td>
                                    <td>${registro.nombreProveedor}</td>

                                    <td>
                                        <a href="productosServ?idpm=${registro.idproducto}" class="btn btn-warning"> <svg
                                                xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                                viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                                                stroke-linecap="round" stroke-linejoin="round"
                                                class="lucide lucide-pen-square">
                                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7" />
                                            <path d="M18.5 2.5a2.12 2.12 0 0 1 3 3L12 15l-4 1 1-4Z" />
                                            </svg>
                                        </a>
                                        <a href="productosServ?idp=${registro.idproducto}" class="btn btn-danger">
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
                    <c:if test="${paginaActual > 1}">           
                        <a class="btn btn-primary" href="productosServ?pagina=${paginaActual - 1}">Anterior</a>
                    </c:if>

                    <c:forEach begin="1" end="${totalPaginas}" var="i">
                        <c:choose>
                            <c:when test="${paginaActual == i}">
                                <span>${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-primary" href="productosServ?pagina=${i}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${paginaActual < totalPaginas}">
                        <a class="btn btn-primary" href="productosServ?pagina=${paginaActual + 1}">Siguiente</a>                   
                    </c:if>

                </div>               
            </div>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

            <script>
                $(document).ready(function () {
                    // Manejador de eventos para el formulario
                    $("#miFormulario").submit(function (event) {
                        event.preventDefault(); // Evita el envío tradicional del formulario

                        // Obtiene los datos del formulario
                        var formData = $(this).serialize();

                        // Realiza una solicitud AJAX
                        $.ajax({
                            type: "GET", // O el método que uses en tu servlet
                            url: "productosServ", // URL de tu servlet
                            data: formData,
                            success: function (data) {
                                // Maneja la respuesta aquí, por ejemplo, actualiza una sección de la página
                                $("#resultados").html(data); // Suponiendo que tengas un elemento con el id "resultados" para mostrar los resultados
                            },
                            error: function () {
                                // Maneja errores si la solicitud AJAX falla
                                alert("Error en la solicitud AJAX");
                            }
                        });
                    });
                });
            </script>



        </main>

    </body>

</html>