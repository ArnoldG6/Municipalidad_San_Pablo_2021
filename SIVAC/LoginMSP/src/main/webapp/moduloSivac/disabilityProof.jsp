<%-- 
    Document   : disabilityProof
    Created on : 29 sept. 2021, 17:26:32
    Author     : f_var
--%>


<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Envío de Incapacidad</title>
        <link rel="stylesheet" href="../css/disabilityProof.css">
        <link rel="stylesheet" href="../css/default_1.css">
    </head>
    
    <%
    service.Service service = new service.Service();
    String menu ;
    try {
        menu = service.userAccess(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
    } catch(NullPointerException | NumberFormatException ex) {  
        menu = "../error";
    } catch (ProcessingException ex) {
        menu = "../errorServidor";
    }
    %>
    
    <jsp:include page='<%= "includes/" + menu + ".jsp" %>' flush="true"/>
    <body>
        <div id="wrapper">
            <header>
                <h1>Envío de comprobante de incapacidad</h1>
            </header>
            <form class='disability' method="POST" action = "../disabilityProof" enctype="multipart/form-data">
                <table class="form"  >
                    <!--tr>
                        <td class="etiqueta">Cédula:</td>
                        <td class="campo"><input type="text" name="ced" id="ced"></td>
                    </tr-->
                    <%
                    out.println(service.disabilityProofAccess(Integer.parseInt(request.getSession(true).getAttribute("id").toString())));
                    %>
                    
                    <tr>
                        <td class="etiqueta">Adjuntar comprobante:</td>
                        <td class="campo"><input type="file" class="form-control-file" name="fileBrowser"></td>
                    </tr>

                    <td colspan="1" class="boton">
                        <button type="submit" class="boton">Enviar</button>
                    </td>


                    <td colspan="1" class="boton1">
                        <button type="reset" class="boton1"><a href="MainScreen.jsp" style="color:white; text-decoration: none;">Cancelar</a></button>
                    </td>
                </table>
            </form>
        </div>
        <div class="main">
        </div>
        <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>
