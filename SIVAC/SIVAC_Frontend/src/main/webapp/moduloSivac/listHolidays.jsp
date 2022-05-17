<%-- 
    Document   : listHolidays
    Created on : 06/05/2022, 01:51:36 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Vacaciones Pendientes</title>
        <link href="../css/listUser.css" rel="stylesheet" type="text/css"/>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
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
        <!--jsp:useBean id="c" class="service.Service" scope="application"-->
        <!--/jsp:useBean-->
        <div id="wrapper">
            <br>
            <div class="container">
                <div class="card1">
                    <div class="card-header2">
                        <h3 style="text-align:center" >Lista de días feriados</h3>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th style="padding-left: 40px;">Día</th>
                                    <th style="padding-left: 40px;">Mes</th>
                                    <th style="padding-left: 40px;">Nombre del Día Feriado</th>
                                    <th style="padding-left: 40px;">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                                
                                out.println(service.getHolidays());
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>   
        </div>
        <div class="main">
        </div>
        <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>