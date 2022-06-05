<%-- 
    Document   : DenegateSGS
    Created on : 29/10/2021, 11:29:43 AM
    Author     : jegon
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="../images/logo.ico"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link href="../css/denegateSGS.css" rel="stylesheet" type="text/css"/>
        <title>Enviar Correo</title>
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
        <h2 class="titulo"> Mensaje de rechazo de Solicitud sin Goce de Salario</h2>
        <table class="form">
            <%
                String numSolicititudSGS = request.getSession(true).getAttribute("numSolicitud").toString();
                out.println(service.sendEmailLicenseSGS(Integer.parseInt(numSolicititudSGS)));
            %>
        </table>
        <div class="main">
        </div>
        <jsp:include page="footerSivac.jsp"  flush="true"/>
    </body>
</html>
