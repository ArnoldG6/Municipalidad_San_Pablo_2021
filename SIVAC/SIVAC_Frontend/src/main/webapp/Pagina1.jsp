

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Módulos</title>
        <link href="css/modulo.css" rel="stylesheet" type="text/css"/>
    </head>
    <jsp:directive.include file="headerModulo.jsp" />
    <body>
        <section id="section1">

            <nav>
                <h2>Bienvenido</h2>
                <%
                    common.model.User user = (common.model.User)request.getSession(true).getAttribute("user");
                    request.getSession(true).setAttribute("id", user.getIdUser());
                %>
                    <ol>
                        <p><button style="border:1px solid black; background-color:#c10707"><a style = "color: white; text-decoration:none;" href="moduloSivac/MainScreen.jsp">SIVAC</a></button></p>
                        <p><button style="border:1px solid black; background-color:#c10707"><a style = "color: white; text-decoration:none;" href="#">Modulo 2</a></button></p>
                        <p><button style="border:1px solid black; background-color:#c10707"><a style = "color: white; text-decoration:none;" href="#">Modulo 3</a></button></p>
                        <p><button style="border:1px solid black; background-color:#c10707"><a style = "color: white; text-decoration:none;" href="#">Modulo 4</a></button></p>
                        <p><button style="border:1px solid black; background-color:#c10707"><a style = "color: white; text-decoration:none;" href="#">Modulo 5</a></button></p>
                    </ol>
                
            </nav>

        </section>
        <section id="section2"></section>

    </body>
    <jsp:directive.include file="footerModulo.jsp" />
</html>
