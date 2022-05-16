<%-- 
    Document   : userRegister
    Created on : 28/09/2021, 03:32:54 PM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro de Funcionarios</title>
        <link href="../css/user.css" rel="stylesheet" type="text/css"/>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <script src="../js/Varios.js" type="text/javascript"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
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
            <header>
                <h1 >Registrar funcionarios</h1>
            </header>
            <form class='user' action="../AdminServ" method="POST">
                <table class="form">
                    <tr>
                        <td class="etiqueta">Nombre&nbsp;</td>
                        <td class="campo">
                            <input name="name" id="name"/> 
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Primer apellido&nbsp;</td>
                        <td class="campo"> 
                            <input name="lastname_1"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Segundo apellido&nbsp;</td>
                        <td class="campo"> 
                            <input name="lastname_2"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Cédula&nbsp;</td>
                        <td class="campo">  
                            <input  name="ID" type="text" pattern="[0-9]{9,}" title="Por favor escriba la cédula sin letras">
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Área de trabajo&nbsp;</td>
                        <td class="campo">  
                            <select id="area" name="area">
                                <%
                                    out.println(service.returnAreas());
                                %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Puesto&nbsp;</td>
                        <td class="campo"> 
                            <input name="employment"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Fecha de ingreso&nbsp;</td>
                        <td class="campo"> 
                               <input type="date" name="admission_Date" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd")
                                    .format(java.util.Calendar.getInstance().getTime())%>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Teléfono&nbsp;</td>
                        <td class="campo"> 
                            <input name="phone" type="tel" pattern="[0-9]{8}" title="Por favor escriba el teléfono sin letras"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Correo institucional&nbsp;</td>
                        <td class="campo"> 
                            <input name="email" type="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Nivel Jerárquico&nbsp;</td>
                        <td class="campo">  
                            <select id = "Role_id" name = "Role_id">
                                <option value="1">Alcalde</option>
                                <option value="2">Director</option>
                                <option value="3">Jefe de Departamento</option>
                                <option value="4">Encargado de Unidad</option>
                                <option value="5">Funcionario</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Anualidad&nbsp;</td>
                        <td class="campo"> 
                            <input name="public_years"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Salario&nbsp;</td>
                        <td class="campo"> 
                            <input name="salary"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="etiqueta">Vacaciones&nbsp;</td>
                        <td class="campo"> 
                            <input name="holidays"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="1" class="boton">
                            <button type="submit" class="boton">Enviar</button>
                        </td>
                        <td colspan="1" class="boton1">
                            <button type="reset" class="boton1"><a href="MainScreen.jsp" style="color:white; text-decoration: none;">Cancelar</a></button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <jsp:include page="footerSivac.jsp" flush="true"/>
    </body>
</html>