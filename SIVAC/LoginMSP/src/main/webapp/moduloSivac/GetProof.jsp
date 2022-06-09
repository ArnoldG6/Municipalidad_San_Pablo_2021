<%-- 
    Document   : GetProof
    Created on : 22 oct. 2021, 16:42:05
    Author     : f_var
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recibo de incapacidad por el jefe inmediato</title>
        <link rel="stylesheet" href="../css/getProof.css">
        <link rel="stylesheet" href="../css/default_1.css">
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
                <h1>Recibo de comprobante de incapacidad</h1>
            </header>
            <form class='proof' method="POST" action = "../AcceptLaboral">
                <table class="form"  >
                    <%
                        String numSolicitud = request.getSession(true).getAttribute("solicitud").toString();
                        service.Service s = new service.Service();
                        out.println(s.getBossLaboral(Integer.parseInt(numSolicitud)));
                    %>

                    <td colspan="5" class="boton">
                        <button type="submit" class="boton">Listo</button>
                    </td>
                </table>
            </form>
        </div>
        <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>