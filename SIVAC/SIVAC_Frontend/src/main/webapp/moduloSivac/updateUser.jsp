
<%-- 
    Document   : updateUser
    Created on : 18/10/2021, 03:55:14 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Actualización de Funcionarios</title>
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
            <form class='user' action="../UpdateAdmin" method="POST">
                <table class="form">
                    <%
                        String numSolicitud = request.getSession(true).getAttribute("solicitud").toString();
                        service.Service s = new service.Service();
                        out.println(s.returnCedula(Integer.parseInt(numSolicitud)));
                    %>
                    <header>
                        <h1 >Cambiar el o los datos del usuario</h1>
                    </header>
                    <%
                        out.println(s.getInfoActUser(Integer.parseInt(numSolicitud)));
                    %>
                    <tr>
                        <td colspan="1" class="boton">
                            <button type="submit" class="boton">Enviar</button>
                        </td>
                        <td colspan="1" class="boton1">
                            <button type="reset" class="boton1"><a href="listUser.jsp" style="color:white; text-decoration: none;">Cancelar</a></button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <jsp:include page="footerSivac.jsp"  flush="true"/>
    </body>
</html>
