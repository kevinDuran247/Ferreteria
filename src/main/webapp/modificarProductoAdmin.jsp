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
        <title>MODIFICAR PRODUCTO</title>

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
                        <div class="col-md-6">
                            <form method="post" action="productosServ">
                                <div class="mb-3">
                                    <label for="inputName" class="col-4 col-form-label">ID:</label>
                                   
                                        <input type="number" class="form-control" name="idM" id="idM"
                                               value="${productos.idproducto}" readonly>
                                </div>
                                                              
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">NOMBRE PRODUCTO:</label>
                                    <input type="text" name="nombreM" class="form-control" value="${productos.nombre}" required>
                                </div>

                                <div class="mb-3">
                                    <label for="descripcion" class="form-label">DESCRIPCIÓN:</label>
                                    <textarea name="descripcionM" class="form-control" required>${productos.descripcion}</textarea>
                                </div>

                                <div class="mb-3">
                                    <label for="urlimagen" class="form-label">IMAGEN:</label>
                                    <input type="text" name="urlimagenM" class="form-control" value="${productos.urlimagen}" placeholder="Pega el url de la imagen" required>
                                </div>
                                <div class="mb-3">
                                    <label for="stock" class="form-label">STOCK:</label>
                                    <input type="number" name="stockM" class="form-control" value="${productos.stock}" placeholder="0" required min="0">
                                </div>
                                

                            </div>

                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="stock" class="form-label">STOCK MINIMO:</label>
                                    <input type="number" name="sminimoM" class="form-control" value="${productos.sminimo}" placeholder="0" required min="0">
                                </div>
                                
                                <div class="mb-3">
                                    <label for="precio" class="form-label">PRECIO:</label>
                                    <input type="number" step="0.01" name="precioM" class="form-control" value="${productos.precio}" placeholder="00.00" required>
                                </div>                              

                                <div class="mb-3">
                                    <label for="proveedor_id" class="form-label">PROVEEDOR:</label>
                                    <select class="form-select form-select-lg" name="nombreProveedorM" id="nombreProveedor" required>
                                        <option value="${productos.proveedorId.idproveedor}">${productos.nombreProveedor}</option>
                                        <c:forEach items="${proveedores}" var="proveedor">
                                            <option value="${proveedor.idproveedor}">${proveedor.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="mb-3">
                                    <label for="categoria_id" class="form-label">CATEGORÍA:</label>
                                    <select class="form-select form-select-lg" name="nombreCategoriaM" id="nombreProveedor" required>
                                        <option value="${productos.categoriaId.idcategoria}">${productos.nombreCategoria}</option>
                                        <c:forEach items="${categorias}" var="categoria">
                                            <option value="${categoria.idcategoria}">${categoria.nombre}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="d-flex justify-content-end">             
                                    <button type="submit" class="btn btn-warning">MODIFICAR</button>
                                </div>

                        </form>
                    </div>
                    
                </div>
            </div>


        </main>

    </body>

</html>