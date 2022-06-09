<%-- 
    Document   : ActualizacionDatos
    Created on : 20/05/2022, 03:09:17 PM
    Author     : Greivin
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Actualización</title>
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
        <div id="wrapper">
            <div class="container">
                <div class="card1">
                    <div class="card-header2">
                        <h3 style="text-align:center">Lista de datos a Actualizar</h3>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th style="padding-left: 72px;">Cédula</th>
                                    <th style="padding-left: 120px;">Mensaje</th>
                                    <th style="padding-left: 30px;">Estado</th>
                                    <th style="padding-left: 10px;">Acción</th>
                                </tr>
                            </thead>
                            <%
                                out.println(service.datosActualizar());
                            %>
                        </table>
                    </div>
                </div>
            </div>            
        </div>
        <jsp:include page="footerSivac.jsp" flush="true" />
    </body>
</html>
