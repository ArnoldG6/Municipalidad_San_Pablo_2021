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
                            <textarea id="texto" name="texto" rows="4" cols="30" onKeyDown="valida_longitud()" onKeyUp="valida_longitud()"
                                      style="color:black;text-align: left; resize: none"></textarea>
                        </td>
                    <script>
                        contenido_textarea = ""
                        num_caracteres_permitidos = 200

                        function valida_longitud() {
                            num_caracteres = document.forms[0].texto.value.length

                            if (num_caracteres > num_caracteres_permitidos) {
                                document.forms[0].texto.value = contenido_textarea
                            } else {
                                contenido_textarea = document.forms[0].texto.value
                            }

                            if (num_caracteres >= num_caracteres_permitidos) {
                                document.forms[0].caracteres.style.color = "#ff0000";
                            } else {
                                document.forms[0].caracteres.style.color = "#000000";
                            }

                            cuenta()
                        }
                        function cuenta() {
                            document.forms[0].caracteres.value = document.forms[0].texto.value.length
                        }
                    </script>
                    </tr>
                    <tr>
                        <td><button type="submit" id="send" name="send" class="boton">Enviar</button></td>
                        <td><button type="reset" id="cancel" name="cancel" class="boton1" onclick="window.location.href='UserProfile.jsp'">Cancelar</button></td>
                    </tr>
                </table>
            </div>
        </form>
        <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>
