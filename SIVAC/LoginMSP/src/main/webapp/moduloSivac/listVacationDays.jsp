<%-- 
    Document   : listVacationDays
    Created on : 12/05/2022, 03:22:17 PM
    Author     : jegon
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/listUser.css" rel="stylesheet" type="text/css"/>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">     

        <title>Lista de Días de Vacaciones</title>
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
            <div class="container">
                <div class="card">
                    <div class="card-header2">
                        <h3 style="text-align:center" >Lista de días de vacaciones</h3>
                    </div>
                    <div class="card-body">

                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th style="padding-left: 72px;">Cédula</th>
                                    <th style="padding-left: 65px;">Nombre</th>
                                    <th style="padding-left: 65px;">Apellido</th>
                                    <th style="padding-left: 35px;">Días Solicitados</th>
                                    <th style="padding-left: 72px;">Estado</th>
                                    <th style="padding-left: 10px;">Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                                //${c.licensesCGS} en caso de que no funcione
                                String numSolicitud = request.getSession(true).getAttribute("solicitud").toString();
                                out.println(service.getVacationDays(Integer.parseInt(numSolicitud)));
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>            
        </div>
    </body>
    <jsp:include page="footerSivac.jsp" flush="true"/>
</html>
