<%-- 
    Document   : holidaysRegister
    Created on : 06/05/2022, 10:59:58 AM
    Author     : YENDRI
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro de días feriados</title>
        <link href="../css/user.css" rel="stylesheet" type="text/css"/>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
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
                <h1 >Registrar días feriados</h1>
            </header>
            <form class='user' action="../HolidaysRegist" method="POST">
                <table class="form">
                    <tr>
                        <td class="etiqueta">Día&nbsp;</td>
                        <td class="campo">  
                            <select  name="dia" >
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                                <option value="29">29</option>
                                <option value="30">30</option>
                                <option value="31">31</option>
                            </select>
                        </td>
                    </tr> 
                    <tr>
                        <td class="etiqueta">Mes&nbsp;</td>
                        <td class="campo">  
                            <select  name="mes" >
                                <option value="1">Enero</option>
                                <option value="2">Febrero</option>
                                <option value="3">Marzo</option>
                                <option value="4">Abril</option>
                                <option value="5">Mayo</option>
                                <option value="6">Junio</option>
                                <option value="7">Julio</option>
                                <option value="8">Agosto</option>
                                <option value="9">Septiembre</option>
                                <option value="10">Octubre</option>
                                <option value="11">Noviembre</option>
                                <option value="12">Diciembre</option>
                            </select>
                        </td>
                    </tr> 
                    
                    <tr>
                        <td class="etiqueta">Nombre&nbsp;</td>
                        <td class="campo">  
                            <input  name="nombre" type="text"/>
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