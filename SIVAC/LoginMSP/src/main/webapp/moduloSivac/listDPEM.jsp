<%-- 
    Document   : listDPEM
    Created on : 21 feb. 2022, 18:49:06
    Author     : f_var
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">     
        <title>Lista de incapacidades</title>
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
        <jsp:useBean id="c" class="service.Service" scope="application">
        </jsp:useBean>
        <div id="wrapper">
            <br>
            <div class="container">
                <div class="card1">
                    <div class="card-header2">
                        <h3 style="text-align:center" >Lista de Incapacidades</h3>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th style="padding-left: 75px;">Cédula</th>
                                    <th style="padding-left: 65px;">Nombre</th>
                                    <th style="padding-left: 58px;">Apellidos</th>
                                    <th style="padding-left: 26px;">Número solicitud</th>
                                    <th style="padding-left: 0px;">Acción</th>
                                </tr>
                            </thead>
                            <%
                                //${c.laboral} en caso de emergencia
                                out.println(service.getLaboralEmployee(Integer.parseInt(request.getSession(true).getAttribute("id").toString())));
                            %>
                        </table>
                    </div>
                </div>
            </div>            
        </div>
        <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>

