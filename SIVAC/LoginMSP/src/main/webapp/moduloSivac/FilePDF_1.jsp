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
    
   

    <body>
       
        <div id="pdf1">
            <div id="pdf">
                <h1>Expediente Digital </h1>
                <h2>Datos Personales</h2>

                <%
                    service.Service service = new service.Service();
                    out.println(service.getFileEmployee(Integer.parseInt(request.getSession(true).getAttribute("visualizar").toString())));
                %>
            </div>
        </div>
            
            <button onclick="downloadFile()" class="boton" style="margin-left: 35%; margin-top: 5%">Descargar Expediente</button>
        <form><button type="submit" formaction="listUser.jsp" class="boton1" style="margin-left: 37%">Volver</button></form>

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
        
    </body>
</html>
