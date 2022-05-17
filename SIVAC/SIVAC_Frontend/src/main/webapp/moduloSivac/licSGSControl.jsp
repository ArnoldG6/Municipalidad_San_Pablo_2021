<%-- 
    Document   : licSGSControl
    Created on : 30/09/2021, 02:54:12 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitud de Permisos sin Goce Salarial</title>
        <link href="../css/licSGSControl.css" rel="stylesheet" type="text/css"/>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
    </head>
    
    <%
    service.Service service = new service.Service();
    String menu;
    try {
        menu = service.userAccess(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
    } catch(ProcessingException p) {
        menu = "../errorServidor";
    } catch (NullPointerException | NumberFormatException ex) {
        menu = "../error";
    }
    %>
    
    <jsp:include page='<%= "includes/" + menu + ".jsp" %>' flush="true" />
    <body>

        <div id="wrapper">
            <header>
                <h1 >Solicitud de permisos sin goce salarial</h1>
            </header>

            <form class='SGS' action="../SGS" method="POST">
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
                            <select id = "categoriaSGS" name = "categoriaSGS">
                                <option>
                                    Asunto personal
                                </option>
                                <option>
                                    Asunto grave de familia
                                </option>
                                <option>
                                    Realización de estudio académico
                                </option>
                                <option>
                                    Actividad municipal
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
                            <textarea class=estilotextarea name="justification"></textarea>
                        </td>
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