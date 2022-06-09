<%-- 
    Document   : listCGS
    Created on : 27/10/2021, 08:18:30 AM
    Author     : Greivin
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page import="java.util.List"%>
<%@page import="model.AdminFile"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">     
        <title>Lista de licencias CGS</title>
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

    <jsp:include page='<%= "includes/" + menu + ".jsp"%>' flush="true" />
    <body>
        <div id="wrapper">
            <div class="container">
                <div class="card">
                    <div class="card-header2">
                        <h3 style="text-align:center" >Lista de licencias con Goce Salarial</h3>
                    </div>
                    <div class="card-body">

                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th style="padding-left: 72px;">Cédula</th>
                                    <th style="padding-left: 65px;">Nombre</th>
                                    <th style="padding-left: 60px;">Apellidos</th>
                                    <th style="padding-left: 26px;">Número solicitud</th>
                                    <th style="padding-left: 72px;">Estado</th>
                                    <th style="padding-left: 40px;">Acción</th>
                                </tr>
                            </thead>
                            <%
                                //${c.licensesCGS} en caso de que no funcione
                                out.println(service.getLicensesCGS(Integer.parseInt(request.getSession(true).getAttribute("id").toString())));
                            %>

                        </table>
                    </div>
                </div>
            </div>            
        </div>
        <jsp:include page="footerSivac.jsp" flush="true" />
    </body>
</html>
