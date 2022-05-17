<%-- 
    Document   : listUser
    Created on : 18/10/2021, 04:42:02 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page import="java.util.List"%>
<%@page import="model.AdminFile"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">     
        <title>Lista de Funcionarios</title>
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
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <header>
                    <h1 >Ingresar la cédula del funcionario que desea consultar</h1>
                </header>
                <form class='list' action="" method="POST">
                    <table class="form">
                        <tr>
                            <td class="etiqueta">Cédula&nbsp;</td>
                            <td class="campo">
                                <input name="ID" id="ID"/> 
                            </td>
                            <td colspan="1" class="boton2">
                                <button type="submit" class="boton2" style='width:50px; height:40px'><img src="../images/search.png" alt="Buscar" title="Buscar"/></button>
                            </td>
                        </tr>
                    </table>
                </form>            
                <br>
                <div class="container">
                    <div class="card">
                        <div class="card-header2">
                            <h3 style="text-align:center" >Lista de Funcionarios</h3>
                        </div>
                        <div class="card-body">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th style="padding-left: 40px;">Cédula</th>
                                        <th style="padding-left: 40px;">Nombre</th>
                                        <th style="padding-left: 40px;">Apellidos</th>
                                        <th style="padding-left: 40px;">&nbsp;Área de trabajo</th>
                                        <th style="padding-left: 40px;">Puesto</th>
                                        <th style="padding-left: 40px;">Email</th>
                                        <th style="padding-left: 40px;">Teléfono</th>
                                        <th style="padding-left: 40px;">Acciones</th>
                                    </tr>
                                </thead>
                                <%
                                    String cedula = (request.getParameter("ID") != null)
                                            ? request.getParameter("ID") : null;
                                    service.Service ser = new service.Service();
                                    out.println(ser.getAdminFiles(cedula));
                                %>
                            </table>
                        </div>
                    </div>
                </div>            
            </div>
        </div>
        <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>