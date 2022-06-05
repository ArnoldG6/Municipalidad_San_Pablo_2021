<%-- 
    Document   : AcceptBossVacation
    Created on : 31/10/2021, 06:09:22 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link href="../css/acceptBossVacation.css" rel="stylesheet" type="text/css"/>
        <title>Aceptar o denegar vacaciones</title>
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
                <h1>Solicitud de vacación</h1>
            </header>
            <form class="VAC" action="../AcceptVacation" method="POST">
                <table class="form">

                    <%
                        String numSolicitud = request.getSession(true).getAttribute("solicitud").toString();
                        service.Service s = new service.Service();
                        out.println(s.getBossVacation(Integer.parseInt(numSolicitud)));
                    %>

                    <td colspan="1" class="boton">
                        <button type="submit" class="boton">Aceptar</button>
                    </td>

                </table>
            </form>
        </div>
        <jsp:include page="footerSivac.jsp" />
    </body>
</html>
