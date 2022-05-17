<%-- 
    Document   : UserProfile
    Created on : 21/02/2022, 10:41:19 PM
    Author     : Gabriela Gutiérrez
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Perfil</title>    
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

    <jsp:include page='<%= "includes/" + menu + ".jsp"%>' />

    <body>
        <section class="section about-section gray-bg" id="about">
            <div class="container">
                <div class="row align-items-center flex-row-reverse">
                    <div class="col-lg-6">
                        <div class="about-text go-to">
                            <h3 class="dark-color">Detalles de usuario</h3>
                            <br>
                            <h6 class="theme-color lead">
                                <p>
                                    <%

                                        model.AdminFile file = service.
                                                searchFileID(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
                                        out.println(file.getName() + " " + file.getLastname_1() + " " + file.getLastname_2());
                                    %>
                                </p>
                            </h6>
                            <div class="row about-list">

                                <div class="col-md-6">
                                    <div class="media">
                                        <label>Cédula</label>
                                        <p>
                                            <%
                                                out.println(file.getID());
                                            %>
                                        </p>
                                    </div>
                                    <div class="media">
                                        <label>Teléfono</label>
                                        <p>
                                            <%
                                                out.println(file.getPhone());
                                            %>
                                        </p>
                                    </div>
                                    <div class="media">
                                        <label>Departamento</label>
                                        <p>
                                            <%
                                                out.println(file.getAreaMuni());
                                            %>
                                        </p>
                                    </div>
                                    <div class="media">
                                        <label>Puesto</label>
                                        <p>
                                            <%
                                                out.println(file.getEmployment());
                                            %>
                                        </p>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="media">
                                        <label>Correo Institucional</label>
                                        <p>
                                            <%
                                                out.println(file.getEmail());
                                            %>
                                        </p>
                                    </div>
                                    <div class="media">
                                        <label>Fecha de Ingreso</label>
                                        <p>
                                            <%
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(file.getAdmission_Date());
                                                String fechaInicio = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                                                out.println(fechaInicio);
                                            %>
                                        </p>
                                    </div>
                                    <div class="media">
                                        <label>Años Laborados</label>
                                        <p>
                                            <%
                                                Calendar fechaActual = Calendar.getInstance();
                                                int annio = fechaActual.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
                                                out.println(annio);
                                            %>
                                        </p>
                                    </div>

                                    <div class="form-control-file">
                                        <form method="POST">
                                            <button class="form-control-file"
                                                    type="submit" formaction="updateDataUser.jsp"
                                                    >Actualizar</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="about-avatar">
                            <img src="../images/user-profile.png" title="" alt="">
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>
    <jsp:include page="footerSivac.jsp" flush ="true"/>
</body>
</html>