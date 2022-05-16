<%-- 
    Document   : VacationError
    Created on : 07/03/2022, 03:54:09 PM
    Author     : jegon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error en Vacaciones</title>
    </head>
    <body>
        <form>
            <div>
                <dialog id="myFirstDialog" style="width:50%;background-color:#FF0000;color:#ffffff;border:1px dotted black;" open>
                    <p>La fecha de inicio no puede ser mayor a la final o no cuenta con vacaciones disponibles</p>
                    <input id="comeback" value = "<%out.println(request.getSession(true).getAttribute("redireccionamiento").toString());%>" type="text"
                           style="background-color:#FF0000;color:#FF0000;border:1px dotted #FF0000;" readonly="readonly"/>
                </dialog >
            </div>
        </form>
        <script type="text/JavaScript">  
            (function() {    
            var dialog = document.getElementById('myFirstDialog');
            var redirect = document.getElementById('comeback');
            setTimeout(function(){
            window.location.href = redirect.value; // La URL que también será redirigida.
            }, 2500);
            })();   
        </script>
    </body>
</html>
