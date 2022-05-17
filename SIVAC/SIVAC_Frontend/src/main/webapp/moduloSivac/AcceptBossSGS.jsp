<%-- 
    Document   : AcceptSGS
    Created on : 21/10/2021, 05:24:03 PM
    Author     : jegon
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link href="../css/acceptSGS.css" rel="stylesheet" type="text/css"/>
        <title>Aceptar o denegar licencia SGS</title>
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
        <div>
            <header>
                <h1>Solicitud de permiso sin Goce Salarial</h1>
            </header>
            <form class="SGS" action="../AcceptSGS" method="POST">
                <table class="form">

                    <%
                        String numSolicitud = request.getSession(true).getAttribute("solicitud").toString();
                        service.Service s = new service.Service();
                        out.println(s.getBossLicenseSGS(Integer.parseInt(numSolicitud)));
                    %>

                    <td colspan="1" class="boton">

                        <button type="submit" class="boton">Enviar</button>
                    </td>
                </table>
            </form>
        </div>
    </body>
    <jsp:include page="footerSivac.jsp" flush="true"/>
</html>
