<%@page import="modelos.Admin"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Calendar"%>
<%@page import="modelos.Ventas"%>
<%@page import="modelos.Productos"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>GRAFICOS</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="scriptMenuAdmin.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            .grafico {
                width: 200px; /* Tamaño deseado en píxeles */
                height: 150px; /* Tamaño deseado en píxeles */
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

            <main>
                <br>
               <button id="generarPDF" class="btn btn-danger" style="float: right;">Generar reporte PDF</button>


                <div class="container" id="invoice">
                    <div >
                        <center><div style="width: 600px; height: 600px;">
                                <br>
                                <h3><center>Productos con muchas existencias</center></h3>
                                <canvas id="grafico1"></canvas>
                            </div></center>
                        <div >
                            <br><br><br><br><br>
                            <h3><center>Suma de ventas por mes 2023</center></h3>
                            <canvas id="grafico2" width="400" height="200"></canvas>
                        </div>

                    </div>



                <%
                    // Realizar la consulta JPA en la página JSP
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ferreteriaPU");
                    EntityManager em = emf.createEntityManager();

                    List<Productos> productos = em.createQuery("SELECT p FROM Productos p", Productos.class).getResultList();
                    List<Ventas> ventas2023 = em.createQuery("SELECT v FROM Ventas v WHERE FUNC('MONTH', v.fecha) BETWEEN 1 AND 12 AND FUNC('YEAR', v.fecha) = 2023", Ventas.class).getResultList();

                    em.close();
                    emf.close();

                    // Inicializar un array para almacenar el total vendido por mes (inicialmente todos en 0)
                    double[] totalPorMes = new double[12];

                    // Calcular el total vendido por mes
                    for (Ventas venta : ventas2023) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(venta.getFecha());
                        int mes = cal.get(Calendar.MONTH);
                        totalPorMes[mes] += venta.getTotal();
                    }
                %>

                <script>
                    var labels = [];
                    var data = [];

                    // Procesar los datos consultados y almacenarlos en las variables JavaScript
                    <%
                        for (Productos producto : productos) {
                    %>
                    labels.push('<%= producto.getNombre()%>');
                    data.push('<%= producto.getStock()%>');
                    <%
                        }
                    %>

                    var ctx = document.getElementById('grafico1').getContext('2d');
                    var myChart = new Chart(ctx, {
                        type: 'pie',
                        data: {
                            labels: labels,
                            datasets: [{
                                    label: 'Stock disponible',
                                    data: data,
                                    backgroundColor: [
                                        'rgba(255, 99, 132, 0.2)',
                                        'rgba(54, 162, 235, 0.2)',
                                        'rgba(255, 206, 86, 0.2)',
                                        'rgba(75, 192, 192, 0.2)',
                                        'rgba(153, 102, 255, 0.2)',
                                        'rgba(255, 159, 64, 0.2)'
                                    ],
                                    borderColor: [
                                        'rgba(255, 99, 132, 1)',
                                        'rgba(54, 162, 235, 1)',
                                        'rgba(255, 206, 86, 1)',
                                        'rgba(75, 192, 192, 1)',
                                        'rgba(153, 102, 255, 1)',
                                        'rgba(255, 159, 64, 1)'
                                    ],
                                    borderWidth: 1
                                }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                </script>
                <script>
                    var meses = [
                        'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                        'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
                    ];

                    var totalVentasPorMes = <%= Arrays.toString(totalPorMes)%>;

                    var ctx = document.getElementById('grafico2').getContext('2d');
                    var myChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: meses,
                            datasets: [{
                                    label: 'Total Vendido $',
                                    data: totalVentasPorMes,
                                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                    borderColor: 'rgba(75, 192, 192, 1)',
                                    borderWidth: 1
                                }]
                        },
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });
                </script>
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
                                doc.save('Estadisticos2023.pdf');
                            }
                        });
                    });
                </script>
            </div>              
        </main>

    </body>
</html>
