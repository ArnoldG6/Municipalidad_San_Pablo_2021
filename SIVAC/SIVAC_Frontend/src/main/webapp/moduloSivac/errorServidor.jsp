<%-- 
    Document   : errorServidor
    Created on : 24/01/2022, 03:52:24 PM
    Author     : Greivin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Servidor</title>
        <link href="../css/default_1.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <link rel=”stylesheet” type=”text/css” href=”toastr/toastr.css”>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script type=”text/javascript” src=”jquery.min.js”></script>
        <script type=”text/javascript” src=”toastr/toastr.js”></script>
        
    </head>
    <body>
        
        <script type="text/JavaScript"> 
            toastr["error"]("El servidor se cayó, contactar al área de informática","Error",{"progressBar": true,
                "positionClass": "toast-top-center","timeOut": "4500"});
            (function() {    
            var dialog = document.getElementById('myFirstDialog');
            setTimeout(function(){
                window.location.href = "../Pagina1.jsp"; // La URL que también será redirigida.
            }, 5000);
            })();   
        </script> 
    </body>
</html>
