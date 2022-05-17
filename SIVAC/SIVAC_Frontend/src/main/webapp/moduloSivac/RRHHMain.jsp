<%-- 
    Document   : RRHHMain
    Created on : 03/01/2022, 03:26:58 PM
    Author     : jegon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="../images/logo.ico"/>
        <jsp:directive.include file="headerSivac.jsp" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="image/x-icon" href="../images/logo.jpg" />
        <link href="../css/menu.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> <!-- incluye jquery para usar ajax -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/push.js/1.0.4/push.min.js"></script>
    </head>
    <body>

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <a class="nav-link" href="MainScreen.jsp" id="navbarDropdown" role="button" aria-expanded="false" style="color: white;">
                            <img src="../images/home.png" alt="Inicio" title="Inicio" width="30" height="30"/></a>
                        </a>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: white;">
                                Vacaciones
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="requestVacation.jsp">Solicitud de vacaciones</a></li>
                                <li><a class="dropdown-item" href="pendingVacation.jsp">Lista de vacaciones</a></li>
                                <li><a class="dropdown-item" href="VacationAnswers.jsp">Estado de solicitud de vacaciones</a></li>
                                <li><a class="dropdown-item" href="holidaysRegister.jsp">Registrar días feriados</a></li>
                                <li><a class="dropdown-item" href="listHolidays.jsp">Listar días feriados</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: white;">
                                Permisos
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="licCGSControl.jsp"> Agregar licencia con goce salarial</a></li>
                                <li><a class="dropdown-item" href="licSGSControl.jsp">Agregar licencia sin goce salarial</a></li>
                                <li><a class= "dropdown-item" href="listCGS.jsp">Lista de licencias con goce salarial</a></li>
                                <li><a class= "dropdown-item" href="listSGS.jsp">Lista de licencias sin goce salarial</a></li>
                                <li><a class= "dropdown-item" href="licCGSResponse.jsp">Estado de permisos con goce salarial</a></li>
                                <li><a class= "dropdown-item" href="licSGSResponse.jsp">Estado de permisos sin goce salarial</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: white;">
                                Incapacidades
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="disabilityProof.jsp">Envío de comprobante</a></li>
                                <li><a class="dropdown-item" href="listDP.jsp">Lista de incapacidades Jefe inmediato</a></li>
                                <li><a class="dropdown-item" href="listDPRH.jsp">Lista de incapacidades Recursos Humanos</a></li>
                                <li><a class="dropdown-item" href="listDPEM.jsp">Ver Incapacidades</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: white;">
                                Usuarios
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="userRegister.jsp">Registrar funcionario</a></li>
                                <li><a class="dropdown-item" href="listUser.jsp">Lista de funcionarios</a></li>
                            </ul>
                        </li>  
                    </ul>
                    <a href="UserProfile.jsp">
                        <img src="../images/user.png" alt="Perfil" title="Perfil" width="32" height="32" HSPACE="10"/></a>
                    <a> Bienvenid@ 
                        <%
                            service.Service service = new service.Service();
                            model.AdminFile file = service.
                                    searchFileID(Integer.parseInt(request.getSession(true).getAttribute("id").toString()));
                            out.println(file.getName() + " " + file.getLastname_1());
                        %>
                        <input type="text" name="ced" id="ced" readonly="readonly"value="<%
                            out.println(request.getSession(true).getAttribute("id").toString());
                               %>" size="10" style="text-align:center; background-color: transparent;outline: none; color: white;
                               border: none;"/>
                        <a style="text-decoration:none; color: white;" href="../Pagina1.jsp";">Salir</a>
                    </a>
                </div>
            </div>
        </nav>
        <br>

        <script>
            function updateNotifications() {
                var ced = document.getElementById("ced").value;
                $.get('http://localhost/Hello/Connection.php', {id_Employee: ced}, // url
                        function (data, textStatus, jqXHR) {  // success callback
                            //alert('status: ' + textStatus + ', data:' + data);
                            var json = $.parseJSON(data);

                            $.each(json, function (i, item) {
                                var descrip = item.Descripcion;
                                var transmitter = item.Emisor;
                                var receiver = item.Receptor;
                                Push.create("Notificación de funcionario: " + transmitter
                                        + " Para: " + receiver, {
                                            body: descrip,
                                            icon: '../images/campana.ico',
                                            onClick: function () { //al hacer click en la notificación se cerrará
                                                window.focus();
                                                this.close();
                                            }

                                        });
                            });

                        });
            }
            setInterval(updateNotifications, 5000);
        </script>

    </body>
</html>
