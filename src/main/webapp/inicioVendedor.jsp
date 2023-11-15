<%@page import="modelos.Vendedor"%>
<%@page import="modelos.Carrito"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("lista") == null) {
        ArrayList<Carrito> lis = new ArrayList<Carrito>();
        session.setAttribute("lista", lis);
    }
    ArrayList<Carrito> lista = (ArrayList<Carrito>) session.getAttribute("lista");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>CAJA</title>
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
    <body id="resultados">
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
                            <a class="nav-link" href="misventasServ?id=<%= vendedor.getIdvendedor()%>"  style="color: white;"><h5>VENTAS</h5></a>
                        </li> 
                        <li class="nav-item">
                            <h6 class="nav-link" style="color: white;"><%= vendedor.getNombresApellidos()%></h6>
                        </li> 
                    </ul>                 
                </div>


                <a href="cerrarSesion?tipo=vendedor" style="color: white;">Cerrar Sesion </a>
            </div>
        </nav>

        <div class="row m-1">
            <div class="col-md-5 p-2">
                <div class="bg-light border dark-border-subtle rounded p-3">    
                    <form method="post" action="facturaServ">
                        <input type="hidden" name="idvendedor" value="<%= vendedor.getIdvendedor()%>">
                        <%
                            } else {
                                // La sesión no contiene un objeto Admin, redirigir al usuario a index.jsp
                                response.sendRedirect("index.jsp");
                            }
                        %>
                        <div class="table-responsive">   
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Producto</th>
                                        <th scope="col">Precio</th>
                                        <th scope="col">Cantidad</th>
                                        <th scope="col">SUBTOTAL</th>                          
                                        <th scope="col">QUITAR</th>                       
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (lista != null) {
                                            for (int i = 0; i < lista.size(); i++) {
                                                Carrito carrito = lista.get(i);
                                    %>
                                    <tr>
                                        <td><%= carrito.getNombreProducto()%></td>
                                        <td>$<%= carrito.getPrecio()%></td>                                
                                        <td>
                                            <a href="vendedorServ?accion=disminuir&indice=<%= i%>">DISMINUIR</a>
                                            <%= carrito.getCantidad()%>                                      
                                        </td>                                  
                                        <td>$<%= carrito.getSubtotal()%></td>
                                        <td>                                   
                                            <a href="vendedorServ?accion=eliminar&indice=<%= i%>" class="btn btn-danger"> 
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

                                <input type="hidden" name="ids" value="<%= carrito.getIdproducto()%>">
                                <input type="hidden" name="nombresProductos" value="<%= carrito.getNombreProducto()%>">
                                <input type="hidden" name="precios" value="<%= carrito.getPrecio()%>">
                                <input type="hidden" name="cantidades" value="<%= carrito.getCantidad()%>">
                                <input type="hidden" name="existencias" value="<%= carrito.getExistencias()%>">
                                <input type="hidden" name="subtotales" value="<%= carrito.getSubtotal()%>">

                                <%
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                        <br>
                        <a href="vendedorServ?accion=vaciar&indice=0">VACIAR FACTURA</a>
                        <h4>Total: $<%= session.getAttribute("total")%></h4> 
                        <input type="hidden" name="total" value="<%= session.getAttribute("total")%>">
                        <button type="submit" class="btn btn-success">FACTURAR</button>
                    </form>
                </div>
            </div>


            <div class="col-md-7 p-2">
                <div class="bg-light border dark-border-subtle rounded p-3"> 
                    <form id="searchForm">
                        <div class="mb-3">
                            <label>Buscar por categoría:</label>
                            <select id="idCategoria" name="idCategoria" required>
                                <option value="" disabled selected>Selecciona una categoría</option>
                                <option value="vacio">Todas</option>
                                <c:forEach items="${categorias}" var="categoria">
                                    <option value="${categoria.idcategoria}">${categoria.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>

                    <div class="table-responsive">   
                        <table class="table table-dark table-striped">
                            <thead>
                                <tr>
                                    <th scope="col">ACCION</th>
                                    <th scope="col">ID</th>
                                    <th scope="col">NOMBRE</th>
                                    <th scope="col">PRECIO</th>
                                    <th scope="col">IMG</th>
                                    <th scope="col">STOCK</th>
                                    <th scope="col">STOCK MINIMO</th>
                                    <th scope="col">CATEGORIA</th>
                                    <th scope="col">PROVEEDOR</th>                              
                                </tr>
                            </thead>
                            <tbody >
                                <c:forEach var="registro" items="${registros}">
                                    <tr>
                                        <td>
                                            <form id="sumar" action="vendedorServ" method="post">                                          
                                                <input type="hidden" name="idproducto" value="${registro.idproducto}"> 
                                                <input type="hidden" name="nombreProducto" value="${registro.nombre}">
                                                <input type="hidden" name="precio" value="${registro.precio}">
                                                <input type="hidden" name="cantidad" value="1">
                                                <input type="hidden" name="existencias" value="${registro.stock}">
                                                <button type="submit" class="btn btn-primary">SUMAR</button>                           
                                            </form>
                                        </td> 
                                        <td>${registro.idproducto}</td>
                                        <td>${registro.nombre}</td>
                                        <td>$${registro.precio}</td>
                                        <td><img src="${registro.urlimagen}" alt="" class="img-thumbnail img-fluid" style="width: 95px; height: auto;"></td>
                                        <td>${registro.stock}</td>
                                         <td>${registro.sminimo}</td>
                                        <td>${registro.nombreCategoria}</td>
                                        <td>${registro.nombreProveedor}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <c:if test="${paginaActual > 1}">
                        <a class="btn btn-outline-dark" href="javascript:void(0);" id="anterior">Anterior</a>
                    </c:if>

                    <c:if test="${paginaActual < totalPaginas}">
                        <a class="btn btn-outline-dark" href="javascript:void(0);" id="siguiente">Siguiente</a>
                    </c:if>
                </div>
            </div>
        </div>



        <script>
            $(document).ready(function () {
                // Manejar el clic en el enlace "Anterior"
                $("#anterior").click(function () {
                    $.ajax({
                        type: "GET",
                        url: "vendedorServ?pagina=${paginaActual - 1}",
                        success: function (data) {
                            // Actualizar el contenido de la página con la respuesta del servidor
                            $("#resultados").html(data);
                        }
                    });
                });

                // Manejar el clic en el enlace "Siguiente"
                $("#siguiente").click(function () {
                    $.ajax({
                        type: "GET",
                        url: "vendedorServ?pagina=${paginaActual + 1}",
                        success: function (data) {
                            // Actualizar el contenido de la página con la respuesta del servidor
                            $("#resultados").html(data);
                        }
                    });
                });
            });
        </script>


        <script>
            $(document).ready(function () {
                // Manejador de eventos para el campo select
                $("#idCategoria").change(function () {
                    // Obtiene los datos del formulario
                    var formData = $("#searchForm").serialize();

                    // Realiza una solicitud AJAX
                    $.ajax({
                        type: "GET", // O el método que uses en tu servlet
                        url: "vendedorServ", // URL de tu servlet
                        data: formData,
                        success: function (data) {
                            // Maneja la respuesta aquí, por ejemplo, actualiza una sección de la página
                            $("#resultados").html(data); // Suponiendo que tengas un elemento con el id "resultados" para mostrar los resultados
                        }
                    });
                });
            });
        </script>




        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
                integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous">
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
                integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz" crossorigin="anonymous">
        </script>
    </body>
</html>
                       