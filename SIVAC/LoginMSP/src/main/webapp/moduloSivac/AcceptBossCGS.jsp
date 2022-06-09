<%-- 
    Document   : AcceptBossCGS
    Created on : 21/10/2021, 08:52:28 PM
    Author     : Greivin
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link href="../css/acceptBossCGS.css" rel="stylesheet" type="text/css"/>
        <title>Aceptar o denegar licencia CGS</title>
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
                <h1>Solicitud de permiso con Goce Salarial</h1>
            </header>
            <form class="CGS" action="../AcceptCGS" method="POST">
                <table class="form">

                    <%
                        try {
                            String numSolicitud = request.getSession(true).getAttribute("solicitud").toString();
                            service.Service s = new service.Service();
                            out.println(s.getBossLicenseCGS(Integer.parseInt(numSolicitud)));
                        } catch (NullPointerException e) {

                        }
                    %>

                    <td colspan="1" class="boton">
                        <button type="submit" class="boton">Aceptar</button>
                    </td>

                </table>
            </form>
        </div>
        <jsp:include page="footerSivac.jsp" flush="true" />
    </body>
</html>
