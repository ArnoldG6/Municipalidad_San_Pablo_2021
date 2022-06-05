<%-- 
    Document   : success
    Created on : 24/01/2022, 05:11:57 PM
    Author     : Greivin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <link rel=”stylesheet” type=”text/css” href=”toastr/toastr.css”>
        <title>Mensaje de exito</title>
    </head>
    <body>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script type=”text/javascript” src=”jquery.min.js”></script>
        <script type=”text/javascript” src=”toastr/toastr.js”></script>
        <input id="comeback" value = "<%out.println(request.getSession(true).getAttribute("redireccionamiento").toString());%>" type="text"
                           style="background-color:white;color:white;border:1px dotted white;" readonly="readonly"/>
        <script>
            toastr["success"]("Datos ingresados con éxito","Éxito",{"progressBar": true,
                "positionClass": "toast-top-center","timeOut": "4500"});
            (function () {
                var redirect = document.getElementById('comeback');
                setTimeout(function () {
                    window.location.href = redirect.value;; // La URL que también será redirigida.
                }, 5000);
            })();
        </script>
    </body>
</html>
