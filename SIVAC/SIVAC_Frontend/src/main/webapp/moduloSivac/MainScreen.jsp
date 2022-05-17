<%-- 
    Document   : MainScreen
    Created on : 03/01/2022, 03:46:08 PM
    Author     : jegon
--%>

<%@page import="javax.ws.rs.ProcessingException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página Principal</title>
    </head>
    <!--jsp:directive.include file="BossMain.jsp" /-->
    <!--%@include file="BossMain.jsp" %-->

    <%
        common.model.User user = (common.model.User) request.getSession(true).getAttribute("user");
        request.getSession(true).setAttribute("id", user.getIdUser());

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
        <div id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="../images/portada.jpeg" class="d-block w-100" alt="..." width="500" height="500">
                </div>
                <div class="carousel-item">
                    <img src="../images/portada1.jpeg" class="d-block w-100" alt="..." width="500" height="500">
                </div>
                <div class="carousel-item">
                    <img src="../images/portada2.jpeg" class="d-block w-100" alt="..." width="500" height="500">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <br>
        <br>
        <div class="row row-cols-1 row-cols-md-2 g-4">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <b class="card-title" style="color:blue;">MISIÓN</b>
                        <br> <br>
                        <p class="card-text" style=”text-align: justify;”>Somos la Municipalidad de San Pablo de Heredia, persona jurídica estatal; 
                            mediante la cual, los munícipes del cantón promueven tanto como administran los intereses locales, construyen obra 
                            pública, gestionan y reciben servicios públicos, con el propósito de contribuir al desarrollo humano, económico, 
                            social, sustentable y al bienestar de las familias del cantón.</p>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <b class="card-title" style="color:blue;">VISIÓN</b>
                        <br> <br>
                        <p class="card-text" style=”text-align: justify;”>Ser la Municipalidad líder en la presentación eficiente de servicios y 
                            construcción de obra pública de la provincia de Heredia, que propicia un mayor desarrollo humano, económico, social, 
                            sustentable y bienestar de las familias del cantón; mediante su gestión administrativa, proactiva e innovadora en el 
                            uso de las tecnologías de información y comunicación.</p>
                    </div>
                </div>
            </div>
        </div>
        <footer id="footerMain">
            <p style="color: white">
                &copy; 2021 Municipalidad San Pablo de Heredia. Todos los derechos reservados
            </p>
        </footer>
    </body>
</html>
