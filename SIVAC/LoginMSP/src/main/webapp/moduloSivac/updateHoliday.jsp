<%-- 
    Document   : updateHoliday
    Created on : 10/05/2022, 11:26:17 AM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualización de días feriados</title>
        <link href="../css/user.css" rel="stylesheet" type="text/css"/>
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
        <div id="wrapper">
            <header>
                <h1 >Actualización de datos</h1>
            </header>
            <form class='user' action="../UpdateHoliday" method="POST">

                <table class="form">
                    <%
                        String numSolicitud = request.getSession(true).getAttribute("solicitud").toString();
                        service.Service s = new service.Service();
                        out.println(s.getInfoActHoliday(Integer.parseInt(numSolicitud)));
                    %>
                </table>
            </form>
        </div>
        <jsp:include page="footerSivac.jsp"  flush="true"/>
    </body>
</html>
