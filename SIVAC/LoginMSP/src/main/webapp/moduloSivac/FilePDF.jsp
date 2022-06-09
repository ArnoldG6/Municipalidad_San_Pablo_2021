<%-- 
    Document   : FilePDF
    Created on : 28/02/2022, 03:18:03 PM
    Author     : jegon
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Historial Expediente</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js">
        </script><script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
        <link href="../css/pdf.css" rel="stylesheet" type="text/css"/>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
    </head>

    <%
        service.Service service = new service.Service();
        String menu;
        try {
            menu = service.userAccess(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
        } catch (NullPointerException | NumberFormatException ex) {
            menu = "../error";
        } catch (ProcessingException e) {
            menu = "../errorServidor";
        };
    %>


    <jsp:include page='<%= "includes/" + menu + ".jsp"%>' flush="true"/>

    <body>
        <jsp:useBean id="c" class="service.Service" scope="application">
        </jsp:useBean>
        <form method="POST" action="../PDF">
            <div id="pdf1">
                <div id="pdf">
                    <h1>Expediente Digital </h1>
                    <h2>Datos Personales</h2>

                    <%
                        //service.Service service = new service.Service();
                        out.println(service.getFileEmployee(Integer.parseInt(request.getParameter("historial").toString())));
                    %>
                </div>
            </div>

            <button type="submit" class="boton" style="margin-left: 35%; margin-top: 5%">Visualizar para descargar</button>
            <button type="submit" formaction="listUser.jsp" class="boton1" >Volver</button>

            <script>
            var cedula = document.getElementById('ced').value;
            function downloadFile() {
                //var doc = new jsPDF(/*'p', 'pt', 'letter'*/);
                //var elementHTML = document.querySelector("#pdf").innerHTML;
                var elementHTML = document.getElementById('pdf');
                /*var specialElementsHandlers = {
                 '#noseparaqueestediv': function (element, renderer) {
                 return true;
                 }
                 };*/
                html2canvas(elementHTML, {
                    onrendered: function (canvas) {
                        var imgData = canvas.toDataURL('image/png');
                        var imgWidth = 210;
                        var pageHeight = 295;
                        var imgHeight = canvas.height * imgWidth / canvas.width;
                        var heightLeft = imgHeight;
                        var doc = new jsPDF('p', 'mm');
                        var position = 0;

                        doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
                        heightLeft -= pageHeight;

                        while (heightLeft >= 0) {
                            position = heightLeft - imgHeight;
                            doc.addPage();
                            doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
                            heightLeft -= pageHeight;
                        }
                        doc.save('Expediente.pdf');
                    }
                });
                /*doc.fromHTML(
                 elementHTML,
                 15,
                 15,
                 {
                 'width': 70,
                 'elementHandlers': specialElementsHandlers
                 }
                 );
                 doc.save('Expediente ' + cedula + '.pdf');*/
            }
            </script>
            <footer id="footerMain">
                <p style="color: white">
                    &copy; 2021 Municipalidad San Pablo de Heredia. Todos los derechos reservados
                </p>
            </footer>
        </form>
    </body>
</html>
