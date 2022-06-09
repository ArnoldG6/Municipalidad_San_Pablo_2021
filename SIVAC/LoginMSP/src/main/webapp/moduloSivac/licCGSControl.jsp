<%-- 
    Document   : licCGSControl
    Created on : 30/09/2021, 04:27:46 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitud de Permisos CGS</title>
        <link href="../css/licCGSControl.css" rel="stylesheet" type="text/css"/>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
    </head>

    <%
        service.Service service = new service.Service();
        String menu;
        try {
            menu = service.userAccess(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
        } catch (ProcessingException p) {
            menu = "../errorServidor";
        } catch (NullPointerException | NumberFormatException ex) {
            menu = "../error";
        }
    %>

    <jsp:include page='<%= "includes/" + menu + ".jsp"%>' flush="true" />
    <body>

        <div id="wrapper">
            <header>
                <h1 >Solicitud de permisos con goce salarial</h1>
            </header>

            <form class='CGS' action="../CGS" method="POST" enctype="multipart/form-data">
                <table class="form" >
                    <!--tr>
                        <td class="etiqueta">Identificación&nbsp;</td>
                        <td class="campo"> 
                            <input name="cedula" id="cedula"/>
                        </td>
                    </tr-->

                    <%
                        out.println(service.licenseAccess(Integer.parseInt(request.getSession(true).getAttribute("id").toString())));
                    %>
                    <tr>
                        <td class="etiqueta">Categoría&nbsp;</td>
                        <td class="campo">  
                            <select id = "categoriaCGS" name = "categoriaCGS">
                                <option>
                                    Licencia de paternidad
                                </option>
                                <option>
                                    Licencia de maternidad
                                </option>
                                <option>
                                    Muerte de un abuelo(a)
                                </option>
                                <option>
                                    Muerte del cónyuge, padre, hijo o hermano
                                </option>
                                <option>
                                    Enfermedad del cónyuge, padre, hijo o hermano
                                </option>
                                <option>
                                    Matrimonio
                                </option>
                                <option>
                                    Cita judicial
                                </option>
                                <option>
                                    Renovación de licencia de conducción
                                </option>
                                <option>
                                    Actividad sindical
                                </option>
                                <option>
                                    Invitaciones de Gobierno 
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Fecha inicio&nbsp;</td>
                        <td class="campo"> 
                               <input type="date" name="start_Date" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd")
                                       .format(java.util.Calendar.getInstance().getTime())%>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Fecha final&nbsp;</td>
                        <td class="campo"> 
                               <input type="date" name="final_Date" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd")
                                       .format(java.util.Calendar.getInstance().getTime())%>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Justificación&nbsp;</td>
                        <td class="estilo">
                            <textarea class=estilotextarea id="justification" name="justification" style="resize: none" 
                                      onKeyDown="valida_longitud()" onKeyUp="valida_longitud()"></textarea>
                        </td>
                    <script>
                        contenido_textarea = ""
                        num_caracteres_permitidos = 200

                        function valida_longitud() {
                            num_caracteres = document.forms[0].justification.value.length

                            if (num_caracteres > num_caracteres_permitidos) {
                                document.forms[0].justification.value = contenido_textarea
                            } else {
                                contenido_textarea = document.forms[0].justification.value
                            }

                            if (num_caracteres >= num_caracteres_permitidos) {
                                document.forms[0].caracteres.style.color = "#ff0000";
                            } else {
                                document.forms[0].caracteres.style.color = "#000000";
                            }

                            cuenta()
                        }
                        function cuenta() {
                            document.forms[0].caracteres.value = document.forms[0].justification.value.length
                        }
                    </script>
                    </tr>
                    <tr>
                        <td class="etiqueta">Adjuntar comprobante&nbsp;</td>
                        <td class="campo"> 
                            <input type="file" name="archivo"/>
                        </td>
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
        <jsp:include page="footerSivac.jsp" flush="true" />
    </body>
</html>