
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de Sesión</title>
        <link href="css/default.css" rel="stylesheet" type="text/css"/>
    </head>
    <jsp:directive.include file="header.jsp" />
    <body background-color: black>

        <form action="LoginService" method="post">
            <div class="imgcontainer">
                <img src="css/image/user.jpg" alt="Avatar" class="avatar" width="250" height="150">
                
            </div>

            <div class="container">
                <label for="username"><p style=" text-align: center "><b>Usuario</b></p></label>
                <input id = "username" type="text" placeholder="Correo Institucional" name="username" 
                       style="display: block; margin:  auto; text-align:center; width:396px; height:30px; color:black;">

                <label  for="pwd"><p style=" text-align: center"><b>Contraseña</b></p></label>
                <input id = "pwd" type="password" placeholder="Contraseña" name="pwd" required 
                       style="display: block; margin:  auto; text-align:center; width:400px; height:30px; color:black;">
                <label style="justify-content:center; display:flex; margin:20px; ">
                    <input type="checkbox" checked="checked" name="remember" style="margin:0px -30px 0px -30px; "> Recordar
                </label>
                <p></p>

                <button style="display: block; margin: 0 auto; width:200px; height:40px">Iniciar Sesión</button>

            </div>

            <div class="container" style="background-color:#f1f1f1">
                <!--button type="button" class="cancelbtn">Cancel</button-->
                <span class="psw">Olvidó su <a href="#">contraseña?</a></span>
            </div>
        </form>
        <jsp:directive.include file="footer.jsp" />
    </body>


</html>
