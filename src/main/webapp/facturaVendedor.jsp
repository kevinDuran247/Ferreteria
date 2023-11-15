<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">
        <style>
            .principal {
                text-align: center;
            }

            table {
                margin: 0 auto;
            }


            .principal {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
            }
            .regresar {
                text-align: left;
                margin: 5px;
                padding: 5px
            }

        </style>
        <title>FACTURA</title>
    </head>
    <body>
        <%
            String[] ids = request.getParameterValues("ids");
            String[] nombresProductos = request.getParameterValues("nombresProductos");
            String[] precios = request.getParameterValues("precios");
            String[] cantidades = request.getParameterValues("cantidades");
            String[] existencias = request.getParameterValues("existencias");
            String[] subtotales = request.getParameterValues("subtotales");
            String total = request.getParameter("total");

            // Obtener la fecha y hora actual
            java.util.Date fechaHoraActual = new java.util.Date();
            java.text.SimpleDateFormat formatoFechaHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaHoraFormateada = formatoFechaHora.format(fechaHoraActual);
        %>
        <div class="regresar">
            <a href="vendedorServ?accion=vaciar&indice=0" class="btn btn-warning"><h4><-REGRESAR</h4></a>
        </div>

        <div class="principal">
  
            <div id="invoice">
                <h2>Factura:</h2>
                <p><%= fechaHoraFormateada%></p>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Producto</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (ids != null && ids.length > 0) {
                                for (int i = 0; i < ids.length; i++) {
                        %>
                        <tr>
                            <td><%= ids[i]%></td>
                            <td><%= nombresProductos[i]%></td>
                            <td>$<%= precios[i]%></td>
                            <td><%= cantidades[i]%></td>
                            <td>$<%= subtotales[i]%></td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>

                <p><strong>Total:</strong> $<%= total%></p>
            </div>

            <button id="generarPDF">Generar factura PDF</button>

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

        </div>


    </body>

</html>