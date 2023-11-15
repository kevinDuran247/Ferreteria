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
        <title>VENTAS DETALLES</title>

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
                    <div class="table-responsive" id="invoice">
                        <table class="table">
                            <thead class="table-dark">
                                <tr>
                                    <th scope="row">ID</th>
                                    <th>PRODUCTO</th>                                     
                                    <th>CANTIDAD</th>
                                    <th>TOTAL</th>
                                    <th>FECHA</th>
                                    <th>ID VENTA</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="venta" items="${detalles}">
                                <tr>                                   
                                    <td>${venta.iddetalle}</td>
                                    <td>${venta.nombreProducto}</td>                                   
                                    <td>${venta.cantidad}</td>
                                    <td>$${venta.total}</td>
                                    <td>${venta.fecha}</td> 
                                    <td>${venta.idventa.idventa}</td>
                                </tr>
                            </c:forEach>        
                        </tbody>
                    </table>
                </div>
                     <button id="generarPDF" style="float: right;">Generar factura PDF</button>

            </div>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.debug.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
        <script>
            document.getElementById('generarPDF').addEventListener('click', function () {
                var invoice = document.getElementById('invoice');
                html2canvas(invoice, {
                    onrendered: function (canvas) {
                        var imgData = canvas.toDataURL('image/png');
                        var doc = new jsPDF('p', 'mm');
                        var pageWidth = doc.internal.pageSize.getWidth();
                        var pageHeight = doc.internal.pageSize.getHeight();
                        var imageWidth = 190; // Tamaño de la imagen en milímetros (ajusta según tus necesidades)
                        var imageHeight = (imageWidth * canvas.height) / canvas.width;

                        // Calcular coordenadas para centrar la imagen
                        var x = (pageWidth - imageWidth) / 2;
                        var y = (pageHeight - imageHeight) / 2;

                        doc.addImage(imgData, 'PNG', x, y, imageWidth, imageHeight);
                        doc.save('Factura.pdf');
                    }
                });
            });
        </script>
    </main>
</body>
</html>