<%-- 
    Document   : requestVacation
    Created on : 28/09/2021, 05:24:24 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Solicitud de Vacaciones</title>
        <link href="../css/requestVacation.css" rel="stylesheet" type="text/css"/>
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

    <jsp:include page= '<%= "includes/" + menu + ".jsp"%>' flush="true" />
    <body>
        <div id="wrapper">
            <label>Días disponibles de vacaciones: <%out.println(service.
                        searchFileID(Integer.parseInt(request.getSession(true).getAttribute("id")
                        .toString())).getHolidays());%></label> </br>
                                <label>
                                    Permiso de vacaciones adelantadas: <%
                                    boolean permiso = service.
                        searchFileID(Integer.parseInt(request.getSession(true).getAttribute("id")
                                .toString())).getIsEarlyVacations();
                                    if (permiso) {
                                        out.println("Permitido");
                                    } else {
                                        out.println("No Permitido");
                                    }
                                    %> 
                                </label>
            <header>
                <h1 >Solicitud de vacaciones</h1>
            </header>
            <form class='vacation' action="../Vacation" method="POST">
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
                    <td colspan="1" class="boton">
                        <button type="submit" class="boton">Enviar</button>
                    </td>
                    <td colspan="1" class="boton1">
                        <button type="reset" class="boton1"><a href="MainScreen.jsp" style="color:white; text-decoration: none;">Cancelar</a></button>
                    </td>
                </table>
            </form>
        </div>
        <div class="main">
        </div>
        <jsp:include page="footerSivac.jsp" flush ="true" />
    </body>
</html>