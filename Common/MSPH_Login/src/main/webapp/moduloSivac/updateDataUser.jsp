<%-- 
    Document   : updateDataUser
    Created on : 14/03/2022, 04:03:44 PM
    Author     : jegon
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitud de actualización de Datos</title>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link href="../css/user.css" rel="stylesheet" type="text/css"/>
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

    <jsp:include page= '<%= "includes/" + menu + ".jsp"%>' flush="true" />
    <body>
        <header>
                <h1>Solicitud de actualización de datos</h1>
            </header>
        <form class='user' action="../UpdateData">
            <div id="wrapper">
                <table class="form">
                    <tr>
                        <td class="etiqueta">Cédula&nbsp;</td>
                        <td>
                               <input type="text" id="cedula" name="cedula" value="<%out.println(
                                           request.getSession(true).getAttribute("id").toString());%>" readonly="readonly" 
                               style="background: transparent;border: none;" />
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Justificación&nbsp;</td>
                        <td>
                            <textarea id="texto" name="texto" rows="4" cols="40"
                                      style="color:black;text-align: left;"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td><button type="submit" id="send" name="send" class="boton">Enviar</button></td>
                        <td><button type="reset" id="cancel" name="cancel" class="boton1">Cancelar</button></td>
                </tr>
                </table>
            </div>
        </form>
      <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>
